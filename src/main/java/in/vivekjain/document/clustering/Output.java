package in.vivekjain.document.clustering;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.NamedVector;
import redis.clients.jedis.Jedis;

import java.util.stream.StreamSupport;

public class Output {
  private HadoopConfig config;
  private Jedis jedis;

  public Output(HadoopConfig config, Jedis jedis) {
    this.config = config;
    this.jedis = jedis;
  }

  public void print() {
    SequenceFileIterable<Writable, Writable> iterable = new SequenceFileIterable<>(
        new Path(config.outputFolder
            + "clusters/clusteredPoints/part-m-00000"), config.configuration);

    StreamSupport.stream(iterable.spliterator(), false)
        .map(this::result)
        .map(result -> new Result(result, jedis.get("documents." + result.documentName)))
        .sorted(Result::compare)
        .forEach(System.out::println);
  }

  private Result result(Pair<Writable, Writable> pair) {
    int clusterId = ((IntWritable) pair.getFirst()).get();
    WeightedPropertyVectorWritable second = (WeightedPropertyVectorWritable) pair.getSecond();
    double weight = second.getWeight();
    double distance = Double.parseDouble(second.getProperties().values().toArray()[0].toString());
    String documentName = ((NamedVector) second.getVector()).getName();
    return new Result(clusterId, weight, distance, documentName);
  }
}
