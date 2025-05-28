@echo off
echo Setting JAVA_HOME
set JAVA_HOME=C:\Users\steve\.jdks\openjdk-24.0.1
echo setting PATH
set PATH=C:\Users\steve\.jdks\openjdk-24.0.1\bin;%PATH%
echo Display java version
java -version
cd /d "%~dp0"
start cmd.exe /c "gradlew.bat shadowJar & java -jar build\libs\Bomba-1.0-SNAPSHOT-all.jar client localhost 8080"
