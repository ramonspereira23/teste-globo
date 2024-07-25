FROM openjdk:17-slim

# Definindo variáveis de ambiente
ENV GRADLE_VERSION=7.6.1
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${GRADLE_HOME}/bin:${PATH}

# Instalando dependências e Gradle
RUN apt-get update \
    && apt-get install -y wget unzip \
    && wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt/ \
    && ln -s /opt/gradle-${GRADLE_VERSION} ${GRADLE_HOME} \
    && rm gradle-${GRADLE_VERSION}-bin.zip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Criando o diretório da aplicação
WORKDIR /app

# Copiando os arquivos da aplicação para o contêiner
COPY . .

# Construindo a aplicação
RUN gradle build --no-daemon


RUN ls
# # Encontrar o JAR gerado e mover para o diretório /app
# RUN JAR_FILE=$(find build/libs -name '*.jar') && \
#     cp $JAR_FILE costmate.jar

# # Verificar se o JAR foi copiado com sucesso
# RUN ls -l costmate.jar

# # Expondo a porta (ajuste conforme necessário)
# EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "costmate.jar"]