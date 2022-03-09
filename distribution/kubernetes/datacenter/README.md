## access cluster via cli with docker

### cd to directory in git repository to mount project
```
cd .\metasfresh\distribution\kubernetes
```
### run docker with digitalocean cli
```
docker run -it --rm -v ${PWD}\datacenter:/datacenter -w /datacenter --entrypoint /bin/bash digitalocean/doctl:1.70.0
```

### access cluster
```
mv /app/doctl /usr/local/bin
doctl auth init --context first
# enter token from digitalocean website (navigate to API)
doctl auth switch --context first
doctl kubernetes cluster kubeconfig save <CLUSTER ID> (navigate to Kubernetes -> select cluster -> copy cluster id )
```

### install kubectl
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

### install helm
```
export VERIFY_CHECKSUM=false (or install openssl)
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
./get_helm.sh
```

## initial setup of new cluster
### install nginx ingress-controller
```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
helm install nginx-ingress ingress-nginx/ingress-nginx --set controller.publishService.enabled=true --namespace nginx-ingress --create-namespace

```

### Securing the Ingress Using Cert-Manager

```
helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install cert-manager jetstack/cert-manager --version v1.7.1 --set installCRDs=true --namespace cert-manager --create-namespace
```

## using helm to deploy metasfresh
```
helm install issuer cert-manager-issuer-helm --namespace cert-manager --create-namespace
helm install dev metasfresh-helm --namespace demo --create-namespace
helm install dev pgadmin-helm --namespace demo --create-namespace
```

## pgadmin
- url: ingress.pgadmin.url as set in [values.yaml](./metasfresh-helm/values.yaml)
- login 
    - pgadmin.defaultEmail as set in [values.yaml](./metasfresh-helm/values.yaml)
    - pgadmin.defaultPassword as set in [values.yaml](./metasfresh-helm/values.yaml)
- create server
    - Host: name/address ```metasfresh-<releasename>-db```
    - Port: 5432
    - Username: db.username as set in [values.yaml](./metasfresh-helm/values.yaml)
    - Password: db.password as set in [values.yaml](./metasfresh-helm/values.yaml)

## debug
port forwarding: https://kubernetes.io/docs/tasks/access-application-cluster/port-forward-access-application-cluster/#forward-a-local-port-to-a-port-on-the-pod  
for example:
```
# kubectl port-forward -n <namespace> service/metasfresh-<releasename>-app 8788:8788
kubectl port-forward -n dev service/metasfresh-dev-app 8788:8788