val df = spark.read.options(Map("inferSchema"->"true")).csv("finalProject/data/fh_clean")
val select_years = df.where('_c2 >= 2017)

val same_score_5yrs = select_years.groupBy("_c0", "_c3").count().where('count===5).orderBy("_c0").drop("count")

val mapped = same_score_5yrs.flatMap(row => List(2017, 2018, 2019, 2020, 2021).map((row.getString(0),_,row.getInt(1)))).toDF("country", "year", "score")

mapped.coalesce(1).write.option("header", "true").csv("finalProject/data/freedom_house");

