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

# Expondo a porta (ajuste conforme necessário)
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "build/libs/*.jar"]
