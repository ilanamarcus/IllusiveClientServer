## IllusiveClientServer

####In order to install, run the following commands in a linux environment:

Make sure to run git clone --recursive due to the presence of a git submodule in the project

cd vt-proj 

mvn install

chmod -R 777 outScript

#### To run, cd to the project directory, then:
1. To start the server: java -jar vt-server-1.0.jar
2. To start the client: sudo java -jar vt-client-0.0.1-SNAPSHOT.jar
3. When the client completes, the report can be found in root of the project directory. 

####Open source choices:

RESTful server - Spark. Read multiple times it’s the fastest to get up and running without lots of XML configuration, uses java 8 which is interesting with lambda syntax, something I’ve wanted to use. Had experience with SpringMVC and recall it being a bit cumbersome to set up. Based on recommendations here: http://www.coolcoder.in/2015/02/6-powerful-frameworks-for-creating.html, 
https://www.quora.com/Whats-the-best-RESTful-web-framework-to-use-with-Java

Google HTTP Client for Java - well documented, helps with json deserialization

VirusTotal java client - was planning on writing my own but then I came across several java implementations on the VirusTotal page, due to time constraints I thought it reasonable to use one of these. The one I picked uses an interface for the client, which I wanted in order to be able to mock it out in a unit test. 

Dependency Injection - Guice, lightweight set up and is compatible with java8. Generally using DI to make testing easier. In the end, didn’t have time to write tests. 

Templating - Trimou, lightweight and allows for json to be the template data source, which prevented an additional data transformation to java objects for data coming from the server. 

####If I'd had more time:
1. I didn't implement a workaround to the 4 request/minute/API key limitation imposed by Virustotal public API. It would have taken at least 5 minutes for the client to run if I'd waited for all of them to run so I only ran 4 hashes all together. Possible solutiosn would include queuing outstanding processes to be run after the timout and cacheing hashes that have already been run. 
2. I ran out of time to write tests, but I would have especially wanted to test my SHA256 hashing function and the report generation
3. I would have added a logging framework with different levels of logging (debug, warn, error, etc...)

