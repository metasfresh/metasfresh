-- Value: de.metas.manufacturing.NOTHING_WAS_RECEIVED_YET
-- 2024-07-18T11:18:09.764Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545436,0,TO_TIMESTAMP('2024-07-18 14:18:09','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Es ist noch nichts eingegangen!','I',TO_TIMESTAMP('2024-07-18 14:18:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.NOTHING_WAS_RECEIVED_YET')
;

-- 2024-07-18T11:18:09.767Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545436 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.NOTHING_WAS_RECEIVED_YET
-- 2024-07-18T11:18:17.520Z
UPDATE AD_Message_Trl SET MsgText='Nothing was received yet!',Updated=TO_TIMESTAMP('2024-07-18 14:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545436
;

-- Value: de.metas.manufacturing.NO_SOURCE_LOCATOR
-- 2024-07-18T11:22:06.280Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545437,0,TO_TIMESTAMP('2024-07-18 14:22:06','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Keine Quelle Lagerort gefunden!','E',TO_TIMESTAMP('2024-07-18 14:22:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.NO_SOURCE_LOCATOR')
;

-- 2024-07-18T11:22:06.284Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545437 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.NO_SOURCE_LOCATOR
-- 2024-07-18T11:22:16.580Z
UPDATE AD_Message_Trl SET MsgText='No source locator found!',Updated=TO_TIMESTAMP('2024-07-18 14:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545437
;

-- Value: de.metas.manufacturing.QR_CODE_DOES_NOT_MATCH_ERR_MSG
-- 2024-07-18T11:23:44.386Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545438,0,TO_TIMESTAMP('2024-07-18 14:23:44','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Der QR-Code stimmt mit keiner der gültigen Optionen überein!','E',TO_TIMESTAMP('2024-07-18 14:23:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.QR_CODE_DOES_NOT_MATCH_ERR_MSG')
;

-- 2024-07-18T11:23:44.387Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545438 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.QR_CODE_DOES_NOT_MATCH_ERR_MSG
-- 2024-07-18T11:23:57.122Z
UPDATE AD_Message_Trl SET MsgText='The QR code does not match any of the valid options!',Updated=TO_TIMESTAMP('2024-07-18 14:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545438
;

-- Value: de.metas.manufacturing.QR_CODE_INVALID_TYPE_ERR_MSG
-- 2024-07-18T11:24:47.601Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545439,0,TO_TIMESTAMP('2024-07-18 14:24:47','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Ungültiger QR-Code','E',TO_TIMESTAMP('2024-07-18 14:24:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.QR_CODE_INVALID_TYPE_ERR_MSG')
;

-- 2024-07-18T11:24:47.843Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545439 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.QR_CODE_INVALID_TYPE_ERR_MSG
-- 2024-07-18T11:24:53.364Z
UPDATE AD_Message_Trl SET MsgText='Invalid QR code',Updated=TO_TIMESTAMP('2024-07-18 14:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545439
;

