package in.vivekjain.twitter.clustering;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;

import java.io.IOException;

public class HadoopConfig {
  public final Configuration configuration;
  public final FileSystem fileSystem;
  public final String outputFolder;
  public final Path documentsSequencePath;
  public final Path tokenizedDocumentsPath;
  public final Path tfidfPath;
  public final Path termFrequencyVectorsPath;

  public HadoopConfig() throws IOException {
    configuration = new Configuration();
    fileSystem = FileSystem.get(configuration);

    outputFolder = "output/";
    documentsSequencePath = new Path(outputFolder, "sequence");
    tokenizedDocumentsPath = new Path(outputFolder,
        DocumentProcessor.TOKENIZED_DOCUMENT_OUTPUT_FOLDER);
    tfidfPath = new Path(outputFolder + "tfidf");
    termFrequencyVectorsPath = new Path(outputFolder
        + DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER);
  }
}
