package br.ufrn.idw;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class ConvertTextToParquet {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("Text to parquet")
                .master("local[*]")
                .getOrCreate();

        StructType schema = DataTypes.createStructType(new StructField[] {
            DataTypes.createStructField("x", DataTypes.IntegerType, false),
            DataTypes.createStructField("y", DataTypes.IntegerType, false),
            DataTypes.createStructField("temperature", DataTypes.DoubleType, false),
        });

        Dataset<Row> df = spark.read()
                .format("csv")
                .option("header", "false")
                .option("sep", ",")
                .schema(schema)
                .load("data/measurements.txt");

        df.write().mode(SaveMode.Overwrite).parquet("data/measurements.parquet");

        spark.stop();
    }
}
