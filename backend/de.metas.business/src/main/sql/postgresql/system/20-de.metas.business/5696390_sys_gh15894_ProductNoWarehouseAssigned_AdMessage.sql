-- Value: PRODUCT_NO_WAREHOUSE_ASSIGNED
-- 2023-07-20T09:05:21.005Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545293,0,TO_TIMESTAMP('2023-07-20 12:05:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dem Produkt ist kein Lager zugeordnet. Bitte ein Lager zuweisen.','E',TO_TIMESTAMP('2023-07-20 12:05:20','YYYY-MM-DD HH24:MI:SS'),100,'PRODUCT_NO_WAREHOUSE_ASSIGNED')
;

-- 2023-07-20T09:05:21.011Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545293 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: PRODUCT_NO_WAREHOUSE_ASSIGNED
-- 2023-07-20T09:05:29.954Z
UPDATE AD_Message_Trl SET MsgText='No warehouse assigned to the product. Please assign one.',Updated=TO_TIMESTAMP('2023-07-20 12:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545293
;

-- 2023-07-20T09:05:29.956Z
UPDATE AD_Message SET MsgText='No warehouse assigned to the product. Please assign one.', Updated=TO_TIMESTAMP('2023-07-20 12:05:29','YYYY-MM-DD HH24:MI:SS') WHERE AD_Message_ID=545293
;

