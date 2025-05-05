-- 2023-12-20T19:43:34.171Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545365,0,TO_TIMESTAMP('2023-12-20 20:43:33.918','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Die angegebenen Daten überschneiden sich mit der letzten Stücklistenversion: {0}','E',TO_TIMESTAMP('2023-12-20 20:43:33.918','YYYY-MM-DD HH24:MI:SS.US'),100,'PP_Product_BOMVersions_Overlapping')
;

-- 2023-12-20T19:43:34.177Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545365 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-12-20T19:44:01.323Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The provided dates overlap with the last BOM version: {0}',Updated=TO_TIMESTAMP('2023-12-20 20:44:01.32','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545365
;

-- 2023-12-20T19:57:01.128Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545366,0,TO_TIMESTAMP('2023-12-20 20:57:00.965','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Das Datum Gültig bis kann nicht vor dem Datum Gültig von liegen.','E',TO_TIMESTAMP('2023-12-20 20:57:00.965','YYYY-MM-DD HH24:MI:SS.US'),100,'PP_Product_BOMVersions_ValidTo_Before_ValidFrom')
;

-- 2023-12-20T19:57:01.130Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545366 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-12-20T19:57:40.052Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Valid To date can''t be prior to Valid From date.',Updated=TO_TIMESTAMP('2023-12-20 20:57:40.049','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545366
;

