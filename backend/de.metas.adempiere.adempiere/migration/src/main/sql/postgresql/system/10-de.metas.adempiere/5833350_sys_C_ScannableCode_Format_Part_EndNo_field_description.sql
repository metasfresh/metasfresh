-- C_ScannableCode_Format_Part.EndNo is optional: tell the user what an empty End No means.
-- Per-field description on window field 742020 only, via its own AD_Element + AD_Field.AD_Name_ID
-- (the shared AD_Element 1318 "End No", also used by AD_ImpFormat_Row, stays untouched).

-- 1. Field first, with an EARLIER timestamp than the element (propagation guard: f.updated <> e_trl.updated)
UPDATE AD_Field
SET AD_Name_ID = 585594,
    Updated    = TO_TIMESTAMP('2026-11-05 19:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy  = 100
WHERE AD_Field_ID = 742020 AND AD_Name_ID IS NULL;

-- 2. Name-only element (ColumnName NULL)
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585594, 0, NULL, TO_TIMESTAMP('2026-11-05 19:30:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Leer lassen: bis zum Ende des Codes bzw. bis zur Position vor dem nächsten Teil lesen.', 'D', 'Y',
        'End-Nr.', 'End-Nr.', TO_TIMESTAMP('2026-11-05 19:30:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100);

-- 3. Translation skeletons (all system languages incl. the base language)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585594
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- 4. Translations
UPDATE AD_Element_Trl SET Description = 'Leer lassen: bis zum Ende des Codes bzw. bis zur Position vor dem nächsten Teil lesen.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-05 19:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Element_ID = 585594 AND AD_Language IN ('de_DE', 'de_CH');
UPDATE AD_Element_Trl SET Name = 'End No', PrintName = 'End No', Description = 'Leave empty: read to the end of the code, or up to the position before the next part.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-05 19:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Element_ID = 585594 AND AD_Language = 'en_US';
UPDATE AD_Element_Trl SET Name = 'Fin no.', PrintName = 'Fin no.', Description = 'Laisser vide : lire jusqu''à la fin du code, ou jusqu''à la position avant la partie suivante.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-05 19:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Element_ID = 585594 AND AD_Language = 'fr_CH';

-- 5. Propagate to AD_Field / AD_Field_Trl
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585594, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585594, 'en_US');
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585594);

-- 6. Base-language description on the field itself (not propagated from the element)
UPDATE AD_Field SET Description = 'Leer lassen: bis zum Ende des Codes bzw. bis zur Position vor dem nächsten Teil lesen.', Updated = TO_TIMESTAMP('2026-11-05 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 742020;

-- 7. Recreate the element link of the field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 742020;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(742020);
