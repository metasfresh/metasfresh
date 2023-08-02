-- Value: webui.modal.actions.close
-- 2023-08-02T13:35:47.712Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545316,0,TO_TIMESTAMP('2023-08-02 16:35:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Schliessen','I',TO_TIMESTAMP('2023-08-02 16:35:47','YYYY-MM-DD HH24:MI:SS'),100,'webui.modal.actions.close')
;

-- 2023-08-02T13:35:47.716Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545316 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.modal.actions.close
-- 2023-08-02T13:35:51.377Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-02 16:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545316
;

-- Value: webui.modal.actions.close
-- 2023-08-02T13:35:54.922Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-02 16:35:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545316
;

-- Value: webui.modal.actions.close
-- 2023-08-02T13:36:00.948Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Close',Updated=TO_TIMESTAMP('2023-08-02 16:36:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545316
;

-- 2023-08-02T13:36:00.950Z
UPDATE AD_Message SET MsgText='Close', Updated=TO_TIMESTAMP('2023-08-02 16:36:00','YYYY-MM-DD HH24:MI:SS') WHERE AD_Message_ID=545316
;

