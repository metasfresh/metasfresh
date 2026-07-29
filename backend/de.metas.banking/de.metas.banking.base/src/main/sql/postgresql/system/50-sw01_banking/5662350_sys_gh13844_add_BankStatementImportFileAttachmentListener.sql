-- Value: MultipleAttachmentsNotAllowedFAILURE
-- 2022-10-27T07:24:52.367Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545173,0,TO_TIMESTAMP('2022-10-27 10:24:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Multiple attachments not allowed.','E',TO_TIMESTAMP('2022-10-27 10:24:52','YYYY-MM-DD HH24:MI:SS'),100,'MultipleAttachmentsNotAllowedFAILURE')
;

-- 2022-10-27T07:24:52.371Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545173 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MultipleAttachmentsNotAllowedFAILURE
-- 2022-10-27T07:25:00.535Z
UPDATE AD_Message_Trl SET MsgText='Nur ein Anhang möglich.',Updated=TO_TIMESTAMP('2022-10-27 10:25:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545173
;

-- Value: MultipleAttachmentsNotAllowedFAILURE
-- 2022-10-27T07:25:03.137Z
UPDATE AD_Message_Trl SET MsgText='Nur ein Anhang möglich.',Updated=TO_TIMESTAMP('2022-10-27 10:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545173
;

-- Value: MultipleAttachmentsNotAllowedFAILURE
-- 2022-10-27T07:25:06.478Z
UPDATE AD_Message_Trl SET MsgText='Nur ein Anhang möglich.',Updated=TO_TIMESTAMP('2022-10-27 10:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545173
;


-- Value: AttachmentNotImportedFAILURE
-- 2022-10-27T08:38:22.192Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545174,0,TO_TIMESTAMP('2022-10-27 11:38:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Attachment not imported.','E',TO_TIMESTAMP('2022-10-27 11:38:21','YYYY-MM-DD HH24:MI:SS'),100,'AttachmentNotImportedFAILURE')
;

-- 2022-10-27T08:38:22.206Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545174 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: AttachmentNotImportedFAILURE
-- 2022-10-27T08:38:35.561Z
UPDATE AD_Message_Trl SET MsgText='Anhang nicht importiert.',Updated=TO_TIMESTAMP('2022-10-27 11:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545174
;

-- Value: AttachmentNotImportedFAILURE
-- 2022-10-27T08:38:43.947Z
UPDATE AD_Message_Trl SET MsgText='Anhang nicht importiert.',Updated=TO_TIMESTAMP('2022-10-27 11:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545174
;

-- Value: AttachmentNotImportedFAILURE
-- 2022-10-27T08:38:46.150Z
UPDATE AD_Message_Trl SET MsgText='Anhang nicht importiert.',Updated=TO_TIMESTAMP('2022-10-27 11:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545174
;



-- 2022-10-27T07:28:07.156Z
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540072,540042,0,'de.metas.banking.importfile.listener.BankStatementImportFileAttachmentListener',TO_TIMESTAMP('2022-10-27 10:28:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','BankStatementImportFileAttachmentListener',TO_TIMESTAMP('2022-10-27 10:28:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-27T11:55:31.538Z
INSERT INTO AD_Table_AttachmentListener (AD_Client_ID,AD_JavaClass_ID,AD_Org_ID,AD_Table_AttachmentListener_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsSendNotification,SeqNo,Updated,UpdatedBy) VALUES (0,540072,0,540007,542246,TO_TIMESTAMP('2022-10-27 14:55:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',10,TO_TIMESTAMP('2022-10-27 14:55:31','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE AD_Table_AttachmentListener attachLsrn
SET ad_message_id = 545173
WHERE attachLsrn.AD_Table_AttachmentListener_ID = 540007;