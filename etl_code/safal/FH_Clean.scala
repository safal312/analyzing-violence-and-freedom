val demoRDD = sc.textFile("finalProject/data/democracy")
val Head = demoRDD.first()
val dataRDD = demoRDD.filter(line => !line.equals(Head))
dataRDD.map(line => ("line", 1)).reduceByKey(_ + _).collect().foreach(println)
val splitRDD = dataRDD.map(line => line.split(','))

splitRDD.map(data => if (data(0) == null || data(0) == "") 1 else 0).sum()
splitRDD.map(data => if (data(1) == null || data(1) == "") 1 else 0).sum()
splitRDD.map(data => if (data(2) == null || data(2) == "") 1 else 0).sum()
splitRDD.map(data => if (data(3) == null || data(3) == "") 1 else 0).sum()

splitRDD.filter(data => data(1) == null || data(1) == "").map(data => (data(0), 1)).reduceByKey(_+_).collect().foreach(println)

val cleanData = splitRDD.filter(data => data(1) != null && data(1) != "")
cleanData.map(data => if (data(1) == null || data(1) == "") 1 else 0).sum()

cleanData.coalesce(1).map(line => line.mkString(",")).saveAsTextFile("finalProject/data/fh_clean");

