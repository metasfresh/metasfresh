-- #############################################################################
-- Migration: make the endpoint FK consistent across all four scripted-conversion
-- tabs. The scripted IMPORT endpoint FK column uses reference Table Direct (19);
-- the scripted EXPORT endpoint FK column used Search (30) with no AD_Reference_Value_ID
-- (an incomplete Search config). Standardise the export column onto Table Direct (19)
-- too — the same self-contained FK-to-ExternalSystem_Endpoint picker the import uses
-- (both FKs already mandatory). Also relabel the export endpoint fields to "Endpunkt"
-- (matching the import), reusing the dedicated AD_Element created for the import fields.
--
-- After this, all four scripted-conversion tabs (import 548472/548473, export
-- 548464/548471) present one consistent mandatory "Endpunkt" FK picker.
-- #############################################################################

-- ============================================================================
-- 1. Standardise the export endpoint FK reference type: Search(30) -> Table Direct(19),
--    matching the import column (592250). Table Direct needs no AD_Reference_Value_ID;
--    the target table (ExternalSystem_Endpoint) is inferred from the column name.
--    The export FK is already mandatory (IsMandatory='Y') and the sole existing export
--    config already has an endpoint, so no data migration is required.
-- ============================================================================

UPDATE AD_Column
SET AD_Reference_ID = 19,
    Updated = TO_TIMESTAMP('2026-07-15 18:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 591483;

-- ============================================================================
-- 2. Relabel the export endpoint fields (755945 on tab 548464, 755944 on tab 548471)
--    to "Endpunkt", pointing them at the dedicated AD_Element 585107 already created
--    for the import fields (fork, not shared-element mutation). Replaces the previous
--    "Ausgehender HTTP-Endpunkt" / "Externer System-Ausgangsendpunkt" labels.
-- ============================================================================

UPDATE AD_Field
SET AD_Name_ID = 585107, Name = 'Endpunkt',
    Updated = TO_TIMESTAMP('2026-07-15 18:30:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (755944, 755945);

UPDATE AD_Field_Trl
SET Name = 'Endpunkt', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-15 18:30:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (755944, 755945) AND AD_Language IN ('de_DE', 'de_CH', 'fr_CH', 'it_CH');

UPDATE AD_Field_Trl
SET Name = 'Endpoint', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-07-15 18:30:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (755944, 755945) AND AD_Language = 'en_US';

-- ============================================================================
-- 3. Align the cached UI-element names on the export tabs (638552 on 548464,
--    638551 on 548471) to "Endpunkt".
-- ============================================================================

UPDATE AD_UI_Element
SET Name = 'Endpunkt',
    Updated = TO_TIMESTAMP('2026-07-15 18:30:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID IN (638551, 638552);
