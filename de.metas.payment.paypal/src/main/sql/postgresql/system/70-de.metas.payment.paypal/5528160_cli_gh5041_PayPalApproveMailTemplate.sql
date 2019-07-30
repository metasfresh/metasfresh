-- 2019-07-26T12:08:10.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO R_MailText (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsHtml,MailHeader,MailText,Name,R_MailText_ID,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2019-07-26 15:08:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Please approve @Amount@ for order #@SalesOrderDocumentNo@','Hi @Name@,

We are about to complete order #@SalesOrderDocumentNo@.
Please approve the money reservation on paypal: @ApproveURL@ .
','PayPal Payer Approval Request',540003,TO_TIMESTAMP('2019-07-26 15:08:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-26T12:08:10.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO R_MailText_Trl (AD_Language,R_MailText_ID, MailHeader,MailText,MailText2,MailText3,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_MailText_ID, t.MailHeader,t.MailText,t.MailText2,t.MailText3,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_MailText t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_MailText_ID=540003 AND NOT EXISTS (SELECT 1 FROM R_MailText_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_MailText_ID=t.R_MailText_ID)
;

