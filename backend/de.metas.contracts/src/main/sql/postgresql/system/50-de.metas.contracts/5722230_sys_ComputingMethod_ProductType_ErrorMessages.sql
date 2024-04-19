-- Run mode: SWING_CLIENT

-- Value: ComputingMethodTypeRequiresRawProduct
-- 2024-04-18T07:44:20.668Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545388,0,TO_TIMESTAMP('2024-04-18 10:44:20.466','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','This Computing Method Type requires the raw product.','E',TO_TIMESTAMP('2024-04-18 10:44:20.466','YYYY-MM-DD HH24:MI:SS.US'),100,'ComputingMethodTypeRequiresRawProduct')
;

-- 2024-04-18T07:44:20.681Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545388 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ComputingMethodTypeRequiresProcessedProduct
-- 2024-04-18T07:44:52.762Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545389,0,TO_TIMESTAMP('2024-04-18 10:44:52.642','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','This Computing Method Type requires the processed product.','E',TO_TIMESTAMP('2024-04-18 10:44:52.642','YYYY-MM-DD HH24:MI:SS.US'),100,'ComputingMethodTypeRequiresProcessedProduct')
;

-- 2024-04-18T07:44:52.763Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545389 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ComputingMethodTypeRequiresCoProduct
-- 2024-04-18T07:45:12.715Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545390,0,TO_TIMESTAMP('2024-04-18 10:45:12.579','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','This Computing Method Type requires the co-product.','E',TO_TIMESTAMP('2024-04-18 10:45:12.579','YYYY-MM-DD HH24:MI:SS.US'),100,'ComputingMethodTypeRequiresCoProduct')
;

-- 2024-04-18T07:45:12.716Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545390 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ComputingMethodTypeRequiresCoProduct
-- 2024-04-18T10:35:14.070Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 12:35:14.07','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545390
;

-- Value: ComputingMethodTypeRequiresCoProduct
-- 2024-04-18T10:39:39.521Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dieser Baustein Typ benötigt das Kuppelprodukt.',Updated=TO_TIMESTAMP('2024-04-18 12:39:39.521','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545390
;

-- Value: ComputingMethodTypeRequiresCoProduct
-- 2024-04-18T10:39:56.881Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dieser Baustein Typ benötigt das Kuppelprodukt.',Updated=TO_TIMESTAMP('2024-04-18 12:39:56.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545390
;

-- 2024-04-18T10:39:56.882Z
UPDATE AD_Message SET MsgText='Dieser Baustein Typ benötigt das Kuppelprodukt.' WHERE AD_Message_ID=545390
;

-- Value: ComputingMethodTypeRequiresProcessedProduct
-- 2024-04-18T10:40:30.919Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 12:40:30.919','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545389
;

-- Value: ComputingMethodTypeRequiresProcessedProduct
-- 2024-04-18T10:40:54.484Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dieser Baustein Typ benötigt das Verarbeitete Produkt.',Updated=TO_TIMESTAMP('2024-04-18 12:40:54.484','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545389
;

-- Value: ComputingMethodTypeRequiresProcessedProduct
-- 2024-04-18T10:41:00.787Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dieser Baustein Typ benötigt das Verarbeitete Produkt.',Updated=TO_TIMESTAMP('2024-04-18 12:41:00.787','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545389
;

-- 2024-04-18T10:41:00.788Z
UPDATE AD_Message SET MsgText='Dieser Baustein Typ benötigt das Verarbeitete Produkt.' WHERE AD_Message_ID=545389
;

-- Value: ComputingMethodTypeRequiresRawProduct
-- 2024-04-18T10:41:16.268Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 12:41:16.268','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545388
;

-- Value: ComputingMethodTypeRequiresRawProduct
-- 2024-04-18T10:41:51.518Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dieser Baustein Typ benötigt das Rohprodukt.',Updated=TO_TIMESTAMP('2024-04-18 12:41:51.517','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545388
;

-- Value: ComputingMethodTypeRequiresRawProduct
-- 2024-04-18T10:41:56.307Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dieser Baustein Typ benötigt das Rohprodukt.',Updated=TO_TIMESTAMP('2024-04-18 12:41:56.306','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545388
;

-- 2024-04-18T10:41:56.308Z
UPDATE AD_Message SET MsgText='Dieser Baustein Typ benötigt das Rohprodukt.' WHERE AD_Message_ID=545388
;

