-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.interimInvoiceCompleteLogDescription
-- 2023-09-18T14:43:36.864Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545338, 0, TO_TIMESTAMP('2023-09-18 16:43:36.683', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Akontorechnung für das Produkt {0} mit der Menge {1} wurde fertiggestellt.', 'I', TO_TIMESTAMP('2023-09-18 16:43:36.683', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.interimInvoiceCompleteLogDescription')
;

-- 2023-09-18T14:43:36.885Z
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545338
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interimInvoiceCompleteLogDescription
-- 2023-09-18T14:43:44.661Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-18 16:43:44.661', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545338
;

-- Value: de.metas.contracts.modular.interimInvoiceCompleteLogDescription
-- 2023-09-18T14:43:46.602Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-18 16:43:46.602', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545338
;

-- Value: de.metas.contracts.modular.interimInvoiceCompleteLogDescription
-- 2023-09-18T14:44:34.273Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='Interim invoice for the product {0} with the quantity {1} was completed.', Updated=TO_TIMESTAMP('2023-09-18 16:44:34.273', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545338
;

-- Value: de.metas.contracts.modular.interimInvoiceReverseLogDescription
-- 2023-09-18T14:45:33.814Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545339, 0, TO_TIMESTAMP('2023-09-18 16:45:33.7', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Akontorechnung für das Produkt {0} mit der Menge {1} wurde storniert.', 'I', TO_TIMESTAMP('2023-09-18 16:45:33.7', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.interimInvoiceReverseLogDescription')
;

-- 2023-09-18T14:45:33.817Z
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545339
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interimInvoiceReverseLogDescription
-- 2023-09-18T14:45:40.485Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-18 16:45:40.485', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545339
;

-- Value: de.metas.contracts.modular.interimInvoiceReverseLogDescription
-- 2023-09-18T14:45:41.504Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-09-18 16:45:41.504', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545339
;

-- Value: de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnComplete.Description
-- 2023-09-18T14:46:11.723Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='Interim invoice for the product {0} with the quantity {1} was reversed.', Updated=TO_TIMESTAMP('2023-09-18 16:46:11.723', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545321
;

