package in.vivekjain.twitter.clustering;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.NamedVector;

public class Output {
  private HadoopConfig config;

  public Output(HadoopConfig config) {
    this.config = config;
  }

  public void print() {
    SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<>(
        new Path(config.outputFolder
            + "clusters/clusteredPoints/part-m-00000"), config.configuration);
    for (Pair<Writable, Writable> pair : iterable) {
      System.out.format("%10s -> %s\n", pair.getFirst(), pair.getSecond());

      int clusterId = ((IntWritable) pair.getFirst()).get();

      WeightedPropertyVectorWritable second = (WeightedPropertyVectorWritable) pair.getSecond();
      double weight = second.getWeight();
      Object distance = second.getProperties().values().toArray()[0];
      String documentName = ((NamedVector) second.getVector()).getName();

      System.out.println("clusterId: " + clusterId);
      System.out.println("weight: " + weight);
      System.out.println("distance: " + distance);
      System.out.println("documentName: " + documentName);

    }
  }
//  Cluster: ((IntWritable) pair.getFirst()).value
//  wt: ((WeightedPropertyVectorWritable) pair.getSecond()).weight
//  distance: ((WeightedPropertyVectorWritable) pair.getSecond()).getProperties().values().toArray()[0]
  // For doc name(NamedVector): ((WeightedPropertyVectorWritable) pair.getSecond()).getVector()
}
