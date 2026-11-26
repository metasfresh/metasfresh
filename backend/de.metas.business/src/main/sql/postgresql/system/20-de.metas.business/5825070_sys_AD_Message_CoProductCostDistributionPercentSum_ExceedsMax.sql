-- AD_Message for the per-order / BOM-rollup Σ(co-product cost distribution percent) > 100% guard.
-- Thrown by BOM.assertValidTotalCoProductDistributionPercent (Standard-cost definitional rollup) and
-- PPOrderCosts.assertValidTotalCoProductDistributionPercent (PP_Order post-calculation). Localized so a
-- German user gets a German message. Params: {0} = the offending sum, {1} = the offending product(s).
-- Distinct from the per-product [0,100] data-entry range guard (AD_Message 545836, 5824770).
--
-- IDs allocated from idserver.metas.de on 2026-09-18:
--   AD_Message 545849 (de.metas.manufacturing.CoProductCostDistributionPercentSum_ExceedsMax)

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545849 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-18 09:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Summe der Kostenverteilungsanteile der Co-Products von {0} überschreitet 100 % für Produkt(e): {1}','E',TO_TIMESTAMP('2026-09-18 09:10:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.manufacturing.CoProductCostDistributionPercentSum_ExceedsMax')
;

-- 2. short ErrorCode (must fit AD_Message.ErrorCode varchar(40))
UPDATE AD_Message SET ErrorCode='CoProductCostDistributionPctSumOver100', Updated=TO_TIMESTAMP('2026-09-18 09:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545849 /*From ID Server*/
;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545849
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='Co-products'' cost distribution percent sum of {0} exceeds 100% for product(s): {1}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-18 09:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545849
;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-18 09:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545849
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-18 09:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545849
;
