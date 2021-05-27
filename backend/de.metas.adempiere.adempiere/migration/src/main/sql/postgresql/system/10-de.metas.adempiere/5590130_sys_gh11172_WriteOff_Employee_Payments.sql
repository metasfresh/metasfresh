-- 2021-05-26T15:27:53.226Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584833,'Y','de.metas.payment.process.C_Payment_Employee_WriteOff','N',TO_TIMESTAMP('2021-05-26 18:27:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'Write off employee payments','json','N','N','Java',TO_TIMESTAMP('2021-05-26 18:27:51','YYYY-MM-DD HH24:MI:SS'),100,'C_Payment_Employee_WriteOff')
;

-- 2021-05-26T15:27:53.854Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584833 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-05-26T15:28:04.269Z
-- URL zum Konzept
UPDATE AD_Process SET IsNotifyUserAfterExecution='Y',Updated=TO_TIMESTAMP('2021-05-26 18:28:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584833
;

-- 2021-05-26T15:29:30.914Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,574,0,584833,542003,15,'StartDate',TO_TIMESTAMP('2021-05-26 18:29:30','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)','D',0,'The Start Date indicates the first or starting date','Y','N','Y','N','Y','N','Anfangsdatum',10,TO_TIMESTAMP('2021-05-26 18:29:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T15:29:30.955Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542003 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-26T15:30:08.628Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,294,0,584833,542004,15,'EndDate',TO_TIMESTAMP('2021-05-26 18:30:08','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','D',0,'The End Date indicates the last date in this range.','Y','N','Y','N','N','N','Enddatum',20,TO_TIMESTAMP('2021-05-26 18:30:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T15:30:08.668Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542004 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-26T15:30:13.173Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-05-26 18:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542004
;

-- 2021-05-26T15:31:31.226Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=579243, ColumnName='DateEnd', Description=NULL, Help=NULL,Updated=TO_TIMESTAMP('2021-05-26 18:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542004
;

-- 2021-05-26T15:32:05.218Z
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542004
;

-- 2021-05-26T15:32:05.404Z
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542004
;

-- 2021-05-26T15:32:23.542Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579243,0,584833,542005,15,'DateEnd',TO_TIMESTAMP('2021-05-26 18:32:23','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Enddatum',20,TO_TIMESTAMP('2021-05-26 18:32:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T15:32:23.574Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542005 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-26T15:32:38.385Z
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542003
;

-- 2021-05-26T15:32:38.568Z
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542003
;

-- 2021-05-26T15:32:58.794Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53280,0,584833,542006,15,'DateStart',TO_TIMESTAMP('2021-05-26 18:32:58','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Startdatum',10,TO_TIMESTAMP('2021-05-26 18:32:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T15:32:58.827Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542006 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;





-- 2021-05-26T15:37:11.490Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584833,335,540936,TO_TIMESTAMP('2021-05-26 18:37:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-05-26 18:37:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
 ;



-- 2021-05-27T11:20:26.232Z
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542006
;

-- 2021-05-27T11:20:26.434Z
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542006
;

-- 2021-05-27T11:21:03.783Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,574,0,584833,542015,15,'StartDate',TO_TIMESTAMP('2021-05-27 14:21:03','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)','D',0,'The Start Date indicates the first or starting date','Y','N','Y','N','N','N','Anfangsdatum',30,TO_TIMESTAMP('2021-05-27 14:21:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-27T11:21:03.820Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542015 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-27T11:21:07.156Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-05-27 14:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542015
;

-- 2021-05-27T11:21:18.132Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2021-05-27 14:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542015
;

-- 2021-05-27T11:21:24.284Z
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542005
;

-- 2021-05-27T11:21:24.491Z
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542005
;

-- 2021-05-27T11:21:42.806Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,294,0,584833,542016,15,'EndDate',TO_TIMESTAMP('2021-05-27 14:21:42','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','D',0,'The End Date indicates the last date in this range.','Y','N','Y','N','Y','N','Enddatum',20,TO_TIMESTAMP('2021-05-27 14:21:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-27T11:21:42.885Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542016 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;



