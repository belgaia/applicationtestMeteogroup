applicationtestMeteogroup
=========================

RESTful webservice to find and create person data.

Deployment description:
1. Build project
2. Get the project/webservice/target/meteogroupService.war and deploy it to an application-server (e.g. Glassfish 3)
3. Service available under base URI http://[servername]:[port]/meteogroupService/meteogroup
4. Install MongoDb on your system (used for persistence)
5. Check the following URIs

Create a new person
-------------------
1. Use curL for emulating a request with XML body, e.g.:
   curl -v -H "Content-Type: application/xml" -X POST --data "@person-1.xml" http://localhost:8080/meteogroupService/meteogroup/person
   (where @person-1.xml is the xml file that contains the person data to store)
2. Check curL output and server log for details of success or failure.

Find a person by ID
-------------------
1. Call URI http://[servername]:[port]/meteogroupService/meteogroup/person/{id} in your browser
   (where {id} is the id of the person you want to find
2. Shows you the result of the found person in browser and XML format
3. If you try to find a person with not existing person id you get an 404 error (Not found)

For further information please contact isabelbatista@gmx.de


Open tasks
----------

1. NoSQL unittesting is still open.
