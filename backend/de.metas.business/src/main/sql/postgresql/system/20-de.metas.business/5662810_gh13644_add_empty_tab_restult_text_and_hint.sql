-- 2022-11-01T21:20:03.874Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545186,0,TO_TIMESTAMP('2022-11-01 22:20:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','There are no detail rows','I',TO_TIMESTAMP('2022-11-01 22:20:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.TAB_EMPTY_RESULT_TEXT')
;

-- 2022-11-01T21:20:03.882Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545186 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-11-01T21:20:33.077Z
UPDATE AD_Message_Trl SET MsgText='Es sind noch keine Detailzeilen vorhanden.',Updated=TO_TIMESTAMP('2022-11-01 22:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545186
;

-- 2022-11-01T21:20:39.594Z
UPDATE AD_Message_Trl SET MsgText='Es sind noch keine Detailzeilen vorhanden.',Updated=TO_TIMESTAMP('2022-11-01 22:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545186
;

-- 2022-11-01T21:20:45.667Z
UPDATE AD_Message_Trl SET MsgText='Es sind noch keine Detailzeilen vorhanden.',Updated=TO_TIMESTAMP('2022-11-01 22:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545186
;

-- 2022-11-01T21:21:28.890Z
UPDATE AD_Message_Trl SET MsgText='Il n''y a pas de lignes de détail',Updated=TO_TIMESTAMP('2022-11-01 22:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545186
;

-- 2022-11-01T21:21:49.642Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-01 22:21:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545186
;

-- 2022-11-01T21:24:28.063Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545187,0,TO_TIMESTAMP('2022-11-01 22:24:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','You can create them in this window.','I',TO_TIMESTAMP('2022-11-01 22:24:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.TAB_EMPTY_RESULT_HINT')
;

-- 2022-11-01T21:24:28.064Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545187 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-11-01T21:24:35.023Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-01 22:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545187
;

-- 2022-11-01T21:24:52.328Z
UPDATE AD_Message_Trl SET MsgText='Du kannst sie im jeweiligen Fenster erfassen.',Updated=TO_TIMESTAMP('2022-11-01 22:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545187
;

-- 2022-11-01T21:24:56.162Z
UPDATE AD_Message_Trl SET MsgText='Du kannst sie im jeweiligen Fenster erfassen.',Updated=TO_TIMESTAMP('2022-11-01 22:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545187
;

-- 2022-11-01T21:25:04.751Z
UPDATE AD_Message_Trl SET MsgText='Du kannst sie im jeweiligen Fenster erfassen.',Updated=TO_TIMESTAMP('2022-11-01 22:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545187
;

-- 2022-11-01T21:25:31.224Z
UPDATE AD_Message_Trl SET MsgText='Vous pouvez en ajouter dans cet écran',Updated=TO_TIMESTAMP('2022-11-01 22:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545187
;

-- 2022-11-01T21:25:34.356Z
UPDATE AD_Message_Trl SET MsgText='Vous pouvez en ajouter dans cet écran.',Updated=TO_TIMESTAMP('2022-11-01 22:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545187
;

