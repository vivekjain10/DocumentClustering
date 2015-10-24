package in.vivekjain.document.clustering;

import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class DocumentSequence {
  private SequenceFile.Writer writer;

  public DocumentSequence(HadoopConfig config) throws IOException {
    writer = new SequenceFile.Writer(config.fileSystem, config.configuration,
        config.documentsSequencePath, Text.class, Text.class);
  }

  public void write(Document document) {
    Text id1 = new Text(String.valueOf(document.id));
    Text text1 = new Text(document.text.get());
    try {
      writer.append(id1, text1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void close() throws IOException {
    writer.close();
  }
}
