package in.vivekjain.twitter.clustering;

import in.vivekjain.twitter.clustering.repository.TweetRepository;
import rx.Observable;

public class Main {
  public static void main(String[] args) {
    Observable<String> observable = Observable.create(new TweetRepository());
    observable.subscribe(System.out::println);
  }
}
