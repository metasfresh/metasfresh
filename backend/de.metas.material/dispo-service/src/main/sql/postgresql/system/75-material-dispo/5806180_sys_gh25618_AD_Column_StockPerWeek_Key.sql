-- 2026-06-04
-- gh#25618 — Bestand pro Woche / Stock per week
-- Add the synthetic primary key column MD_Stock_PerWeek_V_ID to AD_Table 542612
-- (MD_Stock_PerWeek_V). The view now exposes this row_number()-based int as its FIRST
-- column (migration 5806110), giving the WebUI a stable row key for this view-backed window.
--
-- IDs allocated from idserver.metas.de on 2026-06-04:
--   AD_Column_ID   592712  (MD_Stock_PerWeek_V_ID)
--   AD_Element_ID  584947  (MD_Stock_PerWeek_V_ID — new)
--
-- AD_Reference_ID 13 = ID (integer row key).
-- IsKey='Y' on the new column; all other columns on the table must be IsKey='N'.

-- ============================================================
-- 1. AD_Element for the key column
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, EntityType)
VALUES
    (584947 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-04 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     'MD_Stock_PerWeek_V_ID', 'Bestand pro Woche', 'Bestand pro Woche', 'de.metas.material.dispo')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-04 12:00:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-04 12:00:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Element_ID=584947
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- en_US
UPDATE AD_Element_Trl
SET Name='Stock per week', PrintName='Stock per week', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-04 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584947 AND AD_Language='en_US'
;

-- ============================================================
-- 2. AD_Column — synthetic key MD_Stock_PerWeek_V_ID
--    AD_Reference_ID=13 (ID), IsKey='Y'. View column => IsUpdateable='N', IsAlwaysUpdateable='N'.
--    IsMandatory='Y': the view always emits a non-null row_number.
-- ============================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592712 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-04 12:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 12:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 584947, 13,
     'MD_Stock_PerWeek_V_ID', 'Bestand pro Woche', 'de.metas.material.dispo',
     10, 'Y', 'N', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-04 12:01:01','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-04 12:01:01','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592712
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584947);

-- ============================================================
-- 3. Ensure exactly one key column on the table: MD_Stock_PerWeek_V_ID.
--    (The original 6 columns were all created with IsKey='N'; this is defensive.)
-- ============================================================
UPDATE AD_Column
SET IsKey='N',
    Updated=TO_TIMESTAMP('2026-06-04 12:01:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID=542612 AND AD_Column_ID<>592712 AND IsKey='Y'
;

-- ============================================================
-- 4. Repoint the zoom-target AD_Ref_Table key column from WeekStartDate (592708)
--    to the new synthetic primary key MD_Stock_PerWeek_V_ID (592712).
--    AD_Reference 542100 is the C_OrderLine -> MD_Stock_PerWeek_V zoom target
--    (created in migration 5806160). Its AD_Ref_Table.AD_Key must reference the
--    table's real key column now that one exists.
-- ============================================================
UPDATE AD_Ref_Table
SET AD_Key=592712,
    Updated=TO_TIMESTAMP('2026-06-04 12:01:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID=542100 AND AD_Table_ID=542612
;
