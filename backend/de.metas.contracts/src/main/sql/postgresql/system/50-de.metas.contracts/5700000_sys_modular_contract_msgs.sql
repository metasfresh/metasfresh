-- Value: de.metas.contracts.modular.receiptCompleteLogDescription
-- 2023-08-24T09:00:07.080007500Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545323, 0, TO_TIMESTAMP('2023-08-24 11:00:06.915', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Wareneingang für Produkt {0} mit der Menge {1} wurde fertiggestellt.', 'I', TO_TIMESTAMP('2023-08-24 11:00:06.915', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.receiptCompleteLogDescription')
;

-- 2023-08-24T09:00:07.086850500Z
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
  AND t.AD_Message_ID = 545323
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.receiptCompleteLogDescription
-- 2023-08-24T09:00:17.138168300Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:00:17.138', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545323
;

-- Value: de.metas.contracts.modular.receiptCompleteLogDescription
-- 2023-08-24T09:00:18.614690200Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:00:18.614', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545323
;

-- Value: de.metas.contracts.modular.receiptCompleteLogDescription
-- 2023-08-24T09:00:58.044592800Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='Receipt for product {0} with the quantity {1} was completed.', Updated=TO_TIMESTAMP('2023-08-24 11:00:58.044', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545323
;

-- Value: de.metas.contracts.modular.receiptReverseLogDescription
-- 2023-08-24T09:04:54.742965700Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545324, 0, TO_TIMESTAMP('2023-08-24 11:04:54.626', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Wareneingang für Produkt {0} mit der Menge {1} wurde storniert.', 'I', TO_TIMESTAMP('2023-08-24 11:04:54.626', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.receiptReverseLogDescription')
;

-- 2023-08-24T09:04:54.744032500Z
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
  AND t.AD_Message_ID = 545324
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.receiptReverseLogDescription
-- 2023-08-24T09:05:14.606221900Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:05:14.606', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545324
;

-- Value: de.metas.contracts.modular.receiptReverseLogDescription
-- 2023-08-24T09:05:20.763810100Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:05:20.763', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545324
;

-- Value: de.metas.contracts.modular.receiptReverseLogDescription
-- 2023-08-24T09:06:01.926517300Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='Receipt for product {0} with the quantity {1} was reversed.', Updated=TO_TIMESTAMP('2023-08-24 11:06:01.926', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545324
;

-- Value: de.metas.contracts.modular.interimContractReverseLogDescription
-- 2023-08-24T09:39:42.542127800Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545325, 0, TO_TIMESTAMP('2023-08-24 11:39:42.172', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Vorfinanzierungsvertrag für Produkt {0} mit der Menge {1} wurde storniert.', 'I', TO_TIMESTAMP('2023-08-24 11:39:42.172', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.interimContractReverseLogDescription')
;

-- 2023-08-24T09:39:42.543725500Z
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
  AND t.AD_Message_ID = 545325
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interimContractReverseLogDescription
-- 2023-08-24T09:39:47.109395500Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:39:47.109', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545325
;

-- Value: de.metas.contracts.modular.interimContractReverseLogDescription
-- 2023-08-24T09:40:04.797783Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:40:04.797', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545325
;

-- Value: de.metas.contracts.modular.interimContractReverseLogDescription
-- 2023-08-24T09:40:28.232279800Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='Interim contract for product {0} with the quantity {1} was reversed.', Updated=TO_TIMESTAMP('2023-08-24 11:40:28.232', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545325
;

-- Value: de.metas.contracts.modular.interimContractCompleteLogDescription
-- 2023-08-24T09:41:41.525811200Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545326, 0, TO_TIMESTAMP('2023-08-24 11:41:41.416', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Vorfinanzierungsvertrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.', 'I', TO_TIMESTAMP('2023-08-24 11:41:41.416', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.interimContractCompleteLogDescription')
;

-- 2023-08-24T09:41:41.527385700Z
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
  AND t.AD_Message_ID = 545326
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interimContractCompleteLogDescription
-- 2023-08-24T09:42:09.110072700Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:42:09.11', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545326
;

-- Value: de.metas.contracts.modular.interimContractCompleteLogDescription
-- 2023-08-24T09:42:10.322988800Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:42:10.322', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545326
;

-- Value: de.metas.contracts.modular.interimContractCompleteLogDescription
-- 2023-08-24T09:42:47.060432400Z
UPDATE AD_Message_Trl
SET MsgText='Interim Contract for product {0} with the quantity {1} was completed.', Updated=TO_TIMESTAMP('2023-08-24 11:42:47.06', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545326
;

-- Value: de.metas.contracts.modular.reverseNotAllowedIfProcessed
-- 2023-08-24T09:48:03.403664100Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545327, 0, TO_TIMESTAMP('2023-08-24 11:48:02.412', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Verarbeitete modularer Vertrag Logs können nicht storniert werden. Log: {0}', 'E', TO_TIMESTAMP('2023-08-24 11:48:02.412', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.reverseNotAllowedIfProcessed')
;

-- 2023-08-24T09:48:03.404722200Z
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
  AND t.AD_Message_ID = 545327
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.reverseNotAllowedIfProcessed
-- 2023-08-24T09:48:27.143056300Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:48:27.143', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545327
;

-- Value: de.metas.contracts.modular.reverseNotAllowedIfProcessed
-- 2023-08-24T09:48:28.016710100Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-24 11:48:28.016', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545327
;

-- Value: de.metas.contracts.modular.reverseNotAllowedIfProcessed
-- 2023-08-24T09:49:14.518221300Z
UPDATE AD_Message_Trl
SET MsgText='Processed modular contract logs can''t be reversed. Log: {0}', Updated=TO_TIMESTAMP('2023-08-24 11:49:14.518', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545327
;

