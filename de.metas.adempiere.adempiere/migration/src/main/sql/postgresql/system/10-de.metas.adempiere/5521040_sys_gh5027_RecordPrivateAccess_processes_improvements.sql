-- 2019-05-07T14:29:43.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=30,Updated=TO_TIMESTAMP('2019-05-07 14:29:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541400
;

-- 2019-05-07T14:29:46.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2019-05-07 14:29:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541399
;

-- 2019-05-07T14:30:49.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,576702,0,541097,541445,17,540992,'PrincipalType',TO_TIMESTAMP('2019-05-07 14:30:49','YYYY-MM-DD HH24:MI:SS'),100,'G','D',0,'Y','N','Y','N','Y','N','Typ',10,TO_TIMESTAMP('2019-05-07 14:30:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-07T14:30:49.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541445 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-05-07T14:31:00.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@PrincipalType@=U', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-05-07 14:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541399
;

-- 2019-05-07T14:31:09.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@PrincipalType@=G', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-05-07 14:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541400
;

-- 2019-05-07T14:33:06.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=30,Updated=TO_TIMESTAMP('2019-05-07 14:33:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541402
;

-- 2019-05-07T14:33:08.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2019-05-07 14:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541401
;

-- 2019-05-07T14:33:38.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,576702,0,541098,541446,17,540992,'PrincipalType',TO_TIMESTAMP('2019-05-07 14:33:38','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Typ',10,TO_TIMESTAMP('2019-05-07 14:33:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-07T14:33:38.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541446 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-05-07T14:33:49.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@PrincipalType@=U', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-05-07 14:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541401
;

-- 2019-05-07T14:33:57.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@PrincipalType@=G', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-05-07 14:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541402
;

-- 2019-05-07T14:34:50.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='G',Updated=TO_TIMESTAMP('2019-05-07 14:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541446
;

