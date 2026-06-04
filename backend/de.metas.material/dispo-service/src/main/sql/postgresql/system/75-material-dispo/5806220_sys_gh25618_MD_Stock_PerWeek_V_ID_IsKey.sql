-- me03 25618 / F19100 — Stock per week
-- Add the hash-based synthetic primary key column MD_Stock_PerWeek_V_ID to the
-- Application Dictionary for table MD_Stock_PerWeek_V (AD_Table_ID=542612).
-- The column is already present in the view DDL (migration 5806110).
--
-- IDs allocated from idserver.metas.de on 2026-06-04:
--   AD_Column_ID  592715  (MD_Stock_PerWeek_V_ID)
--   AD_Element_ID 584948  (MD_Stock_PerWeek_V_ID — new element)
--   AD_MigrationScript sequence: 5806220
--
-- AD_Reference_ID=13 (ID) — the standard reference for integer primary key columns.
-- IsKey='Y' — exactly one key column per table (enforced by unique index ad_column_iskey).
-- All existing columns for this table have IsKey='N' (verified: 592706-592714).
--
-- After this migration, AD_Ref_Table.AD_Key for reference 542100 must also be updated
-- from 592708 (WeekStartDate) to 592715 (MD_Stock_PerWeek_V_ID).
-- That live UPDATE is included at the end of this script.

-- ============================================================
-- 1. New AD_Element for MD_Stock_PerWeek_V_ID
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (584948 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-04 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'MD_Stock_PerWeek_V_ID', 'Bestand pro Woche (ID)', 'Bestand pro Woche (ID)',
     'de.metas.material.dispo')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-04 14:00:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-04 14:00:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Element_ID=584948
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name='Bestand pro Woche (ID)', PrintName='Bestand pro Woche (ID)', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-04 14:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584948 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Element_Trl
SET Name='Stock per week (ID)', PrintName='Stock per week (ID)', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-04 14:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584948 AND AD_Language='en_US'
;

-- ============================================================
-- 2. AD_Column for MD_Stock_PerWeek_V_ID  (IsKey='Y', AD_Reference_ID=13 ID)
--    View columns: IsUpdateable='N', IsAlwaysUpdateable='N', IsParent='N'.
--    IsMandatory='Y': the hash expression is NEVER NULL (all inputs are non-null in the view).
-- ============================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592715 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-04 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 584948, 13 /*ID*/,
     'MD_Stock_PerWeek_V_ID', 'Bestand pro Woche (ID)', 'de.metas.material.dispo',
     10, 'Y', 'N', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-04 14:01:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-04 14:01:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592715
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584948);

-- ============================================================
-- 3. Repoint AD_Ref_Table.AD_Key for reference 542100 to the new integer key column.
--    Previously pointed to 592708 (WeekStartDate — Date type), which caused the
--    relation-type count supplier to fail because it requires an integer column.
-- ============================================================
UPDATE AD_Ref_Table
SET    AD_Key = 592715 /*MD_Stock_PerWeek_V_ID*/,
       Updated = TO_TIMESTAMP('2026-06-04 14:02:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Reference_ID = 542100
;
