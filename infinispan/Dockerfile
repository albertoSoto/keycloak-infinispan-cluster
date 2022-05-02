ARG INFINISPAN_VERSION
ARG KEYCLOAK_VERSION
ARG MYSQL_DRIVER_VERSION
FROM quay.io/infinispan/server:${INFINISPAN_VERSION:-13.0.6.Final}
COPY ./conf/infinispan-keycloak.xml /opt/infinispan/server/conf/infinispan-keycloak.xml
COPY ./conf/infinispan-jgroups.xml /opt/infinispan/server/conf/jgroups.xml
#ENV JAVA_OPTS="-jar /opt/infinispan/server/lib/keycloak-model-infinispan-17.0.0.jar -jar /opt/infinispan/server/lib/mysql-connector-java-8.0.28.jar"
ADD https://repo1.maven.org/maven2/org/keycloak/keycloak-model-infinispan/${KEYCLOAK_VERSION:-17.0.0}/keycloak-model-infinispan-${KEYCLOAK_VERSION:-17.0.0}.jar /opt/infinispan/server/lib
ADD https://repo1.maven.org/maven2/org/infinispan/infinispan-client-hotrod/${INFINISPAN_VERSION:-13.0.6.Final}/infinispan-client-hotrod-${INFINISPAN_VERSION:-13.0.6.Final}.jar /opt/infinispan/server/lib
#ADD https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_DRIVER_VERSION:-5.1.49}/mysql-connector-java-${MYSQL_DRIVER_VERSION:-5.1.49}.jar /opt/infinispan/server/lib
ADD https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_DRIVER_VERSION:-8.0.28}/mysql-connector-java-${MYSQL_DRIVER_VERSION:-8.0.28}.jar /opt/infinispan/server/lib
USER root
RUN chmod -R 777 /opt/infinispan/server/lib
USER jboss
CMD [ "-c", "/opt/infinispan/server/conf/infinispan-keycloak.xml" ]

# Alternatives
#USER 0
#RUN true \
#  && microdnf clean all \
#  && microdnf install shadow-utils \
#  && microdnf update --nodocs \
#  && adduser ispn \
#  && microdnf remove shadow-utils \
#  && microdnf clean all
#RUN chown -R ispn:0 /opt/infinispan
#USER ispn
#Specify one or more configuration files with the --server-config= or -c argument, for example:
#ENTRYPOINT [ "/opt/infinispan/bin/server.sh" ]
