-- 2026-09-22: Task 7 — make C_TaxCategory.InternalName editable
-- IDs from idserver.metas.de:
--   AD_Element_ID 585483
--   Migration script ID 5825730

-- 1) a dedicated AD_Element, so this field's text survives the element sync
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (585483 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-22 11:15:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-22 11:15:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_TaxCategory_InternalName', 'Interner Name', 'Interner Name',
        'Technischer Name dieser Steuerkategorie, über den externe Systeme sie per API ansprechen.',
        'Wird von der REST-API verwendet (taxOverride.taxCategoryIdentifier, Form int-<InternalName>). Muss eindeutig sein. Eine Änderung kann bestehende API-Aufrufe brechen.',
        'D')
-- conflict target named on purpose: AD_Element also carries a UNIQUE index on ColumnName, and a bare
-- ON CONFLICT DO NOTHING would swallow a ColumnName collision as a silent INSERT 0 0 - the element
-- would be missing while section 3 still points AD_Field.AD_Name_ID at it. Naming the PK keeps the
-- create-once guard (this file is renamed in the same commit, which changes its AD_MigrationScript
-- identity and makes it re-run on any DB that recorded the old name) and lets a ColumnName collision
-- fail loudly.
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- 2) make the field editable (it is read-only today)
UPDATE AD_Column SET ReadOnlyLogic = NULL, Updated = TO_TIMESTAMP('2026-09-22 11:15:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Column_ID = 573228;
UPDATE AD_Field  SET IsReadOnly = 'N',     Updated = TO_TIMESTAMP('2026-09-22 11:15:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID  = 637521;

-- 3) point the field at the new element AND write the base-row text directly:
--    update_FieldTranslation_From_AD_Name_Element writes only Name + Description to AD_Field, never Help
UPDATE AD_Field SET AD_Name_ID = 585483 /*From ID Server*/,
                    Name = 'Interner Name',
                    Description = 'Technischer Name dieser Steuerkategorie, über den externe Systeme sie per API ansprechen.',
                    Help = 'Wird von der REST-API verwendet (taxOverride.taxCategoryIdentifier, Form int-<InternalName>). Muss eindeutig sein. Eine Änderung kann bestehende API-Aufrufe brechen.',
                    Updated = TO_TIMESTAMP('2026-09-22 11:15:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 637521;

-- 4) the UI element's own text (no sync function touches AD_UI_Element)
UPDATE AD_UI_Element SET Name = 'Interner Name',
                         Description = 'Technischer Name dieser Steuerkategorie, über den externe Systeme sie per API ansprechen.',
                         Help = 'Wird von der REST-API verwendet (taxOverride.taxCategoryIdentifier, Form int-<InternalName>). Muss eindeutig sein. Eine Änderung kann bestehende API-Aufrufe brechen.',
                         Updated = TO_TIMESTAMP('2026-09-22 11:15:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 580492;

-- 5) translations for the new element - every active system language AND the base language.
--    de_DE IS the base language on these instances, so the stock IsBaseLanguage='N' filter would
--    leave the element without a de_DE row: nothing would then overwrite the stale de_DE
--    AD_Field_Trl row that 5582810 wrote in 2021 ("make the field read-only ..."), which is the
--    exact opposite of what this script does. IsTranslated='N' is correct for the base-language
--    row - it carries the base text, it is not a translation of it.
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=585483 /*From ID Server*/
  AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- 6) English override for en_US language.
--    IsTranslated='Y': the seed above copied the German base text with 'N'; this row now carries
--    real English, and 'N' would let the English caption fall back to the German base name.
UPDATE AD_Element_Trl
SET Name = 'Internal Name',
    PrintName = 'Internal Name',
    Description = 'Technical name of this tax category, used by external systems to address it through the API.',
    Help = 'Used by the REST API (taxOverride.taxCategoryIdentifier, form int-<InternalName>). Must be unique. Changing it can break existing API calls.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-22 11:15:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 585483 AND AD_Language = 'en_US';

-- 7) propagate the element's translations down (AD_Element_Trl -> AD_Field_Trl, incl. IsTranslated)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585483 /*From ID Server*/);
