FROM mamohr/centos-java:jre8

WORKDIR /apps/
COPY target/demo-server.jar /apps/

CMD java -jar /apps/demo-server.jar --spring.profiles.active=v3