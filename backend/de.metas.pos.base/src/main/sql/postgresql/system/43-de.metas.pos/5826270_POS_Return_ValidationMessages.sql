-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545858 (de.metas.pos.Return.NoTaxFound)
--   AD_Message 545859 (de.metas.pos.Return.PriceUomMismatch)
--   AD_Message 545860 (de.metas.pos.Return.CurrencyMismatch)

-- ############################################################
-- Message 1: de.metas.pos.Return.NoTaxFound
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545858 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Für die Rücknahmeposition wurde keine Steuer gefunden.','E',TO_TIMESTAMP('2026-09-24 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.NoTaxFound')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_NO_TAX_FOUND', Updated=TO_TIMESTAMP('2026-09-24 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545858
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545858
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='No tax was found for the return line.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545858
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545858
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545858
;

-- ############################################################
-- Message 2: de.metas.pos.Return.PriceUomMismatch
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545859 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 14:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Preis-Mengeneinheit der Rücknahmeposition muss mit der Preis-Mengeneinheit der Rechnungsposition übereinstimmen.','E',TO_TIMESTAMP('2026-09-24 14:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.PriceUomMismatch')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_PRICE_UOM_MISMATCH', Updated=TO_TIMESTAMP('2026-09-24 14:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545859
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545859
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The POS return line''s price UOM must match the invoice candidate''s price UOM.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545859
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545859
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545859
;

-- ############################################################
-- Message 3: de.metas.pos.Return.CurrencyMismatch
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545860 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 14:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Währung der Rücknahmeposition muss mit der Währung der Rechnungsposition übereinstimmen.','E',TO_TIMESTAMP('2026-09-24 14:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.CurrencyMismatch')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_CURRENCY_MISMATCH', Updated=TO_TIMESTAMP('2026-09-24 14:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545860
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545860
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The POS return line''s price currency must match the invoice candidate''s currency.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545860
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545860
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 14:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545860
;
