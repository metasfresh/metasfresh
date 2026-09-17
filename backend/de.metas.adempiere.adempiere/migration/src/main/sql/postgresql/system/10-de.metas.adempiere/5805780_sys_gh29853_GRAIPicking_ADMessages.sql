-- IDs allocated from idserver.metas.de on 2026-06-02:
--   AD_Message_ID 545740, 545741, 545742, 545743, 545744 (GRAI picking error messages)
--   AD_MigrationScript 5805780

-- Message 1: InvalidGRAIBarcode
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545740 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Der gescannte Barcode ist kein gültiger GRAI.','E',TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.InvalidGRAIBarcode');
UPDATE AD_Message SET ErrorCode='InvalidGRAIBarcode', Updated=TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545740;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545740
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The scanned barcode is not a valid GRAI.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545740;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545740;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545740;

-- Message 2: GRAINoMatchingTUType
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545741 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-02 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Für den gescannten GRAI ist kein Transporteinheitstyp konfiguriert.','E',TO_TIMESTAMP('2026-06-02 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.GRAINoMatchingTUType');
UPDATE AD_Message SET ErrorCode='GRAINoMatchingTUType', Updated=TO_TIMESTAMP('2026-06-02 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545741;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545741
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='No transport unit type is configured for the scanned GRAI.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545741;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545741;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545741;

-- Message 3: GRAITUNotAllowedOnLU
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545742 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Der Transporteinheitstyp des gescannten GRAI ist auf der Ziel-Ladeeinheit nicht zulässig.','E',TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.GRAITUNotAllowedOnLU');
UPDATE AD_Message SET ErrorCode='GRAITUNotAllowedOnLU', Updated=TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545742;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545742
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The transport unit type for the scanned GRAI is not allowed on the picking-target loading unit.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545742;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545742;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545742;

-- Message 4: GRAIMultipleScanned
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545743 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-02 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Es wurden mehrere unterschiedliche GRAIs erfasst. Bitte auf einen einzigen GRAI eingrenzen.','E',TO_TIMESTAMP('2026-06-02 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.GRAIMultipleScanned');
UPDATE AD_Message SET ErrorCode='GRAIMultipleScanned', Updated=TO_TIMESTAMP('2026-06-02 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545743;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545743
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Multiple different GRAIs were scanned. Please narrow down to a single GRAI.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545743;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545743;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545743;

-- Message 5: GRAINoCapacityForProduct
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545744 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Für den Transporteinheitstyp des gescannten GRAI ist für dieses Produkt keine Kapazität konfiguriert.','E',TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.GRAINoCapacityForProduct');
UPDATE AD_Message SET ErrorCode='GRAINoCapacityForProduct', Updated=TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545744;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545744
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The transport unit type for the scanned GRAI has no capacity configured for this product.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545744;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545744;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-02 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545744;
