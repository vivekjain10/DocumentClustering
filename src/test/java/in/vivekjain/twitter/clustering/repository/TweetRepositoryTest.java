package in.vivekjain.twitter.clustering.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetRepositoryTest {
  @Mock
  private Jedis jedis;
  private Observable<List<String>> observable;

  @Before
  public void beforeEach() {
    observable = Observable
        .create(new TweetRepository(jedis))
        .reduce(new ArrayList<>(), (acc, tweet) -> {
          acc.add(tweet);
          return acc;
        });
  }

  @Test
  public void testName() throws Exception {
    when(jedis.get("tweet.count")).thenReturn("1");
    when(jedis.get("tweet.1")).thenReturn("Tweet 1");

    observable.subscribe(tweets -> {
      assertThat(tweets.size(), is(1));
      assertThat(tweets.get(0), is("Tweet 1"));
    });
  }
}