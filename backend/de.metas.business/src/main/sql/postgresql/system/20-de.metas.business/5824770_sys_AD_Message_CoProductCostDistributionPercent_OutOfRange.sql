-- AD_Message for the M_Product.CoProductCostDistributionPercent data-entry range guard
-- (M_Product#validateCoProductCostDistributionPercent): only [0, 100] is legal; blank/NULL stays legal.

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545836 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der Kostenverteilungsanteil des Co-Products muss zwischen 0 und 100 liegen, war aber: {0}','E',TO_TIMESTAMP('2026-09-17 09:10:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product.model.interceptor.M_Product.CoProductCostDistributionPercent_OutOfRange')
;

-- 2. short ErrorCode (must fit AD_Message.ErrorCode varchar(40); the unabridged
-- 'CoProductCostDistributionPercentOutOfRange' is 42 chars and violates the column)
UPDATE AD_Message SET ErrorCode='CoProductCostDistributionPctOutOfRange', Updated=TO_TIMESTAMP('2026-09-17 09:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545836 /*From ID Server*/
;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545836
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='The co-product''s cost distribution percent must be between 0 and 100, but was: {0}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-17 09:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545836
;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-17 09:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545836
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-17 09:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545836
;
