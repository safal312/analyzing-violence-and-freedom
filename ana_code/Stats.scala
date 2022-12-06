// Ayush Pandey (ap6178)
// This script generates the statistics for the dataset.

// freedom house dataset
var fi_df = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("finalProject/data/fh_population");
fi_df.cache();

// acled cleaned dataset
var acled_df = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("finalProject/data/violence_data");
acled_df.cache();

// inner join the two datasets on country and year
var df = fi_df.join(acled_df, Seq("country", "year"));

// export
df.coalesce(1).write.option("header", "true").csv("finalProject/stats");
