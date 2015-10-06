@Title Copy Database after backup
@Rem $Id: myDBcopyTemplate.bat,v 1.4 2003/12/04 04:38:27 jjanke Exp $

@Echo Modify the script myDBcopy to copy the database backup

@Rem 	This example creates one unique file per day in a month
@Rem	You may want to copy it to another disk first
@Rem	Note that the %DATE% parameter is local specific.
@Rem	In Germany, it is %DATE:~3,2%
@Rem	When called, the following files were created:
@Rem		%ADEMPIERE_HOME%\data\ExpDat.dmp
@Rem		%ADEMPIERE_HOME%\data\ExpDat.log
@Rem		%ADEMPIERE_HOME%\data\ExpDat.jar (containing the above)

@set DATETIME=%date:~6,4%%date:~3,2%%date:~0,2%_%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%
@Echo Creating ExpDat_%DATETIME%.jar
ren ExpDat.jar "ExpDat%DATETIME%.jar"
@dir ExpDat%DATETIME%.jar

@Echo copy %ADEMPIERE_HOME%\data\ExpDat%DATETIME%.jar to backup media

@Rem Sleep 30
@CHOICE /C YN /T 30 /D N > NUL