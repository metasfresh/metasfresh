-- VAT-ID online check: add a Description/help to RestApiBaseURL (AD_Field 781903) — the only
-- free-typed 400-char URL on the VATaxID_Config tab, previously shipped with no in-UI guidance.
-- The field's column-level AD_Element (576182) is shared with CS_Creditpass_Config.RestApiBaseURL
-- (a different REST service), so per the "mutate shared vs. fork dedicated" rule this description
-- must NOT be written onto the shared element. Instead: a new dedicated AD_Element is created and
-- wired via AD_Field.AD_Name_ID, overriding only this field's caption/description.

-- IDs allocated from idserver.metas.de:
--   AD_Element 585173

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         Name, PrintName, Description, EntityType)
VALUES (585173 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-11 16:45:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-11 16:45:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'REST API URL', 'REST API URL',
        'Basis-URL des VIES-REST-Dienstes für die USt-IdNr.-Prüfung. Muss vor der ersten Prüfung konfiguriert werden; es gibt keinen vorbelegten Wert.', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585173
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET Description = 'Base URL of the VIES REST service used for VAT-ID checks. Must be configured before the first check; there is no pre-filled value.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-11 16:45:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585173;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-11 16:45:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585173;

UPDATE AD_Field
SET AD_Name_ID = 585173, Updated = TO_TIMESTAMP('2026-08-11 16:45:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781903;

SELECT update_FieldTranslation_From_AD_Name_Element(585173);

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781903;
SELECT AD_Element_Link_Create_Missing_Field(781903);
