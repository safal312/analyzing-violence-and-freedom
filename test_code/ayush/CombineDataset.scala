// This script combines dataset from all years in the last decade.

// first dataset
var df = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("finalProject/data/dataset1");
df.cache();

// combining datasets from 2 to 5
for (count <- 2 to 5) {
	var _df = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("finalProject/data/dataset" + count);
	df = df.union(_df);
}

// writing to hdfs file
df.write.option("header","true").csv("finalProject/data/acled_dataset");