-- 2022-02-26T16:22:13.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,562883,'O',TO_TIMESTAMP('2022-02-26 18:22:13','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.report.jasper.IsMockReportService',TO_TIMESTAMP('2022-02-26 18:22:13','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2022-02-26T16:22:36.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', EntityType='D',Updated=TO_TIMESTAMP('2022-02-26 18:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=562883
;

-- 2022-02-26T16:22:39.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2022-02-26 18:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=562883
;

-- 2022-02-26T16:22:44.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET AD_Org_ID=0, ConfigurationLevel='O',Updated=TO_TIMESTAMP('2022-02-26 18:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=562883
;

-- 2022-02-26T16:23:21.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Mock report service during cucumber tests',Updated=TO_TIMESTAMP('2022-02-26 18:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=562883
;

