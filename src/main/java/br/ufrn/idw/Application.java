package br.ufrn.idw;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        int x = 0, y = 0;
        boolean isParquetMode = false;

        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            isParquetMode = Boolean.parseBoolean(args[2]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Parâmetros inválidos.");
            System.exit(1);
        }

        Point point = new Point(x, y);

        SparkSession spark = SparkSession.builder()
                .appName("IDW Interpolation")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> df;

        if (isParquetMode) {
            df = spark.read().parquet("data/measurements.parquet");
        } else {
            StructType schema = DataTypes.createStructType(new StructField[] {
                DataTypes.createStructField("x", DataTypes.IntegerType, false),
                DataTypes.createStructField("y", DataTypes.IntegerType, false),
                DataTypes.createStructField("temperature", DataTypes.DoubleType, false),
            });

            df = spark.read()
                    .format("csv")
                    .option("sep", ",")
                    .option("header", "false")
                    .schema(schema)
                    .load("data/measurements.txt");
        }

        JavaRDD<Row> rdd = df.javaRDD();

        IDWCalculator total = rdd.map(row -> {
            Point pointReaded = new Point(row.getInt(0), row.getInt(1));
            double temperatureReaded = row.getDouble(2);
            IDWCalculator parcial = new IDWCalculator(point);
            return parcial.calculate(pointReaded, temperatureReaded);
        }).reduce((a, b) -> {
            IDWCalculator result = new IDWCalculator(point);
            result.numerator = a.numerator + b.numerator;
            result.weights = a.weights + b.weights;
            return result;
        });

        double idw = total.get();
        System.out.println("IDW: " + String.format("%.1f", idw).replace(',', '.'));

        Thread.sleep(200000);
    }
}
