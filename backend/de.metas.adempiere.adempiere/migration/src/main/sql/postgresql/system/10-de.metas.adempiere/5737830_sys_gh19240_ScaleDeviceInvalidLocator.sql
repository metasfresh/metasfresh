-- Value: ScaleDeviceInvalidLocator
-- 2024-10-28T14:03:24.162Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545478,0,TO_TIMESTAMP('2024-10-28 16:03:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Diese Waage passt nicht zum Standort.','E',TO_TIMESTAMP('2024-10-28 16:03:23','YYYY-MM-DD HH24:MI:SS'),100,'ScaleDeviceInvalidLocator')
;

-- 2024-10-28T14:03:24.165Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545478 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ScaleDeviceInvalidLocator
-- 2024-10-28T14:03:36.203Z
UPDATE AD_Message_Trl SET MsgText='This scale does not match the location.',Updated=TO_TIMESTAMP('2024-10-28 16:03:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545478
;

