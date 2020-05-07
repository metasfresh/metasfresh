
--add inactive records

-- 2018-10-17T08:07:25.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2018-10-17 08:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540833
;

-- add inactive record for the cable vertical, if not yet exists
-- 2018-09-17T11:41:47.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,AD_Org_ID,Name,EntityType) 
SELECT 0,TO_TIMESTAMP('2018-09-17 11:41:47','YYYY-MM-DD HH24:MI:SS'),100,'N','S',TO_TIMESTAMP('2018-09-17 11:41:47','YYYY-MM-DD HH24:MI:SS'),100,541233,'de.metas.vertical.cables',0,'de.metas.ui.web.spring.profiles.active1','de.metas.ui.web'
WHERE NOT EXISTS (select 1 from AD_SysConfig where AD_SysConfig_ID=541233)
;
