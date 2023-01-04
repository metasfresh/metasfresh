-- 2022-06-20T13:10:56.886Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545132,0,TO_TIMESTAMP('2022-06-20 16:10:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Starten Sie Ihre Suche','I',TO_TIMESTAMP('2022-06-20 16:10:56','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.emptyReason.pleaseFilterFirst.text')
;

-- 2022-06-20T13:10:56.890Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545132 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-06-20T13:11:21.677Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545133,0,TO_TIMESTAMP('2022-06-20 16:11:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Bitte Suchfelder ausfüllen.','I',TO_TIMESTAMP('2022-06-20 16:11:21','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.emptyReason.pleaseFilterFirst.hint')
;

-- 2022-06-20T13:11:21.679Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545133 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-06-20T13:11:33.040Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-06-20 16:11:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545132
;

-- 2022-06-20T13:11:43.834Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Start your search',Updated=TO_TIMESTAMP('2022-06-20 16:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545132
;

-- 2022-06-20T13:11:47.093Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-06-20 16:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545132
;

-- 2022-06-20T13:12:01.171Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Please fill in the search fields.',Updated=TO_TIMESTAMP('2022-06-20 16:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545133
;

-- 2022-06-20T13:12:06.463Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-06-20 16:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545133
;

-- 2022-06-20T13:12:09.670Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-06-20 16:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545133
;

