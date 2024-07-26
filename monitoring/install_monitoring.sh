#!/bin/bash

# Adiciona o repositório do Prometheus e atualiza
echo "Adicionando o repositório do Prometheus..."
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update

# Instala o Prometheus
echo "Instalando o Prometheus..."
helm upgrade --install prometheus prometheus-community/prometheus

# Adiciona o repositório do Grafana e atualiza
echo "Adicionando o repositório do Grafana..."
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

# Instala o Grafana
echo "Instalando o Grafana..."
helm upgrade --install grafana grafana/grafana

# Obtém a senha do Grafana
echo "Obtendo a senha do Grafana..."
PASSWORD=$(kubectl get secret --namespace default grafana -o jsonpath="{.data.admin-password}" | base64 --decode)
echo "Senha do Grafana: $PASSWORD"

# Exibe a senha do Grafana (caso a senha seja precisa ser fornecida para login)
echo "A senha do Grafana é: $PASSWORD"

# Redireciona a porta do Grafana
echo "Redirecionando a porta do Grafana para http://localhost:3000..."

kubectl port-forward service/grafana 3000:80