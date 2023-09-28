-- Value: webui.login.2FA.caption
-- 2023-09-27T12:58:56.682604500Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545342,0,TO_TIMESTAMP('2023-09-27 15:58:56.322','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Enter authentication code','I',TO_TIMESTAMP('2023-09-27 15:58:56.322','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.login.2FA.caption')
;

-- 2023-09-27T12:58:56.697535900Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545342 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.auth.Invalid2FACode
-- 2023-09-27T14:00:08.662135500Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545343,0,TO_TIMESTAMP('2023-09-27 17:00:08.315','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Invalid code','E',TO_TIMESTAMP('2023-09-27 17:00:08.315','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.auth.Invalid2FACode')
;

-- 2023-09-27T14:00:08.705673200Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545343 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: auth.Invalid2FACode
-- 2023-09-27T14:02:16.947203100Z
UPDATE AD_Message SET Value='auth.Invalid2FACode',Updated=TO_TIMESTAMP('2023-09-27 17:02:16.943','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545343
;

