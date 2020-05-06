#!/bin/bash

set -o errexit  #don't allow any command to exit with an error code !=0

# This will get the hostname of the current app-server and will connect to a specific terminal server in order to update the local ADempiere-Client of the corresponding app-server
# NOTE: Please modify the variable "CLIENT_SERV=" and assign the hostname of the terminal server that is running the ADempiere Local Client
#       Also make sure adempiere@app-server can connect via ssh to adempiere@terminal-server!

APP_SERV=$(hostname)
CLIENT_SERV="deit901"


FILE_TIME=$(date +"%Y%m%d%H%M")
FILE_NAME="AdempiereClient_${APP_SERV}_${FILE_TIME}.zip"

scp /opt/metasfresh/download/metasfresh-client.zip adempiere@$CLIENT_SERV:/home/adempiere/${FILE_NAME}
ssh adempiere@$CLIENT_SERV "/home/adempiere/mf_local_install.sh ${APP_SERV} ${FILE_NAME}"

exit 0

