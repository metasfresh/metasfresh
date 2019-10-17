
-- 2019-05-13T11:57:19.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,AD_Org_ID,EntityType,Value) VALUES ('I','Rechnung: {0}<br>
Grund: {1}',0,'Y',TO_TIMESTAMP('2019-05-13 11:57:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-13 11:57:18','YYYY-MM-DD HH24:MI:SS'),100,544919,0,'de.metas.vertical.healthcare.forum_datenaustausch_ch','de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Text')
;

-- 2019-05-13T11:57:19.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544919 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-05-13T11:58:04.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Rechnung: {0}',Updated=TO_TIMESTAMP('2019-05-13 11:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544919
;

-- 2019-05-13T12:26:53.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Rechnung {0} wurde zurückgewiesen.',Updated=TO_TIMESTAMP('2019-05-13 12:26:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544919
;

-- 2019-05-13T12:29:25.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Content',Updated=TO_TIMESTAMP('2019-05-13 12:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544919
;

-- 2019-05-13T12:32:36.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,AD_Org_ID,EntityType,Value) VALUES ('I','Rechnung zurückgewiesen',0,'Y',TO_TIMESTAMP('2019-05-13 12:32:36','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-13 12:32:36','YYYY-MM-DD HH24:MI:SS'),100,544920,0,'de.metas.vertical.healthcare.forum_datenaustausch_ch','de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Subject')
;

-- 2019-05-13T12:32:36.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544920 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

