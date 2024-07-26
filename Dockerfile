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


WORKDIR /app

COPY . .

RUN gradle build -x test --no-daemon

CMD java -jar build/libs/costmate-0.0.1-SNAPSHOT.jar