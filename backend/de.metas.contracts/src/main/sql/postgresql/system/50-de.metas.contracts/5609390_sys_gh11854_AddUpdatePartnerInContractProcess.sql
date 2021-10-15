-- 2021-10-14T14:41:40.641Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584925,'Y','de.metas.contracts.flatrate.process.C_Flatrate_Term_Change_BillPartner','N',TO_TIMESTAMP('2021-10-14 17:41:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'Abo-Rechnungspartner ändern','json','N','N','xls','Java',TO_TIMESTAMP('2021-10-14 17:41:39','YYYY-MM-DD HH24:MI:SS'),100,'C_Flatrate_Term_Change_BillPartner')
;

-- 2021-10-14T14:41:40.712Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584925 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-10-14T14:43:17.945Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2039,0,584925,542120,30,541252,'Bill_BPartner_ID',TO_TIMESTAMP('2021-10-14 17:43:17','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung','D',0,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','Rechnungspartner',10,TO_TIMESTAMP('2021-10-14 17:43:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T14:43:17.981Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542120 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-14T14:43:21.102Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-14 17:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542120
;

-- 2021-10-14T14:45:58.488Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2040,0,584925,542122,30,159,540071,'Bill_Location_ID',TO_TIMESTAMP('2021-10-14 17:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung','D',0,'Y','N','Y','N','N','N','Rechnungsstandort',20,TO_TIMESTAMP('2021-10-14 17:45:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T14:45:58.520Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542122 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-14T14:46:04.356Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-14 17:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542122
;

-- 2021-10-14T14:47:02.313Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2041,0,584925,542123,30,110,191,'Bill_User_ID',TO_TIMESTAMP('2021-10-14 17:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung','D',0,'Y','N','Y','N','N','N','Rechnungskontakt',30,TO_TIMESTAMP('2021-10-14 17:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T14:47:02.346Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542123 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-14T14:48:54.627Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584925,540320,541011,TO_TIMESTAMP('2021-10-14 17:48:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-10-14 17:48:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-10-14T15:07:41.834Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584926,'Y','de.metas.contracts.flatrate.process.C_Flatrate_Term_Change_BillPartner_For_FlatrateData','N',TO_TIMESTAMP('2021-10-14 18:07:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'Abo-Rechnungspartner ändern','json','N','N','xls','Java',TO_TIMESTAMP('2021-10-14 18:07:41','YYYY-MM-DD HH24:MI:SS'),100,'C_Flatrate_Term_Change_BillPartner_For_FlatrateData')
;

-- 2021-10-14T15:07:41.865Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584926 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-10-14T15:08:37.662Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2039,0,584926,542124,30,541252,'Bill_BPartner_ID',TO_TIMESTAMP('2021-10-14 18:08:37','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner für die Rechnungsstellung','D',0,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','Y','N','Rechnungspartner',10,TO_TIMESTAMP('2021-10-14 18:08:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T15:08:37.696Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542124 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-14T15:08:37.763Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542124
;

-- 2021-10-14T15:08:37.795Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542124, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542120
;

-- 2021-10-14T15:08:38.116Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2040,0,584926,542125,30,159,540071,'Bill_Location_ID',TO_TIMESTAMP('2021-10-14 18:08:37','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung','D',0,'Y','N','Y','N','Y','N','Rechnungsstandort',20,TO_TIMESTAMP('2021-10-14 18:08:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T15:08:38.146Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542125 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-14T15:08:38.210Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542125
;

-- 2021-10-14T15:08:38.240Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542125, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542122
;

-- 2021-10-14T15:08:38.557Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2041,0,584926,542126,30,110,191,'Bill_User_ID',TO_TIMESTAMP('2021-10-14 18:08:38','YYYY-MM-DD HH24:MI:SS'),100,'Ansprechpartner des Geschäftspartners für die Rechnungsstellung','D',0,'Y','N','Y','N','N','N','Rechnungskontakt',30,TO_TIMESTAMP('2021-10-14 18:08:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T15:08:38.591Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542126 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-14T15:08:38.656Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542126
;

-- 2021-10-14T15:08:38.688Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542126, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542123
;

-- 2021-10-14T15:09:31.109Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584926,540310,541012,TO_TIMESTAMP('2021-10-14 18:09:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-10-14 18:09:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- 2021-10-14T15:10:14.317Z
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2021-10-14 18:10:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541011
;

-- 2021-10-14T15:51:41.884Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540392,Updated=TO_TIMESTAMP('2021-10-14 18:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542120
;

-- 2021-10-14T15:52:23.730Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540392,Updated=TO_TIMESTAMP('2021-10-14 18:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542124
;

-- 2021-10-14T16:01:32.391Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541252, AD_Val_Rule_ID=540245,Updated=TO_TIMESTAMP('2021-10-14 19:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542124
;

-- 2021-10-14T16:01:47.022Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541252, AD_Val_Rule_ID=540245,Updated=TO_TIMESTAMP('2021-10-14 19:01:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542120
;

-- 2021-10-14T16:14:02.214Z
-- URL zum Konzept
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2021-10-14 19:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584926
;

-- 2021-10-14T18:53:33.038Z
-- URL zum Konzept
UPDATE AD_Message SET EntityType='D', MsgText='Der Vertrag kann nicht geändert werden, weil es schon Rechnungen zu ihm gibt.',Updated=TO_TIMESTAMP('2021-10-14 21:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545066
;

-- 2021-10-14T18:53:56.379Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='The contract can''t be changed because it already has invoices.',Updated=TO_TIMESTAMP('2021-10-14 21:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545066
;

-- 2021-10-14T18:54:15.101Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Vertrag kann nicht geändert werden, weil es schon Rechnungen zu ihm gibt.',Updated=TO_TIMESTAMP('2021-10-14 21:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545066
;

-- 2021-10-14T18:54:42.670Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Der Vertrag kann nicht geändert werden, weil es schon Rechnungen zu ihm gibt.',Updated=TO_TIMESTAMP('2021-10-14 21:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545066
;

