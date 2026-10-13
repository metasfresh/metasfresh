-- Window "Vertragsbedingungen" (AD_Window_ID=540113), tab "Bedingungen" (540331): read-only field
-- "Servicegebühr übernehmen von" below the compensation group contract settings (form only, not in the grid).
-- ============================================================================
-- 1) AD_Field (label and description come from the column's element 585538)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593737, 785685 /*From ID Server*/, 0, 540331, TO_TIMESTAMP('2026-10-13 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.contracts', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Servicegebühr übernehmen von', TO_TIMESTAMP('2026-10-13 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785685
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585538 /*PassOn_InvoiceProcessingServiceCompany_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785685
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785685)
;
-- ============================================================================
-- 2) AD_UI_Element in the existing default group (540760), after the contract settings (SeqNo 90)
-- ============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785685, 0, 540331, 540760, 655013 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-13 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Servicegebühr übernehmen von', 100, 0, 0, TO_TIMESTAMP('2026-10-13 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
