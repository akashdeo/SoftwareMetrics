# SoftwareMetrics

SoftwareMetrics is a Apache Maven plugin used to measure the code. It calculates in the form of number of operators, operands, lines of code, modifier types of methods.


## How to build

Navigate to the root folder of the project, and open console. <br>
Use the following code to build the project.
<code>
mvn clean install
</code>

## How to use the plugin

1. Add the following plugin to the pom file of the project.

<pre>
<code>
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
</code>
</pre>

2. To run the plugin on any project, navigate to the root of that project, and execute:
<code>
mvn metrics:metrics
</code>

The metrics are stored in <code>metrics.txt</code> file in the root directory.
