
-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: NegateAmounts
-- 2023-04-18T14:44:20.789Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542612
;

-- 2023-04-18T14:44:21.442Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542612
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: ReversePostingSign
-- 2023-04-18T14:48:45.517Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585254,542617,20,'ReversePostingSign',TO_TIMESTAMP('2023-04-18 15:48:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','D',0,'Y','N','Y','N','N','N','Kontobuchungen umkehren (Haben/Soll)',20,TO_TIMESTAMP('2023-04-18 15:48:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-18T14:48:45.620Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542617 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: SAP_GLJournal_CopyDocument(de.metas.acct.gljournal_sap.process.SAP_GLJournal_CopyDocument)
-- ParameterName: ReversePostingSign
-- 2023-04-18T14:49:06.666Z
UPDATE AD_Process_Para SET Description='Legt fest, ob die Vorzeichen von Kontobuchungen beim Erstellen einer Kopie umgekehrt werden sollen (Haben zu Soll und umgekehrt).',Updated=TO_TIMESTAMP('2023-04-18 15:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542617
;

-- 2023-04-18T14:49:06.758Z
UPDATE AD_Process_Para_Trl trl SET Description='Legt fest, ob die Vorzeichen von Kontobuchungen beim Erstellen einer Kopie umgekehrt werden sollen (Haben zu Soll und umgekehrt).' WHERE AD_Process_Para_ID=542617 AND AD_Language='en_US'
;

-- 2023-04-18T14:50:33.261Z
UPDATE AD_Process_Para_Trl SET Description='Determines whether the leading signs of account postings (+/-) shall be reversed upon creating a copy (credit to debit and vice versa).', IsTranslated='Y', Name='Reverse account postings (credit/debit)',Updated=TO_TIMESTAMP('2023-04-18 15:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542617
;

-- 2023-04-18T14:50:33.418Z
UPDATE AD_Process_Para SET Description='Determines whether the leading signs of account postings (+/-) shall be reversed upon creating a copy (credit to debit and vice versa).', Name='Reverse account postings (credit/debit)' WHERE AD_Process_Para_ID=542617
;