FROM mamohr/centos-java:jre8

WORKDIR /apps/
COPY target/demo-client.jar /apps/

CMD java -jar /apps/demo-client.jar --spring.profiles.active=v2