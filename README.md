# BinaryComparatorAPI

	
## The API

• Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints

	o <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
• The provided data needs to be diff-ed and the results shall be available on a third end point
	
	o <host>/v1/diff/<ID>

• The results shall provide the following info in JSON format

	If equal return that
	
	If not of equal size just return that
	
	If of same size provide insight in where the diffs are, actual diffs are not needed.
		• So mainly offsets + length in the data
		
• Make assumptions in the implementation explicit, choices are good but need to be communicated
	
• Dependencies

	Java 8 or later;
	Maven 3.3.9 or later;
	Tomcat 8 or later.

• Technologies

	API Rest with Jersey;
	H2 Database Embedded (in-memory);
	Log4J.

• Tools

	Eclipse;
	Insomnia REST Client (https://insomnia.rest/).

• Compilation and Test
	
	mvn package

• Only Test
	
	mvn test

• Integration Test
	
	mvn clean verify -Pfailsafe	

• Install
	
	cd BinaryComparatorAPI
	mvn package
	cp target/cpr-api.war $TOMCAT_FOLDER/webapps

• Notes
	
	The log file is generated on /tmp/cpr-api.log;
	The database is automatically generate when not exists on API boostrap.

 
