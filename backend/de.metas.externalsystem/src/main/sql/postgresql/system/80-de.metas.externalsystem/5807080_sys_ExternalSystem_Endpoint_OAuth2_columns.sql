-- OAuth2 config fields for ExternalSystem_Endpoint.
-- Adds: AD_Ref_List OAuth2 (544260), columns OAuthTokenUrl / OAuthScope,
--       AD_Element + AD_Element_Trl for each, AD_Field + AD_UI_Element on the endpoint tab.
-- Also keeps: IsFileUpload column/field/element (unchanged from cookie template).
-- Also fixes: SftpFilenamePattern element help text (was empty).
-- Extends: display logic on ClientId, ClientSecret, LoginUsername, Password to include OAuth2.
--
-- IDs allocated from idserver.metas.de on 2026-06-10:
--   AD_Ref_List  544260  (OAuth2 value on reference 542017)
--   AD_Element   584970  (OAuthTokenUrl)
--   AD_Element   584971  (OAuthScope)
--   AD_Element   584969  (IsFileUpload)
--   AD_Column    592797  (ExternalSystem_Endpoint.OAuthTokenUrl)
--   AD_Column    592798  (ExternalSystem_Endpoint.OAuthScope)
--   AD_Column    592796  (ExternalSystem_Endpoint.IsFileUpload)
--   AD_Field     780751  (OAuthTokenUrl field on tab 548506)
--   AD_Field     780752  (OAuthScope field on tab 548506)
--   AD_Field     780750  (IsFileUpload field on tab 548506)
--   AD_UI_Element 652046 (OAuthTokenUrl in HTTP group 554995)
--   AD_UI_Element 652047 (OAuthScope in HTTP group 554995)
--   AD_UI_Element 652045 (IsFileUpload in flags group 553740)

-- ============================================================
-- 1. AD_Ref_List: add OAuth2 to AuthType reference (542017)
--    OAuth2 is a proper noun; same name in all languages.
-- ============================================================
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Reference_ID, AD_Ref_List_ID, Value, Name,
    EntityType, ValueName)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    542017, 544260 /*From ID Server*/, 'OAuth2', 'OAuth2',
    'de.metas.externalsystem', 'OAuth2')
ON CONFLICT (AD_Ref_List_ID) DO NOTHING;

-- Skeleton _Trl rows for all active system languages
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544260
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- English translation (OAuth2 is the same in all languages)
UPDATE AD_Ref_List_Trl
SET Name='OAuth2', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544260 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544260 AND AD_Language IN ('de_DE', 'de_CH');

-- ============================================================
-- 2. Fix SftpFilenamePattern element help text (AD_Element 584677)
-- ============================================================
UPDATE AD_Element
SET Description = 'Muster für ausgehende Dateinamen; {...}-Platzhalter werden beim Senden ersetzt.',
    Help        = 'Platzhalter in geschweiften Klammern werden beim Senden der Datei ersetzt: {documentno} (Belegnummer des exportierten Datensatzes), {table} (dessen Tabellenname, z.B. M_InOut), {recordid} (dessen Datenbankschlüssel), {timestamp} (Sendezeitpunkt, Format yyyyMMdd_HHmmss). Unbekannte Platzhalter bleiben unverändert. Beispiel: DESADV_{documentno}_{timestamp}.json',
    Updated     = TO_TIMESTAMP('2026-06-10 08:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584677;

-- German trl rows (de_DE, de_CH) - mirror the base German text
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
    Updated     = TO_TIMESTAMP('2026-06-10 08:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584677 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, 'en_US');

-- ============================================================
-- 3. AD_Element: OAuthTokenUrl (584970)
-- ============================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, ColumnName, Name, PrintName, Description, Help,
    EntityType)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584970 /*From ID Server*/, 'OAuthTokenUrl', 'OAuth2 Token-URL', 'OAuth2 Token-URL',
    'OAuth2 Token-Endpoint-URL, an die der Password-Grant-Request gesendet wird.',
    'Wird verwendet, wenn Authentifizierungstyp = OAuth2. Beispiel: https://dw.example.com/docuware/platform/oauth/token',
    'de.metas.externalsystem')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Skeleton _Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584970
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- German translations (de_DE, de_CH)
UPDATE AD_Element_Trl
SET Name='OAuth2 Token-URL', PrintName='OAuth2 Token-URL',
    Description='OAuth2 Token-Endpoint-URL, an die der Password-Grant-Request gesendet wird.',
    Help='Wird verwendet, wenn Authentifizierungstyp = OAuth2. Beispiel: https://dw.example.com/docuware/platform/oauth/token',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584970 AND AD_Language IN ('de_DE', 'de_CH');

