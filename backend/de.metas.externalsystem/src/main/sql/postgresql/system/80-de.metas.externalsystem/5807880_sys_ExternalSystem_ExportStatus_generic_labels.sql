-- Generic-naming: rename M_InOut export-status tab caption + column label from EPCIS-specific to generic 'Exportstatus'/'Export Status'. Labels only; column identifiers unchanged.

-- ===========================================================================
-- AD_Element 584966 (ColumnName=EPCIS_ExportStatus_Tab, tab caption)
-- AD_Element 584959 (ColumnName=EPCIS_ExportStatus, header virtual column label)
-- Both: Name/PrintName DE base -> 'Exportstatus'; en_US -> 'Export Status'.
-- ColumnName identifiers are NOT changed.
-- ===========================================================================

-- 1) Update AD_Element base rows (German base language)
UPDATE AD_Element
SET Name      = 'Exportstatus',
    PrintName = 'Exportstatus',
    Updated   = TO_TIMESTAMP('2026-06-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 584966
;

UPDATE AD_Element
SET Name      = 'Exportstatus',
    PrintName = 'Exportstatus',
    Updated   = TO_TIMESTAMP('2026-06-15 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 584959
;

-- 2) Update AD_Element_Trl rows for 584966
-- en_US
UPDATE AD_Element_Trl
SET Name         = 'Export Status',
    PrintName    = 'Export Status',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 10:00:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584966
  AND AD_Language = 'en_US'
;

-- de_DE / de_CH (German is the base language → mark actively translated, per convention
-- and matching the Stage-1 state these rows had before this rename)
UPDATE AD_Element_Trl
SET Name         = 'Exportstatus',
    PrintName    = 'Exportstatus',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 10:00:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584966
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 3) Update AD_Element_Trl rows for 584959
-- en_US
UPDATE AD_Element_Trl
SET Name         = 'Export Status',
    PrintName    = 'Export Status',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 10:00:20', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584959
  AND AD_Language = 'en_US'
;

-- de_DE / de_CH (German is the base language → mark actively translated, per convention
-- and matching the Stage-1 state these rows had before this rename)
UPDATE AD_Element_Trl
SET Name         = 'Exportstatus',
    PrintName    = 'Exportstatus',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-15 10:00:21', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584959
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- fr_CH (adopts German word verbatim; IsTranslated='N' — no language-specific override)
UPDATE AD_Element_Trl
SET Name         = 'Exportstatus',
    PrintName    = 'Exportstatus',
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-06-15 10:00:25', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584966
  AND AD_Language = 'fr_CH'
;

UPDATE AD_Element_Trl
SET Name         = 'Exportstatus',
    PrintName    = 'Exportstatus',
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-06-15 10:00:26', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584959
  AND AD_Language = 'fr_CH'
;

-- 4) Propagate element translations -> AD_Tab_Trl / AD_Tab.Name (549295),
--    AD_Field_Trl / AD_Field.Name (780740), AD_Column_Trl / AD_Column.Name.
--    update_TRL_Tables_On_AD_Element_TRL_Update already delegates internally to
--    update_FieldTranslation_From_AD_Name_Element, so no separate call is needed.
--    The guard `updated <> e_trl.updated` passes because element timestamps
--    above (10:00:10/11, 10:00:20/21, 10:00:25/26) differ from the field/tab
--    timestamps set in the Stage-1 migration (2026-06-09).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584966);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584959);

-- 5) AD_UI_Element.Name is not covered by any propagation function.
--    Set directly for the one UI element that carries the EPCIS label.
--    652035 = M_InOut header tab UI element for EPCIS_ExportStatus field (780740)
UPDATE AD_UI_Element
SET Name      = 'Exportstatus',
    Updated   = TO_TIMESTAMP('2026-06-15 10:00:30', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 652035
;
