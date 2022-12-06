source ./uploadToHDFS.sh

spark-shell < ./etl_code/ayush/Clean.scala --deploy-mode client
spark-shell < ./etl_code/safal/FH_Clean.scala --deploy-mode client
spark-shell < ./etl_code/safal/Scores.scala --deploy-mode client
spark-shell < ./etl_code/safal/Join_Population.scala --deploy-mode client

spark-shell < ./profiling_code/ayush/CountViolence.scala --deploy-mode client
spark-shell < ./profiling_code/safal/CountRecs.scala --deploy-mode client

spark-shell < ./ana_code/Stats.scala --deploy-mode client

hdfs dfs -get finalProject/stats ./ana_code/stats
mv ./ana_code/stats/*.csv ./ana_code/stats.csv

