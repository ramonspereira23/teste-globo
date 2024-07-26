#!/bin/bash



# Variáveis para a nova imagem e tag, atualizar após execução do pipeline no Github Actions que resultará numa nova image e tag!
IMAGE="ramonglobo/app-globo"
TAG="latest"


# Define os arquivos YAML
DATABASE_YAML="database.yaml"
APP_YAML="app.yaml"

# Aplica os manifests do Kubernetes
echo "Aplicando o manifesto do banco de dados..."
kubectl apply -f "$DATABASE_YAML"

if [ $? -eq 0 ]; then
  echo "Manifesto do banco de dados aplicado com sucesso."
else
  echo "Falha ao aplicar o manifesto do banco de dados."
  exit 1
fi

echo # Linha em branco para melhorar a legibilidade

echo "Atualizando a imagem e a tag no manifesto da aplicação..."

# Atualiza a imagem e a tag no arquivo app.yaml
sed -i '' "s|image: ${IMAGE}:.*|image: ${IMAGE}:${TAG}|g" "$APP_YAML"

if [ $? -eq 0 ]; then
  echo "Imagem e tag atualizadas no manifesto da aplicação com sucesso."
else
  echo "Falha ao atualizar a imagem e a tag no manifesto da aplicação."
  exit 1
fi

echo # Linha em branco para melhorar a legibilidade

echo "Aplicando o manifesto da aplicação..."
kubectl apply -f "$APP_YAML"

if [ $? -eq 0 ]; then
  echo "Manifesto da aplicação aplicado com sucesso."
else
  echo "Falha ao aplicar o manifesto da aplicação."
  exit 1
fi

echo # Linha em branco para melhorar a legibilidade

sleep 60
# Inicia o port-forward para o serviço
echo "Iniciando o port-forward para o serviço globo-service..."
kubectl port-forward svc/globo-service 8080:80 -n globo
