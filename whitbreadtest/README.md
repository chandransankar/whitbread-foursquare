Whitbread Test
==============

This a content package project generated using the multimodule-content-package-archetype.


1. AEM component and client libs are under the follwoing paths

    a. /apps/whitbread-test
    b. /etc/designs/whitbread-test


2. Content page is available under 

    /content/whitbread-test
 
3. OSGI services ( Java source code ) are available under 

    /whitbread-foursquare/whitbreadtest/bundle


To Build the project 
--------------------

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

Using with VLT
--------------

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

Specifying CRX Host/Port
------------------------

The CRX host and port can be specified on the command line with:
mvn -Dcrx.host=otherhost -Dcrx.port=4502 <goals>


( Or )

Install it from GIT 
-------------------

Please install the following crx content package "/whitbread-foursquare/whitbreadtest/content/target/whitbreadtest-content-1.0-SNAPSHOT.zip" 
in AEM publish or author using crx package manager and access the following page "http://localhost:4502/content/whitbread-test.html"
to enter the place name to get the popular places near the entered place.




Note : 

1. It works fine with the javascript implementation but not with the java implemention (i have created the sling handler and osgi service to invoke the json) as its throing {"error":"invalid_grant"} and HTTP/1.1 400 Bad Request error. I can fix this issue if you can provide me some more time like an hour or so later tomorrow. Thanks.

2. Tried using FoursquareApi to get the venues but this API throws org.json.JSONException: JSONObject['specials'] is not a JSONArray exception. Its a FoursquareApi library issue so reverted back to 
javascript implementation. Please refer whitbreadtest/pom.xml and whitbreadtest/bundle/pom.xml
