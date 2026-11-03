-- VAT-ID online check: VATaxID_Config table.
-- One record per organisation, controlling the offline format check and the online
-- VIES check independently, and what an unreachable VIES means once the last
-- successful result is older than RecheckAfterDays. Not a MaxChecksPerRun home --
-- that stays a single AD_Process_Para (follow-up task), so the throttle has one definition.

-- IDs allocated from idserver.metas.de:
--   AD_Table            542638
--   AD_Column           593132..593146 (15, standard + business columns, in column order)
--   AD_Element          585165..585172, minus 585168 (7 new: 1 for the table's own ID column + 6
--                        business columns). RestApiBaseURL reuses the existing shared element 576182
--                        ("REST API URL", ColumnName is globally unique) instead of a new one.

-- 1. Physical table
CREATE TABLE VATaxID_Config
(
    VATaxID_Config_ID         NUMERIC(10)  NOT NULL,
    AD_Client_ID               NUMERIC(10)  NOT NULL,
    AD_Org_ID                  NUMERIC(10)  NOT NULL,
    IsActive                   CHAR(1)      NOT NULL DEFAULT 'Y',
    Created                    TIMESTAMP    NOT NULL,
    CreatedBy                  NUMERIC(10)  NOT NULL,
    Updated                    TIMESTAMP    NOT NULL,
    UpdatedBy                  NUMERIC(10)  NOT NULL,
    IsFormatCheckEnabled        CHAR(1)      NOT NULL DEFAULT 'Y',
    IsVIESCheckEnabled          CHAR(1)      NOT NULL DEFAULT 'N',
    RestApiBaseURL              VARCHAR(400),
    RequesterMemberStateCode    VARCHAR(2),
    RequesterNumber             VARCHAR(20),
    RecheckAfterDays            NUMERIC(10)  NOT NULL DEFAULT 90,
    OnServiceUnavailable        VARCHAR(20)  NOT NULL DEFAULT 'ServiceUnavailable',
    CONSTRAINT VATaxID_Config_key PRIMARY KEY (VATaxID_Config_ID),
    CONSTRAINT VATaxID_Config_IsActive_check CHECK (IsActive IN ('Y', 'N')),
    CONSTRAINT VATaxID_Config_IsFormatCheckEnabled_check CHECK (IsFormatCheckEnabled IN ('Y', 'N')),
    CONSTRAINT VATaxID_Config_IsVIESCheckEnabled_check CHECK (IsVIESCheckEnabled IN ('Y', 'N')),
    CONSTRAINT VATaxID_Config_OnServiceUnavailable_check CHECK (OnServiceUnavailable IN
        ('NotChecked', 'RequestSent', 'Valid', 'Invalid', 'NotSupported', 'ServiceUnavailable'))
);

COMMENT ON TABLE VATaxID_Config IS 'Per-organisation configuration for the offline VAT-ID format check and the online VIES check.';

-- one active config per organisation
CREATE UNIQUE INDEX VATaxID_Config_AD_Org_ID_active_uidx
    ON VATaxID_Config (AD_Org_ID)
    WHERE IsActive = 'Y';

-- 2. AD_Table (cloned from X_TableTemplate 540290)
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, TableName, AccessLevel, IsView, IsSecurityEnabled, IsChangeLog, IsDeleteable,
                       IsHighVolume, LoadSeq, EntityType, ImportTable)
VALUES (542638 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxID_Config', 'VATaxID_Config', '3', 'N', 'Y', 'Y', 'Y',
        'N', 0, 'D', 'N');

