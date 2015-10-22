package in.vivekjain.twitter.clustering.repository;

import redis.clients.jedis.Jedis;
import rx.Observable;
import rx.Subscriber;

import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;


public class TweetRepository implements Observable.OnSubscribe<String> {
  private Jedis jedis;

  public TweetRepository() {
    this(new Jedis());
  }

  public TweetRepository(Jedis jedis) {
    this.jedis = jedis;
  }

  public void call(Subscriber<? super String> subscriber) {
    IntStream.rangeClosed(1, totalTweets())
        .forEach(value -> subscriber.onNext(jedis.get("tweet." + value)));
  }

  private int totalTweets() {
    return parseInt(jedis.get("tweet.count"));
  }
}
