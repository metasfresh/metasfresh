-- Add SeqNo column to C_BPartner_EDI_Setting; drop the unique index
-- so multiple rows per BPartner/location can be ordered and the model
-- generator picks up the new column.
--
-- IDs allocated from idserver.metas.de on 2026-06-09:
--   AD_MigrationScript prefix : 5806970  (raw 580697 × 10)
--   AD_Column                 : 592791   (C_BPartner_EDI_Setting.SeqNo)
-- Reused:
--   AD_Element 566 (SeqNo / "Reihenfolge")
--   AD_Table_ID 542610 (C_BPartner_EDI_Setting)

-- ============================================================
-- 1. Physical DDL — add SeqNo column with default 10
-- ============================================================
-- New column: use ALTER TABLE ADD COLUMN (not t_alter_column, which only works on
-- columns that already exist physically).  Default 10 is set at DDL level so all
-- existing rows are backfilled automatically; NOT NULL follows after the backfill.
-- db_alter_table drops/recreates dependent views around the DDL statement.
-- 2026-06-09 09:00:00
/* DDL */ SELECT public.db_alter_table('C_BPartner_EDI_Setting', 'ALTER TABLE public.C_BPartner_EDI_Setting ADD COLUMN IF NOT EXISTS SeqNo NUMERIC(10) DEFAULT 10')
;

-- Ensure existing rows carry the default (belt-and-suspenders: the DEFAULT in
-- ADD COLUMN already does this, but an explicit UPDATE is clearer and safe).
-- 2026-06-09 09:00:01
UPDATE C_BPartner_EDI_Setting
SET SeqNo = 10
WHERE SeqNo IS NULL
;

-- Apply NOT NULL constraint now that every row has a value.
-- t_alter_column is correct here: the column now physically exists.
-- 2026-06-09 09:00:02
INSERT INTO t_alter_column VALUES('C_BPartner_EDI_Setting','SeqNo','NUMERIC(10)','NOT NULL',null)
;

-- ============================================================
-- 2. Drop the unique index
-- ============================================================
-- 2026-06-09 09:00:03
DROP INDEX IF EXISTS c_bpartner_edi_setting_unique
;

-- ============================================================
-- 3. AD_Column — register SeqNo in the application dictionary
-- ============================================================
-- Reusing the standard AD_Element 566 (ColumnName=SeqNo, Name='Reihenfolge').
-- AD_Reference_ID=11 (Integer), IsMandatory='Y', DefaultValue='10',
-- PersonalDataCategory='NP' (a sequence number is not personal data).
-- SeqNo=20 (positioned after C_BPartner_ID which sits at 10; all other columns
-- are at 0 and will be renumbered by the window designer in a later task).
-- 2026-06-09 09:01:00
INSERT INTO AD_Column (
    AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
    ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description,
    EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAllowLogging,
    IsAlwaysUpdateable, IsAutoApplyValidationRule, IsCalculated, IsEncrypted,
    IsFacetFilter, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn,
    IsShowFilterIncrementButtons, IsShowFilterInline, IsSyncDatabase, IsTranslated,
    IsUpdateable, Name, PersonalDataCategory, SelectionColumnSeqNo, SeqNo,
    Updated, UpdatedBy, Version
)
VALUES (
    0,
    592791 /*From ID Server*/,
    566,  -- AD_Element_ID: SeqNo / Reihenfolge
    0,
    11,   -- AD_Reference_ID: Integer
    542610,
    'SeqNo',
    TO_TIMESTAMP('2026-06-09 09:01:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    'N',  -- DDL_NoForeignKey
    '10', -- DefaultValue
    (SELECT Description FROM AD_Element WHERE AD_Element_ID = 566),
    'de.metas.esb.edi',
    0,    -- FacetFilterSeqNo
    10,   -- FieldLength
    (SELECT Help FROM AD_Element WHERE AD_Element_ID = 566),
    'Y',  -- IsActive
    'Y',  -- IsAllowLogging
    'N',  -- IsAlwaysUpdateable
    'N',  -- IsAutoApplyValidationRule
    'N',  -- IsCalculated
    'N',  -- IsEncrypted
    'N',  -- IsFacetFilter
    'N',  -- IsIdentifier
    'N',  -- IsKey
    'Y',  -- IsMandatory
    'N',  -- IsParent
    'N',  -- IsSelectionColumn
    'N',  -- IsShowFilterIncrementButtons
    'N',  -- IsShowFilterInline
    'Y',  -- IsSyncDatabase (physical column)
    'N',  -- IsTranslated
    'Y',  -- IsUpdateable
    (SELECT Name FROM AD_Element WHERE AD_Element_ID = 566),
    'NP', -- PersonalDataCategory
    0,    -- SelectionColumnSeqNo
    20,   -- SeqNo (after C_BPartner_ID=10)
    TO_TIMESTAMP('2026-06-09 09:01:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    0     -- Version
)
;

-- ============================================================
-- 4. AD_Column_Trl — skeleton rows for all system languages
-- ============================================================
-- 2026-06-09 09:01:01
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Column_ID = 592791
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- ============================================================
-- 5. Propagate translations from AD_Element 566
-- ============================================================
-- 2026-06-09 09:01:02
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(566)
;
