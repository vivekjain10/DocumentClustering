[![Build Status](https://travis-ci.org/vivekjain10/DocumentClustering.svg?branch=master)](https://travis-ci.org/vivekjain10/DocumentClustering)

Document Clustering
-------------------

Picks documents from Redis and creates clusters using [Apache Mahout](https://mahout.apache.org) clustering tools.

How to use this tool
--------------------

Place documents in local [Redis](http://redis.io) instance.
 
* `documents.count` should contain total count of document. This application will start fetching documents from 1 to `count`.
* `document.1` .. `documents.N` should contain the documents.

Running the application
-----------------------

`mvn clean compile exec:java -Dexec.mainClass=in.vivekjain.document.clustering.Main`

If you have thousands of documents, you might need to provide additional memory(like `export MAVEN_OPTS="-Xmx10240m"`).

The final output will list all documents pre-fixed by the following information:

`cluster-id weight distance document-name`

You can tweak `Result.toString` method to get the desired output.

Troubleshooting
---------------

* If you have too many documents to cluster, you might face `OutOfMemory` error even after providing additional memory. You will need to either reduce the number of documents in Redis or specify the `DOCUMENTS_TO_CLUSTER` environment variable to limit the number of documents to be picked up:

For example, `export DOCUMENTS_TO_CLUSTER="1000"` will enable clustering of only 1000 documents.


Reference
---------

* http://technobium.com/introduction-to-clustering-using-apache-mahout/
