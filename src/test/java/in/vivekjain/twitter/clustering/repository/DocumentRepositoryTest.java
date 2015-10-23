package in.vivekjain.twitter.clustering.repository;

import in.vivekjain.twitter.clustering.Document;
import in.vivekjain.twitter.clustering.DocumentRepository;
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
public class DocumentRepositoryTest {
  @Mock
  private Jedis jedis;
  private Observable<List<Document>> observable;

  @Before
  public void beforeEach() {
    observable = Observable
        .create(new DocumentRepository(jedis))
        .reduce(new ArrayList<>(), (docs, doc) -> {
          docs.add(doc);
          return docs;
        });
  }

  @Test
  public void shouldReturnDocuments() throws Exception {
    when(jedis.get("documents.count")).thenReturn("2");
    when(jedis.get("documents.1")).thenReturn("Document 1");
    when(jedis.get("documents.2")).thenReturn("Document 2");

    observable.subscribe(documents -> {
      assertThat(documents.size(), is(2));
      assertThat(documents.get(0).id, is(1));
      assertThat(documents.get(0).text.get(), is("Document 1"));
      assertThat(documents.get(1).id, is(2));
      assertThat(documents.get(1).text.get(), is("Document 2"));
    });
  }

  @Test
  public void shouldFilterOutInvalidDocuments() throws Exception {
    when(jedis.get("documents.count")).thenReturn("2");
    when(jedis.get("documents.1")).thenReturn("Document 1");

    observable.subscribe(documents -> {
      assertThat(documents.size(), is(1));
      assertThat(documents.get(0).text.get(), is("Document 1"));
    });
  }

}