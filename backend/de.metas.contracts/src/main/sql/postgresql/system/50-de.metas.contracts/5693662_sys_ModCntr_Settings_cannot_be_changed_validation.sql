-- Value: ModCntr_Settings_cannot_be_changed
-- 2023-06-29T10:38:53.676008600Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545280,0,TO_TIMESTAMP('2023-06-29 13:38:53.423','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Die Einstellungen können nicht geändert werden, da sie bereits als Teil einer abgeschlossenen Vertragslaufzeit verwendet werden.','E',TO_TIMESTAMP('2023-06-29 13:38:53.423','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Settings_cannot_be_changed')
;

-- 2023-06-29T10:38:53.679135100Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545280 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ModCntr_Settings_cannot_be_changed
-- 2023-06-29T10:39:13.078070900Z
UPDATE AD_Message_Trl SET MsgText='The settings cannot be changed because they are already used as part of a completed contract term.',Updated=TO_TIMESTAMP('2023-06-29 13:39:13.078','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545280
;

