-- #############################################################################
-- Migration: rename ExternalSystem_Endpoint.OutboundHttpEP -> HttpEndPoint and
-- relabel it to reflect that the HTTP endpoint serves BOTH directions (inbound
-- REST import + outbound export), not outbound-only. Previously labelled
-- "Ausgehender HTTP-Endpunkt" (outbound HTTP endpoint).
--
-- Only the ExternalSystem_Endpoint column is renamed. The identically-named column
-- on ExternalSystem_Config_ScriptedExportConversion (a separate, legacy column that
-- shares the old AD_Element 584104) is intentionally NOT touched here — so we FORK a
-- new AD_Element for the endpoint field rather than mutate the shared 584104.
--
-- IDs from idserver.metas.de:  AD_Element 585109 (new bidirectional label).
-- #############################################################################

-- ============================================================================
-- 1. New AD_Element for the renamed, bidirectional column.
-- ============================================================================

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (585109 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-16 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	'HttpEndPoint', 'HTTP-Endpunkt', 'HTTP-Endpunkt',
	'HTTP endpoint URL. Used for both inbound (REST import) and outbound (export) transport.',
	'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585109
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'HTTP-Endpunkt', PrintName = 'HTTP-Endpunkt', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585109 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl SET Name = 'HTTP Endpoint', PrintName = 'HTTP Endpoint', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585109 AND AD_Language = 'en_US';

-- ============================================================================
-- 2. Point the endpoint column at the new element + rename the AD_Column, then
--    rename the physical column. (591478 is ExternalSystem_Endpoint.OutboundHttpEP.)
-- ============================================================================

UPDATE AD_Column
SET ColumnName = 'HttpEndPoint', AD_Element_ID = 585109,
    Description = 'HTTP endpoint URL. Used for both inbound (REST import) and outbound (export) transport.',
    Updated = TO_TIMESTAMP('2026-07-16 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 591478;

SELECT public.db_alter_table('ExternalSystem_Endpoint',
	'ALTER TABLE public.ExternalSystem_Endpoint RENAME COLUMN OutboundHttpEP TO HttpEndPoint');

-- ============================================================================
-- 3. Propagate the new element's name/description down to AD_Column(_Trl) and the
--    other element-derived AD_* tables. The endpoint column (591478) now points at
--    element 585109; without this the column keeps the old "Ausgehender HTTP-Endpunkt"
--    (outbound-only) text. update_TRL_Tables_On_AD_Element_TRL_Update propagates per
--    language present on the element.
--    RUN THIS BEFORE the explicit AD_Field/AD_UI_Element relabels in step 4, so those
--    explicit values win last — propagation reads AD_Element_Trl (fr_CH/it_CH untranslated
--    → IsTranslated='N') and, if run after step 4, would revert the field's fr_CH/it_CH
--    IsTranslated flag set there.
-- ============================================================================

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585109, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585109, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585109, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585109, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585109, 'it_CH');

-- ============================================================================
-- 4. Relabel the endpoint field + UI element (AD_Field 755940, AD_UI_Element 648567)
--    to the new bidirectional element / name. These explicit overrides run AFTER the
--    step-3 propagation so they are the final word on the field/UI-element labels.
-- ============================================================================

UPDATE AD_Field
SET AD_Name_ID = 585109, Name = 'HTTP-Endpunkt',
	Updated = TO_TIMESTAMP('2026-07-16 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 755940;

UPDATE AD_Field_Trl SET Name = 'HTTP-Endpunkt', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 755940 AND AD_Language IN ('de_DE', 'de_CH', 'fr_CH', 'it_CH');

UPDATE AD_Field_Trl SET Name = 'HTTP Endpoint', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 755940 AND AD_Language = 'en_US';

UPDATE AD_UI_Element
SET Name = 'HTTP-Endpunkt',
	Updated = TO_TIMESTAMP('2026-07-16 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 648567;
