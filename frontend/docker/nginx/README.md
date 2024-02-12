### Note

Currently this docker image which contains the metasfresh frontend is intended for development usage.

It does not do SSL.


### The following environment variables can be set

Set the base URL for the webui-api server as seen from the client web browser.
Note that the webui-frontend itself does *not* need to be able to access the API;
It merely communicates this URL to your web browser which accesses the API directly. 
* `WEBUI_API_CLIENT_PROTOCOL`
* `WEBUI_API_CLIENT_HOST`
* `WEBUI_API_CLIENT_PORT`

Set the base URL for the webui-api server as seen from the frontend http server:
* `WEBUI_API_PROXYPASS_PROTOCOL`
* `WEBUI_API_PROXYPASS_HOST`
* `WEBUI_API_PROXYPASS_PORT`

The default, is 'n'; everything besides 'n' makes the startup script cat the nginx config file that will be used
* `DEBUG_CAT_NGINX_CONF_FILE`
