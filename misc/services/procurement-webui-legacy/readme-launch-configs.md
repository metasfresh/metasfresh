
# config de.metas.procurement.webui.launch

this launch config starts the procurementUI with

 * -Dserver.port=8181 
  
So the application will open its http web interface on port 8181 to avoid port collisions with a possibly running metasfresh server (->port 8080)

Rationale: the metasfresh server is there by default, so it may run with the default ports. 
The webui-server only runs while we develop particular procurement tasks, so we give it non-standard ports to avoid colisions.

# config de.metas.procurement.webui_jrebel.launch

like de.metas.procurement.webui.launch, but starts the webUI with jrebel
