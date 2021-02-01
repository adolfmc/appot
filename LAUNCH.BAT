@echo off  
cd C:\Users\Administrator\IdeaProjects\appot
start mvn clean -Dmaven.test.skip=true package wagon:upload-single  wagon:sshexec

exit