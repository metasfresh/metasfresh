# Setup cert-manager

## Install cert manager
```
helm repo add jetstack https://charts.jetstack.io
```
```
helm repo update
```
```
helm install cert-manager jetstack/cert-manager --version v1.7.1 --set installCRDs=true --namespace cert-manager --create-namespace
```
<br><br>

## Setup cert provisioner
adjust mail in [values.yaml](./values.yaml) and run
```
helm install issuer cert-manager-issuer-helm --namespace cert-manager --create-namespace
```