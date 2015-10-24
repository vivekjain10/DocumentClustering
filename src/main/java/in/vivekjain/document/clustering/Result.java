package in.vivekjain.document.clustering;

public class Result {
  public int clusterId;
  public double weight;
  public double distance;
  public String documentName;
  public String documentText;

  public Result(int clusterId, double weight, double distance, String documentName) {
    this.clusterId = clusterId;
    this.weight = weight;
    this.distance = distance;
    this.documentName = documentName;
  }

  public Result(Result result, String documentText) {
    this(result.clusterId, result.weight, result.distance, result.documentName);
    this.documentText = documentText;
  }

  public int compare(Result that) {
    if (this.clusterId < that.clusterId) return -1;
    if (this.clusterId > that.clusterId) return 1;

    if (this.weight < that.weight) return -1;
    if (this.weight > that.weight) return 1;

    if (this.distance < that.distance) return -1;
    if (this.distance > that.distance) return 1;

    return 0;
  }

  @Override
  public String toString() {
    return clusterId + " " + weight + " " + distance + " " + documentName + " " + documentText;
  }
}
