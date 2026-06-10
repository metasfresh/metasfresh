-- Generic cookie-logon + file-upload config for ExternalSystem_Endpoint.
-- Adds: AD_Ref_List CookieLogon (542017), columns LogonUrl / LogonFormParams / IsFileUpload,
--       AD_Element + AD_Element_Trl for each, AD_Field + AD_UI_Element on the endpoint tab.
-- Also fixes the SftpFilenamePattern element help text (was empty).
--
-- IDs allocated from idserver.metas.de on 2026-06-10:
--   AD_Ref_List  544259  (CookieLogon value on reference 542017)
--   AD_Element   584967  (LogonUrl)
--   AD_Element   584968  (LogonFormParams)
--   AD_Element   584969  (IsFileUpload)
--   AD_Column    592794  (ExternalSystem_Endpoint.LogonUrl)
--   AD_Column    592795  (ExternalSystem_Endpoint.LogonFormParams)
--   AD_Column    592796  (ExternalSystem_Endpoint.IsFileUpload)
--   AD_Field     780748  (LogonUrl field on tab 548506)
--   AD_Field     780749  (LogonFormParams field on tab 548506)
--   AD_Field     780750  (IsFileUpload field on tab 548506)
--   AD_UI_Element 652043 (LogonUrl in HTTP group 554995)
--   AD_UI_Element 652044 (LogonFormParams in HTTP group 554995)
--   AD_UI_Element 652045 (IsFileUpload in flags group 553740)

-- ============================================================
-- 1. AD_Ref_List: add CookieLogon to AuthType reference (542017)
--    German name in base column, English override via _Trl
-- ============================================================
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Reference_ID, AD_Ref_List_ID, Value, Name,
    EntityType, ValueName)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    542017, 544259 /*From ID Server*/, 'CookieLogon', 'Cookie-Anmeldung',
    'de.metas.externalsystem', 'CookieLogon')
ON CONFLICT (AD_Ref_List_ID) DO NOTHING;

-- Skeleton _Trl rows for all active system languages
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544259
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- English translation
UPDATE AD_Ref_List_Trl
SET Name='Cookie Logon', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544259 AND AD_Language='en_US';

-- ============================================================
-- 2. Fix SftpFilenamePattern element help text (AD_Element 584677)
-- ============================================================
UPDATE AD_Element
SET Description = 'Muster für ausgehende Dateinamen; {...}-Platzhalter werden beim Senden ersetzt.',
    Help        = 'Platzhalter in geschweiften Klammern werden beim Senden der Datei ersetzt: {documentno} (Belegnummer des exportierten Datensatzes), {table} (dessen Tabellenname, z.B. M_InOut), {recordid} (dessen Datenbankschlüssel), {timestamp} (Sendezeitpunkt, Format yyyyMMdd_HHmmss). Unbekannte Platzhalter bleiben unverändert. Beispiel: DESADV_{documentno}_{timestamp}.json',
    Updated     = TO_TIMESTAMP('2026-06-10 08:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584677;

-- German trl rows (de_DE, de_CH) — mirror the base German text
UPDATE AD_Element_Trl
SET Description = 'Muster für ausgehende Dateinamen; {...}-Platzhalter werden beim Senden ersetzt.',
    Help        = 'Platzhalter in geschweiften Klammern werden beim Senden der Datei ersetzt: {documentno} (Belegnummer des exportierten Datensatzes), {table} (dessen Tabellenname, z.B. M_InOut), {recordid} (dessen Datenbankschlüssel), {timestamp} (Sendezeitpunkt, Format yyyyMMdd_HHmmss). Unbekannte Platzhalter bleiben unverändert. Beispiel: DESADV_{documentno}_{timestamp}.json',
    IsTranslated= 'Y',
    Updated     = TO_TIMESTAMP('2026-06-10 08:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584677 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Description = 'Pattern for outbound file names; {...} placeholders are replaced at send time.',
    Help        = 'Placeholders in curly braces are replaced when the file is sent: {documentno} (document number of the exported record), {table} (its table name, e.g. M_InOut), {recordid} (its database ID), {timestamp} (send time, format yyyyMMdd_HHmmss). Unknown placeholders are left unchanged. Example: DESADV_{documentno}_{timestamp}.json',
    IsTranslated= 'Y',
    Updated     = TO_TIMESTAMP('2026-06-10 08:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584677 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'en_US');

-- ============================================================
-- 3. AD_Element: LogonUrl (584967)
-- ============================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, ColumnName, Name, PrintName, Description, Help,
    EntityType)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584967 /*From ID Server*/, 'LogonUrl', 'Anmelde-URL', 'Anmelde-URL',
    'URL, an die das Anmeldeformular per POST gesendet wird, um das Sitzungs-Cookie zu erhalten.',
    'Wird verwendet, wenn Authentifizierungstyp = Cookie-Anmeldung. Der Endpunkt sendet die Anmeldeformularparameter per POST an diese URL; die zurückgegebenen Cookies werden bei der eigentlichen Anfrage mitgesendet. Beispiel: https://dw.example.com/docuware/platform/Account/Logon',
    'de.metas.externalsystem')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Skeleton _Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584967
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- English translation
UPDATE AD_Element_Trl
SET Name='Logon URL', PrintName='Logon URL',
    Description='URL the logon form is POSTed to, to obtain the session cookie.',
    Help='Used when Auth Type = Cookie Logon. The endpoint POSTs the Logon Form Parameters here; the returned cookie(s) are sent on the actual request. Example: https://dw.example.com/docuware/platform/Account/Logon',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:01:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584967 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584967, 'en_US');

