val pops = spark.read.option("header", "true").option("inferSchema", "true").csv("finalProject/data/population")
val select_years = pops.select("Country Name", "2015", "2016", "2017", "2018", "2019", "2020", "2021")

val df = select_years.na.drop()

val cleaned = spark.read.options(Map("inferSchema"->"true", "header" -> "true")).csv("finalProject/data/freedom_house")

val popsFlat = df.flatMap(row => List(2017, 2018, 2019, 2020, 2021).map(i => (row.getString(0),i,row.getLong(i - 2014)))).toDF("country", "year", "population")

val joined_pop_score = cleaned.join(popsFlat, Seq("country", "year"), "inner")
joined_pop_score.coalesce(1).write.option("header","true").csv("finalProject/data/fh_population");

