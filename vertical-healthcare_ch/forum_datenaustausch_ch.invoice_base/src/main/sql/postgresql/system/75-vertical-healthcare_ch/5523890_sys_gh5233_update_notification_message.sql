-- 2019-06-06T09:25:02.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Subject Rückweisung der Rechnungsnummer {0}',Updated=TO_TIMESTAMP('2019-06-06 09:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544920
;

-- 2019-06-06T09:26:37.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Sehr geehrte(r) Herr/Frau Blah {0},

Bitte bearbeiten Sie die Rückweisung.

MFG',Updated=TO_TIMESTAMP('2019-06-06 09:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544919
;

-- 2019-06-06T09:31:11.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Content_WhenUserExists',Updated=TO_TIMESTAMP('2019-06-06 09:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544919
;

-- 2019-06-06T09:33:20.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544926,0,TO_TIMESTAMP('2019-06-06 09:33:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Sehr geehrte Damen und Herren

Bitte bearbeiten Sie die Rückweisung.

MFG','I',TO_TIMESTAMP('2019-06-06 09:33:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Content_WhenUserDoesNotExist')
;

-- 2019-06-06T09:33:20.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544926 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

