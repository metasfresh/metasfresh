
-- 28.08.2015 13:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) 
SELECT 0,0,540876,'S',TO_TIMESTAMP('2015-08-28 13:43:27','YYYY-MM-DD HH24:MI:SS'),100,'List of resource name endings which shall be interpreted as Organization Logo when the jasper reports are generated. The list is separated by semi-colon (i.e. ";")','de.metas.adempiere.adempiereJasper','Y','de.metas.adempiere.report.jasper.OrgLogoClassLoaderHook.ResourceNameEndsWith',TO_TIMESTAMP('2015-08-28 13:43:27','YYYY-MM-DD HH24:MI:SS'),100,'de/metas/generics/logo.png'
WHERE NOT EXISTS (select 1 from AD_SysConfig where AD_SysConfig_ID=540876)
;

