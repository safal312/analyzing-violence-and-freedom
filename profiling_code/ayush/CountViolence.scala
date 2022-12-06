// acled cleaned dataset
var acled_df = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("finalProject/data/acled_clean_dataset");
acled_df.cache();

// group by country, year with count of rows
var df = acled_df.groupBy("country", "year").count();

// sort by country
df = df.sort("country");

df.show();

// export to hdfs file
df.write.option("header","true").csv("finalProject/data/violence_data");
