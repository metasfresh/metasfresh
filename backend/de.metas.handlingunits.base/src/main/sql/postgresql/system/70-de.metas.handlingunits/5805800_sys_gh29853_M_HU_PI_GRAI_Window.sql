-- IDs allocated from idserver.metas.de on 2026-06-02:
--   AD_Element  584932  (window name element: "GRAI-Packvorschrift-Zuordnung")
--   AD_Window   542157  (M_HU_PI_GRAI dedicated window)
--   AD_Tab      549285  (single tab on M_HU_PI_GRAI)
--   AD_UI_Section  547805
--   AD_UI_Column   549532  (left),  549533  (right)
--   AD_UI_ElementGroup  555412  (primary / left),  555413  (flags / right),  555414  (org / right)
--   AD_Field    780647  (M_HU_PI_ID),   780648  (GRAI_CompanyPrefix), 780649  (GRAI_AssetType)
--   AD_Field    780650  (IsActive),     780651  (AD_Org_ID),           780652  (AD_Client_ID)
--   AD_UI_Element 651947 (M_HU_PI_ID), 651948 (GRAI_CompanyPrefix),  651949 (GRAI_AssetType)
--   AD_UI_Element 651950 (IsActive),   651951 (AD_Org_ID),            651952 (AD_Client_ID)
--   AD_Menu     542333
--
-- Reused:
--   AD_Table_ID  542611  (M_HU_PI_GRAI — already exists)
--   AD_Column_IDs: 592689 M_HU_PI_GRAI_ID (PK), 592690 M_HU_PI_ID, 592691 GRAI_CompanyPrefix,
--                  592692 GRAI_AssetType, 592693 AD_Client_ID, 592694 AD_Org_ID, 592695 IsActive
--   AD_Element_IDs: 542135 M_HU_PI_ID, 584929 GRAI_CompanyPrefix, 584930 GRAI_AssetType
--   Menu parent: 1000016 (Logistik), sibling: 540830 (Packvorschrift) at SeqNo=6
--   Menu tree: AD_Tree_ID=10

-- ============================================================
-- 1. AD_Element for the window name
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES
    (584932 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_HU_PI_GRAI',
     'GRAI-Packvorschrift-Zuordnung',
     'GRAI-Packvorschrift-Zuordnung',
     NULL, NULL,
     'de.metas.handlingunits');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated,
     Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 584932 /*From ID Server*/, 'N',
       e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND e.AD_Element_ID = 584932
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584932);

UPDATE AD_Element_Trl
SET    Name = 'GRAI-Packvorschrift-Zuordnung', PrintName = 'GRAI-Packvorschrift-Zuordnung',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584932 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    Name = 'GRAI-Packvorschrift-Zuordnung', PrintName = 'GRAI-Packvorschrift-Zuordnung',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584932 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    Name = 'GRAI to Packing Instruction Mapping', PrintName = 'GRAI to Packing Instruction Mapping',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584932 AND AD_Language = 'en_US';

-- ============================================================
-- 2. AD_Window
-- ============================================================
INSERT INTO AD_Window
    (AD_Window_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, WindowType, IsSOTrx, EntityType,
     AD_Element_ID)
VALUES
    (542157 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GRAI-Packvorschrift-Zuordnung', 'M', 'Y', 'de.metas.handlingunits',
     584932 /*From ID Server*/);

INSERT INTO AD_Window_Trl
    (AD_Language, AD_Window_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542157 /*From ID Server*/, 'N', w.Name, w.Description, w.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Window w
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND w.AD_Window_ID = 542157
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = 542157);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584932 /*window element*/);

-- Register as the primary window on the table
UPDATE AD_Table
SET    AD_Window_ID = 542157 /*From ID Server*/,
       Updated = TO_TIMESTAMP('2026-06-02 11:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Table_ID = 542611;

-- ============================================================
-- 3. AD_Tab
-- ============================================================
INSERT INTO AD_Tab
    (AD_Tab_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Window_ID, AD_Table_ID, TabLevel, SeqNo,
     IsSingleRow, IsInfoTab, IsTranslationTab, IsReadOnly,
     IsInsertRecord, IsAdvancedTab,
     EntityType, AD_Element_ID)
VALUES
    (549285 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GRAI-Packvorschrift-Zuordnung',
     542157 /*From ID Server*/, 542611 /*M_HU_PI_GRAI*/, 0, 10,
     'Y', 'N', 'N', 'N',
     'Y', 'N',
     'de.metas.handlingunits', 584932 /*From ID Server*/);

INSERT INTO AD_Tab_Trl
    (AD_Language, AD_Tab_ID, IsTranslated, Name, Description, Help,
     CommitWarning,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 549285 /*From ID Server*/, 'N', t.Name, t.Description, t.Help,
       NULL,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Tab_ID = 549285
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549285);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584932 /*tab element — propagate to AD_Tab_Trl*/);

