-- Run mode: SWING_CLIENT

-- IDs fetched from ID server (http://idserver.metas.de):
-- AD_MigrationScript  -> 5809330  (×10 = not appended here; filename prefix = 5809330)
-- AD_UI_Element       -> 652361   /*From ID Server*/  (TaxID field in group 542729)
-- AD_Field            -> 781243   /*From ID Server*/  (CommercialRegisterNumber on tab 541852)
-- AD_UI_Element       -> 652362   /*From ID Server*/  (CommercialRegisterNumber in group 542729)

-- Context (pre-resolved, no re-query needed):
-- Window 540676 "Geschäftspartner (Org)" / Tab 541852 (C_BPartner, TabLevel=0)
-- AD_UI_ElementGroup 542729: Value/Name/CompanyName/AD_OrgBP_ID/URL/VATaxID
--   VATaxID (AD_Field 583134) is SeqNo 120 (last in group) — already displayed, no change
-- TaxID:                AD_Field 583087 exists, IsDisplayed='N', no AD_UI_Element on this tab
-- CommercialRegisterNumber: AD_Column 583367, AD_Element 581038, no AD_Field on this tab
-- EntityType 'U' (matches sibling fields 583087, 583134 on this tab)

-- ===========================================================================
-- 1. TaxID (Steuernummer) — make AD_Field 583087 visible
-- ===========================================================================
-- 2026-06-22 10:00:00
UPDATE AD_Field
SET    IsDisplayed = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-22 10:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Field_ID = 583087
;

-- ===========================================================================
-- 2. TaxID — insert AD_UI_Element in group 542729, SeqNo 130
-- ===========================================================================
-- 2026-06-22 10:00:01
INSERT INTO AD_UI_Element
    (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID,
     AD_UI_ElementType,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (652361 /*From ID Server*/, 0, 0,
     541852, 583087, 542729,
     'F',
     'Steuernummer', 130, 0, 0,
     'Y', 'N', 'N',
     'Y', 'N', 'N', 'N', 0,
     TO_TIMESTAMP('2026-06-22 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-22 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ===========================================================================
-- 3. CommercialRegisterNumber (Handelsregisternr) — insert AD_Field on tab 541852
--    AD_Column 583367, AD_Element 581038
-- ===========================================================================
-- 2026-06-22 10:00:02
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID,
     AD_Tab_ID, AD_Column_ID,
     Name, EntityType,
     IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
     SeqNo, SeqNoGrid,
     ColumnDisplayLength, DisplayLength,
     IncludedTabHeight, SortNo, SpanX, SpanY,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (781243 /*From ID Server*/, 0, 0,
     541852, 583367,
     'Handelsregisternr', 'U',
     'Y', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N',
     325, 0,
     0, 0,
     0, 0, 1, 1,
     TO_TIMESTAMP('2026-06-22 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-22 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- AD_Field_Trl (seed for all non-base system languages)
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-22 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-22 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 781243 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Field_ID = t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(581038);

/* DDL */ DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781243 /*From ID Server*/;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781243 /*From ID Server*/);

-- Fix IsTranslated='Y' for CommercialRegisterNumber AD_Field_Trl rows
-- (update_FieldTranslation_From_AD_Name_Element seeds the text but leaves IsTranslated='N')
-- 2026-06-22 10:00:04
UPDATE AD_Field_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-06-22 10:00:04','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Field_ID = 781243 /*From ID Server*/
  AND  AD_Language IN ('de_DE','de_CH','en_US')
;

-- ===========================================================================
-- 4. CommercialRegisterNumber — insert AD_UI_Element in group 542729, SeqNo 140
-- ===========================================================================
-- 2026-06-22 10:00:03
INSERT INTO AD_UI_Element
    (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID,
     AD_UI_ElementType,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (652362 /*From ID Server*/, 0, 0,
     541852, 781243 /*From ID Server*/, 542729,
     'F',
     'Handelsregisternr', 140, 0, 0,
     'Y', 'N', 'N',
     'Y', 'N', 'N', 'N', 0,
     TO_TIMESTAMP('2026-06-22 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-22 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100)
;
