FROM FROM amazoncorretto:11
ADD target/jm_311-0.0.1-SNAPSHOT.jar jm_pp_313-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jm_pp_313-0.0.1-SNAPSHOT.jar"]