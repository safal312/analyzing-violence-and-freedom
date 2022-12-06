hdfs dfs -mkdir -p finalProject/data
hdfs dfs -put ./ana_code/data/acled_5yrs_dataset.csv finalProject/data/acled_dataset
hdfs dfs -put ./ana_code/data/democracy.csv finalProject/data/democracy
hdfs dfs -put ./ana_code/data/population.csv finalProject/data/population