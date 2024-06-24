-- Value: mobileui.workplaceManager.appName
-- 2024-02-14T15:24:47.678Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545372,0,TO_TIMESTAMP('2024-02-14 17:24:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Workplace','I',TO_TIMESTAMP('2024-02-14 17:24:47','YYYY-MM-DD HH24:MI:SS'),100,'mobileui.workplaceManager.appName')
;

-- 2024-02-14T15:24:47.686Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545372 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: mobileui.workplaceManager.appName
-- 2024-02-14T15:25:35.257Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Arbeitsplatz',Updated=TO_TIMESTAMP('2024-02-14 17:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545372
;

-- Value: mobileui.workplaceManager.appName
-- 2024-02-14T15:25:36.895Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-02-14 17:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545372
;

-- Value: mobileui.workplaceManager.appName
-- 2024-02-14T15:25:40.282Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Arbeitsplatz',Updated=TO_TIMESTAMP('2024-02-14 17:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545372
;