-- English translation
UPDATE AD_Element_Trl
SET Name='OAuth2 Token URL', PrintName='OAuth2 Token URL',
    Description='OAuth2 token endpoint URL the password-grant request is POSTed to.',
    Help='Used when Auth Type = OAuth2. Example: https://dw.example.com/docuware/platform/oauth/token',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:01:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584970 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584970, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584970, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584970, 'en_US');

-- ============================================================
-- 4. AD_Element: OAuthScope (584971)
-- ============================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, ColumnName, Name, PrintName, Description, Help,
    EntityType)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584971 /*From ID Server*/, 'OAuthScope', 'OAuth2 Scope', 'OAuth2 Scope',
    'Optionaler OAuth2 Scope, der mit dem Token-Request gesendet wird (z.B. docuware.platform).',
    'Wenn leer, wird kein Scope-Parameter gesendet. Beispiel: docuware.platform',
    'de.metas.externalsystem')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Skeleton _Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584971
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- German translations (de_DE, de_CH)
UPDATE AD_Element_Trl
SET Name='OAuth2 Scope', PrintName='OAuth2 Scope',
    Description='Optionaler OAuth2 Scope, der mit dem Token-Request gesendet wird (z.B. docuware.platform).',
    Help='Wenn leer, wird kein Scope-Parameter gesendet. Beispiel: docuware.platform',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:02:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584971 AND AD_Language IN ('de_DE', 'de_CH');

-- English translation
UPDATE AD_Element_Trl
SET Name='OAuth2 Scope', PrintName='OAuth2 Scope',
    Description='Optional OAuth2 scope sent with the token request (e.g. docuware.platform).',
    Help='If empty, no scope parameter is sent. Example: docuware.platform',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:02:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584971 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584971, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584971, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584971, 'en_US');

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

-- German translations (de_DE, de_CH)
UPDATE AD_Element_Trl
SET Name='Datei-Upload', PrintName='Datei-Upload',
    Description='Anfrage-Body als multipart/form-data senden (Dokument + Datei).',
    Help='Wenn gesetzt, wird die Nutzlast als multipart/form-data hochgeladen, mit den JSON-Metadaten und der Binärdatei als separate Teile, anstatt als einfachen Body. Wird für DMS-Upload-Ziele wie DocuWare verwendet.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:03:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584969 AND AD_Language IN ('de_DE', 'de_CH');

-- English translation
UPDATE AD_Element_Trl
SET Name='File Upload', PrintName='File Upload',
    Description='Send the request body as multipart/form-data (document + file).',
    Help='If set, the payload is uploaded as multipart/form-data with the metadata JSON and the binary file as separate parts, instead of a plain body. Used for DMS upload targets such as DocuWare.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 08:03:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584969 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'en_US');

-- ============================================================
-- 6. Physical columns on ExternalSystem_Endpoint
-- ============================================================
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS OAuthTokenUrl VARCHAR(255);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS OAuthScope VARCHAR(255);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS IsFileUpload CHAR(1) DEFAULT 'N';
UPDATE ExternalSystem_Endpoint SET IsFileUpload = 'N' WHERE IsFileUpload IS NULL;
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN IsFileUpload SET NOT NULL;
ALTER TABLE ExternalSystem_Endpoint DROP CONSTRAINT IF EXISTS ck_endpoint_isfileupload;
ALTER TABLE ExternalSystem_Endpoint ADD CONSTRAINT ck_endpoint_isfileupload CHECK (IsFileUpload IN ('Y','N'));

-- ============================================================
-- 7. AD_Column: OAuthTokenUrl (592797)
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
VALUES (592797 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584970, 542551, 'OAuthTokenUrl', 'OAuth2 Token-URL',
    'OAuth2 Token-Endpoint-URL, an die der Password-Grant-Request gesendet wird.',
    'Wird verwendet, wenn Authentifizierungstyp = OAuth2. Beispiel: https://dw.example.com/docuware/platform/oauth/token',
    0, 'de.metas.externalsystem', 10,
    'N', 'Y', 'N', 'N', 'N', 'N',
    255, 'N', 'N',
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
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592797
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584970, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584970, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584970, 'en_US');

-- ============================================================
-- 8. AD_Column: OAuthScope (592798)
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
VALUES (592798 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 08:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 08:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    584971, 542551, 'OAuthScope', 'OAuth2 Scope',
    'Optionaler OAuth2 Scope, der mit dem Token-Request gesendet wird (z.B. docuware.platform).',
    'Wenn leer, wird kein Scope-Parameter gesendet. Beispiel: docuware.platform',
    0, 'de.metas.externalsystem', 10,
    'N', 'Y', 'N', 'N', 'N', 'N',
    255, 'N', 'N',
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
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592798
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584971, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584971, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584971, 'en_US');

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

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584969, 'en_US');

