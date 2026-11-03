-- HU Manager create from label - AD_Message for a product code on the label that matches no product (DE base text + en_US translation). AD_Message_ID from the ID server.

-- Value: de.metas.handlingunits.createFromLabel.ProductNotFoundForProductCode
-- 2026-11-02 22:40
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545981 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-02 22:40','YYYY-MM-DD HH24:MI'),100,'D','Y','Kein Artikel mit Artikelnummer {0} gefunden','E','PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE',TO_TIMESTAMP('2026-11-02 22:40','YYYY-MM-DD HH24:MI'),100,'de.metas.handlingunits.createFromLabel.ProductNotFoundForProductCode')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545981
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='No product found for product code {0}',Updated=TO_TIMESTAMP('2026-11-02 22:40','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545981
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-02 22:40','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545981
;
