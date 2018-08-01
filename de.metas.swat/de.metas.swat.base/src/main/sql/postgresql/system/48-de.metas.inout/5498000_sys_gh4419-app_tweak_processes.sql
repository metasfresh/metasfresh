-- 2018-07-26T10:06:43.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 10:06:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Wareneingang erst.' WHERE AD_Process_ID=540750 AND AD_Language='de_CH'
;

-- 2018-07-26T10:06:46.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 10:06:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540750 AND AD_Language='en_US'
;

-- 2018-07-26T10:07:52.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540992,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateMaterialReceipt','N',TO_TIMESTAMP('2018-07-26 10:07:52','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','N','N','N','N','N','Y',0,'Wareneingang mit Einlagerung erst.','N','Y','Java',TO_TIMESTAMP('2018-07-26 10:07:52','YYYY-MM-DD HH24:MI:SS'),100,'10000000')
;

-- 2018-07-26T10:07:52.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540992 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-07-26T10:08:20.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2018-07-26 10:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540992
;

-- 2018-07-26T10:08:38.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='WEBUI_M_HU_CreateMaterialReceipt_Select_DestinationLocator',Updated=TO_TIMESTAMP('2018-07-26 10:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540992
;

-- 2018-07-26T10:09:23.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541643,0,540992,541327,30,'M_Warehouse_Dest_ID',TO_TIMESTAMP('2018-07-26 10:09:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web',0,'Y','N','Y','N','N','N','Ziel-Lager',10,TO_TIMESTAMP('2018-07-26 10:09:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-26T10:09:23.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541327 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-07-26T10:11:21.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544182,0,'M_Locator_Dest_ID',TO_TIMESTAMP('2018-07-26 10:11:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Ziel-Lagerort','Ziel-Lagerort',TO_TIMESTAMP('2018-07-26 10:11:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-26T10:11:21.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544182 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-26T10:11:27.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 10:11:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544182 AND AD_Language='de_CH'
;

-- 2018-07-26T10:11:27.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544182,'de_CH') 
;

-- 2018-07-26T10:11:41.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 10:11:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Destination warehouse locator',PrintName='Destination warehouse locator' WHERE AD_Element_ID=544182 AND AD_Language='en_US'
;

-- 2018-07-26T10:11:41.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544182,'en_US') 
;

-- 2018-07-26T10:11:59.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 10:11:59','YYYY-MM-DD HH24:MI:SS'),Name='Destination warehouse locator',PrintName='Destination warehouse locator' WHERE AD_Element_ID=541643 AND AD_Language='en_GB'
;

-- 2018-07-26T10:11:59.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541643,'en_GB') 
;

-- 2018-07-26T10:12:01.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541643 AND AD_Language='en_GB'
;

-- 2018-07-26T10:12:08.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 10:12:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Destination warehouse locator',PrintName='Destination warehouse locator' WHERE AD_Element_ID=541643 AND AD_Language='en_US'
;

-- 2018-07-26T10:12:08.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541643,'en_US') 
;

-- 2018-07-26T10:12:15.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-26 10:12:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=541643 AND AD_Language='de_CH'
;

-- 2018-07-26T10:12:15.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541643,'de_CH') 
;

-- 2018-07-26T10:12:19.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541643 AND AD_Language='fr_CH'
;

-- 2018-07-26T10:12:21.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=541643 AND AD_Language='it_CH'
;

-- 2018-07-26T10:13:04.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544182,0,540992,541329,30,127,'M_Locator_Dest_ID',TO_TIMESTAMP('2018-07-26 10:13:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web',0,'Y','Y','Y','N','N','N','Ziel-Lagerort',20,TO_TIMESTAMP('2018-07-26 10:13:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-26T10:13:04.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541329 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-07-26T10:13:08.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2018-07-26 10:13:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541327
;

-- 2018-07-26T10:13:44.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540992,540516,TO_TIMESTAMP('2018-07-26 10:13:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2018-07-26 10:13:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-07-26T10:28:04.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540420,Updated=TO_TIMESTAMP('2018-07-26 10:28:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541327
;

-- 2018-07-26T10:28:11.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=191,Updated=TO_TIMESTAMP('2018-07-26 10:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541329
;

-- 2018-07-26T10:31:41.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2018-07-26 10:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540992 AND AD_Table_ID=540516
;

-- 2018-07-26T10:49:07.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_NoParams', Value='WEBUI_M_HU_CreateReceipt_NoParams',Updated=TO_TIMESTAMP('2018-07-26 10:49:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540750
;

-- 2018-07-26T10:52:27.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='WEBUI_M_HU_CreateReceipt_LocatorParams',Updated=TO_TIMESTAMP('2018-07-26 10:52:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540992
;

-- 2018-07-26T10:52:35.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_LocatorParams',Updated=TO_TIMESTAMP('2018-07-26 10:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540992
;
