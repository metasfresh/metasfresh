-- VAT-ID online check: denormalise the check status onto C_BPartner and C_BPartner_Location.
-- Each parent gains its own VATaxIDStatus (source of truth for filtering and for the tax query),
-- VATaxIDCheckedAt (when that status was last determined) and a reference to the row in
-- VATaxID_CheckLog that produced it, kept purely for one-click zoom.
--
-- The reference DOES carry a real foreign key, unlike VATaxID_CheckLog.AD_PInstance_ID /
-- AD_Session_ID: those two are FK-less because their targets (AD_PInstance, AD_Session) are purge
-- targets, so an FK would block or cascade away evidence that must survive the purge.
-- VATaxID_CheckLog itself is append-only legal evidence: it has no retention limit, and no purge
-- job or retention configuration registers it (verified -- the only reference to deletion is the
-- table's own COMMENT, "never deleted"). Pointing at it therefore carries no such risk: a plain FK
-- is safe and gives the DB an extra integrity guarantee for free.

-- IDs allocated from idserver.metas.de:
--   AD_Element  585210 (1: VATaxIDCheckedAt, new; VATaxIDStatus and the check-log reference reuse
--                        elements 585194 and 585185, created for VATaxID_CheckLog earlier in this module)
--   AD_Column   593201..593206 (6: 3 columns x 2 tables, in table/column order)

-- 1. Physical columns -- C_BPartner
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS VATaxIDStatus VARCHAR(20) NOT NULL DEFAULT 'NotChecked';
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS VATaxIDCheckedAt TIMESTAMP;
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS VATaxID_CheckLog_ID NUMERIC(10);

ALTER TABLE C_BPartner ADD CONSTRAINT C_BPartner_VATaxIDStatus_check CHECK (VATaxIDStatus IN
    ('NotChecked', 'RequestSent', 'Valid', 'Invalid', 'NotSupported', 'ServiceUnavailable'));
ALTER TABLE C_BPartner ADD CONSTRAINT C_BPartner_VATaxID_CheckLog_ID_FK FOREIGN KEY (VATaxID_CheckLog_ID)
    REFERENCES VATaxID_CheckLog (VATaxID_CheckLog_ID);
CREATE INDEX C_BPartner_VATaxID_CheckLog_ID_idx ON C_BPartner (VATaxID_CheckLog_ID);

-- 2. Physical columns -- C_BPartner_Location
ALTER TABLE C_BPartner_Location ADD COLUMN IF NOT EXISTS VATaxIDStatus VARCHAR(20) NOT NULL DEFAULT 'NotChecked';
ALTER TABLE C_BPartner_Location ADD COLUMN IF NOT EXISTS VATaxIDCheckedAt TIMESTAMP;
ALTER TABLE C_BPartner_Location ADD COLUMN IF NOT EXISTS VATaxID_CheckLog_ID NUMERIC(10);

ALTER TABLE C_BPartner_Location ADD CONSTRAINT C_BPartner_Location_VATaxIDStatus_check CHECK (VATaxIDStatus IN
    ('NotChecked', 'RequestSent', 'Valid', 'Invalid', 'NotSupported', 'ServiceUnavailable'));
ALTER TABLE C_BPartner_Location ADD CONSTRAINT C_BPartner_Location_VATaxID_CheckLog_ID_FK FOREIGN KEY (VATaxID_CheckLog_ID)
    REFERENCES VATaxID_CheckLog (VATaxID_CheckLog_ID);
CREATE INDEX C_BPartner_Location_VATaxID_CheckLog_ID_idx ON C_BPartner_Location (VATaxID_CheckLog_ID);

-- 3. AD_Element for the one genuinely new business column, VATaxIDCheckedAt
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES (585210 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxIDCheckedAt', 'USt-IdNr. zuletzt geprüft am', 'USt-IdNr. zuletzt geprüft am',
        'Zeitpunkt der letzten USt-IdNr.-Prüfung (Anfrage oder Antwort).', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585210
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Name = 'VAT-ID Last Checked On', PrintName = 'VAT-ID Last Checked On',
    Description = 'Point in time of the most recent VAT-ID check (request or response).',
    Updated = TO_TIMESTAMP('2026-08-12 15:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585210;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-12 15:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585210;

-- 4. AD_Column -- C_BPartner (291)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        DDL_NoForeignKey, DefaultValue, EntityType, PersonalDataCategory, Version)
VALUES
    (593201 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 15:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 15:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     291, 585194, 'VATaxIDStatus', 17, 542125, 20,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'NotChecked', 'D', 'NP', 0),
    (593202 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 15:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 15:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     291, 585210, 'VATaxIDCheckedAt', 16, NULL, 29,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', NULL, 'D', 'NP', 0),
    (593203 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 15:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 15:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     291, 585185, 'VATaxID_CheckLog_ID', 19, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', NULL, 'D', 'NP', 0);

-- 5. AD_Column -- C_BPartner_Location (293)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        DDL_NoForeignKey, DefaultValue, EntityType, PersonalDataCategory, Version)
VALUES
    (593204 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 15:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 15:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
     293, 585194, 'VATaxIDStatus', 17, 542125, 20,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'NotChecked', 'D', 'NP', 0),
    (593205 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 15:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 15:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
     293, 585210, 'VATaxIDCheckedAt', 16, NULL, 29,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', NULL, 'D', 'NP', 0),
    (593206 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 15:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 15:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
     293, 585185, 'VATaxID_CheckLog_ID', 19, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', NULL, 'D', 'NP', 0);

-- 6. AD_Column_Trl skeleton rows for all 6 new columns
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID IN (593201, 593202, 593203, 593204, 593205, 593206)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 7. Propagate element translations to the newly-created columns (both reused elements and the new one)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(x.AD_Element_ID)
FROM (VALUES (585194), (585185), (585210)) AS x(AD_Element_ID);
