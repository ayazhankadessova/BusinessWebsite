## RFM Segmentation/Analysis

### 1) What is RFM analysis?

#### RFM Analysis divides users into segments depending on:

• **Recency**: How much time has elapsed since a customer'scustomer's last activity or transaction with the brand? Activity is usually a purchase, although variations are sometimes used, e.g., the last visit to a website or a mobile app. In most cases, the more recently a customer has interacted or transacted with a brand, the more likely that customer will be responsive to communications from the brand.

• **Frequency**: How often has a customer transacted or interacted with the brand during a particular period? Customers with frequent activities are more engaged and more loyal than customers who rarely do so. And one-time-only customers are in a class of their own.

• **Monetary**: Also referred to as"  "monetary value,"" this factor reflects how much a customer has spent with the brand during a particular period. Big spenders should usually be treated differently than customers who spend little. Looking at monetary divided by frequency indicates the average purchase amount – a crucial secondary factor to consider when segmenting customers.

## 2) Performing RFM Segmentation and RFM Analysis

1. 3 indicators (Recency, Frequency, Monetary) need to be assigned separately for each customer.

2. Assign grades from 1-3 or 1-5 based on the results. The wider the range, the narrower the segments we get.

3. Grades can be set using quantiles. We sort the data according to one of the criteria and divide it into equal groups.

4. Visualizing results

## 3) Project Flow

1. Use the public dataset: https://www.kaggle.com/olistbr/brazilian-ecommerce, namely olist_orders_dataset.csv and olist_order_payments_dataset.csv files.  
2. Import all the necessary libraries and read the files.
3. Convert the date of delivery of the order by the original carrier (order_delivered_carrier_date) to datetime64. -> as we are going to base our calculations on it.
4. Create an index order_id and connect the datasets through it.
5. Since the dataset is not recent, we will use max + 1 instead of the current date. To create Recency, Frequency and Monetary for every customer, group the records through customer_id.
6. Check the first 10 Table rows!
7. Assigning grades. Split into a range from 1 to 5. The wider the range, the more accurate our groups are, but at the same time, it is more challenging to work with a large number of combinations.
8. Segmented_rfm Table is ready! RFM score of 413 means: r_quartile = 4, f_quartile = 1, m_quartile = 3.
9. Separately, the average recency/frequency/monetary_value values for all RFMScore can be seen using matplotlib.

## 4) Application

#### RFM analysis aims to form segments to craft specific messaging tailored for each customer group. By focusing on the behavioral patternsof particular groups, RFM marketing allows marketers to communicate with customers much more effectively. For example: offer a bonus/sale, benefit, send a push or email notification. It'sIt's essential to do this in a targeted manner.

#### The effect of using RFM analysis can be as follows: customer retention, increased income, increased customer loyalty.

#### ➭ **Examples of interpretation of RFM analysis segments:**

• Best Customers: R = 5, F = 5, M = 5 - Customers that pay often, a lot and recently. The most loyal and active users. Communications with this group should make them feel valued and appreciated. These customers likely generate a disproportionately high percentage of overall revenues, and thus focusing on keeping them happy should be a top priority. Further analyzing their individual preferences and affinities will provide additional opportunities for even more personalized messaging.

• Lost Customers: R = 1, F = 1, M = 1 - they pay little, rarely and stopped transacting a long time ago. Most likely lost clients. It may not be worth taking action to return them if the acquisition price is higher than the expected profit.

• Churned Best Customers: R = 1/5, F = 4/5, M = 4/5 - loyal users on the verge of leaving. While it's often challenging to re-engage churned customers, the high value of these customers makes it worthwhile trying. Like with the Best Customers group, it's important to communicate with them based on their specific preferences, as known from earlier transaction data. We offer them a bonus, a discount and try to get them back.

• High-Spending New Customers: R = 4/5, F = 1, M = 1/2/3/4/5 - users have made a payment recently. It is always a good idea to carefully “incubate” all new customers, but it's even more important because they spend a lot on their first purchase. Like with the Best Customers group, it’s important to make them feel valued and appreciated and give them terrific incentives to continue interacting with the brand.

## 5) Conclusion

Learned a simple and effective way to segment users/customers. I hope to utilize RFM analysis to help companies select targeted exposure and thus increase revenue or retain users in the future.

### Resources:
• https://www.optimove.com/resources/learning-center/rfm-segmentation

• https://www.kaggle.com/olistbr/brazilian-ecommerce