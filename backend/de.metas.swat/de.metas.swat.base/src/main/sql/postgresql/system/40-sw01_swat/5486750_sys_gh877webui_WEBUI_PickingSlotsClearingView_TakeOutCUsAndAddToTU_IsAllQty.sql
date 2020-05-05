-- 2018-02-25T12:05:39.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'All Quantity','D','IsAllQty',543912,0,'All Quantity',100,TO_TIMESTAMP('2018-02-25 12:05:38','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-25 12:05:38','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-25T12:05:39.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543912 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-02-25T12:09:52.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,DefaultValue,IsCentrallyMaintained,AD_Element_ID,EntityType,IsEncrypted,DisplayLogic,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540901,20,'N',541268,'N','Y',543912,'de.metas.picking','N','1=2','IsAllQty','Y','N',0,'All Quantity',100,TO_TIMESTAMP('2018-02-25 12:09:52','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-25 12:09:52','YYYY-MM-DD HH24:MI:SS'),0,20)
;

-- 2018-02-25T12:09:52.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541268 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-02-25T12:10:17.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@IsAllQty/N@=N', IsMandatory='N',Updated=TO_TIMESTAMP('2018-02-25 12:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541253
;

-- 2018-02-25T12:11:41.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2018-02-25 12:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541268
;

-- 2018-02-25T12:11:57.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=5,Updated=TO_TIMESTAMP('2018-02-25 12:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541268
;

-- 2018-02-25T12:14:04.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2018-02-25 12:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541268
;

