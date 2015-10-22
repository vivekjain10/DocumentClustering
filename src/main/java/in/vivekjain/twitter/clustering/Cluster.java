package in.vivekjain.twitter.clustering;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.common.PartialVectorMerger;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

import java.io.IOException;
import java.util.List;

public class Cluster {
  private HadoopConfig config;

  public Cluster(HadoopConfig config) {
    this.config = config;
  }

  public void generate() throws Exception {
    calculateTfIdf();
    clusterDocs();
  }

  public void calculateTfIdf() throws ClassNotFoundException, IOException,
      InterruptedException {
    DocumentProcessor.tokenizeDocuments(config.documentsSequencePath,
        StandardAnalyzer.class, config.tokenizedDocumentsPath, config.configuration);

    DictionaryVectorizer.createTermFrequencyVectors(config.tokenizedDocumentsPath,
        new Path(config.outputFolder),
        DictionaryVectorizer.DOCUMENT_VECTOR_OUTPUT_FOLDER,
        config.configuration, 1, 1, 0.0f, PartialVectorMerger.NO_NORMALIZING,
        true, 1, 100, false, false);

    Pair<Long[], List<Path>> documentFrequencies = TFIDFConverter
        .calculateDF(config.termFrequencyVectorsPath, config.tfidfPath,
            config.configuration, 100);

    TFIDFConverter.processTfIdf(config.termFrequencyVectorsPath, config.tfidfPath,
        config.configuration, documentFrequencies, 1, 100,
        PartialVectorMerger.NO_NORMALIZING, false, false, false, 1);
  }

  void clusterDocs() throws ClassNotFoundException, IOException,
      InterruptedException {
    String vectorsFolder = config.outputFolder + "tfidf/tfidf-vectors/";
    String canopyCentroids = config.outputFolder + "canopy-centroids";
    String clusterOutput = config.outputFolder + "clusters";

    FileSystem fs = FileSystem.get(config.configuration);
    Path oldClusterPath = new Path(clusterOutput);

    if (fs.exists(oldClusterPath)) {
      fs.delete(oldClusterPath, true);
    }

    CanopyDriver.run(new Path(vectorsFolder), new Path(canopyCentroids),
        new EuclideanDistanceMeasure(), 20, 5, true, 0, true);

    FuzzyKMeansDriver.run(new Path(vectorsFolder), new Path(
            canopyCentroids, "clusters-0-final"), new Path(clusterOutput),
        0.01, 20, 2, true, true, 0, false);
  }
}
