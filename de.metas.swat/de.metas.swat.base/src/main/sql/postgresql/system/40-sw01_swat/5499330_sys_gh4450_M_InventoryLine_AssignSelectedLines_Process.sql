-- 2018-08-14T15:25:54.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540996,'Y','de.metas.inventory.process.M_InventoryLine_AssignSelectedLines','N',TO_TIMESTAMP('2018-08-14 15:25:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','N','N','N','N','Y',0,'Ausgewählte Zeilen zuweisen','N','Y','Java',TO_TIMESTAMP('2018-08-14 15:25:53','YYYY-MM-DD HH24:MI:SS'),100,'M_InventoryLine_AssignSelectedLines')
;

-- 2018-08-14T15:25:54.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540996 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-08-14T15:26:06.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Name='Assign Selected Lines' WHERE AD_Process_ID=540996 AND AD_Language='en_US'
;

-- 2018-08-14T15:28:20.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544206,0,'NumberOfCounters',TO_TIMESTAMP('2018-08-14 15:28:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','NumberOfCounters','NumberOfCounters',TO_TIMESTAMP('2018-08-14 15:28:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-14T15:28:20.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544206 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-14T15:28:46.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544206,0,540996,541333,11,'NumberOfCounters',TO_TIMESTAMP('2018-08-14 15:28:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat',0,'Y','N','Y','N','N','N','NumberOfCounters',10,TO_TIMESTAMP('2018-08-14 15:28:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-14T15:28:46.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541333 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-08-14T15:30:49.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y', ValueMax='25', ValueMin='1',Updated=TO_TIMESTAMP('2018-08-14 15:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541333
;

-- 2018-08-14T15:32:34.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540996,322,540458,TO_TIMESTAMP('2018-08-14 15:32:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y',TO_TIMESTAMP('2018-08-14 15:32:34','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;



-- 2018-08-14T16:44:21.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Anzahl Inventur Zähler', PrintName='Anzahl Inventur Zähler',Updated=TO_TIMESTAMP('2018-08-14 16:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544206
;

-- 2018-08-14T16:44:21.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NumberOfCounters', Name='Anzahl Inventur Zähler', Description=NULL, Help=NULL WHERE AD_Element_ID=544206
;

-- 2018-08-14T16:44:21.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NumberOfCounters', Name='Anzahl Inventur Zähler', Description=NULL, Help=NULL, AD_Element_ID=544206 WHERE UPPER(ColumnName)='NUMBEROFCOUNTERS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-14T16:44:21.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NumberOfCounters', Name='Anzahl Inventur Zähler', Description=NULL, Help=NULL WHERE AD_Element_ID=544206 AND IsCentrallyMaintained='Y'
;

-- 2018-08-14T16:44:21.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anzahl Inventur Zähler', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544206) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544206)
;

-- 2018-08-14T16:44:21.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anzahl Inventur Zähler', Name='Anzahl Inventur Zähler' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544206)
;

-- 2018-08-14T16:44:41.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Number Inventory Counters',PrintName='Number Inventory Counters' WHERE AD_Element_ID=544206 AND AD_Language='en_US'
;

-- 2018-08-14T16:44:41.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544206,'en_US') 
;

