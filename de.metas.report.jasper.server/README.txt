metas ADempiere Jasper server

== Building ==

Build war file:
mvn clean war:war

After you did some changes in maven dependencies, please run:
	mvn eclipse:clean
	mvn -Dwtpversion=2.0 eclipse:eclipse

or simple
	mvn -Dwtpversion=2.0 eclipse:clean eclipse:eclipse
	
or simply run
	mvn_eclipse.launch
