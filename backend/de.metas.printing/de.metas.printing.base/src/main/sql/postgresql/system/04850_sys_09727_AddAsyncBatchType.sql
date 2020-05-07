-- 28.01.2016 10:21:16 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Async_Batch_Type (AD_Client_ID,AD_Org_ID,C_Async_Batch_Type_ID,Created,CreatedBy,InternalName,IsActive,IsSendMail,IsSendNotification,KeepAliveTimeHours,Updated,UpdatedBy) VALUES (1000000,0,1000010,TO_TIMESTAMP('2016-01-28 10:21:16','YYYY-MM-DD HH24:MI:SS'),100,'InvoicingWizardSingle','Y','N','Y','24',TO_TIMESTAMP('2016-01-28 10:21:16','YYYY-MM-DD HH24:MI:SS'),100)
;



update C_Async_Batch_Type 
set InternalName = 'PDFPrinting'
where C_Async_Batch_Type_ID=1000010;


-- 28.01.2016 17:16:44 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543766,0,TO_TIMESTAMP('2016-01-28 17:16:44','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.printing','Y','PDFPrintingAsyncBatchListener_PrintJob_Done','I',TO_TIMESTAMP('2016-01-28 17:16:44','YYYY-MM-DD HH24:MI:SS'),0,'PDFPrintingAsyncBatchListener_PrintJob_Done')
;

-- 28.01.2016 17:16:44 OEZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543766 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


update AD_Message 
set msgtext = 'Druck-Job ist fertiggestellt.'
where ad_message_ID = 543766;

