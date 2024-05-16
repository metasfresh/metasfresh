# How to run metasfresh in Kubernetes Cluster (DigitalOcean example)

## Create Kubernetes Cluster on DigitalOcean
https://cloud.digitalocean.com/
<br><br>

## Run docker with digitalocean cli

Run this from within the kubernetes directory,
to make sure that the distribution folder is mounted into the right place within the docker container.
In the end we want to see distribution/metasfresh-helm within the digitalocean docker container.

```
docker run -it --rm -v ${PWD}\datacenter:/datacenter -w /datacenter --entrypoint /bin/bash digitalocean/doctl:1.70.0
```
<br><br>

## Connect to cluster
```
mv /app/doctl /usr/local/bin
```
```
doctl auth init --context demo
```

enter token from digitalocean website (navigate to API)

```
doctl auth switch --context demo
```
enter cluster id from digitalocean website
(navigate to Kubernetes -> select cluster -> copy cluster id)

```
doctl kubernetes cluster kubeconfig save <CLUSTER ID> 
```
<br><br>

## Install kubectl
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
```
```
install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```
<br><br>

## Install helm
```
export VERIFY_CHECKSUM=false # (or install openssl)
```
```
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod u+x ./get_helm.sh
```
```
./get_helm.sh
```
<br><br>

## Install nginx ingress-controller
```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
```
```
helm repo update
```
```
helm install nginx-ingress ingress-nginx/ingress-nginx --set controller.publishService.enabled=true --namespace nginx-ingress --create-namespace
```  

<br><br>

## Add TLS for Ingress with [Cert-Manager](./cert-manager-issuer-helm/README.md)

<br><br>

## Use helm to deploy metasfresh
set [values.yaml](./metasfresh-helm/values.yaml) and run
```
helm install demo metasfresh-helm --namespace demo --create-namespace
```
<br><br>

## Optional additions
- [pgadmin](./pgadmin-helm/README.md)