-- ============================================================
-- 4. AD_Fields (M_HU_PI_ID, GRAI_CompanyPrefix, GRAI_AssetType, IsActive, AD_Org_ID, AD_Client_ID)
-- ============================================================

-- 4a. M_HU_PI_ID (FK lookup, mandatory)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted,
     EntityType)
VALUES
    (780647 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Packvorschrift',
     549285 /*From ID Server*/, 592690 /*M_HU_PI_ID*/,
     'Y', 10, 'Y', 10,
     'N', 'N', 'N', 'N',
     'de.metas.handlingunits');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 780647 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:03:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 780647
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 780647);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(542135 /*M_HU_PI_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780647;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780647);

-- 4b. GRAI_CompanyPrefix (varchar, mandatory)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted,
     EntityType)
VALUES
    (780648 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:03:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GS1 Firmenpräfix',
     549285 /*From ID Server*/, 592691 /*GRAI_CompanyPrefix*/,
     'Y', 20, 'Y', 20,
     'N', 'N', 'N', 'N',
     'de.metas.handlingunits');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 780648 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:03:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 780648
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 780648);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584929 /*GRAI_CompanyPrefix element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780648;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780648);

-- 4c. GRAI_AssetType (varchar, mandatory)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted,
     EntityType)
VALUES
    (780649 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:03:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GRAI Gebindetyp',
     549285 /*From ID Server*/, 592692 /*GRAI_AssetType*/,
     'Y', 30, 'Y', 30,
     'N', 'N', 'N', 'N',
     'de.metas.handlingunits');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 780649 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:03:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 780649
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 780649);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584930 /*GRAI_AssetType element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780649;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780649);

-- 4d. IsActive (flags group)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted,
     EntityType)
VALUES
    (780650 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:03:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktiv',
     549285 /*From ID Server*/, 592695 /*IsActive*/,
     'Y', 40, 'N', 0,
     'N', 'N', 'N', 'N',
     'de.metas.handlingunits');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 780650 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:03:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 780650
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 780650);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780650;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780650);

-- 4e. AD_Org_ID (org group)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted,
     EntityType)
VALUES
    (780651 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:03:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Organisation',
     549285 /*From ID Server*/, 592694 /*AD_Org_ID*/,
     'Y', 50, 'Y', 40,
     'N', 'N', 'N', 'N',
     'de.metas.handlingunits');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 780651 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:03:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 780651
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 780651);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113 /*AD_Org_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780651;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780651);

-- 4f. AD_Client_ID (org group — last)
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID,
     IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted,
     EntityType)
VALUES
    (780652 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:03:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Mandant',
     549285 /*From ID Server*/, 592693 /*AD_Client_ID*/,
     'Y', 60, 'N', 0,
     'N', 'N', 'N', 'N',
     'de.metas.handlingunits');

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 780652 /*From ID Server*/, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:03:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 780652
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 780652);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(102 /*AD_Client_ID element*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780652;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780652);

-- ============================================================
-- 5. AD_UI_Section → AD_UI_Column (left/right) → AD_UI_ElementGroup
-- ============================================================

-- Section (value='main' is the standard non-null value; Name is left NULL per convention)
INSERT INTO AD_UI_Section
    (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, SeqNo, Value)
VALUES
    (547805 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549285 /*From ID Server*/, 10, 'main');

-- Left column (SeqNo=10)
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES
    (549532 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:04:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:04:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547805 /*From ID Server*/, 10);

-- Right column (SeqNo=20)
INSERT INTO AD_UI_Column
    (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Section_ID, SeqNo)
VALUES
    (549533 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:04:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:04:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     547805 /*From ID Server*/, 20);

-- Left column, primary group (mandatory/key fields — UIStyle='primary')
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555412 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:04:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:04:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549532 /*From ID Server*/, 10, 'primary', 'default');

-- Right column, flags group (IsActive first — UIStyle=NULL)
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555413 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:04:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:04:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549533 /*From ID Server*/, 10, NULL, 'flags');

