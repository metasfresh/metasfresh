
# config de.metas.procurement.webui.launch

this launch config starts the procurementUI with

 * -Dserver.port=8181 
  
So the application will open its http web interface on port 8181 to avoid port collisions with a possibly running metasfresh server (->port 8080)

 * -Dmfprocurement.activemq.runEmbeddedBroker=true
 * -Dmfprocurement.jms.broker-url=tcp://localhost:61618

So the application will start its own jms broker, but that broker will listen on port 61618 to avoid port collisions with the metasfresh server that might also run with an embedded jms broker at the same time (->port 61616)

 * -Dmfprocurement.activemq.broker.networkConnector.discoveryAddress=static:(tcp://localhost:61616)

So the application's embedded jms-broker will attempt to connect another locally running jms-broker (presumably the one of metasfresh)  
  

Rationale: the metasfresh server is there by default, so it may run with the default ports. 
The webui-server only runs while we develop particular procurement tasks, so we give it non-standard ports to avoid colisions.

# de.metas.procurement.webui_jrebel.launch

like de.metas.procurement.webui.launch, but starts the webUI with jrebel
