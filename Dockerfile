FROM openjdk:11.0.6-jdk
VOLUME /tmp
ADD website-0.0.1-SNAPSHOT.jar /wendev.jar
RUN sh -c 'touch /wendev.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /wendev.jar" ]
