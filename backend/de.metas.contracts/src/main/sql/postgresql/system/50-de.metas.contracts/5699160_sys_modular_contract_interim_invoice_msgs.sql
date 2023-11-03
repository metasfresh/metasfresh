-- Value: de.metas.contracts.modular.interim.notActiveRejection
-- 2023-08-14T07:55:58.234687900Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545318, 0, TO_TIMESTAMP('2023-08-14 09:55:58.006', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'U', 'Y', 'Vorfinazierungsverträge sind nicht aktiviert', 'I', TO_TIMESTAMP('2023-08-14 09:55:58.006', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.interim.notActiveRejection')
;

-- 2023-08-14T07:55:58.267896Z
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
  AND t.AD_Message_ID = 545318
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interim.notActiveRejection
-- 2023-08-14T07:56:07.154917300Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-14 09:56:07.154', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545318
;

-- Value: de.metas.contracts.modular.interim.notActiveRejection
-- 2023-08-14T07:56:09.276659Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-14 09:56:09.276', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545318
;

-- Value: de.metas.contracts.modular.interim.notActiveRejection
-- 2023-08-14T07:56:21.949090300Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='Interim Payment Contracts aren''t activated', Updated=TO_TIMESTAMP('2023-08-14 09:56:21.949', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545318
;

-- Value: de.metas.contracts.modular.interim.notActiveRejection
-- 2023-08-14T08:36:59.394735600Z
UPDATE AD_Message
SET EntityType='de.metas.contracts', MsgType='E', Updated=TO_TIMESTAMP('2023-08-14 10:36:59.391', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Message_ID = 545318
;

-- Value: de.metas.contracts.modular.settings.missingFlatrateTermCondition
-- 2023-08-14T09:20:37.578574400Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545319, 0, TO_TIMESTAMP('2023-08-14 11:20:37.436', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Es existieren keine Vertragsbedingungen für Vorfinanzierungsverträge in den Einstellungen für modulare Verträge: {0}.', 'E', TO_TIMESTAMP('2023-08-14 11:20:37.436', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.settings.missingFlatrateTermCondition')
;

-- 2023-08-14T09:20:37.592460900Z
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
  AND t.AD_Message_ID = 545319
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.missingFlatrateTermCondition
-- 2023-08-14T09:20:46.138540900Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-14 11:20:46.138', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545319
;

-- Value: de.metas.contracts.modular.settings.missingFlatrateTermCondition
-- 2023-08-14T09:20:46.857307Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-14 11:20:46.857', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545319
;

-- Value: de.metas.contracts.modular.settings.missingFlatrateTermCondition
-- 2023-08-14T09:21:07.138182800Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='No interim flatrate conditions exist for the modular contract settings: {0}.', Updated=TO_TIMESTAMP('2023-08-14 11:21:07.138', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545319
;

-- Value: de.metas.contracts.modular.interim.noModularContractsFound
-- 2023-08-14T14:19:52.224049Z
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545320, 0, TO_TIMESTAMP('2023-08-14 16:19:52.031', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'Es wurde keine modularer Vertrage mit dem gegebenen Zeitraum gefunden.', 'E', TO_TIMESTAMP('2023-08-14 16:19:52.031', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts.modular.interim.noModularContractsFound')
;

-- 2023-08-14T14:19:52.230948400Z
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
  AND t.AD_Message_ID = 545320
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interim.noModularContractsFound
-- 2023-08-14T14:20:10.235486100Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-14 16:20:10.235', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545320
;

-- Value: de.metas.contracts.modular.interim.noModularContractsFound
-- 2023-08-14T14:20:11.091950Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-14 16:20:11.091', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545320
;

-- Value: de.metas.contracts.modular.interim.noModularContractsFound
-- 2023-08-14T14:20:20.975297300Z
UPDATE AD_Message_Trl
SET IsTranslated='Y', MsgText='No modular contract found with given timeperiod.', Updated=TO_TIMESTAMP('2023-08-14 16:20:20.975', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545320
;

