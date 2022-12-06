# Analyzing Freedom House Index vs Violence Against Civilians in Multiple Countries

Group Members: Ayush Pandey (ap6178), Safal Shrestha (ss13750)

[Link to Proposal](https://docs.google.com/document/d/17-y3heCmwxLJKk_ABka1UBNt7E-qUdpIS8RdnuXnC4c/edit)

## Directory Structure

<pre>
./
├── README.md
├── ana_code
|   ├── Stats.ipynb
│   ├── Stats.scala
│   └── data
│       ├── acled_5yrs_dataset.csv
│       ├── democracy.csv
│       └── population.csv
├── etl_code
│   ├── ayush
│   │   └── Clean.scala
│   └── safal
│       ├── FH_Clean.scala
│       ├── Join_Population.scala
│       ├── Scores.scala
│       └── screenshots
│           ├── clean-1.png
│           ├── clean-2.png
│           ├── joined.png
│           └── scores.png
├── profiling_code
│   ├── ayush
│   │   └── CountViolence.scala
│   └── safal
│       └── CountRecs.scala
├── run.sh
├── test_code
│   ├── ayush
│   │   └── CombineDataset.scala
│   └── safal
│       ├── Acled.scala
│       ├── Average.scala
│       └── DecadeFilter.scala
└── uploadToHDFS.sh

12 directories, 21 files
</pre>

### How to run the code?

On a Linux terminal, run the following command:

```bash
./run.sh
```

**Content of run.sh**

```bash
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

```

### Description of Files

1. **./etl_code/ayush.Clean.scala** - cleans the ACLED dataset
2. **./etl_code/safal/FH_Clean.scala** - cleans the Freedom House dataset
3. **./etl_code/safal/Scores.scala** - creates a dataset with countries, and their freedom house index whose index for last five years has stayed consistent
4. **./etl_code/safal/Join_Population.scala** - joins the Freedom House and Population datasets
5. **./profiling_code/ayush/CountViolence.scala** - counts and adds the number of violence events in each country in a new dataset
6. **./profiling_code/safal/CountRecs.scala** - counts the number of records in the Freedom House datasets
7. **./ana_code/Stats.scala** - calculates the new statistics dataset using all generated datasets
8. **./uploadToHDFS.sh** - creates new HDFS directory _finalProject/data/_ and uploads all the datasets to it
9. **./run.sh** - runs all the above scripts in the correct order

### RESULTS

When **run.sh** finishes running, the following files will be generated inside the **./ana_code/** directory: `stats.csv`

This file is then used in `Stats.ipynb` to generate the required graphs for the research report.