-- ============================================================
-- 4. AD_Element: LogonFormParams (584968)
-- ============================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, ColumnName, Name, PrintName, Description, Help,
    EntityType)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584968 /*From ID Server*/, 'LogonFormParams', 'Anmeldeformular-Parameter', 'Anmeldeformular-Parameter',
    'JSON-Formularfelder, die für die Cookie-Authentifizierung an die Anmelde-URL gesendet werden.',
    'Ein JSON-Objekt mit den Feldern, die an die Anmelde-URL gesendet werden. Die Platzhalter {user} und {password} werden durch Benutzernamen und (entschlüsseltes) Kennwort dieses Endpunkts ersetzt; alle anderen Werte werden unverändert gesendet. Beispiel: {"UserName":"{user}","Password":"{password}","Organization":"PEERS","HostID":"metasfresh","LicenseType":"PlatformService"}',
    'de.metas.externalsystem')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Skeleton _Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584968
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- English translation
UPDATE AD_Element_Trl
SET Name='Logon Form Params', PrintName='Logon Form Params',
    Description='JSON form fields POSTed to the Logon URL for cookie authentication.',
    Help='A JSON object of the fields sent to the Logon URL. The placeholders {user} and {password} are replaced with this endpoint''s Login Username and (decrypted) Password; every other value is sent literally. Example: {"UserName":"{user}","Password":"{password}","Organization":"PEERS","HostID":"metasfresh","LicenseType":"PlatformService"}',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:02:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584968 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584968, 'en_US');

-- ============================================================
-- 5. AD_Element: IsFileUpload (584969)
-- ============================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, ColumnName, Name, PrintName, Description, Help,
    EntityType)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584969 /*From ID Server*/, 'IsFileUpload', 'Datei-Upload', 'Datei-Upload',
    'Anfrage-Body als multipart/form-data senden (Dokument + Datei).',
    'Wenn gesetzt, wird die Nutzlast als multipart/form-data hochgeladen, mit den JSON-Metadaten und der Binärdatei als separate Teile, anstatt als einfachen Body. Wird für DMS-Upload-Ziele wie DocuWare verwendet.',
    'de.metas.externalsystem')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Skeleton _Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584969
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- English translation
UPDATE AD_Element_Trl
SET Name='File Upload', PrintName='File Upload',
    Description='Send the request body as multipart/form-data (document + file).',
    Help='If set, the payload is uploaded as multipart/form-data with the metadata JSON and the binary file as separate parts, instead of a plain body. Used for DMS upload targets such as DocuWare.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:03:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584969 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'en_US');

-- ============================================================
-- 6. Physical columns on ExternalSystem_Endpoint
-- ============================================================
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS LogonUrl VARCHAR(120);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS LogonFormParams TEXT;
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS IsFileUpload CHAR(1) DEFAULT 'N';
UPDATE ExternalSystem_Endpoint SET IsFileUpload = 'N' WHERE IsFileUpload IS NULL;
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN IsFileUpload SET NOT NULL;

-- ============================================================
-- 7. AD_Column: LogonUrl (592794)
-- ============================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, ColumnName, Name, Description, Help,
    Version, EntityType, AD_Reference_ID,
    IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, IsKey, IsParent,
    FieldLength, IsTranslated, IsSelectionColumn,
    PersonalDataCategory,
    CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
    IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
    IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
    IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
    IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
    IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
    SelectionColumnSeqNo, SeqNo)
