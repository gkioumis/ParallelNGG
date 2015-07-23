package gr.demokritos.iit.nGramGraphMethods

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

/**
 * @author Kontopoulos Ioannis
 */
object Main extends App {
  override def main(args: Array[String]) {
    //tests
    val conf = new SparkConf().setAppName("Graph Methods").setMaster("local")
    val sc = new SparkContext(conf)
    val vertexArray = Array(
      (1L, ("a")),
      (2L, ("b")),
      (3L, ("c"))
    )
    val edgeArray = Array(
      Edge(1L, 2L, 1.0),
      Edge(2L, 3L, 8.0)
    )
    val vertexRDD: RDD[(Long, String)] = sc.parallelize(vertexArray)
    val edgeRDD: RDD[Edge[Double]] = sc.parallelize(edgeArray)
    val graph: Graph[String, Double] = Graph(vertexRDD, edgeRDD)
    val vertexArray2 = Array(
      (1L, ("a")),
      (2L, ("b")),
      (3L, ("c")),
      (4L, ("d")),
      (5L, ("e"))
    )
    val edgeArray2 = Array(
      Edge(1L, 2L, 1.0),
      Edge(2L, 3L, 4.0),
      Edge(3L, 5L, 1.0),
      Edge(2L, 4L, 1.0)
    )
    val vertexRDD2: RDD[(Long, String)] = sc.parallelize(vertexArray2)
    val edgeRDD2: RDD[Edge[Double]] = sc.parallelize(edgeArray2)
    val graph2: Graph[String, Double] = Graph(vertexRDD2, edgeRDD2)



    //Use of delta operator
    val op = new GraphDeltaOperator
    val g = op.getResult(graph2, graph)
    g.edges.collect.foreach(println)

    //Use of similarities
    //val gsc = new GraphSimilarityCalculator
    //val gs = gsc.getSimilarity(graph, graph2)
    //println("Overall " + gs.getOverallSimilarity + " Size " + gs.getSimilarityComponents("size") + " Value " + gs.getSimilarityComponents("value") + " Containment " + gs.getSimilarityComponents("containment"))
  }

}