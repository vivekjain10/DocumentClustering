package in.vivekjain.document.clustering;

import java.util.Optional;

public class Document {
  public int id;
  public Optional<String> text;

  public Document(int id, String text) {
    this.id = id;
    this.text = Optional.ofNullable(text);
  }
}
