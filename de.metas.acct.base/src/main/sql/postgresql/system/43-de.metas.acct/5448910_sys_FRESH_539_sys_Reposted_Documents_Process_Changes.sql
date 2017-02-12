-- 29.07.2016 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2016-07-29 15:44:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540709
;

-- 29.07.2016 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='30 1 * * *', EntityType='de.metas.a',Updated=TO_TIMESTAMP('2016-07-29 15:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550039
;


-- 29.07.2016 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53280,0,540709,541001,15,'DateStart',TO_TIMESTAMP('2016-07-29 15:58:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct',0,'Y','N','Y','N','N','N','DateStart',10,TO_TIMESTAMP('2016-07-29 15:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.07.2016 15:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541001 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 29.07.2016 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2016-07-29 15:59:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550039
;

