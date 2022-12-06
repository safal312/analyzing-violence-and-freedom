// This script cleans the final dataset generated for the last decade.

// creating a dataframe from the dataset
var df = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("finalProject/data/acled_dataset");
df.cache();

// display distinct values from columns to be used
val keep = Array("event_date", "year", "event_type","sub_event_type","actor1","assoc_actor_1","actor2","assoc_actor_2","country","latitude","longitude","source","fatalities");
val columns = df.columns.toSeq;

// drop columns not in keep
for (column <- columns) {
	if (!keep.contains(column)) {
		df = df.drop(column);
	}
}

// drop rows with event_type != "Violence against civilians"
df = df.filter(df("event_type") === "Violence against civilians");

df.printSchema();

// write to hdfs file
df.write.option("header","true").csv("finalProject/data/acled_clean_dataset");

