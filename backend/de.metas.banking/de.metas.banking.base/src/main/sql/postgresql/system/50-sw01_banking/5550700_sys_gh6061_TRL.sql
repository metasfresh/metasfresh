-- 2020-01-30T13:51:18.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544958,0,TO_TIMESTAMP('2020-01-30 15:51:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bank Statement has to be Completed.','I',TO_TIMESTAMP('2020-01-30 15:51:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.process.C_BankStatementLine_AllocatePayment.Bank_Statement_has_to_be_Completed')
;

-- 2020-01-30T13:51:18.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544958 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-01-30T13:51:35.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 15:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544958
;

-- 2020-01-30T13:51:41.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Kontoauszug muss fertiggestellt werden.',Updated=TO_TIMESTAMP('2020-01-30 15:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544958
;

-- 2020-01-30T13:51:44.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Kontoauszug muss fertiggestellt werden.',Updated=TO_TIMESTAMP('2020-01-30 15:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544958
;

-- 2020-01-30T13:52:37.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Bank Statement has to be Completed.',Updated=TO_TIMESTAMP('2020-01-30 15:52:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544958
;

-- 2020-01-30T13:55:27.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544959,0,TO_TIMESTAMP('2020-01-30 15:55:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bank Statement must be Completed or In Progress.','I',TO_TIMESTAMP('2020-01-30 15:55:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.process.C_BankStatement_AddBpartnerAndPayment.BankStatement_must_be_Completed_or_In_Progress')
;

-- 2020-01-30T13:55:27.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544959 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-01-30T13:55:44.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544960,0,TO_TIMESTAMP('2020-01-30 15:55:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','A single line should be selected','I',TO_TIMESTAMP('2020-01-30 15:55:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.process.C_BankStatement_AddBpartnerAndPayment.A_single_line_should_be_selected')
;

-- 2020-01-30T13:55:44.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544960 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-01-30T13:56:08.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544961,0,TO_TIMESTAMP('2020-01-30 15:56:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Line must not have a payment assigned.','I',TO_TIMESTAMP('2020-01-30 15:56:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.process.C_BankStatement_AddBpartnerAndPayment.Line_should_not_have_a_Payment')
;

-- 2020-01-30T13:56:08.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544961 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-01-30T13:58:44.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Kontoauszug muss fertiggestellt oder in Bearbeitung sein.',Updated=TO_TIMESTAMP('2020-01-30 15:58:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544959
;

-- 2020-01-30T13:58:49.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 15:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544959
;

-- 2020-01-30T13:58:58.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Kontoauszug muss fertiggestellt oder in Bearbeitung sein.',Updated=TO_TIMESTAMP('2020-01-30 15:58:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544959
;

-- 2020-01-30T13:59:45.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Eine einzelne Zeile sollte ausgewählt werden.',Updated=TO_TIMESTAMP('2020-01-30 15:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544960
;

-- 2020-01-30T13:59:50.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Eine einzelne Zeile sollte ausgewählt werden.',Updated=TO_TIMESTAMP('2020-01-30 15:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544960
;

-- 2020-01-30T13:59:57.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='A single line should be selected.',Updated=TO_TIMESTAMP('2020-01-30 15:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544960
;

-- 2020-01-30T14:00:23.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Zeile darf keine Zahlung zugeordnet sein.',Updated=TO_TIMESTAMP('2020-01-30 16:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544961
;

-- 2020-01-30T14:00:27.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Zeile darf keine Zahlung zugeordnet sein.',Updated=TO_TIMESTAMP('2020-01-30 16:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544961
;

-- 2020-01-30T14:00:31.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-30 16:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544961
;

