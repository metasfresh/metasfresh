#!/bin/sh

#QnD script do explode the adempiere ear. 
#This is usefull e.g. to place a custom adempiereJasper war inside it without having to do a full build&rollout
#Note that wrting this script took less time than a full build

BASE_DIR=../jboss/server/adempiere
EAR_FILENAME=de.metas.endcustomer.fresh.ear.ear


mkdir -vp ${BASE_DIR}/${EAR_FILENAME}
rm -r ${BASE_DIR}/${EAR_FILENAME}/*
mv ${BASE_DIR}/deploy/${EAR_FILENAME} ${BASE_DIR}/${EAR_FILENAME}/${EAR_FILENAME}.zip
unzip ${BASE_DIR}/${EAR_FILENAME}/${EAR_FILENAME}.zip -d ${BASE_DIR}/${EAR_FILENAME}
rm ${BASE_DIR}/${EAR_FILENAME}/${EAR_FILENAME}.zip
mv ${BASE_DIR}/${EAR_FILENAME} ${BASE_DIR}/deploy