VALUES (592794 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584967, 542551, 'LogonUrl', 'Anmelde-URL',
    'URL, an die das Anmeldeformular per POST gesendet wird, um das Sitzungs-Cookie zu erhalten.',
    'Wird verwendet, wenn Authentifizierungstyp = Cookie-Anmeldung. Der Endpunkt sendet die Anmeldeformularparameter per POST an diese URL; die zurückgegebenen Cookies werden bei der eigentlichen Anfrage mitgesendet. Beispiel: https://dw.example.com/docuware/platform/Account/Logon',
    0, 'de.metas.externalsystem', 10,
    'N', 'Y', 'N', 'N', 'N', 'N',
    120, 'N', 'N',
    'NP',
    'DC', 0, 'N', 'Y',
    'N', 'N', 'N', 'N',
    'N', 'N', 'Y', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N', 'N', 0,
    0, 0)
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592794
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584967, 'en_US');

-- ============================================================
-- 8. AD_Column: LogonFormParams (592795)
-- ============================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, ColumnName, Name, Description, Help,
    Version, EntityType, AD_Reference_ID,
    IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, IsKey, IsParent,
    FieldLength, IsTranslated, IsSelectionColumn,
    PersonalDataCategory,
    CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
    IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
    IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
    IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
    IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
    IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
    SelectionColumnSeqNo, SeqNo)
VALUES (592795 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584968, 542551, 'LogonFormParams', 'Anmeldeformular-Parameter',
    'JSON-Formularfelder, die für die Cookie-Authentifizierung an die Anmelde-URL gesendet werden.',
    'Ein JSON-Objekt mit den Feldern, die an die Anmelde-URL gesendet werden. Die Platzhalter {user} und {password} werden durch Benutzernamen und (entschlüsseltes) Kennwort dieses Endpunkts ersetzt; alle anderen Werte werden unverändert gesendet. Beispiel: {"UserName":"{user}","Password":"{password}","Organization":"PEERS","HostID":"metasfresh","LicenseType":"PlatformService"}',
    0, 'de.metas.externalsystem', 14,
    'N', 'Y', 'N', 'N', 'N', 'N',
    2000, 'N', 'N',
    'NP',
    'DC', 0, 'N', 'Y',
    'N', 'N', 'N', 'N',
    'N', 'N', 'Y', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N', 'N', 0,
    0, 0)
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592795
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584968, 'en_US');

-- ============================================================
-- 9. AD_Column: IsFileUpload (592796)
-- ============================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, ColumnName, Name, Description, Help,
    Version, EntityType, AD_Reference_ID,
    IsMandatory, DefaultValue, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, IsKey, IsParent,
    FieldLength, IsTranslated, IsSelectionColumn,
    PersonalDataCategory,
    CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
    IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
    IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
    IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
    IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
    IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
    SelectionColumnSeqNo, SeqNo)
VALUES (592796 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584969, 542551, 'IsFileUpload', 'Datei-Upload',
    'Anfrage-Body als multipart/form-data senden (Dokument + Datei).',
    'Wenn gesetzt, wird die Nutzlast als multipart/form-data hochgeladen, mit den JSON-Metadaten und der Binärdatei als separate Teile, anstatt als einfachen Body. Wird für DMS-Upload-Ziele wie DocuWare verwendet.',
    0, 'de.metas.externalsystem', 20,
    'Y', 'N', 'Y', 'N', 'N', 'N', 'N',
    1, 'N', 'N',
    'NP',
    'DC', 0, 'N', 'Y',
    'N', 'N', 'N', 'N',
    'N', 'N', 'Y', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N', 'N', 0,
    0, 0)
ON CONFLICT (AD_Column_ID) DO NOTHING;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592796
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'en_US');

-- ============================================================
-- 10. AD_Field: LogonUrl (780748) on tab 548506
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_Column_ID,
    Name, Description, Help,
    IsDisplayed, IsReadOnly, IsMandatory, IsEncrypted,
    SeqNo, SeqNoGrid, EntityType, IsDisplayedGrid)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:07:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:07:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    780748 /*From ID Server*/, 548506, 592794,
    'Anmelde-URL',
    'URL, an die das Anmeldeformular per POST gesendet wird, um das Sitzungs-Cookie zu erhalten.',
    'Wird verwendet, wenn Authentifizierungstyp = Cookie-Anmeldung. Der Endpunkt sendet die Anmeldeformularparameter per POST an diese URL; die zurückgegebenen Cookies werden bei der eigentlichen Anfrage mitgesendet. Beispiel: https://dw.example.com/docuware/platform/Account/Logon',
    'Y', 'N', 'N', 'N',
    0, 0, 'de.metas.externalsystem', 'N')
