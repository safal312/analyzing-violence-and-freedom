import org.apache.spark.sql.SQLContext

val data = spark.read.option("header", "false").option("inferSchema", "true").csv("project/decade/decade_data.csv").toDF(
    "Country", "Country Code", "Year", "Score"
)

val grouped = data.groupBy("Country").avg("Score")

grouped.show()