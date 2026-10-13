-- Window "Kompensationsgruppen-Vertragseinstellungen" (AD_Window_ID=542194): header field + list column
-- "Servicegebühr übernehmen von", placed after the compensation group schema (form: SeqNo 25, grid: SeqNoGrid 25).
-- ============================================================================
-- 1) AD_Field (label and description come from the column's element 585538)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593735, 785684 /*From ID Server*/, 0, 549507, TO_TIMESTAMP('2026-10-13 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.contracts', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Servicegebühr übernehmen von', TO_TIMESTAMP('2026-10-13 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785684
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585538 /*PassOn_InvoiceProcessingServiceCompany_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785684
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785684)
;
-- ============================================================================
-- 2) AD_UI_Element in the existing default group (555794) of the header tab
-- ============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785684, 0, 549507, 555794, 655012 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-13 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Servicegebühr übernehmen von', 25, 25, 0, TO_TIMESTAMP('2026-10-13 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
