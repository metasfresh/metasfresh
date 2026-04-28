-- 2024-06-06T07:04:51.019Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,545418,0,TO_TIMESTAMP('2024-06-06 09:04:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Der ausgewählte Datensatz ist schon verarbeitet','Der Datensatz muss vor dem Öffnen der Maske wieder reaktiviert werden.','E',TO_TIMESTAMP('2024-06-06 09:04:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.contract.flatrate.process.WEBUI_C_Flatrate_DataEntry_Detail_Launcher.EntryAlreadyProcessed')
;

-- 2024-06-06T07:04:51.276Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545418 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2024-06-06T07:05:10.712Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-06 09:05:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545418
;

-- 2024-06-06T07:05:47.263Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The selected record is already processed', MsgTip='The data record must be reactivated before opening the modal.',Updated=TO_TIMESTAMP('2024-06-06 09:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545418
;

-- 2024-06-06T07:07:06.649Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Datensatz ist schon verarbeitet.',Updated=TO_TIMESTAMP('2024-06-06 09:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545418
;

-- 2024-06-06T07:07:10.774Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Datensatz ist schon verarbeitet.',Updated=TO_TIMESTAMP('2024-06-06 09:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545418
;

-- 2024-06-06T07:07:12.874Z
UPDATE AD_Message_Trl SET MsgText='The selected record is already processed.',Updated=TO_TIMESTAMP('2024-06-06 09:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545418
;

-- 2024-06-06T07:07:16.259Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der ausgewählte Datensatz ist schon verarbeitet.',Updated=TO_TIMESTAMP('2024-06-06 09:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545418
;

-- 2024-06-06T07:07:25.818Z
UPDATE AD_Message SET MsgText='Der ausgewählte Datensatz ist schon verarbeitet.',Updated=TO_TIMESTAMP('2024-06-06 09:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545418
;

-- 2024-06-06T07:07:57.359Z
UPDATE AD_Message SET MsgText='Der ausgewählte Abrechnungssatz ist schon verarbeitet.', MsgTip='Der Abrechnungssatz muss vor dem Öffnen der Maske wieder reaktiviert werden.',Updated=TO_TIMESTAMP('2024-06-06 09:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545418
;

-- 2024-06-06T07:08:05.994Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Abrechnungssatz  ist schon verarbeitet.', MsgTip='Der Abrechnungssatz  muss vor dem Öffnen der Maske wieder reaktiviert werden.',Updated=TO_TIMESTAMP('2024-06-06 09:08:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545418
;

-- 2024-06-06T07:08:11.615Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Abrechnungssatz  ist schon verarbeitet.', MsgTip='Der Abrechnungssatz  muss vor dem Öffnen der Maske wieder reaktiviert werden.',Updated=TO_TIMESTAMP('2024-06-06 09:08:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545418
;

-- 2024-06-06T07:08:25.434Z
UPDATE AD_Message_Trl SET MsgText='The selected billing-record is already processed.', MsgTip='The record must be reactivated before opening the modal.',Updated=TO_TIMESTAMP('2024-06-06 09:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545418
;

-- 2024-06-06T07:08:33.822Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Abrechnungssatz  ist schon verarbeitet.', MsgTip='Der Abrechnungssatz muss vor dem Öffnen der Maske wieder reaktiviert werden.',Updated=TO_TIMESTAMP('2024-06-06 09:08:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545418
;

