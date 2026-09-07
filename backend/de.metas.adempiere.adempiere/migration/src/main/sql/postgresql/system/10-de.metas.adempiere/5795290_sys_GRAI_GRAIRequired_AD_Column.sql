-- 2026-03-23
-- AD_Column on C_BPartner for GRAIRequired + DDL

-- AD_Column: GRAIRequired on C_BPartner
INSERT INTO AD_Column (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, ColumnName, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, DefaultValue, IsMandatory, IsUpdateable, IsKey, IsParent,
                       IsIdentifier, IsSelectionColumn, IsTranslated, IsEncrypted, IsAlwaysUpdateable,
                       IsAllowLogging, PersonalDataCategory, Version, EntityType, AD_Column_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        291, 'GRAIRequired', 584694 /*From ID Server*/, 17, 542081 /*From ID Server*/,
        1, 'N', 'Y', 'Y', 'N', 'N',
        'N', 'N', 'N', 'N', 'N',
        'Y', 'NP', 0, 'de.metas.handlingunits', 592261 /*From ID Server*/);

-- DDL: Add column to C_BPartner
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS GRAIRequired VARCHAR(1) DEFAULT 'N';

-- Backfill existing rows
UPDATE C_BPartner SET GRAIRequired = 'N' WHERE GRAIRequired IS NULL;

-- Set NOT NULL constraint
ALTER TABLE C_BPartner ALTER COLUMN GRAIRequired SET NOT NULL;