-- 3. AD_Element for the table's own ID column
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES (585165 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxID_Config_ID', 'USt-IdNr.-Konfiguration', 'USt-IdNr.-Konfiguration',
        'Konfiguration der USt-IdNr.-Prüfung je Organisation.', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585165
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Name = 'VAT-ID Check Configuration', PrintName = 'VAT-ID Check Configuration',
    Description = 'Configuration of the VAT-ID check, per organisation.',
    Updated = TO_TIMESTAMP('2026-08-11 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585165;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-11 14:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585165;

-- 4. AD_Elements for the seven business columns
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES
    (585166 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-11 14:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsFormatCheckEnabled', 'Formatprüfung aktiv', 'Formatprüfung aktiv',
     'Legt fest, ob die lokale Format- und Prüfziffernvalidierung der USt-IdNr. durchgeführt wird.', 'D'),
    (585167 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-11 14:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsVIESCheckEnabled', 'VIES-Prüfung aktiv', 'VIES-Prüfung aktiv',
     'Legt fest, ob die USt-IdNr. online über den VIES-Dienst geprüft wird.', 'D'),
    -- RestApiBaseURL reuses the existing shared AD_Element 576182 ("REST API URL") --
    -- ColumnName is globally unique on AD_Element, and a same-purpose element already exists.
    (585169 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-11 14:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequesterMemberStateCode', 'Anfragender Mitgliedstaat', 'Anfragender Mitgliedstaat',
     'Länderkürzel der eigenen USt-IdNr., die bei der VIES-Anfrage als Anfragender übermittelt wird.', 'D'),
    (585170 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-11 14:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequesterNumber', 'Anfragende USt-IdNr.', 'Anfragende USt-IdNr.',
     'Eigene USt-IdNr. (ohne Länderkürzel), die bei der VIES-Anfrage als Anfragender übermittelt wird.', 'D'),
    (585171 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-11 14:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RecheckAfterDays', 'Erneute Prüfung nach (Tagen)', 'Erneute Prüfung nach (Tagen)',
     'Anzahl Tage, die ein erfolgreiches Prüfergebnis gültig bleibt, bevor eine erneute Prüfung erfolgt.', 'D'),
    (585172 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-11 14:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'OnServiceUnavailable', 'Verhalten bei nicht erreichbarem Dienst', 'Verhalten bei nicht erreichbarem Dienst',
     'Status, der angenommen wird, wenn der VIES-Dienst nicht erreichbar ist und das letzte Ergebnis älter als die konfigurierte Frist ist.', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID IN (585166, 585167, 585169, 585170, 585171, 585172)
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Format Check Enabled', PrintName = 'Format Check Enabled',
    Description = 'Determines whether the local format and check-digit validation of the VAT-ID is performed.',
    Updated = TO_TIMESTAMP('2026-08-11 14:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585166;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'VIES Check Enabled', PrintName = 'VIES Check Enabled',
    Description = 'Determines whether the VAT-ID is checked online via the VIES service.',
    Updated = TO_TIMESTAMP('2026-08-11 14:00:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585167;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Requester Member State', PrintName = 'Requester Member State',
    Description = 'Country code of our own VAT-ID, sent as the requester on the VIES request.',
    Updated = TO_TIMESTAMP('2026-08-11 14:00:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585169;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Requester VAT Number', PrintName = 'Requester VAT Number',
    Description = 'Our own VAT number (without the country prefix), sent as the requester on the VIES request.',
    Updated = TO_TIMESTAMP('2026-08-11 14:00:24', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585170;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Recheck After (Days)', PrintName = 'Recheck After (Days)',
    Description = 'Number of days a successful check result stays valid before a recheck is triggered.',
    Updated = TO_TIMESTAMP('2026-08-11 14:00:25', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585171;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'On Service Unavailable', PrintName = 'On Service Unavailable',
    Description = 'Status assumed when the VIES service is unreachable and the last result is older than the configured interval.',
    Updated = TO_TIMESTAMP('2026-08-11 14:00:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585172;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-11 14:00:27', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID IN (585166, 585167, 585169, 585170, 585171, 585172);

-- 5. AD_Column — standard columns (cloned from X_TableTemplate 540290, reusing the shared standard elements)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        EntityType, PersonalDataCategory, Version)
VALUES
    (593132 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 585165, 'VATaxID_Config_ID', 13, 10,
     'Y', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593133 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 102, 'AD_Client_ID', 19, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593134 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 113, 'AD_Org_ID', 30, 10,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593135 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 348, 'IsActive', 20, 1,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593136 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 245, 'Created', 16, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593137 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 246, 'CreatedBy', 18, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593138 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 607, 'Updated', 16, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593139 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 608, 'UpdatedBy', 18, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0);

-- 6. AD_Column — business columns
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        DefaultValue, EntityType, PersonalDataCategory, Version)
VALUES
    (593140 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 585166, 'IsFormatCheckEnabled', 20, NULL, 1,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 'D', 'NP', 0),
    (593141 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 585167, 'IsVIESCheckEnabled', 20, NULL, 1,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593142 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 576182, 'RestApiBaseURL', 10, NULL, 400,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', NULL, 'D', 'NP', 0),
    (593143 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 585169, 'RequesterMemberStateCode', 10, NULL, 2,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', NULL, 'D', 'NP', 0),
    (593144 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 585170, 'RequesterNumber', 10, NULL, 20,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', NULL, 'D', 'NP', 0),
    (593145 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 585171, 'RecheckAfterDays', 11, NULL, 10,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', '90', 'D', 'NP', 0),
    (593146 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-11 14:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-11 14:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542638, 585172, 'OnServiceUnavailable', 17, 542125, 20,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'ServiceUnavailable', 'D', 'NP', 0);

-- 7. AD_Column_Trl skeleton rows for all 15 columns
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Table_ID = 542638
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 8. Propagate element translations to the newly-created columns
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(x.AD_Element_ID)
FROM (VALUES (585165), (585166), (585167), (576182), (585169), (585170), (585171), (585172)) AS x(AD_Element_ID);
