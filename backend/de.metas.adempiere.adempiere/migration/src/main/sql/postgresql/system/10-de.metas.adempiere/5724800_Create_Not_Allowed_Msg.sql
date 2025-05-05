-- Run mode: SWING_CLIENT

-- Value: CreateNotAllowed
-- 2024-05-29T08:39:08.906Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545415,0,TO_TIMESTAMP('2024-05-29 11:39:08.676','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Create not allowed','E',TO_TIMESTAMP('2024-05-29 11:39:08.676','YYYY-MM-DD HH24:MI:SS.US'),100,'CreateNotAllowed')
;

-- 2024-05-29T08:39:08.922Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545415 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CreateNotAllowed
-- 2024-05-29T08:39:28.504Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Erstellen nicht erlaubt',Updated=TO_TIMESTAMP('2024-05-29 11:39:28.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545415
;

-- Value: CreateNotAllowed
-- 2024-05-29T08:39:43.202Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Erstellen nicht erlaubt',Updated=TO_TIMESTAMP('2024-05-29 11:39:43.202','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545415
;

-- 2024-05-29T08:39:43.204Z
UPDATE AD_Message SET MsgText='Erstellen nicht erlaubt' WHERE AD_Message_ID=545415
;

-- Value: de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed
-- 2024-05-29T08:44:46.881Z
UPDATE AD_Message SET Value='de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed',Updated=TO_TIMESTAMP('2024-05-29 11:44:46.879','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545415
;

-- Run mode: SWING_CLIENT

-- Value: de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed
-- 2024-05-29T09:04:46.809Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-29 12:04:46.809','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545415
;

