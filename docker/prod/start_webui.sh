#!/bin/bash

if [[ ! -z $WEBAPI_URL ]]; then
    sed -i 's,http\:\/\/MYDOCKERHOST\:PORT,'$WEBAPI_URL',g' /opt/metasfresh-webui-frontend/dist/config.js
fi

if [[ -f "/etc/apache2/certs/fullchain.pem" ]] && [[ -f "/etc/apache2/certs/privkey.pem" ]]; then
	sed -i 's/\bhttp\b/https/g' /opt/metasfresh-webui-frontend/dist/config.js
	a2ensite metasfresh_webui_ssl.conf
	echo "[METASFRESH] Activated SSL!"
else 
	sed -i 's/\https\b/http/g' /opt/metasfresh-webui-frontend/dist/config.js
	a2ensite metasfresh_webui.conf
	a2dissite metasfresh_webui_ssl.conf
	echo "[METASFRESH] Runnning Non-SSL!"
fi

/usr/sbin/apache2ctl -DFOREGROUND	
