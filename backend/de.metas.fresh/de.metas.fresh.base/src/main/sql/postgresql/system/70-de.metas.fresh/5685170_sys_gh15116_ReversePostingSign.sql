-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: NegateAmounts
-- 2023-04-17T11:47:07.798Z
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2023-04-17 12:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542612
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: ReversePostingSign
-- 2023-04-17T11:47:59.520Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585254,542616,20,'ReversePostingSign',TO_TIMESTAMP('2023-04-17 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','D',0,'Y','N','Y','N','Y','N','Reverse Posting Sign',30,TO_TIMESTAMP('2023-04-17 12:47:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-17T11:47:59.522Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542616 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: ReversePostingSign
-- 2023-04-17T11:53:05.928Z
UPDATE AD_Process_Para SET Name='Umgekehrtes Aushangschild',Updated=TO_TIMESTAMP('2023-04-17 12:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542616
;

-- 2023-04-17T11:53:05.930Z
UPDATE AD_Process_Para_Trl trl SET Name='Umgekehrtes Aushangschild' WHERE AD_Process_Para_ID=542616 AND AD_Language='de_DE'
;

-- 2023-04-17T11:53:20.899Z
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-17 12:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542616
;

-- 2023-04-17T11:53:26.417Z
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-17 12:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542616
;