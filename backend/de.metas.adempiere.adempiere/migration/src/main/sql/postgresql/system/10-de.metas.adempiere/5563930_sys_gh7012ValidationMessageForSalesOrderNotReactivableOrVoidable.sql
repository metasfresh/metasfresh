-- 2020-07-21T07:54:21.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544987,0,TO_TIMESTAMP('2020-07-21 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Action not allowed. Shipment Schedule exported.','E',TO_TIMESTAMP('2020-07-21 10:54:21','YYYY-MM-DD HH24:MI:SS'),100,'webui.salesorder.shipmentschedule.exported.')
;

-- 2020-07-21T07:54:21.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544987 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-07-21T07:54:49.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.salesorder.shipmentschedule.exported',Updated=TO_TIMESTAMP('2020-07-21 10:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544987
;

-- 2020-07-21T08:48:58.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2020-07-21 11:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544987
;

-- 2020-07-21T08:53:06.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Beleg kann nicht geändert werden, weil bereit Lieferdispo-Datensätze exportiert wurden.',Updated=TO_TIMESTAMP('2020-07-21 11:53:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544987
;

-- 2020-07-21T08:53:32.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Changing the document is not allowed because shipment schedules were already exported.',Updated=TO_TIMESTAMP('2020-07-21 11:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544987
;

-- 2020-07-21T08:53:40.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Beleg kann nicht geändert werden, weil bereit Lieferdispo-Datensätze exportiert wurden.',Updated=TO_TIMESTAMP('2020-07-21 11:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544987
;

-- 2020-07-21T08:53:46.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Beleg kann nicht geändert werden, weil bereit Lieferdispo-Datensätze exportiert wurden.',Updated=TO_TIMESTAMP('2020-07-21 11:53:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544987
;

-- 2020-07-21T08:53:50.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Changing the document is not allowed because shipment schedules were already exported.',Updated=TO_TIMESTAMP('2020-07-21 11:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544987
;

