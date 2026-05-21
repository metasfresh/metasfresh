-- gh#29689 — MobileUI: user-friendly error messages for QR code scan failures
-- Creates 15 AD_Message records for de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages
-- Backfills ErrorCode on 3 pre-existing picking messages (545452, 545454, 545477)

-- Value: de.metas.mobile.qr.WrongType.Locator
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545689 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:00','YYYY-MM-DD HH24:MI'),100,'D','Y','Lagerort ''{0}'' eingescannt — erwartet wird eine Artikel-HU','E','QR_WRONG_TYPE_LOCATOR',TO_TIMESTAMP('2026-05-21 10:00','YYYY-MM-DD HH24:MI'),100,'de.metas.mobile.qr.WrongType.Locator')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545689
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Scanned locator ''{0}'' — expected a product HU',Updated=TO_TIMESTAMP('2026-05-21 10:00','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545689
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:00','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545689
;

-- Value: de.metas.mobile.qr.WrongType.Generic
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545690 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:01','YYYY-MM-DD HH24:MI'),100,'D','Y','Falscher QR-Code-Typ: {0}','E','QR_WRONG_TYPE',TO_TIMESTAMP('2026-05-21 10:01','YYYY-MM-DD HH24:MI'),100,'de.metas.mobile.qr.WrongType.Generic')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545690
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Wrong QR code type: {0}',Updated=TO_TIMESTAMP('2026-05-21 10:01','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545690
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:01','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545690
;

