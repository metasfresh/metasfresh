-- Value: WEBUI_Missing_Base_PriceList_Version
-- 2023-10-10T15:48:20.932Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545352,0,TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Preisliste hat keine Basis Preisliste','I',TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Missing_Base_PriceList_Version')
;

-- 2023-10-10T15:48:20.934Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545352 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: WEBUI_Missing_Base_PriceList_Version
-- 2023-10-10T18:06:53.914Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Pricelist has no Base Pricelist',Updated=TO_TIMESTAMP('2023-10-10 19:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545352
;

