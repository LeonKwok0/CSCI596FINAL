# Product recommendations based on user interests

the GitHub repository for the CSCI596 final project. We will learn and use spark to implement our project.

## Group Members

    Hongming Deng
    Teng Gao
    Daohang Song
    Liang Guo

## Spark

Spark is a fast, general-purpose computing engine designed for large-scale data processing. 

Spark is an open source parallel framework like Hadoop MapReduce developed by UC Berkeley AMP Lab (AMP Lab of University of California, Berkeley)

<img src="https://github.com/LeonKwok0/CSCI596FINAL/blob/main/1.png"  style="zoom:10%;" />

## User-based CF

Collaborative Filtering (CF) is a commonly used algorithm that is available on a variety of e-commerce sites. CF algorithms include user-based CF and item-based CF. We use user-based CF to implement product recommendations.

The basic idea of user-based CF is fairly simple. Based on the user's preference for items, find the adjacent user and then recommend the neighbor user's favorite to the current user. In terms of calculation, a user's preference for all items is used as a vector to calculate the similarity between users. After finding K neighbors, according to the similarity weight of neighbors and their preference for items, the uninvolved items that the current user has no preference are predicted, and a sorted list of items is calculated as a recommendation. 

Figure shows an example. We can find that User1 and User 3 have similar tastes and preferences, and user 1 also likes Product 1 and 4, so we can infer that user 3 may also like Product 1 and 4, so we can recommend Product 1 and 4 to User3.

<img src="https://github.com/LeonKwok0/CSCI596FINAL/blob/main/2.png" alt="截屏2020-11-23 下午12.54.18" style="zoom:10%;" />



## Work flow of our final project (Implementation)

### Load Data

Assuming that we have such data, we can assign different scores to different behaviors (suppose browsing 1 point, collecting 3 points, adding to the shopping cart 5 points, buying 10 points), and covert data to score matrix.

### Calculate Similarity

There are many ways to calculate similarity . We choose Cosine Similarity

Assume the two-dimensional vector A and B as shown in the figure below

<img src="https://github.com/LeonKwok0/CSCI596FINAL/blob/main/3.png" alt="截屏2020-11-23 下午2.03.10" style="zoom:30%;" />

Then their cosine similarity is

<img src="https://github.com/LeonKwok0/CSCI596FINAL/blob/main/4.png" alt="截屏2020-11-23 下午2.03.45" style="zoom:30%;" />

Extended to multi-dimensional vector A (A1, A2, A3, A4...) , b (b1, b2, b3, b4...)<img src="https://github.com/LeonKwok0/CSCI596FINAL/blob/main/5.png" alt="截屏2020-11-23 下午2.04.32" style="zoom:50%;" />The similarity between these users can be converted into a similarity matrix



**Recommendation list = similarity matrix X score matrix**



### Duplicate Removal

Since the user has already acted on some of the items in the recommended list, it is important to filter them out.



### Result

Get the final recommendation list, whose value represents the user's interest in the product. Finally we make recommendations

