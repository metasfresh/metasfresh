#!/bin/bash

#title           :build_new_release.sh
#description     :This Shell-Script will create a new build-version, using the files and database from the Source Server
#		  After the build-process is completed, the script will upload the zipped build to the webserver
#		  On user-request, the script will also create a package from the rollout-files and provide them
#		  on the webserver as "Update-Package"
#author          :metas.jb
#date            :2016-01-22
#version         :0.1    
#usage           :/bin/bash metasfresh_release.sh
#notes           :This script requires root-privileges on this server and ssh-access on the Source Server
#		  and ssh-privileges to the webserver in order to upload the release-packages.
#                 Modify parameters below as you need
#IMPORTANT       :
#bash_version    :4.2.25(1)-release
#==============================================================================

set -e

main(){

 sanity_check
 user_menu
 resolve_builds
 create_main_package
 create_update_package
# upload_release #TODO

}



sanity_check(){

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
CONF_FILE=${SCRIPT_DIR}/.build_new_release.conf
if [[ ! -f ${SCRIPT_DIR}/.build_new_release.conf ]]; then
	echo "No config file found ( ${SCRIPT_DIR}/.build_new_release.conf )! Exiting..."
	exit 1
else
	if egrep -q -v '^#|^[^ ]*=[^;]*' "$CONF_FILE"; then
		echo "Configfile not clean!"
		exit 1
	fi
	source "$CONF_FILE"
fi

if [[ -z $SRC_USER ]] || [[ -z $SRC_HOST ]] || [[ -z $TRGT_USER ]] || [[ -z $TRGT_HOST ]]; then
	echo "Error evaluating config-file parameters (SRC_USER, SRC_HOST, TRGT_USER or TRGT_HOST are not set)"
	exit 1
fi
# check for access (root&ssh)

}



user_menu(){

CURRENT_VER=$(ls -tr /opt/build/ | grep "metasfresh-[0-9]*_[0-9]*_[0-9]*" | tail -1 | cut -d "-" -f2 | tr "_" ".")
echo "======= Create new metasfresh release script =======

Latest already released version: $CURRENT_VER"
echo -n "New version: "
read NEW_VER
echo "


"
}



resolve_builds(){
echo "SRC_USER=$SRC_USER
SRC_HOST=$SRC_HOST
TRGT_USER=$TRGT_USER
TRGT_HOST=$TRGT_HOST
"

CURRENT_VER=$(ls -tr /opt/build/ | grep "metasfresh-[0-9]*_[0-9]*_[0-9]*" | tail -1 | cut -d "-" -f2 | tr "_" ".")
CURRENT_BUILD_FOLDER=/opt/build/$(ls -tr /opt/build/ | grep "metasfresh-[0-9]*_[0-9]*_[0-9]*" | tail -1)
NEW_VER_DB=$(ssh ${SRC_USER}@${SRC_HOST} 'ls -tr /home/metasfresh/build_storage/database/ | tail -1')
NEW_VER_CLEAN="metasfresh-$(echo $NEW_VER | tr "." "_")"
NEW_BUILD_FOLDER=/opt/build/$NEW_VER_CLEAN

echo "SRC_USER=$SRC_USER
SRC_HOST=$SRC_HOST
TRGT_USER=$TRGT_USER
TRGT_HOST=$TRGT_HOST
"
}



create_main_package(){

echo "--- Creating new build!"

mkdir -p $NEW_BUILD_FOLDER
rsync -ar $CURRENT_BUILD_FOLDER/* $NEW_BUILD_FOLDER
rm -r $NEW_BUILD_FOLDER/opt/metasfresh_install/apps/metasfresh/*
rm $NEW_BUILD_FOLDER/opt/metasfresh_install/database/metasfresh.pgdump
rsync -rae ssh ${SRC_USER}@${SRC_HOST}:/opt/metasfresh/* $NEW_BUILD_FOLDER/opt/metasfresh_install/apps/metasfresh
scp ${SRC_USER}@${SRC_HOST}:/home/metasfresh/build_storage/database/$NEW_VER_DB $NEW_BUILD_FOLDER/opt/metasfresh_install/database/metasfresh.pgdump
rm $NEW_BUILD_FOLDER/opt/metasfresh_install/apps/metasfresh/*.properties
rm $NEW_BUILD_FOLDER/opt/metasfresh_install/apps/metasfresh/*.conf
sed -i 's/'${CURRENT_VER}'/'${NEW_VER}'/g' $NEW_BUILD_FOLDER/DEBIAN/control
chown root:root -R $NEW_BUILD_FOLDER
cd /opt/ && dpkg-deb --build build/$NEW_VER_CLEAN
mv /opt/build/metasfresh*.deb /opt/build_storage/${NEW_VER_CLEAN}.deb
rm /opt/build_tar/metasfresh_install/metasfresh*.deb
cp -a /opt/build_storage/${NEW_VER_CLEAN}.deb /opt/build_tar/metasfresh_install
cd /opt/build_tar && tar cvzf /opt/build_tar/${NEW_VER_CLEAN}.tar.gz metasfresh_install
echo "--- done!"
echo "[INFO] New build available here: /opt/build_tar/${NEW_VER_CLEAN}.tar.gz"
echo "--- Copying database to /opt/build_db/"
cp -a $NEW_BUILD_FOLDER/opt/metasfresh_install/database/metasfresh.pgdump /opt/build_db/${NEW_VER_CLEAN}.pgdump
echo "--- Done!"

}



create_update_package(){
NEW_UPDATE_BUILD_FOLDER=$(ssh ${SRC_USER}@${SRC_HOST}"ls -tr /home/metasfresh/ | tail -1")
NEW_UPDATE_VER_CLEAN="metasfreshupdate_$(echo $NEW_VER | tr "." "_")"
echo "--- Creating Update Package!"
ssh ${SRC_USER}@${SRC_HOST} "rm /home/metasfresh/$NEW_UPDATE_BUILD_FOLDER/*dist.tar.gz && mv /home/metasfresh/$NEW_UPDATE_BUILD_FOLDER /home/metasfresh/$NEW_UPDATE_VER_CLEAN"
ssh ${SRC_USER}@${SRC_HOST} "cd /home/metasfresh/ && tar cvzf /home/metasfresh/build_storage/${NEW_UPDATE_VER_CLEAN}.tar.gz $NEW_UPDATE_VER_CLEAN"
scp ${SRC_USER}@${SRC_HOST}:/home/metasfresh/build_storage/${NEW_UPDATE_VER_CLEAN}.tar.gz /opt/mf_updates/
echo "--- done!"
echo "[INFO] New update available here: /opt/mf_updates/${NEW_UPDATE_VER_CLEAN}.tar.gz"

}


main
exit 0
