-- Value: mobileui.workstationManager.appName
-- 2024-03-07T17:03:56.300Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545375,0,TO_TIMESTAMP('2024-03-07 19:03:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Arbeitsstation','I',TO_TIMESTAMP('2024-03-07 19:03:56','YYYY-MM-DD HH24:MI:SS'),100,'mobileui.workstationManager.appName')
;

-- 2024-03-07T17:03:56.305Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545375 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: mobileui.workstationManager.appName
-- 2024-03-07T17:04:01.409Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-07 19:04:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545375
;

-- Value: mobileui.workstationManager.appName
-- 2024-03-07T17:04:15.208Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Workstation',Updated=TO_TIMESTAMP('2024-03-07 19:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545375
;

-- Value: mobileui.workstationManager.appName
-- 2024-03-07T17:04:17.463Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-07 19:04:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545375
;

