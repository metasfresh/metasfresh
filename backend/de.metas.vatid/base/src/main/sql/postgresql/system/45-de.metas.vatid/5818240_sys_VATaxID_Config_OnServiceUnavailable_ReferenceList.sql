-- VAT-ID online check: dedicated reference list for VATaxID_Config.OnServiceUnavailable.
-- Review finding on 5818220_sys_VATaxID_Config_Table.sql: that script pointed
-- OnServiceUnavailable at AD_Reference 542125 (the full VATaxIDStatus check-result list), which
-- admits transitional/positive values (e.g. RequestSent, Valid, NotChecked) that are meaningless
-- as a static configuration fallback for "VIES stayed unreachable past the recheck interval".
-- Per REQUIREMENTS.md, the only two outcomes that make sense there are: keep the VAT-ID's tax
-- certificate as if the service were still merely unavailable (fail-open, the shipped default),
-- or withdraw it as if VIES had answered "invalid" (fail-closed). 5818220 is already applied to
-- live databases, so it is corrected here with a new migration rather than edited in place.
--
-- IDs allocated from idserver.metas.de:
--   AD_Reference 542126
--   AD_Ref_List  544335..544336

-- 1. AD_Reference (List type)
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Reference_ID, ValidationType, Name, Description, EntityType, IsOrderByValue)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-08-11 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542126 /*From ID Server*/, 'L', 'USt-IdNr.: Verhalten bei Dienstausfall',
        'Status, der einer USt-IdNr. zugewiesen wird, wenn der VIES-Dienst über die konfigurierte Frist hinaus nicht erreichbar war.', 'D', 'N');

-- 2. AD_Reference_Trl: skeleton rows for every active system language, then the English override
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description, IsTranslated,
                               AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Reference_ID = 542126
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID);

UPDATE AD_Reference_Trl
SET IsTranslated = 'Y', Name = 'VAT-ID: On Service Unavailable',
    Description = 'Status assigned to a VAT-ID when the VIES service stayed unreachable past the configured interval.',
    Updated = TO_TIMESTAMP('2026-08-11 15:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Reference_ID = 542126;

UPDATE AD_Reference_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-11 15:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Reference_ID = 542126;

-- 3. AD_Ref_List entries -- exactly the two outcomes that are meaningful for this fallback
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                          AD_Ref_List_ID, ValueName, Value, Name, Description, EntityType)
VALUES
  (542126, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 15:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 15:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
   544335 /*From ID Server*/, 'ServiceUnavailable', 'ServiceUnavailable', 'Dienst nicht erreichbar',
   'Die USt-IdNr. gilt weiterhin als nicht online prüfbar; der bisherige Steuerzertifikat-Status bleibt unverändert erhalten.', 'D'),
  (542126, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 15:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
   TO_TIMESTAMP('2026-08-11 15:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
   544336 /*From ID Server*/, 'Invalid', 'Invalid', 'Ungültig',
   'Die USt-IdNr. wird ohne erneute Bestätigung durch VIES als ungültig behandelt; der Steuerzertifikat-Status entfällt.', 'D');

-- 4. AD_Ref_List_Trl: skeleton rows for every active system language, then English overrides
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated,
                              AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Reference_ID = 542126
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID);

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y', Name = 'Service unavailable',
    Description = 'The VAT-ID keeps its current tax-certificate treatment; it is still not verifiable online.',
    Updated = TO_TIMESTAMP('2026-08-11 15:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Ref_List_ID = 544335;

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y', Name = 'Invalid',
    Description = 'The VAT-ID is treated as invalid without a fresh VIES confirmation; it no longer holds a tax certificate.',
    Updated = TO_TIMESTAMP('2026-08-11 15:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Ref_List_ID = 544336;

UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-11 15:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Ref_List_ID IN (544335, 544336);
