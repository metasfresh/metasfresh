-- Feb 15, 2017 1:15 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_UsingConfig', Description='Receive HUs using custom configuration', EntityType='de.metas.ui.web', Value='WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_UsingConfig',Updated=TO_TIMESTAMP('2017-02-15 13:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540753
;

-- Feb 15, 2017 1:15 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540753
;

-- Feb 15, 2017 1:16 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-02-15 13:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541135
;

-- Feb 15, 2017 1:16 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-02-15 13:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541136
;

-- Feb 15, 2017 1:16 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-02-15 13:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541137
;

-- Feb 15, 2017 1:16 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-02-15 13:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541138
;

-- Feb 15, 2017 1:16 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-02-15 13:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541139
;

-- Feb 15, 2017 1:16 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-02-15 13:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540753 AND AD_Table_ID=540524
;

-- Feb 15, 2017 1:17 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_UsingDefaults', Description='Receive HUs using default configuration', EntityType='de.metas.ui.web', Value='WEBUI_M_ReceiptSchedule_GeneratePlanningHUs_UsingDefaults',Updated=TO_TIMESTAMP('2017-02-15 13:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540749
;

-- Feb 15, 2017 1:17 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540749
;

-- Feb 15, 2017 1:17 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-02-15 13:17:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540749 AND AD_Table_ID=540524
;

-- Feb 15, 2017 1:18 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543278,0,'IsSaveLUTUConfiguration',TO_TIMESTAMP('2017-02-15 13:18:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Save configuration','Save configuration',TO_TIMESTAMP('2017-02-15 13:18:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Feb 15, 2017 1:18 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543278 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Feb 15, 2017 1:19 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543278,0,540753,541151,20,'IsSaveLUTUConfiguration',TO_TIMESTAMP('2017-02-15 13:19:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ui.web',0,'Y','N','Y','N','Y','N','Save configuration',5,TO_TIMESTAMP('2017-02-15 13:19:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Feb 15, 2017 1:19 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541151 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Feb 15, 2017 6:35 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-02-15 18:35:40','YYYY-MM-DD HH24:MI:SS'),Description='Receive HUs using custom configuration' WHERE AD_Process_ID=540753 AND AD_Language='en_US'
;

-- Feb 15, 2017 6:35 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-02-15 18:35:49','YYYY-MM-DD HH24:MI:SS'),Description='Receive HUs using default configuration' WHERE AD_Process_ID=540749 AND AD_Language='en_US'
;

