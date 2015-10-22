package in.vivekjain.twitter.clustering;

import in.vivekjain.twitter.clustering.repository.TweetRepository;
import rx.Observable;

import java.util.Date;

public class Main {
  public static void main(String[] args) throws Exception {
    log("Starting...");

    Observable<Document> observable = Observable.create(new TweetRepository());
    HadoopConfig config = new HadoopConfig();
    DocumentSequence documentSequence = new DocumentSequence(config);

    observable.subscribe(documentSequence::write, Throwable::printStackTrace);

    documentSequence.close();

    log("Done...");
  }

  private static void log(String text) {
    System.out.println(new Date() + " " + text);
  }
}
