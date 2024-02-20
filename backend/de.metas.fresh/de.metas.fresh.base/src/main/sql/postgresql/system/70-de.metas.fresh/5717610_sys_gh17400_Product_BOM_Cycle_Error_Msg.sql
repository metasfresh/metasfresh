-- 2024-02-19T18:44:46.819Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545374,0,TO_TIMESTAMP('2024-02-19 19:44:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cycle detected in BOM for product: {0}','E',TO_TIMESTAMP('2024-02-19 19:44:46','YYYY-MM-DD HH24:MI:SS'),100,'Product_BOM_Cycle_Error')
;

-- 2024-02-19T18:44:46.825Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545374 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2024-02-19T18:45:07.388Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zyklus in der Stückliste für das Produkt erkannt: {0}',Updated=TO_TIMESTAMP('2024-02-19 19:45:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545374
;