-- Value: de.metas.mobile.qr.NotRecognized
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545691 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:02','YYYY-MM-DD HH24:MI'),100,'D','Y','QR-Code nicht erkannt: ''{0}''','E','QR_NOT_RECOGNIZED',TO_TIMESTAMP('2026-05-21 10:02','YYYY-MM-DD HH24:MI'),100,'de.metas.mobile.qr.NotRecognized')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545691
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='QR code not recognized: ''{0}''',Updated=TO_TIMESTAMP('2026-05-21 10:02','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545691
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:02','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545691
;

-- Value: de.metas.mobile.qr.HuNotFound
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545692 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:03','YYYY-MM-DD HH24:MI'),100,'D','Y','Keine HU für diesen QR-Code gefunden','E','QR_HU_NOT_FOUND',TO_TIMESTAMP('2026-05-21 10:03','YYYY-MM-DD HH24:MI'),100,'de.metas.mobile.qr.HuNotFound')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545692
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='No HU found for this QR code',Updated=TO_TIMESTAMP('2026-05-21 10:03','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545692
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:03','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545692
;

-- Value: de.metas.mobile.qr.HuAmbiguous
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545693 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:04','YYYY-MM-DD HH24:MI'),100,'D','Y','Mehrere HUs für diesen QR-Code gefunden','E','QR_HU_AMBIGUOUS',TO_TIMESTAMP('2026-05-21 10:04','YYYY-MM-DD HH24:MI'),100,'de.metas.mobile.qr.HuAmbiguous')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545693
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Multiple HUs found for this QR code',Updated=TO_TIMESTAMP('2026-05-21 10:04','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545693
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:04','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545693
;

-- Value: de.metas.distribution.HuReservedByOtherDocument
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545694 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:05','YYYY-MM-DD HH24:MI'),100,'D','Y','HU ist für einen anderen Auftrag reserviert','E','DISTRIBUTION_HU_RESERVED',TO_TIMESTAMP('2026-05-21 10:05','YYYY-MM-DD HH24:MI'),100,'de.metas.distribution.HuReservedByOtherDocument')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545694
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='HU is reserved by another document',Updated=TO_TIMESTAMP('2026-05-21 10:05','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545694
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:05','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545694
;

-- Value: de.metas.distribution.HuNotAtTargetWorkplace
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545695 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:06','YYYY-MM-DD HH24:MI'),100,'D','Y','HU befindet sich nicht am Ziel-Wagen','E','DISTRIBUTION_HU_NOT_AT_TARGET',TO_TIMESTAMP('2026-05-21 10:06','YYYY-MM-DD HH24:MI'),100,'de.metas.distribution.HuNotAtTargetWorkplace')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545695
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='HU is not at the target trolley',Updated=TO_TIMESTAMP('2026-05-21 10:06','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545695
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:06','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545695
;

-- Value: de.metas.distribution.HuAlreadyAtTarget
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545696 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:07','YYYY-MM-DD HH24:MI'),100,'D','Y','HU befindet sich bereits am Ziel','E','DISTRIBUTION_HU_ALREADY_AT_TARGET',TO_TIMESTAMP('2026-05-21 10:07','YYYY-MM-DD HH24:MI'),100,'de.metas.distribution.HuAlreadyAtTarget')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545696
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='HU is already at the target',Updated=TO_TIMESTAMP('2026-05-21 10:07','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545696
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:07','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545696
;

-- Value: de.metas.inventory.HuAlreadyCounted
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545697 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:08','YYYY-MM-DD HH24:MI'),100,'D','Y','HU wurde bereits gezählt','E','INVENTORY_HU_ALREADY_COUNTED',TO_TIMESTAMP('2026-05-21 10:08','YYYY-MM-DD HH24:MI'),100,'de.metas.inventory.HuAlreadyCounted')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545697
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='HU was already counted',Updated=TO_TIMESTAMP('2026-05-21 10:08','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545697
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:08','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545697
;

-- Value: de.metas.hu_consolidation.LuExpectedAtTarget
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545698 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:09','YYYY-MM-DD HH24:MI'),100,'D','Y','LU wird am Ziel-Regal erwartet','E','HU_CONSOL_LU_EXPECTED_AT_TARGET',TO_TIMESTAMP('2026-05-21 10:09','YYYY-MM-DD HH24:MI'),100,'de.metas.hu_consolidation.LuExpectedAtTarget')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545698
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='LU is expected at the target rack',Updated=TO_TIMESTAMP('2026-05-21 10:09','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545698
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:09','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545698
;

-- Value: de.metas.hu_consolidation.LuNotAtPickingSlot
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545699 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:10','YYYY-MM-DD HH24:MI'),100,'D','Y','LU befindet sich nicht am Kommissionierplatz','E','HU_CONSOL_LU_NOT_AT_SLOT',TO_TIMESTAMP('2026-05-21 10:10','YYYY-MM-DD HH24:MI'),100,'de.metas.hu_consolidation.LuNotAtPickingSlot')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545699
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='LU is not at the picking slot',Updated=TO_TIMESTAMP('2026-05-21 10:10','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545699
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:10','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545699
;

-- Value: de.metas.mobile.InternalError
-- 2026-05-21 10:00
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545700 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:11','YYYY-MM-DD HH24:MI'),100,'D','Y','Interner Fehler. Bitte wenden Sie sich an den Support.','E','MOBILE_INTERNAL_ERROR',TO_TIMESTAMP('2026-05-21 10:11','YYYY-MM-DD HH24:MI'),100,'de.metas.mobile.InternalError')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545700
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Internal error. Please contact support.',Updated=TO_TIMESTAMP('2026-05-21 10:11','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545700
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:11','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545700
;

-- Value: de.metas.inventory.HuNotInInventory
-- 2026-05-21 10:12
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545705 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:12','YYYY-MM-DD HH24:MI'),100,'D','Y','HU wurde in diesem Inventar nicht gefunden','E','INVENTORY_HU_NOT_IN_INVENTORY',TO_TIMESTAMP('2026-05-21 10:12','YYYY-MM-DD HH24:MI'),100,'de.metas.inventory.HuNotInInventory')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545705
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='HU not found in this inventory',Updated=TO_TIMESTAMP('2026-05-21 10:12','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545705
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:12','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545705
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_HU_NOT_FOUND_BY_ATTRIBUTE
-- Picking: scanned custom QR code matches the product but no HU with this attribute exists in stock
-- 2026-05-21 10:13
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545706 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:13','YYYY-MM-DD HH24:MI'),100,'D','Y','Keine HU mit diesem Merkmal im Lager gefunden','E','PICKING_QR_HU_NOT_FOUND_BY_ATTRIBUTE',TO_TIMESTAMP('2026-05-21 10:13','YYYY-MM-DD HH24:MI'),100,'de.metas.handlingunits.picking.job.QR_CODE_HU_NOT_FOUND_BY_ATTRIBUTE')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545706
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='No HU found in stock for this attribute',Updated=TO_TIMESTAMP('2026-05-21 10:13','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545706
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:13','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545706
;

-- Value: de.metas.handlingunits.picking.job.NO_PICKABLE_HU_IN_WAREHOUSE
-- Picking: system tried to auto-pick from warehouse but found no stock for the product
-- 2026-05-21 10:14
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,545707 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-21 10:14','YYYY-MM-DD HH24:MI'),100,'D','Y','Keine kommissionierbare HU im Lager vorhanden','E','PICKING_NO_PICKABLE_HU_IN_WAREHOUSE',TO_TIMESTAMP('2026-05-21 10:14','YYYY-MM-DD HH24:MI'),100,'de.metas.handlingunits.picking.job.NO_PICKABLE_HU_IN_WAREHOUSE')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545707
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='No pickable HU available in warehouse',Updated=TO_TIMESTAMP('2026-05-21 10:14','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545707
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-21 10:14','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545707
;

-- Backfill missing ErrorCode on pre-existing picking messages
-- Value: de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG (ID 545452)
UPDATE AD_Message SET ErrorCode='PICKING_LM_QR_NO_LOT_NUMBER',Updated=TO_TIMESTAMP('2026-05-21 10:15','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Message_ID=545452
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG (ID 545454)
UPDATE AD_Message SET ErrorCode='PICKING_NO_HU_FOR_EXTERNAL_LOT',Updated=TO_TIMESTAMP('2026-05-21 10:15','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Message_ID=545454
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG (ID 545477)
UPDATE AD_Message SET ErrorCode='PICKING_QR_PRODUCT_NOT_MATCHING',Updated=TO_TIMESTAMP('2026-05-21 10:15','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Message_ID=545477
;
