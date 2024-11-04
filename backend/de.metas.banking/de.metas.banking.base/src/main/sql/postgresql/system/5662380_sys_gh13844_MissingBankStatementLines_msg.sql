
-- Process: C_BankStatement_Import_File_Camt53_ImportAttachment(de.metas.banking.camt53.process.C_BankStatement_Import_File_Camt53_ImportAttachment)
-- ParameterName: AD_AttachmentEntry_ID
-- 2022-10-27T12:53:49.187Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542328
;

-- 2022-10-27T12:53:49.229Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542328
;

-- Name: AD_AttachmentEntry_for_C_BankStatement_Import_File
-- 2022-10-27T12:54:17.536Z
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540605
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoAttachment
-- 2022-10-27T12:58:01.759Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545175,0,TO_TIMESTAMP('2022-10-27 15:58:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Kein Anhang','E',TO_TIMESTAMP('2022-10-27 15:58:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoAttachment')
;

-- 2022-10-27T12:58:01.770Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545175 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoAttachment
-- 2022-10-27T12:58:11.987Z
UPDATE AD_Message_Trl SET MsgText='No attachment',Updated=TO_TIMESTAMP('2022-10-27 15:58:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545175
;


-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementLines
-- 2022-10-28T07:25:29.244Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545176,0,TO_TIMESTAMP('2022-10-28 10:25:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Die Kontoauszugdatei einhält keine Zeilen ("Ntry")','E',TO_TIMESTAMP('2022-10-28 10:25:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementLines')
;

-- 2022-10-28T07:25:29.248Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545176 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementLines
-- 2022-10-28T07:25:41.266Z
UPDATE AD_Message_Trl SET MsgText='The bank statement file does not have any lines ("Ntry")',Updated=TO_TIMESTAMP('2022-10-28 10:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545176
;


-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.MultipleAttachments
-- 2022-10-28T14:29:07.302Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545185,0,TO_TIMESTAMP('2022-10-28 17:29:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Für den Kontoauszug wurden mehrere Anhänge gefunden','E',TO_TIMESTAMP('2022-10-28 17:29:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.MultipleAttachments')
;

-- 2022-10-28T14:29:07.318Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545185 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.MultipleAttachments
-- 2022-10-28T14:29:14.954Z
UPDATE AD_Message_Trl SET MsgText='Multiple attachments were found for bank statement',Updated=TO_TIMESTAMP('2022-10-28 17:29:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545185
;


