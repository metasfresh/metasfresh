#!/bin/bash
set -e

template_file="metasfresh-all-in-one-deployment_template.yaml"
user_id=$1 # "tobi"
stage=$2 # "demo"

deployment_file="metasfresh-all-in-one-deployment_${user_id}_${stage}.yaml"

cat ${template_file}\
 | sed "s/%USER-ID%/${user_id}/g"\
 | sed "s/%STAGE%/${stage}/g"\
 > ${deployment_file}

kubectl create --save-config --filename ${deployment_file}
 
# TODO: commit the manifest that we just deployed to kabernetes
#git add ${deployment_file}
#git commit -m "blah blah" ${deployment_file}

# TODO: find out which node we deployed the pod to and label the node to make the pod stick