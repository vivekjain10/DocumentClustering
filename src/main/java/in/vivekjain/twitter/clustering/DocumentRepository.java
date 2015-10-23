package in.vivekjain.twitter.clustering;

import redis.clients.jedis.Jedis;
import rx.Observable;
import rx.Subscriber;

import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;


public class DocumentRepository implements Observable.OnSubscribe<Document> {
  private Jedis jedis;

  public DocumentRepository() {
    this(new Jedis());
  }

  public DocumentRepository(Jedis jedis) {
    this.jedis = jedis;
  }

  public void call(Subscriber<? super Document> subscriber) {
    IntStream.rangeClosed(1, totalTweets())
        .mapToObj(value -> new Document(value, jedis.get("documents." + value)))
        .filter(document -> document.text.isPresent())
        .forEach(subscriber::onNext);
    subscriber.onCompleted();
  }

  private int totalTweets() {
    return parseInt(jedis.get("documents.count"));
  }
}
