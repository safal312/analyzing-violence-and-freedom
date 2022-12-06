val df = spark.read.options(Map("inferSchema"->"true", "header"->"true")).csv("project/acled/acled_1yr.csv")

val groupedConflicts = df.groupBy("country", "event_type", "year").count().orderBy("country").where('year === 2021)
val averageConflicts = groupedConflicts.groupBy("country").avg("count")


val data = spark.read.option("header", "false").option("inferSchema", "true").csv("project/decade/decade_data.csv").toDF(
    "Country", "Country Code", "Year", "Score"
)

val scoreData = data.where('year === 2021)

val joinedData = averageConflicts.join(scoreData, Seq("country"), "inner")
val newJoin = joinedData.drop("Country Code").drop("Year")

newJoin.stat.corr("avg(count)", "Score")

newJoin.colaesce(1).rdd.saveAsTextFile("project/merged_country_score_2021.csv")
