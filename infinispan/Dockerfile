ARG INFINISPAN_VERSION
ARG KEYCLOAK_VERSION
FROM quay.io/infinispan/server:${INFINISPAN_VERSION:-13.0.6.Final}
COPY ./conf/infinispan-keycloak.xml /opt/infinispan/server/conf/infinispan-keycloak.xml
COPY ./conf/infinispan-jgroups.xml /opt/infinispan/server/conf/jgroups.xml
ADD https://repo1.maven.org/maven2/org/keycloak/keycloak-model-infinispan/${KEYCLOAK_VERSION:-17.0.0}/keycloak-model-infinispan-${KEYCLOAK_VERSION:-17.0.0}.jar /opt/infinispan/server/lib
ADD https://repo1.maven.org/maven2/org/infinispan/infinispan-client-hotrod/${INFINISPAN_VERSION:-13.0.6.Final}/infinispan-client-hotrod-${INFINISPAN_VERSION:-13.0.6.Final}.jar /opt/infinispan/server/lib

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
