val allRDD = sc.textFile("project/out_clean.csv")
val splitRDD = allRDD.map(line => line.split(','))
splitRDD.filter(data => data(2).toInt >= 2011 && data(2).toInt < 2022).map(line => line.mkString(",")).saveAsTextFile("project/decade")