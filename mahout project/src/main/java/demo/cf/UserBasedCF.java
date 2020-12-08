package demo.cf;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class UserBasedCF {
    final static int NEIGHBORHOOD_NUM = 2;
    final static int RECOMMENDER_NUM = 3;

    public static void main(String[] args) throws Exception {
        String file = "D:\\download\\data\\ratingdata.txt";
        DataModel model = new FileDataModel(new File(file));

        UserSimilarity usersimilarity = new EuclideanDistanceSimilarity(model);
        
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, usersimilarity, model);
        
        Recommender r = new GenericUserBasedRecommender(model, neighbor, usersimilarity);
        
        List<RecommendedItem> recommendations = r.recommend(1, 2);
		for (RecommendedItem recommendation : recommendations) {
			System.out.println("" + recommendation.getItemID());
		}
		
		System.out.println("*******************************");
        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            System.out.printf("ID:%s  ", uid);            
            
            List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);
            for (RecommendedItem ritem : list) {
                System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
            }
            System.out.println();
        }
    }
}