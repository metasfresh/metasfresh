-- AD_Message for the product life-cycle (BBS-Status) enforcement predicate.
-- Shown when a business action is blocked by M_Product.ProductLifeCycleStatus (see de.metas.product.BBSStatus /
-- ProductBL.assertAllowed).
--
-- AD_Message base text is in German (DE). Rationale: most users are German-speakers; if a
-- translation is missing the fallback (AD_Message.MsgText) shows German. en_US translation
-- is provided below.
-- {0}/{1} are product value / BBS-Status code placeholders filled in by AdempiereException(MSG, ...).

-- =============================================================================
-- M_Product_BBSStatus_ActionBlocked  (AD_Message_ID 545793)
-- =============================================================================

-- 2026-07-27T17:20:18.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545793 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-27 17:20:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkt {0} ist im Status {1} - Aktion nicht erlaubt.','E',TO_TIMESTAMP('2026-07-27 17:20:18','YYYY-MM-DD HH24:MI:SS'),100,'M_Product_BBSStatus_ActionBlocked')
;

-- 2026-07-27T17:20:19.000Z
UPDATE AD_Message SET ErrorCode='M_Product_BBSStatus_ActionBlocked', Updated=TO_TIMESTAMP('2026-07-27 17:20:19','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545793 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-07-27T17:20:20.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545793 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-07-27T17:20:21.000Z
UPDATE AD_Message_Trl SET MsgText='Product {0} is in status {1} - action not allowed.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 17:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545793 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-07-27T17:20:22.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 17:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545793 /*From ID Server*/
;

-- 2026-07-27T17:20:23.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 17:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545793 /*From ID Server*/
;
