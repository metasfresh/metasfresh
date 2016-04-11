-------------------------------------------------------------------
MFProcurement Vaadin TouchKit app skeleton created by maven archetype
-------------------------------------------------------------------

USING THE GENERATED PROJECT:
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

The project is pretty standard Maven web app project and should thus
be compatible with practically all IDE's. The application stub
contains usage examples of some basic components and also provides a
method to serve different UI for desktop browsers.

Packaging/installing the project
--------------------------------

Run maven command:

$ mvn package

Running the default package target generates war file. Also
widgetset compilation is automatically done at this point.

Running the app in development server
-------------------------------------

The project has by default jetty plugin configure as a web server. It
can be started with maven command:

$ mvn vaadin:compile jetty:run

Importing the project in eclipse
-------------------------------------

You can use any IDE for editing your project, but we normally use eclipse,
install Vaadin Eclipse Plugin if you have not done yet, and then run

$ mvn eclipse:eclipse

finally you can import your project in eclipse as usual.

Debugging the client-side code
-------------------------------------

If you are modigying client-side code and you want to debug it using
the gwt devmode, open one terminal an run the command:

$ mvn vaadin:run

Then open another terminal and run:

$ mvn jetty:run

Finally open your browser and open the url
http://localhost:8080/?gwt.codesvr=127.0.0.1:9997

