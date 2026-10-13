-- Field "Servicegebühr übernehmen von" on the contract conditions: shown only for compensation group contracts (like the
-- neighbouring settings field) and with its own description (the shared element text speaks about purchase orders and would
-- mislead here). Own element so that the settings window keeps its description.
-- ============================================================================
-- 1) Dedicated AD_Element for the field label/description
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585539 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 15:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-13 15:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
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
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 15:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585539 AND AD_Language = 'en_US'
;
-- de_DE / de_CH: text is already the German base text (no ß in it)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 15:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585539 AND AD_Language IN ('de_DE', 'de_CH')
;
-- ============================================================================
-- 2) Field: own element + display logic
-- ============================================================================
UPDATE AD_Field
SET AD_Name_ID = 585539, DisplayLogic = '@Type_Conditions/''''@=''CompensationGroup''',
    Updated = TO_TIMESTAMP('2026-10-13 15:01:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785685
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585539)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585539)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785685
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785685)
;
