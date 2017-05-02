# SoftwareMetrics

SoftwareMetrics is a Apache Maven plugin used to measure the code. It calculates in the form of number of operators, operands, lines of code, modifier types of 'methods' of any github project.

It Calculates the following method metrics:

1.No. of operators
2.Variable References
3.Class References			
4.Method Name
5.No. of arguments
6.No. of external methods
7.No. of local methods
8.No. of expressions
9.No. of operands
10.Exceptions referenced
11.Exceptions thrown
12.No. of statements
13.Variable Declarations
14.Halstead Length
15.Halstead Volume
16.Halstead vocabulary
17.Halstead difficulty
18.Halstead effort
19.Halstead Bugs
20.Modifiers
21.No. of Jump Instructions
22.Lines of Code



## How to build

Navigate to the root folder of the project, and open console. <br>
Use the following code to build the project.
<code>
mvn clean install
</code>

## How to use the plugin

1. Add the following plugin to the pom file (inside the <build> of the github project which you clone or download.
```xml
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
```

2. To run the plugin on any project, navigate to the root of that project, and execute:
<code>
mvn metrics:metrics
</code>

The metrics are stored in <code>metrics.txt</code> file in the root directory.
