import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author George K. <gkiom@iit.demokritos.gr>
 */
object MainExample {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ParallelNGG")
      .setMaster("local[*]")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryoserializer.buffer","64mb")
      .registerKryoClasses(Array(classOf[MergeOperator], classOf[IntersectOperator], classOf[InverseIntersectOperator], classOf[DeltaOperator], classOf[GraphSimilarityCalculator], classOf[StringEntityTokenizer], classOf[OpenNLPSentenceSplitter]))
      .set("spark.executor.memory", "2g")
    val sc = new SparkContext(conf)

    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)

    val start = System.currentTimeMillis

    val numPartitions = 4
    val e1 = new StringEntity
    e1.readFile(sc, "./res/test1", numPartitions)

    val nggc1 = new NGramGraphCreator(sc, numPartitions, 3, 3)
    val ngg1 = nggc1.getGraph(e1)

    val e2 = new StringEntity
    e2.readFile(sc, "./res/test2", numPartitions)

    val nggc2 = new NGramGraphCreator(sc, numPartitions, 3, 3)
    val ngg2 = nggc2.getGraph(e2)

    val gsc = new GraphSimilarityCalculator
    val gs = gsc.getSimilarity(ngg1, ngg2)

    println("overall similarity: " + gs.getOverallSimilarity)
    println("size similarity: " + gs.getSimilarityComponents("size"))
    println("value similarity: " + gs.getSimilarityComponents("value"))
    println("containment similarity: " + gs.getSimilarityComponents("containment"))
    println("normalized similarity: " + gs.getSimilarityComponents("normalized"))

    val end = System.currentTimeMillis

    println("\nDuration: " + (end-start).toDouble/1000 + " seconds")
  }
}
