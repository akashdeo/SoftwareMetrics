1. In StatementCoverage,open cmd
2.mvn clean
3.mvn install
4.Download any github project from the net
5.Clone/Download it.
6.Then change the contents of pom file as follows:

Add Plugin as follows:

<plugin>
 <groupId>cs6367.Coverage</groupId>
  <artifactId>plugin-metrics</artifactId>
   <version>1.0-SNAPSHOT</version>
    <executions>
     <execution>
      <goals>
       <goal>metrics</goal>
      </goals>
     </execution>
    </executions>
</plugin>

7.Then open cmd in the github project
8.Type mvn metrics:projectMetrics.
9.Note the metrics.txt file which contains all the calculated metrics.