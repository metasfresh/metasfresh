-- 2022-09-09T10:49:40.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581436,0,'Counterpart_BankStatement_ID',TO_TIMESTAMP('2022-09-09 13:49:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gegenkontoauszug','Gegenkontoauszug',TO_TIMESTAMP('2022-09-09 13:49:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T10:49:40.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581436 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-09T10:49:58.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-09 13:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581436 AND AD_Language='de_CH'
;

-- 2022-09-09T10:49:58.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581436,'de_CH') 
;

-- 2022-09-09T10:50:00.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-09 13:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581436 AND AD_Language='de_DE'
;

-- 2022-09-09T10:50:00.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581436,'de_DE') 
;

-- 2022-09-09T10:50:00.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581436,'de_DE') 
;

-- 2022-09-09T10:50:14.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Counterpart Bank Statement', PrintName='Counterpart Bank Statement',Updated=TO_TIMESTAMP('2022-09-09 13:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581436 AND AD_Language='en_US'
;

-- 2022-09-09T10:50:14.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581436,'en_US') 
;

-- 2022-09-09T10:50:45.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581437,0,'Counterpart_BankStatementLine_ID',TO_TIMESTAMP('2022-09-09 13:50:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gegenkontoauszug Position','Gegenkontoauszug Position',TO_TIMESTAMP('2022-09-09 13:50:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T10:50:45.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581437 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-09T10:51:04.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Counterpart Bank Statement', PrintName='Counterpart Bank Statement',Updated=TO_TIMESTAMP('2022-09-09 13:51:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581437 AND AD_Language='en_US'
;

-- 2022-09-09T10:51:04.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581437,'en_US') 
;

-- 2022-09-09T10:51:05.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-09 13:51:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581437 AND AD_Language='de_DE'
;

-- 2022-09-09T10:51:05.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581437,'de_DE') 
;

-- 2022-09-09T10:51:05.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581437,'de_DE') 
;

-- 2022-09-09T10:51:07.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-09 13:51:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581437 AND AD_Language='de_CH'
;

-- 2022-09-09T10:51:07.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581437,'de_CH') 
;

-- 2022-09-09T11:13:10.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,
                        IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585106,'Y','de.metas.banking.process.C_BankStatement_ReconcileBankTransfer','N',TO_TIMESTAMP('2022-09-09 14:13:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','N','Y',
        'Y',0,'Als Überweisung abgleichen','json','N','N','xls','Java',TO_TIMESTAMP('2022-09-09 14:13:10','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement_ReconcileBankTransfer')
;

-- 2022-09-09T11:13:10.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585106 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-09-09T11:13:17.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-09 14:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585106
;

-- 2022-09-09T11:13:19.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-09 14:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585106
;

-- 2022-09-09T11:13:40.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Reconcile as Bank Transfer',Updated=TO_TIMESTAMP('2022-09-09 14:13:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585106
;

-- 2022-09-09T11:13:42.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-09 14:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585106
;

-- 2022-09-09T11:14:04.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585106,392,541278,TO_TIMESTAMP('2022-09-09 14:14:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-09-09 14:14:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- 2022-09-09T11:15:43.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541659,TO_TIMESTAMP('2022-09-09 14:15:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BankStatement',TO_TIMESTAMP('2022-09-09 14:15:43','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-09-09T11:15:43.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541659 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-09-09T11:15:57.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,4909,0,541659,392,TO_TIMESTAMP('2022-09-09 14:15:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-09-09 14:15:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T11:28:10.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540599,'c_bankstatement.c_bankstatement_id != @C_BankStatement_ID/-1@
AND c_bankstatement.docstatus IN (''DR'', ''IP'', ''CO'')
AND EXISTS(SELECT 1 FROM c_bankstatementline bsl WHERE bsl.c_bankstatement_id = c_bankstatement.c_bankstatement_id AND bsl.isreconciled = ''N'')
',TO_TIMESTAMP('2022-09-09 14:28:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BankStatement_BankTransferCounterpart','S',TO_TIMESTAMP('2022-09-09 14:28:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T11:30:07.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540600,'c_bankstatementline.c_bankstatement_id = @Counterpart_BankStatement_ID/-1@ and isreconciled=''N''',TO_TIMESTAMP('2022-09-09 14:30:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_BankStatementLine_BankTransferCounterpart','S',TO_TIMESTAMP('2022-09-09 14:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T11:30:28.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581436,0,585106,542300,30,541659,540599,'Counterpart_BankStatement_ID',TO_TIMESTAMP('2022-09-09 14:30:28','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Gegenkontoauszug',10,TO_TIMESTAMP('2022-09-09 14:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T11:30:28.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542300 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-09-09T11:30:38.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2022-09-09 14:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542300
;

-- 2022-09-09T11:31:26.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_BankStatementLine',Updated=TO_TIMESTAMP('2022-09-09 14:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540049
;

-- 2022-09-09T11:31:36.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581437,0,585106,542301,30,540049,540600,'Counterpart_BankStatementLine_ID',TO_TIMESTAMP('2022-09-09 14:31:36','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Gegenkontoauszug Position',20,TO_TIMESTAMP('2022-09-09 14:31:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T11:31:36.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542301 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-09-09T11:31:38.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-09-09 14:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542301
;

-- 2022-09-09T11:31:39.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-09-09 14:31:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542300
;

-- 2022-09-09T11:31:44.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2022-09-09 14:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542301
;

