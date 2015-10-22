package in.vivekjain.twitter.clustering.repository;

import in.vivekjain.twitter.clustering.Document;
import redis.clients.jedis.Jedis;
import rx.Observable;
import rx.Subscriber;

import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;


public class TweetRepository implements Observable.OnSubscribe<Document> {
  private Jedis jedis;

  public TweetRepository() {
    this(new Jedis());
  }

  public TweetRepository(Jedis jedis) {
    this.jedis = jedis;
  }

  public void call(Subscriber<? super Document> subscriber) {
    IntStream.rangeClosed(1, totalTweets())
        .mapToObj(value -> new Document(value, jedis.get("tweet." + value)))
        .filter(document -> document.text.isPresent())
        .forEach(subscriber::onNext);
    subscriber.onCompleted();
  }

  private int totalTweets() {
    return parseInt(jedis.get("tweet.count"));
  }
}
