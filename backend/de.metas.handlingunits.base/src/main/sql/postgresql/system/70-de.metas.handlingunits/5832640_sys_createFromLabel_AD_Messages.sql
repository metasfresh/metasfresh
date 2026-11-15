-- HU Manager create from label - AD_Messages (DE base text + en_US translation). AD_Message_IDs from the ID server.

-- Value: de.metas.handlingunits.createFromLabel.LabelNotRecognized
-- 2026-11-02 21:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545974 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Etikett nicht erkannt: {0}','E','LABEL_NOT_RECOGNIZED',TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.createFromLabel.LabelNotRecognized')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545974
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Label not recognized: {0}',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545974
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545974
;

-- Value: de.metas.handlingunits.createFromLabel.LabelHasNoProduct
-- 2026-11-02 21:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545975 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Das Etikett enthält keinen Artikel: {0}','E','LABEL_HAS_NO_PRODUCT',TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.createFromLabel.LabelHasNoProduct')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545975
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='The label does not identify a product: {0}',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545975
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545975
;

-- Value: de.metas.handlingunits.createFromLabel.ProductNotFoundForGTIN
-- 2026-11-02 21:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545976 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Kein Artikel für GTIN {0} gefunden','E','PRODUCT_NOT_FOUND_FOR_GTIN',TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.createFromLabel.ProductNotFoundForGTIN')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545976
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='No product found for GTIN {0}',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545976
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545976
;

-- Value: de.metas.handlingunits.createFromLabel.NotAProductLabel
-- 2026-11-02 21:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545977 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Kein Artikeletikett: {0}','E','NOT_A_PRODUCT_LABEL',TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.createFromLabel.NotAProductLabel')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545977
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Not a product label: {0}',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545977
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545977
;

-- Value: de.metas.handlingunits.createFromLabel.WeightNotUsed
-- 2026-11-02 21:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545978 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Das Gewicht auf dem Etikett wird für diesen Artikel nicht verwendet','I','WEIGHT_NOT_USED',TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.createFromLabel.WeightNotUsed')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545978
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='The weight on the label is not used for this product',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545978
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545978
;

-- Value: de.metas.handlingunits.createFromLabel.BarcodeFitsSeveralProducts
-- 2026-11-02 21:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545980 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Barcode {0} passt zu den Artikeln {1} — Buchen per Scan nicht möglich','E','BARCODE_FITS_SEVERAL_PRODUCTS',TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.createFromLabel.BarcodeFitsSeveralProducts')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545980
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Barcode {0} fits articles {1} — cannot book by scan',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545980
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-02 21:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545980
;
