# GitRestApiChallenge
build
> ./gradlew --no-daemon --stacktrace clean bootJar

start 
> ./gradlew --no-daemon --stacktrace clean bootRun

dockerbuild
> docker build .

docker run
> docker run -p 8080:8080 {HashCode}
