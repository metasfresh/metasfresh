-- 2019-01-11T17:49:20.237
-- #298 changing anz. stellen
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) 

SELECT 0,0,541257,'S',TO_TIMESTAMP('2019-01-11 17:49:20','YYYY-MM-DD HH24:MI:SS'),100,'Applies only for Excel Open XML format.

Y - (enabled) the streaming version of workbook will be used, i.e. SXSSFWorkbook, see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/streaming/SXSSFWorkbook.html

N - (disabled) the regular implementation will be used, i.e. XSSFWorkbook, see https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html','D','Y','de.metas.excel.UseStreamingWorkbookImplementation',TO_TIMESTAMP('2019-01-11 17:49:20','YYYY-MM-DD HH24:MI:SS'),100,'Y'
WHERE NOT EXISTS (select 1 from AD_SysConfig where Name='de.metas.excel.UseStreamingWorkbookImplementation')
;

