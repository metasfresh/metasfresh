-- 2019-02-11T18:14:51.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544209,0,540969,541353,20,'IsShowAllParams',TO_TIMESTAMP('2019-02-11 18:14:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','1=2','de.metas.ui.web',0,'Y','N','Y','N','Y','N','Display All Parameters',20,TO_TIMESTAMP('2019-02-11 18:14:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-11T18:14:51.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541353 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-02-11T18:17:49.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@IsShowAllParams@=''Y''',Updated=TO_TIMESTAMP('2019-02-11 18:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541302
;

-- 2019-02-11T18:32:53.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544209,0,540873,541354,20,'IsShowAllParams',TO_TIMESTAMP('2019-02-11 18:32:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','1=2','de.metas.ui.web',0,'Y','N','Y','N','N','N','Display All Parameters',20,TO_TIMESTAMP('2019-02-11 18:32:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-11T18:32:53.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541354 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-02-11T18:33:02.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@IsShowAllParams@=''Y''',Updated=TO_TIMESTAMP('2019-02-11 18:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541224
;

-- 2019-02-11T18:34:36.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='',Updated=TO_TIMESTAMP('2019-02-11 18:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541224
;

-- 2019-02-11T18:34:42.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541354
;

-- 2019-02-11T18:34:42.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541354
;

