#!/bin/bash

echo "starting minikube"
# uncomment mount, if you want db,logs etc stored on your system and not only inside minikube
# without mount data is lost on restart
# absolute path is needed
minikube start --addons ingress #--mount-string "/home/vm2/metasfresh/kubernetes/volumes:/volumes" --mount #--memory 8192 --cpus 2

echo "waiting for ingress-nginx-admission (validatingwebhookconfiguration)"
sleep 30

echo "starting installation with helm"
# modify release-name, dir and namespace if needed
# helm install <release-name> <dir> --namespace <namespace> --create-namespace
helm install demo metasfresh-helm --namespace demo --create-namespace
