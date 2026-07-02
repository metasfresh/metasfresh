-- Overview tab (581171): replace the two text fields ProductValue (581172) and ProductName (581173)
-- with a single M_Product_ID lookup field so the grid shows the product via its standard lookup.
--
-- IDs allocated from idserver.metas.de on 2026-07-02:
--   AD_Field       781321  (M_Product_ID in Overview tab)
--   AD_UI_Element  652429  (M_Product_ID in Overview tab)

-- ============================================================
-- 1. Remove ProductValue and ProductName fields from Overview tab
-- ============================================================
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID IN (581186/*ProductValue*/, 581187/*ProductName*/);

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (581172/*ProductValue*/, 581173/*ProductName*/);
DELETE FROM AD_Field_Trl     WHERE AD_Field_ID IN (581172/*ProductValue*/, 581173/*ProductName*/);
DELETE FROM AD_Field          WHERE AD_Field_ID IN (581172/*ProductValue*/, 581173/*ProductName*/);

-- ============================================================
-- 2. Add M_Product_ID lookup field (replaces both removed fields, SeqNo=10)
-- ============================================================
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781321/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 23:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 23:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Artikel', 581171, 581150/*M_Product_ID in M_Picking_OrderBoard_Overview_v*/, 'Y', 10, 'Y', 10,
    'N', 'N', 'N', 'N', 'D')
;

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781321, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 23:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-02 23:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781321
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781321)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(454/*M_Product_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781321;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781321);

-- ============================================================
-- 3. Add AD_UI_Element for M_Product_ID
-- ============================================================
INSERT INTO AD_UI_Element
    (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_UI_ElementGroup_ID, AD_Field_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsDisplayed_SideList, SeqNo_SideList, EntityType)
VALUES (652429/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 23:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 23:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    581185/*AD_UI_ElementGroup for overview tab*/, 781321/*AD_Field M_Product_ID*/,
    'Y', 10, 'Y', 10, 'N', 0, 'D')
;
