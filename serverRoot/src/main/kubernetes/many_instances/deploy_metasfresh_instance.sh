#!/bin/bash
set -e

create_target_file()
{
	local template_file=$1
	local target_file=$2
	local user_id=$3
	local stage=$4
	
	cat ${template_file}\
	 | sed "s/%USER-ID%/${user_id}/g"\
	 | sed "s/%STAGE%/${stage}/g"\
	 > ${target_file}
}

user_id=$1 # "tobi"
stage=$2 # "demo"

deployment_file="./created/metasfresh-all-in-one-deployment_${user_id}_${stage}.yaml"
service_file="./created/metasfresh-all-in-one-service_${user_id}_${stage}.yaml"

create_target_file "./template/metasfresh-all-in-one-deployment_template.yaml" ${deployment_file} $user_id $stage
create_target_file "./template/metasfresh-all-in-one-service_template.yaml" ${service_file} $user_id $stage
 
#kubectl create --save-config --filename ./created

# TODO: commit the manifest that we just deployed to kubernetes
#git add ${deployment_file}
#git commit -m "blah blah" ${deployment_file}

# TODO: find out which node we deployed the pod to and label the node to make the pod stick
#nodename="node-name"
#kubectl label nodes "node-name" "de.metas.deployment.${user_id}-${stage}"="metasfresh-all-in-one-${user_id}-${stage}"

#echo "  #"  >> ${deployment_file}
#echo "  nodeSelector:" >> ${deployment_file}
#echo "    de.metas.deployment.${user_id}-${stage}: metasfresh-all-in-one-${user_id}-${stage}" >> ${deployment_file}