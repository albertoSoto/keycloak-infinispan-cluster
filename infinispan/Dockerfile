ARG INFINISPAN_VERSION
FROM quay.io/infinispan/server:${INFINISPAN_VERSION:-13.0.5.Final-2}
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
CMD [ "-c", "/opt/infinispan/server/conf/infinispan-keycloak.xml" ]
#ENTRYPOINT [ "/opt/infinispan/bin/server.sh" ]
