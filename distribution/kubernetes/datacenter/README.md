# access cluster via cli with docker

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

### using helm
```
helm install <release-name> <dir> --namespace <namespace> --create-namespace
helm install dev metasfresh-helm --namespace dev --create-namespace
helm upgrade dev metasfresh-helm --namespace dev
helm delete dev --namespace dev
```