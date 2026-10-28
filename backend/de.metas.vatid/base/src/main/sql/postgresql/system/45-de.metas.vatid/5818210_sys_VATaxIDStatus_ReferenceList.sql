-- VAT-ID online check: reference list for the VAT-ID VIES verification status.
-- Enum contract (values, in this order): NotChecked, RequestSent, Valid, Invalid, NotSupported, ServiceUnavailable.
-- No AD_Column/table is touched here -- wiring this list to a column is a follow-up migration.

-- IDs allocated from idserver.metas.de:
--   AD_Reference 542125
--   AD_Ref_List  544329..544334 (one per enum value, in the order above)

-- 1. AD_Reference (List type) for VATaxIDStatus
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Reference_ID, ValidationType, Name, Description, EntityType, IsOrderByValue)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-08-11 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        542125 /*From ID Server*/, 'L', 'USt-IdNr.-Prüfstatus',
        'Status der Online-Prüfung einer USt-IdNr. über den VIES-Dienst.', 'D', 'N');

-- 2. AD_Reference_Trl: skeleton rows for every active system language (copies the German base text)
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description, IsTranslated,
                               AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542125
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID);

-- 3. English override for AD_Reference
UPDATE AD_Reference_Trl
SET IsTranslated='Y', Name='VAT-ID Check Status',
    Description='Status of a VAT-ID''s online check via the VIES service.',
    Updated=TO_TIMESTAMP('2026-08-11 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Reference_ID=542125;

-- 4. Mark de_DE/de_CH as actively translated (text already matches the German base)
UPDATE AD_Reference_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-11 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Reference_ID=542125;

-- 5. AD_Ref_List entries -- one per enum value, German Name/Description in the base column
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Ref_List_ID, ValueName, Value, Name, Description, EntityType)
VALUES
  (542125, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   544329 /*From ID Server*/, 'NotChecked', 'NotChecked', 'Nicht geprüft',
   'Die USt-IdNr. wurde noch nie online geprüft.', 'D'),
  (542125, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   544330 /*From ID Server*/, 'RequestSent', 'RequestSent', 'Anfrage gesendet',
   'Eine Prüfung wurde angefragt; das Ergebnis liegt noch nicht vor oder wurde nie ermittelt.', 'D'),
  (542125, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   544331 /*From ID Server*/, 'Valid', 'Valid', 'Gültig',
   'VIES hat die USt-IdNr. bestätigt.', 'D'),
  (542125, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   544332 /*From ID Server*/, 'Invalid', 'Invalid', 'Ungültig',
   'VIES hat die USt-IdNr. als ungültig gemeldet.', 'D'),
  (542125, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   544333 /*From ID Server*/, 'NotSupported', 'NotSupported', 'Nicht unterstützt',
   'Das Länderpräfix wird von VIES nicht unterstützt; es gilt die offline durchgeführte Formatprüfung.', 'D'),
  (542125, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 09:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
   544334 /*From ID Server*/, 'ServiceUnavailable', 'ServiceUnavailable', 'Dienst nicht erreichbar',
   'VIES oder der zuständige Mitgliedstaat war nicht erreichbar.', 'D');

-- 6. AD_Ref_List_Trl: skeleton rows for every active system language (copies the German base text)
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated,
                              AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542125
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- 7. English overrides, one UPDATE per value
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Not checked', Description='The VAT-ID was never checked online.',
    Updated=TO_TIMESTAMP('2026-08-11 09:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544329;

UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Request sent',
    Description='A check was requested; the outcome is not yet known or was never learned.',
    Updated=TO_TIMESTAMP('2026-08-11 09:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544330;

UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Valid', Description='VIES confirmed the VAT-ID.',
    Updated=TO_TIMESTAMP('2026-08-11 09:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544331;

UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Invalid', Description='VIES reported the VAT-ID as invalid.',
    Updated=TO_TIMESTAMP('2026-08-11 09:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544332;

UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Not supported',
    Description='The country prefix is not covered by VIES; the offline format check applies.',
    Updated=TO_TIMESTAMP('2026-08-11 09:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544333;

UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Service unavailable',
    Description='VIES or the responsible member state could not be reached.',
    Updated=TO_TIMESTAMP('2026-08-11 09:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544334;

-- 8. Mark de_DE/de_CH as actively translated (text already matches the German base)
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-11 09:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Ref_List_ID IN (544329,544330,544331,544332,544333,544334);
