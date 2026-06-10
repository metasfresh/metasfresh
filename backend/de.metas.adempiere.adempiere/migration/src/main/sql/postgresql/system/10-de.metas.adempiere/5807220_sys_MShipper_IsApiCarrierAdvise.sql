-- IDs allocated from idserver.metas.de on 2026-06-10:
--   AD_Element    584972 (IsApiCarrierAdvise label)
--   AD_Column     592800 (M_Shipper.IsApiCarrierAdvise)
--   AD_Field      780754 (Shipper window field)
--   AD_UI_Element 652049 (Shipper window UI element, flags group 541020)
--
-- Literals used:
--   AD_Table_ID   253    (M_Shipper)
--   AD_Window_ID  142    (Lieferweg / Shipper)
--   AD_Tab_ID     185    (main tab)
--   AD_UI_ElementGroup_ID 541020 (flags group, right column)

-- =============================================================================
-- 1. DDL — add column to M_Shipper
-- =============================================================================
ALTER TABLE M_Shipper ADD COLUMN IF NOT EXISTS IsApiCarrierAdvise CHAR(1) DEFAULT 'N';
UPDATE M_Shipper SET IsApiCarrierAdvise = 'N' WHERE IsApiCarrierAdvise IS NULL;
ALTER TABLE M_Shipper ALTER COLUMN IsApiCarrierAdvise SET NOT NULL;
ALTER TABLE M_Shipper ADD CONSTRAINT IsApiCarrierAdvise_Check CHECK (IsApiCarrierAdvise IN ('Y','N'));

-- =============================================================================
-- 2. AD_Element (base = de_DE)
-- =============================================================================
INSERT INTO AD_Element
  (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   ColumnName, Name, PrintName, EntityType)
VALUES
  (584972 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-06-10 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
   TO_TIMESTAMP('2026-06-10 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
   'IsApiCarrierAdvise', 'API Carrier Advise', 'API Carrier Advise', 'D')
;

-- =============================================================================
-- 3. AD_Element_Trl — seed all active system languages
-- =============================================================================
INSERT INTO AD_Element_Trl
  (AD_Language, AD_Element_ID, IsTranslated,
   Name, PrintName, Description, Help,
   AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, e.AD_Element_ID, 'N',
  e.Name, e.PrintName, e.Description, e.Help,
  e.AD_Client_ID, e.AD_Org_ID, 'Y',
  TO_TIMESTAMP('2026-06-10 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
  TO_TIMESTAMP('2026-06-10 10:00:00','YYYY-MM-DD HH24:MI:SS'),100
FROM AD_Language l, AD_Element e
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND e.AD_Element_ID=584972
  AND NOT EXISTS (
    SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=e.AD_Element_ID
  )
;

-- 4. Override en_US with English text
UPDATE AD_Element_Trl
SET Name='API Carrier Advise', PrintName='API Carrier Advise',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-10 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584972 AND AD_Language='en_US'
;

-- =============================================================================
-- 6. AD_Column
-- =============================================================================
INSERT INTO AD_Column
  (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   Version, Name, AD_Table_ID, ColumnName,
   AD_Reference_ID, FieldLength, IsKey, IsParent, IsMandatory, IsTranslated,
   IsIdentifier, IsEncrypted, IsSelectionColumn, IsUpdateable, IsAlwaysUpdateable,
   AD_Element_ID, EntityType, IsExcludeFromZoomTargets, IsSyncDatabase,
   IsLazyLoading, IsAllowLogging, PersonalDataCategory,
   DefaultValue)
VALUES
  (592800 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-06-10 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,
   TO_TIMESTAMP('2026-06-10 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,
   0, 'API Carrier Advise', 253 /*AD_Table_ID M_Shipper*/, 'IsApiCarrierAdvise',
   20 /*YesNo*/, 1, 'N', 'N', 'Y', 'N',
   'N', 'N', 'N', 'Y', 'N',
   584972, 'D', 'Y', 'Y',
   'Y', 'Y', 'NP',
   'N')
;

-- =============================================================================
-- 7. AD_Column_Trl — seed skeleton rows
-- =============================================================================
INSERT INTO AD_Column_Trl
  (AD_Language, AD_Column_ID, Name, IsTranslated,
   AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, c.AD_Column_ID, c.Name, 'N',
  c.AD_Client_ID, c.AD_Org_ID, 'Y',
  TO_TIMESTAMP('2026-06-10 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,
  TO_TIMESTAMP('2026-06-10 10:01:00','YYYY-MM-DD HH24:MI:SS'),100
FROM AD_Language l, AD_Column c
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND c.AD_Column_ID=592800
  AND NOT EXISTS (
    SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=c.AD_Column_ID
  )
;

-- =============================================================================
-- 8. AD_Field on Shipper window main tab (tab 185)
-- =============================================================================
INSERT INTO AD_Field
  (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   Name, AD_Tab_ID, AD_Column_ID, IsDisplayed,
   SeqNo, SeqNoGrid, IsSameLine, IsHeading, IsFieldOnly,
   IsReadOnly, IsMandatory, IsEncrypted, DisplayLength,
   EntityType, IsDisplayedGrid, SortNo)
VALUES
  (780754 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-06-10 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,
   TO_TIMESTAMP('2026-06-10 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,
   'API Carrier Advise', 185 /*AD_Tab_ID Shipper main tab*/, 592800, 'Y',
   100, 75, 'N', 'N', 'N',
   'N', 'N', 'N', 1,
   'D', 'Y', 0)
;

-- =============================================================================
-- 9. AD_Field_Trl — seed skeleton rows
-- =============================================================================
INSERT INTO AD_Field_Trl
  (AD_Language, AD_Field_ID, Name, IsTranslated,
   AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, f.AD_Field_ID, f.Name, 'N',
  f.AD_Client_ID, f.AD_Org_ID, 'Y',
  TO_TIMESTAMP('2026-06-10 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,
  TO_TIMESTAMP('2026-06-10 10:02:00','YYYY-MM-DD HH24:MI:SS'),100
FROM AD_Language l, AD_Field f
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND f.AD_Field_ID=780754
  AND NOT EXISTS (
    SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=f.AD_Field_ID
  )
;

-- =============================================================================
-- 10. Propagate element translations → field (element-id, not field-id)
-- =============================================================================
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584972);

-- =============================================================================
-- 11. Rebuild element links for the new field
-- =============================================================================
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780754;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780754);

-- =============================================================================
-- 12. AD_UI_Element — place field in the flags group (541020), form+grid
--     SeqNo=30 (after IsActive=10, IsDefault=20)
--     SeqNoGrid=75 (between IsDefault=70 and AD_Org_ID=80)
-- =============================================================================
INSERT INTO AD_UI_Element
  (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
   Created, CreatedBy, Updated, UpdatedBy,
   AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
   Name, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
   SeqNo, SeqNoGrid, SeqNo_SideList, IsAdvancedField)
VALUES
  (652049 /*From ID Server*/, 0, 0, 'Y',
   TO_TIMESTAMP('2026-06-10 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,
   TO_TIMESTAMP('2026-06-10 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,
   780754, 185 /*AD_Tab_ID*/, 541020 /*flags group*/, 'F',
   'API Carrier Advise', 'Y', 'Y', 'N',
   30, 75, 0, 'N')
;

-- =============================================================================
-- 13. Propagate element translations to TRL tables
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584972);
