-- AD_Messages for the product purchase/sales gate feature.
-- Shown when a product is blocked from purchase or sale due to IsPurchased/IsSold=N.
-- Also shown when CreatePOFromSOs is attempted for products not marked as purchased.
--
-- AD_Message base text is in German (DE). Rationale: most users are German-speakers; if a
-- translation is missing the fallback (AD_Message.MsgText) shows German. en_US translation
-- is provided below.
-- {0}/{1} are product name/value placeholders filled in by AdempiereException(MSG, ...).

-- =============================================================================
-- MSG_M_Product_NotPurchased  (AD_Message_ID 545768)
-- =============================================================================

-- 2026-06-29T00:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545768 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-29 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkt {0} ({1}) ist nicht zum Einkauf freigegeben (Wird Eingekauft = Nein).','E',TO_TIMESTAMP('2026-06-29 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MSG_M_Product_NotPurchased')
;

-- 2026-06-29T00:00:01.000Z
UPDATE AD_Message SET ErrorCode='M_Product_NotPurchased', Updated=TO_TIMESTAMP('2026-06-29 00:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545768 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-06-29T00:00:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545768 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-06-29T00:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Product {0} ({1}) is not released for purchasing (Is Purchased = No).',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545768 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-06-29T00:00:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545768 /*From ID Server*/
;

-- 2026-06-29T00:00:05.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545768 /*From ID Server*/
;

-- =============================================================================
-- MSG_M_Product_NotSold  (AD_Message_ID 545769)
-- =============================================================================

-- 2026-06-29T00:00:06.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545769 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-29 00:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkt {0} ({1}) ist nicht zum Verkauf freigegeben (Verkauft = Nein).','E',TO_TIMESTAMP('2026-06-29 00:00:06','YYYY-MM-DD HH24:MI:SS'),100,'MSG_M_Product_NotSold')
;

-- 2026-06-29T00:00:07.000Z
UPDATE AD_Message SET ErrorCode='M_Product_NotSold', Updated=TO_TIMESTAMP('2026-06-29 00:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545769 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-06-29T00:00:08.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545769 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-06-29T00:00:09.000Z
UPDATE AD_Message_Trl SET MsgText='Product {0} ({1}) is not released for sales (Is Sold = No).',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545769 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-06-29T00:00:10.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545769 /*From ID Server*/
;

-- 2026-06-29T00:00:11.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545769 /*From ID Server*/
;

-- =============================================================================
-- MSG_CreatePOFromSOs_ProductsNotPurchased  (AD_Message_ID 545770)
-- =============================================================================

-- 2026-06-29T00:00:12.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545770 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-29 00:00:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Es wurde keine Bestellung erstellt. Folgende Produkte sind nicht zum Einkauf freigegeben (Wird Eingekauft = Nein): {0}','E',TO_TIMESTAMP('2026-06-29 00:00:12','YYYY-MM-DD HH24:MI:SS'),100,'MSG_CreatePOFromSOs_ProductsNotPurchased')
;

-- 2026-06-29T00:00:13.000Z
UPDATE AD_Message SET ErrorCode='CreatePOFromSOs_ProductsNotPurchased', Updated=TO_TIMESTAMP('2026-06-29 00:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545770 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-06-29T00:00:14.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545770 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-06-29T00:00:15.000Z
UPDATE AD_Message_Trl SET MsgText='No purchase order was created. The following products are not released for purchasing (Is Purchased = No): {0}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545770 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-06-29T00:00:16.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545770 /*From ID Server*/
;

-- 2026-06-29T00:00:17.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-29 00:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545770 /*From ID Server*/
;