-- ============================================================
-- 10. AD_Field: OAuthTokenUrl (780751) on tab 548506
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
    780751 /*From ID Server*/, 548506, 592797,
    'OAuth2 Token-URL',
    'OAuth2 Token-Endpoint-URL, an die der Password-Grant-Request gesendet wird.',
    'Wird verwendet, wenn Authentifizierungstyp = OAuth2. Beispiel: https://dw.example.com/docuware/platform/oauth/token',
    'Y', 'N', 'N', 'N',
    0, 0, 'de.metas.externalsystem', 'N')
ON CONFLICT (AD_Field_ID) DO NOTHING;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780751
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(584970);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780751;
SELECT AD_Element_Link_Create_Missing_Field(780751);

-- ============================================================
-- 11. AD_Field: OAuthScope (780752) on tab 548506
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
    780752 /*From ID Server*/, 548506, 592798,
    'OAuth2 Scope',
    'Optionaler OAuth2 Scope, der mit dem Token-Request gesendet wird (z.B. docuware.platform).',
    'Wenn leer, wird kein Scope-Parameter gesendet. Beispiel: docuware.platform',
    'Y', 'N', 'N', 'N',
    0, 0, 'de.metas.externalsystem', 'N')
ON CONFLICT (AD_Field_ID) DO NOTHING;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780752
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(584971);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780752;
SELECT AD_Element_Link_Create_Missing_Field(780752);

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
-- 13. AD_UI_Element: OAuthTokenUrl (652046) in HTTP group 554995
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780751, 0, 548506, 554995,
    652046 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-06-10 08:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'N', 0,
    'OAuth2 Token-URL', 110, 0, 0,
    TO_TIMESTAMP('2026-06-10 08:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
ON CONFLICT (AD_UI_Element_ID) DO NOTHING;

-- ============================================================
-- 14. AD_UI_Element: OAuthScope (652047) in HTTP group 554995
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780752, 0, 548506, 554995,
    652047 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-06-10 08:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'N', 0,
    'OAuth2 Scope', 120, 0, 0,
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
-- 16. Display logic on new OAuth2 fields
--     OAuthTokenUrl: show + mandatory only for HTTP + OAuth2.
--     OAuthScope: show only for HTTP + OAuth2 (no mandatory - scope is optional per spec).
--     IsFileUpload: show for HTTP transport (unchanged from cookie template).
-- ============================================================
UPDATE AD_Field
SET DisplayLogic='@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth2''',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID IN (780751 /*OAuthTokenUrl*/, 780752 /*OAuthScope*/);

UPDATE AD_Column
SET MandatoryLogic='@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth2''',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592797 /*OAuthTokenUrl*/;

UPDATE AD_Field
SET DisplayLogic='@TransportType/X@=''HTTP''',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=780750 /*IsFileUpload*/;

-- ============================================================
-- 17. Extend display logic on credential fields to include OAuth2
--
-- Current values (read from DB before this migration):
--   ClientId      (755948): @TransportType/X@='HTTP' & @AuthType/X@='OAuth'
--   ClientSecret  (755947): @TransportType/X@='HTTP' & @AuthType/X@='OAuth'
--   LoginUsername (755949): @TransportType/X@='HTTP' & (@AuthType/X@='OAuth' | @AuthType/X@='Basic')
--   Password      (755950): (@TransportType/X@='HTTP' & @AuthType/X@='Basic') | (@TransportType/X@='SFTP' & @SftpAuthType/X@='PASSWORD')
-- ============================================================

-- ClientId: add OAuth2 alongside existing OAuth
UPDATE AD_Field
SET DisplayLogic='@TransportType/X@=''HTTP'' & (@AuthType/X@=''OAuth'' | @AuthType/X@=''OAuth2'')',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=755948 /*ClientId*/;

-- ClientSecret: add OAuth2 alongside existing OAuth
UPDATE AD_Field
SET DisplayLogic='@TransportType/X@=''HTTP'' & (@AuthType/X@=''OAuth'' | @AuthType/X@=''OAuth2'')',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=755947 /*ClientSecret*/;

-- LoginUsername: add OAuth2 alongside existing OAuth + Basic
UPDATE AD_Field
SET DisplayLogic='@TransportType/X@=''HTTP'' & (@AuthType/X@=''OAuth'' | @AuthType/X@=''Basic'' | @AuthType/X@=''OAuth2'')',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=755949 /*LoginUsername*/;

-- Password: add OAuth2 HTTP branch alongside existing Basic (HTTP) + PASSWORD (SFTP)
UPDATE AD_Field
SET DisplayLogic='(@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'') | (@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD'') | (@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth2'')',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=755950 /*Password*/;

-- Password is REQUIRED for the OAuth2 password grant (grant_type=password always sends the resource-owner password):
-- extend the column mandatory logic by the same OAuth2 HTTP branch the display logic gained above.
UPDATE AD_Column
SET MandatoryLogic='(@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'') | (@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD'') | (@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth2'')',
    Updated=TO_TIMESTAMP('2026-06-10 08:13:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=591488 /*Password*/;
