-- me03 30088: EPCIS Error-Handling & Retry
-- Adds AD_Reference (List) 'ExternalSystem_ExportStatus' that unifies the export-status
-- vocabulary across external-system integrations. Codes and semantics mirror EDI's
-- EDIExportStatus (AD_Reference 540381) 1:1; EN/DE labels mirror EDI's labels so the two
-- vocabularies stay semantically unified.
--
-- Vocabulary (Value -> ValueName -> meaning):
--   P -> Pending        : not yet sent
--   U -> Enqueued       : queued for async sending
--   D -> SendingStarted : transmission running
--   S -> Sent           : sent
--   E -> Error          : transmission error
--   I -> Invalid        : data error (master data not correctly set up)
--   N -> DontSend       : must not be sent
--
-- IDs allocated from idserver.metas.de on 2026-06-08:
--   AD_Reference 542104
--   AD_Ref_List  544252 (P), 544253 (U), 544254 (D), 544255 (S),
--                544256 (E), 544257 (I), 544258 (N)

-- 1) AD_Reference (List container) -------------------------------------------------------
INSERT INTO AD_Reference (AD_Client_ID, IsActive, Created, CreatedBy, IsOrderByValue,
  Updated, UpdatedBy, AD_Reference_ID, ValidationType, Name, AD_Org_ID, EntityType)
VALUES (0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'N',
  TO_TIMESTAMP('2026-06-08 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  542104 /*From ID Server*/, 'L', 'ExternalSystem_ExportStatus', 0, 'de.metas.externalsystem');

-- 2) AD_Reference_Trl (skeleton for every active system non-base language) ----------------
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Reference_ID=542104
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID);

-- 3) AD_Ref_List entries (German in Name; EN via _Trl below) ------------------------------
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542104, 0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
  'Noch nicht gesendet', TO_TIMESTAMP('2026-06-08 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
  544252 /*From ID Server*/, 'Pending', 'P', 0, NULL, 'de.metas.externalsystem');

INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542104, 0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
  'Übertragung läuft', TO_TIMESTAMP('2026-06-08 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
  544253 /*From ID Server*/, 'Enqueued', 'U', 0,
  'Wie "Übertragung läuft", jedoch als asynchron laufend gekennzeichnet', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542104, 0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
  'Übertragung läuft', TO_TIMESTAMP('2026-06-08 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
  544254 /*From ID Server*/, 'SendingStarted', 'D', 0, NULL, 'de.metas.externalsystem');

INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542104, 0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
  'Gesendet', TO_TIMESTAMP('2026-06-08 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
  544255 /*From ID Server*/, 'Sent', 'S', 0, NULL, 'de.metas.externalsystem');

INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542104, 0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
  'Übertragungsfehler', TO_TIMESTAMP('2026-06-08 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
  544256 /*From ID Server*/, 'Error', 'E', 0, NULL, 'de.metas.externalsystem');

INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542104, 0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
  'Daten Fehler', TO_TIMESTAMP('2026-06-08 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
  544257 /*From ID Server*/, 'Invalid', 'I', 0,
  'Einige Stammdaten sind nicht korrekt eingerichtet. Bitte die Fehlermeldung beachten', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542104, 0, 'Y', TO_TIMESTAMP('2026-06-08 10:00:07','YYYY-MM-DD HH24:MI:SS'), 100,
  'Soll nicht gesendet werden', TO_TIMESTAMP('2026-06-08 10:00:07','YYYY-MM-DD HH24:MI:SS'), 100,
  544258 /*From ID Server*/, 'DontSend', 'N', 0, NULL, 'de.metas.externalsystem');

-- 4) AD_Ref_List_Trl skeleton for every active system non-base language --------------------
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Ref_List_ID IN (544252,544253,544254,544255,544256,544257,544258)
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- 5) English (en_US) translations ---------------------------------------------------------
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Pending',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Ref_List_ID=544252;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Enqueued',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Ref_List_ID=544253;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Sending Started',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Ref_List_ID=544254;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Sent',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Ref_List_ID=544255;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Error',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Ref_List_ID=544256;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Invalid', Description='Data problem',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Ref_List_ID=544257;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Don''t Send',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language='en_US' AND AD_Ref_List_ID=544258;

-- 6) Mark de_DE / de_CH as actively translated (text already German in base) ---------------
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-06-08 10:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
  WHERE AD_Language IN ('de_DE','de_CH')
    AND AD_Ref_List_ID IN (544252,544253,544254,544255,544256,544257,544258);
