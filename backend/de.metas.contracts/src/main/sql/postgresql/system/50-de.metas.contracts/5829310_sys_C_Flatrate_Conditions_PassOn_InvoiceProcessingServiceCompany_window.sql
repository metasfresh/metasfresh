-- Window "Vertragsbedingungen" (AD_Window_ID=540113), tab "Bedingungen" (540331): read-only field
-- "Servicegebühr übernehmen von" below the compensation group contract settings (form only, not in the grid).
-- Shown only for compensation group contracts (like the neighbouring settings field) and with its own description:
-- the text of the settings column's element speaks about purchase orders and would mislead here, so the field
-- gets a dedicated element (AD_Name_ID); the settings window keeps its description.
-- ============================================================================
-- 1) Dedicated AD_Element for the field label/description
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585539 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-13 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'PassOn_InvoiceProcessingServiceCompany_Conditions', 'de.metas.contracts', 'Servicegebühr übernehmen von', 'Servicegebühr übernehmen von',
        'Wird aus den Einstellungen des Kompensationsgruppen-Vertrags übernommen (nur Anzeige).')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585539, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585539
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Pass on service fee of', PrintName = 'Pass on service fee of',
    Description = 'Taken from the settings of the compensation group contract (display only).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585539 AND AD_Language = 'en_US'
;
-- ============================================================================
-- 2) AD_Field (label and description come from the dedicated element 585539)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, DisplayLogic, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593737, 785685 /*From ID Server*/, 585539, 0, 540331, TO_TIMESTAMP('2026-10-13 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, '@Type_Conditions/''''@=''CompensationGroup''', 'de.metas.contracts', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Servicegebühr übernehmen von', TO_TIMESTAMP('2026-10-13 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785685
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585539 /*dedicated element of the field*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785685
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785685)
;
-- ============================================================================
-- 3) AD_UI_Element in the existing default group (540760), after the contract settings (SeqNo 90)
-- ============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785685, 0, 540331, 540760, 655013 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-13 14:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Servicegebühr übernehmen von', 100, 0, 0, TO_TIMESTAMP('2026-10-13 14:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
