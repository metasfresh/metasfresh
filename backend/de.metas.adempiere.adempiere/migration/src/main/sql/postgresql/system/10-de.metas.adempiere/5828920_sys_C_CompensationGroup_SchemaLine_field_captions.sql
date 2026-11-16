-- Window "Kompensationsgruppe Schema" (540415), tab "Kompensationszeilen" (541042, C_CompensationGroup_SchemaLine):
-- captions + descriptions that say what the condition fields do.
--   Type (field 563021):       "Bedingung" / "Condition"       - new dedicated element 585531 (column element 600 "Art" is shared)
--   BreakValue (field 563022): "Umsatz ab" / "Revenue From"    - new dedicated element 585532 (column element 1708
--                              "Mengenstufe" is shared and right for discount schema breaks)
--   C_Flatrate_Conditions_ID (field 563025): German caption unchanged, en_US "Flatrate Conditions" -> "Contract Conditions",
--                              description added; on its element 1002927, which is used by this field only
--
-- IDs allocated from idserver.metas.de on 2026-10-09:
--   AD_MigrationScript 5828920 (this script)
--   AD_Element 585531 (Bedingung), 585532 (Umsatz ab)

-- ============================================================================
-- 1) AD_Element "Bedingung"
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585531 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-09 17:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-09 17:05:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.order', 'Bedingung', 'Bedingung',
        'Leer: die Zeile gilt immer. Vertrag: die Zeile gilt, wenn die Gruppe Positionen mit den angegebenen Vertragsbedingungen enthält. Umsatzstaffel: die Zeile gilt, wenn der Nettobetrag der regulären Gruppenpositionen zwischen „Umsatz ab“ dieser Zeile und dem der nächsten Umsatzstaffel-Zeile liegt.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585531
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Condition', PrintName = 'Condition',
    Description = 'Empty: the line always applies. Contract: the line applies if the group contains lines with the given contract conditions. Revenue breaks: the line applies if the net amount of the group''s regular lines is between “Revenue From” of this line and that of the next revenue-break line.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 17:05:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585531 AND AD_Language = 'en_US'
;
-- de_DE / de_CH: the German base text is final (no ß in it)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 17:05:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585531 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ============================================================================
-- 2) AD_Element "Umsatz ab"
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585532 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-09 17:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-09 17:05:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.order', 'Umsatz ab', 'Umsatz ab',
        'Nettobetrag der regulären Gruppenpositionen, ab dem diese Zeile gilt (bis zum Wert der nächsten Umsatzstaffel-Zeile).')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585532
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Revenue From', PrintName = 'Revenue From',
    Description = 'Net amount of the group''s regular lines from which this line applies (up to the value of the next revenue-break line).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 17:05:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585532 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 17:05:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585532 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ============================================================================
-- 3) Element 1002927 (caption of field 563025 only): en_US caption + description
-- ============================================================================
UPDATE AD_Element_Trl
SET Description  = 'Nur bei Bedingung „Vertrag“: die Zeile gilt, wenn die Gruppe Positionen mit diesen Vertragsbedingungen enthält.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 17:05:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 1002927 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Element_Trl
SET Name         = 'Contract Conditions',
    PrintName    = 'Contract Conditions',
    Description  = 'Only for condition “Contract”: the line applies if the group contains lines with these contract conditions.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 17:05:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 1002927 AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(1002927, 'de_DE')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(1002927, 'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(1002927, 'en_US')
;

-- ============================================================================
-- 4) Fields Type / BreakValue: point AD_Name_ID at the new elements, propagate, rebuild element links
-- ============================================================================
UPDATE AD_Field
SET AD_Name_ID = 585531, Updated = TO_TIMESTAMP('2026-10-09 17:06:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 563021 /* C_CompensationGroup_SchemaLine.Type */
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585531)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 563021
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(563021)
;

UPDATE AD_Field
SET AD_Name_ID = 585532, Updated = TO_TIMESTAMP('2026-10-09 17:06:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 563022 /* C_CompensationGroup_SchemaLine.BreakValue */
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585532)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 563022
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(563022)
;
