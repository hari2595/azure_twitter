# azure_twitter
<br>
This an end to end project where we pull live tweets from twitter with help Spark clusters on Azure Data Bricks and perform sentiment analysis on the data with Azure Language Services. Then we store the final Data in a Azure DB and a Parquet file in ADS Gen 2 Data Lake as a backup, this is orchestrated with the help of azure Data Factory. We finally use Power BI to connect with the Final DB in Azure SQL DB. Note: Azure Synapse can be utilized instead of Azure DB. In this project I have retrieved tweets regarding Marvel since the new Ms Marvel movie was just released, the final Power BI will show how much people feel about the movie in twitter.
<br>
<br>
<p>
    <img src="https://raw.githubusercontent.com/hari2595/azure_twitter/main/Azure%20DFD.jpeg" width="880" height="440" />
</p>
<h3> Pre Requisites: </h3><br>
Twitter Developer account <br>
Azure Cloud Service Account <br>
<br>
<h3> Execution </h3>
<br>
<br>
Step 1: Login Into Azure cloud services. <br><br>
Step 2: Create a new Data Brick Resource. <br><br>
Step 3: Create a new Azure Language Services account. <br><br>
Step 4: Go to the access keys section and copy the key and end point, this will be used to connect with the spark cluster notebook. <br><br>
<p>
    <img src="https://raw.githubusercontent.com/hari2595/azure_twitter/main/Azure%20Lanugage%20Services/cognitive-service-key-and-endpoints.PNG" width="880" height="440" />
</p>
Step 5: Create a new cluster in your Data bricks resource. Select Runtime 6.4 <br><br>
Step 6: Add the following 2 libraries by entering the maven coordinates, com.microsoft.azure:azure-eventhubs-spark_2.11:2.3.10 and org.twitter4j:twitter4j-core:4.0.7 . This will install the libraries to your Spark cluster. <br><br>
Step 7: Create a new Event Hub namespace, then create a new Event hub. <br><br>
Step 8: Create a new SAS poilcy with manage permission and copy the SAS Key. <br><br>
Step 9: Copy the conetent of ToStream.scala on to notebook1, replace the authentication values with your own. <br><br>
Step 10: Create a new Azure SQL DB or an Azure synapse dedicated sql pool.<br><br>
Step 11: Set the permissions to be accessed by any Azure services. Note the SQL Server login and password. <br><br>
Step 12: Copy the conetent of ToStream.scala on to notebook2, replace the authentication values with your own. <br><br>
Step 13: Make sure the table mentioned in notebook 2 does not exist in your DB. <br><br>
Step 14: Run both the notebooks, you should start recieving the tweets in your notebook 2. When enough data is collected run th final cell to create your copy of the table in Azure Sql or Synapse DB. <br><br>
Step 15: Create a new Data Factory Resource and create a data flow as per screenshots mentioned in the DataFactory folder. <br><br>
<p>
    <img src="https://github.com/hari2595/azure_twitter/blob/main/Data%20Factory/2.PNG" width="880" height="440" />
</p><br><br>
 <p>
    <img src="https://github.com/hari2595/azure_twitter/blob/main/Data%20Factory/3.PNG" width="880" height="440" />
</p>
Step 16: You can schedule the Notebooks and Data Factory to run on specific time to Automate the Lambda process(Batch Processing and Stream Processing together). <br><br><br>
Step 17: Open Power BI and select Azure SQL DB in your datasource option, Enter the Server Login and
https://app.powerbi.com/groups/me/reports/f221e832-bcd6-475a-824d-ce8a88aec06c/ReportSection
