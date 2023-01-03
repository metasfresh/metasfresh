-- 2022-01-04T15:54:42.557208500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541440,'O',TO_TIMESTAMP('2022-01-04 17:54:42','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.printing.UserQuery_Example',TO_TIMESTAMP('2022-01-04 17:54:42','YYYY-MM-DD HH24:MI:SS'),100,'ItemName=''Rechnung'' AND Processed=''N''')
;

-- 2022-01-04T15:54:48.295671300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', IsActive='N',Updated=TO_TIMESTAMP('2022-01-04 17:54:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541440
;

-- 2022-01-04T16:01:11.743439900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='This sys config is used de.metas.printing.model.validator.ConcatenatePDFsCommand#enqueuePrintQueues for filter which printing queues to enqueue.',Updated=TO_TIMESTAMP('2022-01-04 18:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541440
;

