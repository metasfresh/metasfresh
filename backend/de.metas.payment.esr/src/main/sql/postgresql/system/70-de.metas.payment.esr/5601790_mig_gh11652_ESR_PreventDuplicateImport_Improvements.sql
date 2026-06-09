




CREATE TABLE backup.BKP_AD_SysConfig_ESR_PreventDuplicateImportFiles_25082021
AS
    SELECT * from ad_sysconfig
WHERE  AD_SYSConfig_ID = 540535;











-- 2021-08-25T11:14:32.989Z
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='When the value is ''Y'', the duplicate ESR files will not be imported again and an error message will be shown. When the value is ''N'', a file can be imported many times.
A file is called a duplicate when its content is identical to another file that was already imported in ESR, even if the name of the file is different.
If the duplicate file belongs to a zip import file and the configuration is on ''''Y'''', nothing will be imported from that zip.''
',Updated=TO_TIMESTAMP('2021-08-25 14:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540535
;


UPDATE AD_SYSConfig c
SET VALUE = (CASE WHEN VALUE IN ('W', 'E') THEN 'Y' ELSE 'N' END)
WHERE AD_SYSConfig_ID = 540535;

