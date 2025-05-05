-- Value: MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED
-- 2023-07-07T13:18:33.198895728Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545287,0,TO_TIMESTAMP('2023-07-07 14:18:32.453','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Extension not allowed','E',TO_TIMESTAMP('2023-07-07 14:18:32.453','YYYY-MM-DD HH24:MI:SS.US'),100,'MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED')
;

-- 2023-07-07T13:18:33.206484001Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545287 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED
-- 2023-07-07T13:19:41.860874811Z
UPDATE AD_Message_Trl SET MsgText='Verlängerung nicht zulässig',Updated=TO_TIMESTAMP('2023-07-07 14:19:41.86','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545287
;

-- Value: MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED
-- 2023-07-07T13:19:45.855156863Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-07 14:19:45.854','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545287
;

-- Value: MSG_FLATRATE_CONDITIONS_EXTENSION_NOT_ALLOWED
-- 2023-07-07T13:19:55.591670333Z
UPDATE AD_Message_Trl SET MsgText='Verlängerung nicht zulässig',Updated=TO_TIMESTAMP('2023-07-07 14:19:55.591','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545287
;

-- 2023-07-07T13:19:55.592841088Z
UPDATE AD_Message SET MsgText='Verlängerung nicht zulässig' WHERE AD_Message_ID=545287
;

-- Value: MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS
-- 2023-07-07T14:32:26.640658297Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545288,0,TO_TIMESTAMP('2023-07-07 15:32:22.781','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Settings with same year already exists','E',TO_TIMESTAMP('2023-07-07 15:32:22.781','YYYY-MM-DD HH24:MI:SS.US'),100,'MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS')
;

-- 2023-07-07T14:32:26.646605787Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545288 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS
-- 2023-07-07T14:33:11.853856992Z
UPDATE AD_Message_Trl SET MsgText='Einstellungen mit demselben Jahr sind bereits vorhanden',Updated=TO_TIMESTAMP('2023-07-07 15:33:11.853','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545288
;

-- Value: MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS
-- 2023-07-07T14:33:15.776648384Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-07 15:33:15.776','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545288
;

-- Value: MSG_SETTINGS_WITH_SAME_YEAR_ALREADY_EXISTS
-- 2023-07-07T14:33:21.768592143Z
UPDATE AD_Message_Trl SET MsgText='Einstellungen mit demselben Jahr sind bereits vorhanden',Updated=TO_TIMESTAMP('2023-07-07 15:33:21.768','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545288
;

-- 2023-07-07T14:33:21.769451353Z
UPDATE AD_Message SET MsgText='Einstellungen mit demselben Jahr sind bereits vorhanden' WHERE AD_Message_ID=545288
;
