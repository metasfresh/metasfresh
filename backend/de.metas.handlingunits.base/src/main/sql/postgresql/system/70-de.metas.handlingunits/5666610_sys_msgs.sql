-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T08:05:36.859Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545214,0,TO_TIMESTAMP('2022-11-29 10:05:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','HU is empty','E',TO_TIMESTAMP('2022-11-29 10:05:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.HUIsEmpty')
;

-- 2022-11-29T08:05:36.862Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545214 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T08:06:26.355Z
UPDATE AD_Message SET MsgText='HU ist leer',Updated=TO_TIMESTAMP('2022-11-29 10:06:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545214
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T08:07:22.692Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU ist leer',Updated=TO_TIMESTAMP('2022-11-29 10:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545214
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T08:07:33.190Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU ist leer',Updated=TO_TIMESTAMP('2022-11-29 10:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545214
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T08:07:35.786Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-29 10:07:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545214
;

-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T08:09:37.400Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545215,0,TO_TIMESTAMP('2022-11-29 10:09:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','None of the HU''s products are needed for issuing','E',TO_TIMESTAMP('2022-11-29 10:09:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.HUProductsNotMatchingIssuingProducts')
;

-- 2022-11-29T08:09:37.402Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545215 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T08:09:53.485Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Für die Ausstellung werden keine Produkte der HU benötigt',Updated=TO_TIMESTAMP('2022-11-29 10:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545215
;

-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T08:09:55.782Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-29 10:09:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545215
;

-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T08:09:59.943Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Für die Ausstellung werden keine Produkte der HU benötigt',Updated=TO_TIMESTAMP('2022-11-29 10:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545215
;







-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T09:06:43.582Z
UPDATE AD_Message SET MsgText='Keines der Produkte der HU entspricht den für die Zuteilung erforderlichen Produkten.',Updated=TO_TIMESTAMP('2022-11-29 11:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545215
;

-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T09:06:48.400Z
UPDATE AD_Message_Trl SET MsgText='Keines der Produkte der HU entspricht den für die Zuteilung erforderlichen Produkten.',Updated=TO_TIMESTAMP('2022-11-29 11:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545215
;

-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T09:06:52.607Z
UPDATE AD_Message_Trl SET MsgText='Keines der Produkte der HU entspricht den für die Zuteilung erforderlichen Produkten.',Updated=TO_TIMESTAMP('2022-11-29 11:06:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545215
;

-- Value: de.metas.handlingunits.HUProductsNotMatchingIssuingProducts
-- 2022-11-29T09:07:05.113Z
UPDATE AD_Message_Trl SET MsgText='None of the HU products match the products needed for issuing.',Updated=TO_TIMESTAMP('2022-11-29 11:07:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545215
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T09:07:33.986Z
UPDATE AD_Message SET MsgText='Handling Unit ist leer.',Updated=TO_TIMESTAMP('2022-11-29 11:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545214
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T09:07:40.559Z
UPDATE AD_Message_Trl SET MsgText='Handling Unit ist leer.',Updated=TO_TIMESTAMP('2022-11-29 11:07:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545214
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T09:07:45.308Z
UPDATE AD_Message_Trl SET MsgText='Handling Unit ist leer.',Updated=TO_TIMESTAMP('2022-11-29 11:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545214
;

-- Value: de.metas.handlingunits.HUIsEmpty
-- 2022-11-29T09:07:57.409Z
UPDATE AD_Message_Trl SET MsgText='Handling Unit is empty.',Updated=TO_TIMESTAMP('2022-11-29 11:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545214
;