-- Right column, org group (AD_Org_ID, AD_Client_ID — UIStyle=NULL)
INSERT INTO AD_UI_ElementGroup
    (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES
    (555414 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:04:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:04:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
     549533 /*From ID Server*/, 20, NULL, 'default');

-- ============================================================
-- 6. AD_UI_Elements — pairing each AD_Field
-- ============================================================
-- Left/primary group: M_HU_PI_ID (SeqNo=10, grid=10), GRAI_CompanyPrefix (20,20), GRAI_AssetType (30,30)
-- Right/flags group:  IsActive (10, not in grid)
-- Right/org group:    AD_Org_ID (10, grid=40), AD_Client_ID (20, not in grid)

-- M_HU_PI_ID — primary group, form+grid
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780647 /*M_HU_PI_ID field*/, 0, 549285 /*tab*/,
     555412 /*primary group*/, 651947 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-02 11:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
     'Y', 'Y', 'N',
     'Packvorschrift', 10, 10, 0,
     TO_TIMESTAMP('2026-06-02 11:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- GRAI_CompanyPrefix — primary group, form+grid
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780648 /*GRAI_CompanyPrefix field*/, 0, 549285 /*tab*/,
     555412 /*primary group*/, 651948 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-02 11:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
     'Y', 'Y', 'N',
     'GS1 Firmenpräfix', 20, 20, 0,
     TO_TIMESTAMP('2026-06-02 11:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- GRAI_AssetType — primary group, form+grid
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780649 /*GRAI_AssetType field*/, 0, 549285 /*tab*/,
     555412 /*primary group*/, 651949 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-02 11:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
     'Y', 'Y', 'N',
     'GRAI Gebindetyp', 30, 30, 0,
     TO_TIMESTAMP('2026-06-02 11:05:02', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- IsActive — flags group, form only (not in grid)
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780650 /*IsActive field*/, 0, 549285 /*tab*/,
     555413 /*flags group*/, 651950 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-02 11:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
     'Y', 'N', 'N',
     'Aktiv', 10, 0, 0,
     TO_TIMESTAMP('2026-06-02 11:05:03', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- AD_Org_ID — org group, form+grid (last grid column)
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780651 /*AD_Org_ID field*/, 0, 549285 /*tab*/,
     555414 /*org group*/, 651951 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-02 11:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
     'Y', 'Y', 'N',
     'Organisation', 10, 40, 0,
     TO_TIMESTAMP('2026-06-02 11:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- AD_Client_ID — org group, form only (rule: Client NOT in grid)
INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
     Created, CreatedBy, IsActive, IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name, SeqNo, SeqNoGrid, SeqNo_SideList,
     Updated, UpdatedBy)
VALUES
    (0, 780652 /*AD_Client_ID field*/, 0, 549285 /*tab*/,
     555414 /*org group*/, 651952 /*From ID Server*/, 'F',
     TO_TIMESTAMP('2026-06-02 11:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N',
     'Y', 'N', 'N',
     'Mandant', 20, 0, 0,
     TO_TIMESTAMP('2026-06-02 11:05:05', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 7. AD_Menu entry + tree placement (sibling to Packvorschrift, SeqNo=7)
-- ============================================================
INSERT INTO AD_Menu
    (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, Action, AD_Window_ID, IsSummary,
     EntityType, AD_Element_ID, InternalName)
VALUES
    (542333 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GRAI-Packvorschrift-Zuordnung', 'W', 542157 /*From ID Server*/, 'N',
     'de.metas.handlingunits', 584932 /*From ID Server*/,
     'M_HU_PI_GRAI_Window' /*unique internal name*/);

INSERT INTO AD_Menu_Trl
    (AD_Language, AD_Menu_ID, IsTranslated, Name, Description,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 542333 /*From ID Server*/, 'N', m.Name, m.Description,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 11:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 11:06:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Menu m
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND m.AD_Menu_ID = 542333
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = 542333);

-- Propagate translations from element (de_DE, de_CH, en_US all set on element 584932)
-- Function signature: update_menu_translation_from_ad_element(p_ad_element_id, p_ad_language)
-- Pass NULL for language to update all languages at once
SELECT update_menu_translation_from_ad_element(584932 /*element id*/, NULL /*all languages*/);

-- Place in the menu tree as sibling of "Packvorschrift" (540830, SeqNo=6) → SeqNo=7
-- Parent: 1000016 (Logistik), AD_Tree_ID=10
INSERT INTO AD_TreeNodeMM
    (AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
VALUES
    (0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 11:06:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 11:06:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     10 /*AD_Tree_ID*/, 542333 /*Node_ID = new menu*/, 1000016 /*Parent=Logistik*/, 7);
