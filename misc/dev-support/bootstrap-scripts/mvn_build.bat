
REM thx to https://stackoverflow.com/questions/4036754/why-does-only-the-first-line-of-this-windows-batch-file-execute-but-all-three-li
call mvn --file ..\..\parent-pom\pom.xml -DskipTests --settings ..\maven\settings.xml clean install
call mvn --file ..\..\de-metas-common\pom.xml -DskipTests --settings ..\maven\settings.xml clean install
call mvn --file ..\..\..\backend\pom.xml -T2C -DskipTests --settings ..\maven\settings.xml clean install