ON CONFLICT (AD_Field_ID) DO NOTHING;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780748
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(584967);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780748;
SELECT AD_Element_Link_Create_Missing_Field(780748);

-- ============================================================
-- 11. AD_Field: LogonFormParams (780749) on tab 548506
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_Column_ID,
    Name, Description, Help,
    IsDisplayed, IsReadOnly, IsMandatory, IsEncrypted,
    SeqNo, SeqNoGrid, EntityType, IsDisplayedGrid)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:08:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    780749 /*From ID Server*/, 548506, 592795,
    'Anmeldeformular-Parameter',
    'JSON-Formularfelder, die für die Cookie-Authentifizierung an die Anmelde-URL gesendet werden.',
    'Ein JSON-Objekt mit den Feldern, die an die Anmelde-URL gesendet werden. Die Platzhalter {user} und {password} werden durch Benutzernamen und (entschlüsseltes) Kennwort dieses Endpunkts ersetzt; alle anderen Werte werden unverändert gesendet. Beispiel: {"UserName":"{user}","Password":"{password}","Organization":"PEERS","HostID":"metasfresh","LicenseType":"PlatformService"}',
    'Y', 'N', 'N', 'N',
    0, 0, 'de.metas.externalsystem', 'N')
ON CONFLICT (AD_Field_ID) DO NOTHING;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780749
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(584968);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780749;
SELECT AD_Element_Link_Create_Missing_Field(780749);

-- ============================================================
-- 12. AD_Field: IsFileUpload (780750) on tab 548506
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_Column_ID,
    Name, Description, Help,
    IsDisplayed, IsReadOnly, IsMandatory, IsEncrypted,
    SeqNo, SeqNoGrid, EntityType, IsDisplayedGrid)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:09:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:09:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    780750 /*From ID Server*/, 548506, 592796,
    'Datei-Upload',
    'Anfrage-Body als multipart/form-data senden (Dokument + Datei).',
    'Wenn gesetzt, wird die Nutzlast als multipart/form-data hochgeladen, mit den JSON-Metadaten und der Binärdatei als separate Teile, anstatt als einfachen Body. Wird für DMS-Upload-Ziele wie DocuWare verwendet.',
    'Y', 'N', 'N', 'N',
    0, 0, 'de.metas.externalsystem', 'N')
ON CONFLICT (AD_Field_ID) DO NOTHING;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780750
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(584969);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780750;
SELECT AD_Element_Link_Create_Missing_Field(780750);

-- ============================================================
-- 13. AD_UI_Element: LogonUrl (652043) in HTTP group 554995
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780748, 0, 548506, 554995,
    652043 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-06-10 08:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'N', 0,
    'Anmelde-URL', 110, 0, 0,
    TO_TIMESTAMP('2026-06-10 08:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
ON CONFLICT (AD_UI_Element_ID) DO NOTHING;

-- ============================================================
-- 14. AD_UI_Element: LogonFormParams (652044) in HTTP group 554995
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780749, 0, 548506, 554995,
    652044 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-06-10 08:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'Y', 5,
    'Anmeldeformular-Parameter', 120, 0, 0,
    TO_TIMESTAMP('2026-06-10 08:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
ON CONFLICT (AD_UI_Element_ID) DO NOTHING;

-- ============================================================
-- 15. AD_UI_Element: IsFileUpload (652045) in flags group 553740
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780750, 0, 548506, 553740,
    652045 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-06-10 08:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'N', 0,
    'Datei-Upload', 30, 0, 0,
    TO_TIMESTAMP('2026-06-10 08:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
ON CONFLICT (AD_UI_Element_ID) DO NOTHING;

-- ============================================================
-- 16. Display logic (AD_Field) + Mandatory logic (AD_Column)
--     Mirrors the existing endpoint pattern (@TransportType/X@ & @AuthType/X@).
--     Cookie-logon fields show + are mandatory only for HTTP + AuthType=CookieLogon.
--     File-upload flag shows for HTTP transport (generic; boolean NOT NULL → no mandatory logic).
-- ============================================================
UPDATE AD_Field SET DisplayLogic='@TransportType/X@=''HTTP'' & @AuthType/X@=''CookieLogon''',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID IN (780748 /*LogonUrl*/, 780749 /*LogonFormParams*/);

UPDATE AD_Field SET DisplayLogic='@TransportType/X@=''HTTP''',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780750 /*IsFileUpload*/;

UPDATE AD_Column SET MandatoryLogic='@TransportType/X@=''HTTP'' & @AuthType/X@=''CookieLogon''',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID IN (592794 /*LogonUrl*/, 592795 /*LogonFormParams*/);
