package in.vivekjain.twitter.clustering;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;

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
    }
  }

}
