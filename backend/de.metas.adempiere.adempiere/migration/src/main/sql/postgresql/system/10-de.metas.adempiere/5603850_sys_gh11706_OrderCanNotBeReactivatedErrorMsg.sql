-- 2021-09-08T14:45:50.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545058,0,TO_TIMESTAMP('2021-09-08 17:45:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','Document can not be reactivated because already has Completed Invoice.','E',TO_TIMESTAMP('2021-09-08 17:45:49','YYYY-MM-DD HH24:MI:SS'),100,'salesorder.invoice.completed')
;

-- 2021-09-08T14:45:50.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545058 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-09-08T14:46:24.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Order can not be reactivated because already has Completed Invoice.',Updated=TO_TIMESTAMP('2021-09-08 17:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545058
;

-- 2021-09-08T14:46:31.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Order can not be reactivated because already has Completed Invoice.',Updated=TO_TIMESTAMP('2021-09-08 17:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545058
;

-- 2021-09-10T06:21:08.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Order cannot be reactivated because it already has a completed invoice.', Value='order.invoice.completed',Updated=TO_TIMESTAMP('2021-09-10 09:21:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545058
;

-- 2021-09-10T06:21:27.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Order cannot be reactivated because it already has a completed invoice.',Updated=TO_TIMESTAMP('2021-09-10 09:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545058
;

-- 2021-09-10T06:21:43.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Auftrag kann nicht reaktiviert werden, weil es bereits eine fertiggestellte Rechnung dazu gibt.',Updated=TO_TIMESTAMP('2021-09-10 09:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545058
;

-- 2021-09-10T06:21:48.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Auftrag kann nicht reaktiviert werden, weil es bereits eine fertiggestellte Rechnung dazu gibt.',Updated=TO_TIMESTAMP('2021-09-10 09:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545058
;

-- 2021-09-10T06:21:55.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Auftrag kann nicht reaktiviert werden, weil es bereits eine fertiggestellte Rechnung dazu gibt.',Updated=TO_TIMESTAMP('2021-09-10 09:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545058
;

-- 2021-09-10T09:05:24.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Sales Order cannot be reactivated because it already has a completed invoice.', Value='salesorder.invoice.completed',Updated=TO_TIMESTAMP('2021-09-10 12:05:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545058
;

-- 2021-09-10T09:05:58.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Sales Order cannot be reactivated because it already has a completed invoice.',Updated=TO_TIMESTAMP('2021-09-10 12:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545058
;

-- 2021-09-10T09:07:54.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545059,0,TO_TIMESTAMP('2021-09-10 12:07:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','Purchase Order cannot be reactivated because it already has a completed invoice.','E',TO_TIMESTAMP('2021-09-10 12:07:53','YYYY-MM-DD HH24:MI:SS'),100,'purchaseorder.invoice.completed')
;

-- 2021-09-10T09:07:54.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545059 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-09-10T09:08:09.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-10 12:08:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545059
;

-- 2021-09-10T09:11:32.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Bestellung kann nicht reaktiviert werden, weil es bereits eine fertiggestellte Rechnung dazu gibt.',Updated=TO_TIMESTAMP('2021-09-10 12:11:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545059
;

-- 2021-09-10T09:11:39.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Bestellung kann nicht reaktiviert werden, weil es bereits eine fertiggestellte Rechnung dazu gibt.',Updated=TO_TIMESTAMP('2021-09-10 12:11:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545059
;

-- 2021-09-10T09:11:52.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Bestellung kann nicht reaktiviert werden, weil es bereits eine fertiggestellte Rechnung dazu gibt.',Updated=TO_TIMESTAMP('2021-09-10 12:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545059
;

-- 2021-09-10T14:11:22.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Sales order cannot be reactivated because it already has a completed invoice.',Updated=TO_TIMESTAMP('2021-09-10 17:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545058
;