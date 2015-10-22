package in.vivekjain.twitter.clustering;

import rx.Observable;

import java.util.Date;

public class Main {
  public static void main(String[] args) throws Exception {
    log("Starting...");

    HadoopConfig config = new HadoopConfig();

    DocumentSequence documentSequence = new DocumentSequence(config);

    Observable.create(new TweetRepository())
        .subscribe(documentSequence::write, Throwable::printStackTrace);

    documentSequence.close();

    Cluster cluster = new Cluster(config);
    cluster.generate();

    Output output = new Output(config);
    output.print();

    log("Done...");
  }

  private static void log(String text) {
    System.out.println(new Date() + " " + text);
  }
}
