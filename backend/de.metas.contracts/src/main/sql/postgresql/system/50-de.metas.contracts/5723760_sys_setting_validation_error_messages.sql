-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsRaw
-- 2024-05-16T06:32:37.472Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545400,0,TO_TIMESTAMP('2024-05-16 08:32:37.142','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Die Lagermaßeinheit von diesem Bausteinprodukt muss mit der Lagermaßeinheit des Rohprodukts übereinstimmen.','E',TO_TIMESTAMP('2024-05-16 08:32:37.142','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsRaw')
;

-- 2024-05-16T06:32:37.475Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545400 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsRaw
-- 2024-05-16T06:32:44.900Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 08:32:44.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545400
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsRaw
-- 2024-05-16T06:32:46.847Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 08:32:46.847','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545400
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsRaw
-- 2024-05-16T06:33:52.486Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The stock UOM of this modules product needs to be identical with raw products stock UOM.',Updated=TO_TIMESTAMP('2024-05-16 08:33:52.486','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545400
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsProcessed
-- 2024-05-16T06:35:41.240Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545401,0,TO_TIMESTAMP('2024-05-16 08:35:41.123','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Die Lagermaßeinheit von diesem Bausteinprodukt muss mit der Lagermaßeinheit des verarbeiteten Erzeugnisses übereinstimmen.','E',TO_TIMESTAMP('2024-05-16 08:35:41.123','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsProcessed')
;

-- 2024-05-16T06:35:41.242Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545401 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsProcessed
-- 2024-05-16T06:35:49.808Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 08:35:49.808','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545401
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsProcessed
-- 2024-05-16T06:35:50.761Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 08:35:50.761','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545401
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsProcessed
-- 2024-05-16T06:36:38.237Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The stock UOM of this modules product needs to be identical with processed products stock UOM.',Updated=TO_TIMESTAMP('2024-05-16 08:36:38.237','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545401
;

-- Value: de.metas.contracts.modular.settings.interceptor.CoProductSetWithoutProcessedProductSet
-- 2024-05-16T07:20:30.461Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545402,0,TO_TIMESTAMP('2024-05-16 09:20:30.345','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Wenn ein Kuppelprodukt angeben wird, muss auch ein verarbeitetes Erzeugnis angegeben werden.','E',TO_TIMESTAMP('2024-05-16 09:20:30.345','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.CoProductSetWithoutProcessedProductSet')
;

-- 2024-05-16T07:20:30.464Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545402 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.CoProductSetWithoutProcessedProductSet
-- 2024-05-16T07:20:35.647Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:20:35.647','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545402
;

-- Value: de.metas.contracts.modular.settings.interceptor.CoProductSetWithoutProcessedProductSet
-- 2024-05-16T07:20:42.600Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:20:42.6','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545402
;

-- Value: de.metas.contracts.modular.settings.interceptor.CoProductSetWithoutProcessedProductSet
-- 2024-05-16T07:21:22.707Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='If the co-product is set, the processed product needs also to be set',Updated=TO_TIMESTAMP('2024-05-16 09:21:22.707','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545402
;

-- Value: de.metas.contracts.modular.settings.interceptor.NoBOMFoundForProcessedProduct
-- 2024-05-16T07:24:34.235Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545404,0,TO_TIMESTAMP('2024-05-16 09:24:34.122','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Es konnte keine Stückliste für das verarbeitete Erzeugnis gefunden werden.','E',TO_TIMESTAMP('2024-05-16 09:24:34.122','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.NoBOMFoundForProcessedProduct')
;

-- 2024-05-16T07:24:34.237Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545404 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.NoBOMFoundForProcessedProduct
-- 2024-05-16T07:24:42.313Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:24:42.313','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545404
;

-- Value: de.metas.contracts.modular.settings.interceptor.NoBOMFoundForProcessedProduct
-- 2024-05-16T07:24:44.139Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:24:44.139','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545404
;

-- Value: de.metas.contracts.modular.settings.interceptor.NoBOMFoundForProcessedProduct
-- 2024-05-16T07:25:20.164Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Couldn''t find Bill of Material for processed product.',Updated=TO_TIMESTAMP('2024-05-16 09:25:20.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545404
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyRawComponent
-- 2024-05-16T07:34:35.352Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545405,0,TO_TIMESTAMP('2024-05-16 09:34:35.205','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Die gefundene Stückliste für das verarbeitete Erzeugnis hat nicht das Rohprodukt als einzige Komponente.','E',TO_TIMESTAMP('2024-05-16 09:34:35.205','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyRawComponent')
;

-- 2024-05-16T07:34:35.354Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545405 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyRawComponent
-- 2024-05-16T07:34:40.511Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:34:40.511','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545405
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyRawComponent
-- 2024-05-16T07:34:41.825Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:34:41.825','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545405
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyRawComponent
-- 2024-05-16T07:36:08.328Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Bill of material found for processed product doesn''t have raw product as only component.',Updated=TO_TIMESTAMP('2024-05-16 09:36:08.328','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545405
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyCoProduct
-- 2024-05-16T07:38:37.987Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545406,0,TO_TIMESTAMP('2024-05-16 09:38:37.867','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Die gefundene Stückliste für das verarbeitete Erzeugnis hat nicht das Kuppelprodukt als einziges Co-Produkt.','E',TO_TIMESTAMP('2024-05-16 09:38:37.867','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyCoProduct')
;

-- 2024-05-16T07:38:37.989Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545406 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyCoProduct
-- 2024-05-16T07:38:42.823Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:38:42.823','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545406
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyCoProduct
-- 2024-05-16T07:38:43.641Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:38:43.64','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545406
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyCoProduct
-- 2024-05-16T07:39:28.765Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Bill of material found for processed product doesn''t have co product as only co product.',Updated=TO_TIMESTAMP('2024-05-16 09:39:28.765','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545406
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMCoProductDoesntMatch
-- 2024-05-16T07:42:04.706Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545407,0,TO_TIMESTAMP('2024-05-16 09:42:04.587','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Das angebene Kuppelprodukt stimmt nicht mit dem Co-Produkt der gefundene Stückliste überein.','E',TO_TIMESTAMP('2024-05-16 09:42:04.587','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.FoundBOMCoProductDoesntMatch')
;

-- 2024-05-16T07:42:04.708Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545407 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMCoProductDoesntMatch
-- 2024-05-16T07:42:13.582Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:42:13.582','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545407
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMCoProductDoesntMatch
-- 2024-05-16T07:42:23.694Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 09:42:23.694','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545407
;

-- Value: de.metas.contracts.modular.settings.interceptor.FoundBOMCoProductDoesntMatch
-- 2024-05-16T07:43:42.465Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Set Co-Product doesn''t match Co-Product of found Bill of Material.',Updated=TO_TIMESTAMP('2024-05-16 09:43:42.465','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545407
;

-- 2024-05-16T13:14:07.301Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545408 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineDependOnProduct
-- 2024-05-16T13:14:13.398Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 15:14:13.398','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545408
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineDependOnProduct
-- 2024-05-16T13:14:18.501Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-16 15:14:18.501','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545408
;

-- Value: de.metas.contracts.modular.settings.interceptor.SettingLineDependOnProduct
-- 2024-05-16T13:14:32.526Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='SettingLines still containing ComputingMethods depending on this product',Updated=TO_TIMESTAMP('2024-05-16 15:14:32.526','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545408
;
