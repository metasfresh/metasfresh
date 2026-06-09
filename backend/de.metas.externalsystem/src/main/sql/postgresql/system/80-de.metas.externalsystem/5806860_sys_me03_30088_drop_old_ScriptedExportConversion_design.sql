-- me03 30088: R1.2 — Drop superseded old-design DB objects for ScriptedExportConversion.
-- Removes artifacts introduced before the re-architecture to ExternalSystem_ScriptedExportConversion_Status:
--
--   1. Log table ExternalSystem_ScriptedExportConversion_Log (AD_Table 542614, AD_Columns 592734..592750)
--      including its child tab (AD_Tab 549292) in window 169, all AD_Fields (780729..780736)
--      and AD_UI_Elements (652024..652031), UI layout (section 547811, column 549543, group 555430),
--      plus AD_Element 584960 (tab caption).
--      Log-specific AD_Element 584953 (PK) is dropped; 584954 was never inserted.
--      Shared elements 584955/584956/584957 (StatusMessage/HttpResponseCode/IsResend) are KEPT —
--      R1.1's new Status table reuses them.
--
--   2. EPCIS_ExportStatus field from Shipment (M_InOut) header tab 257:
--      AD_Field 780728, AD_UI_Element 652023, AD_Column 592752, physical M_InOut.EPCIS_ExportStatus.
--      AD_Element 584959 (EPCIS_ExportStatus) is KEPT — R1.3 reuses it for a virtual column.
--
--   3. Config column ExternalSystem_Config_ScriptedExportConversion.Status_AD_Column_ID:
--      AD_UI_Element 652022, AD_Field 780727, AD_Column 592751, AD_Element 584958, AD_Val_Rule 540790.
--
-- NOTE on ordering: the config table has an FK column (Status_AD_Column_ID → AD_Column 592751)
-- and the physical column must be dropped BEFORE any AD_Column rows are deleted, to avoid
-- FK violations when the DB enforces the constraint. Likewise the physical log table references
-- AD_Column 592752 via its Status_AD_Column_ID FK, so the config physical column is dropped first.

-- ============================================================================
-- PART 1: Tear down the log child tab (549292) in window 169
-- Teardown order: AD_UI_Element → AD_Element_Link → AD_Field_Trl → AD_Field
--                 → AD_UI_ElementGroup → AD_UI_Column → AD_UI_Section_Trl
--                 → AD_UI_Section → AD_Tab_Trl → AD_Tab → AD_Element (caption)
-- ============================================================================

-- 1.1 AD_UI_Elements for all log tab fields (652024..652031)
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID IN (652024,652025,652026,652027,652028,652029,652030,652031);

-- 1.2 AD_Element_Links for all log tab fields
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (780729,780730,780731,780732,780733,780734,780735,780736);

-- 1.3 AD_Field_Trl for all log tab fields
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (780729,780730,780731,780732,780733,780734,780735,780736);

-- 1.4 AD_Fields for all log tab fields
DELETE FROM AD_Field WHERE AD_Field_ID IN (780729,780730,780731,780732,780733,780734,780735,780736);

-- 1.5 UI layout: element group → column → section_trl → section
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=555430;
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=549543;
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID=547811;
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=547811;

-- 1.6 AD_Tab_Trl + AD_Tab
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID=549292;
DELETE FROM AD_Tab WHERE AD_Tab_ID=549292;

-- 1.7 Tab caption element (584960 — only used as the tab caption, not by any column)
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=584960;
DELETE FROM AD_Element WHERE AD_Element_ID=584960;

-- ============================================================================
-- PART 2: Drop the EPCIS_ExportStatus field from the Shipment (M_InOut) header tab 257
-- Teardown order: AD_UI_Element → AD_Element_Link → AD_Field_Trl → AD_Field
-- AD_Column 592752 and physical column dropped in Part 3 (after physical FK from config removed)
-- AD_Element 584959 is intentionally KEPT — R1.3 reuses it.
-- ============================================================================

-- 2.1 AD_UI_Element (652023 — in header tab element group 1000029)
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=652023;

-- 2.2 AD_Element_Link
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780728;

-- 2.3 AD_Field_Trl
DELETE FROM AD_Field_Trl WHERE AD_Field_ID=780728;

-- 2.4 AD_Field
DELETE FROM AD_Field WHERE AD_Field_ID=780728;

-- ============================================================================
-- PART 3: Drop config column Status_AD_Column_ID — AD metadata + physical column FIRST
-- Must drop physical FK column before any AD_Column rows are deleted to avoid
-- the FK constraint on externalsystem_config_scriptedexportconversion.Status_AD_Column_ID
-- → AD_Column(ad_column_id) blocking the DELETE on AD_Column 592751/592752.
-- Teardown order: AD_UI_Element → AD_Element_Link → AD_Field_Trl → AD_Field
--                 → AD_Column_Trl → AD_Column → AD_Element → AD_Val_Rule
--                 → physical column (DROP COLUMN before any AD_Column deletes below)
-- ============================================================================

-- 3.1 AD_UI_Element (652022 — pairs the field in config tab 548471)
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=652022;

-- 3.2 AD_Element_Link for the config field
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780727;

-- 3.3 AD_Field_Trl
DELETE FROM AD_Field_Trl WHERE AD_Field_ID=780727;

-- 3.4 AD_Field
DELETE FROM AD_Field WHERE AD_Field_ID=780727;

-- 3.5 AD_Column_Trl for Status_AD_Column_ID
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=592751;

-- 3.6 Physical column + FK constraint — MUST happen before AD_Column 592751 is deleted
SELECT public.backup_table('externalsystem_config_scriptedexportconversion', '_me03_30088_r12');
ALTER TABLE ExternalSystem_Config_ScriptedExportConversion
    DROP CONSTRAINT IF EXISTS adcolumn_externalsystemconfigscriptedexportconversion;
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedExportConversion',
    'ALTER TABLE public.ExternalSystem_Config_ScriptedExportConversion DROP COLUMN IF EXISTS Status_AD_Column_ID');

-- 3.7 AD_Column (now safe — physical column gone)
DELETE FROM AD_Column WHERE AD_Column_ID=592751;

-- 3.8 AD_Element_Trl + AD_Element for Status_AD_Column_ID (config-specific, not shared)
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=584958;
DELETE FROM AD_Element WHERE AD_Element_ID=584958;

-- 3.9 AD_Val_Rule (restricts the column picker to the config row's own target table)
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540790;

-- ============================================================================
-- PART 4: Drop M_InOut.EPCIS_ExportStatus physical column + AD_Column 592752
-- (AD_Field 780728 already removed in Part 2; physical column drop here)
-- AD_Element 584959 is intentionally KEPT — R1.3 reuses it.
-- ============================================================================

-- 4.1 AD_Column_Trl for M_InOut.EPCIS_ExportStatus
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=592752;

-- 4.2 Physical column + check constraint
SELECT public.backup_table('m_inout', '_me03_30088_r12_epcisexport');
ALTER TABLE M_InOut DROP CONSTRAINT IF EXISTS EPCIS_ExportStatus_Check;
/* DDL */ SELECT public.db_alter_table('M_InOut',
    'ALTER TABLE public.M_InOut DROP COLUMN IF EXISTS EPCIS_ExportStatus');

-- 4.3 AD_Column (now safe — physical column gone)
DELETE FROM AD_Column WHERE AD_Column_ID=592752;

-- ============================================================================
-- PART 5: Drop log table AD_Columns (592734..592750), AD_Table 542614, physical table
-- (AD_Fields referencing these columns were all removed in Part 1)
-- (Config physical FK column already dropped in Part 3 — no more FK violations)
-- Teardown order: AD_Column_Trl → AD_Column → AD_Table_Trl → AD_Table → physical table → AD_Element
-- ============================================================================

-- 5.1 AD_Column_Trl for all log table columns
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (592734,592735,592736,592737,592738,592739,592740,592741,592742,592743,592744,592745,592746,592747,592748,592749,592750);

-- 5.2 AD_Columns
DELETE FROM AD_Column WHERE AD_Table_ID=542614;

-- 5.3 AD_Table_Trl + AD_Table
DELETE FROM AD_Table_Trl WHERE AD_Table_ID=542614;
DELETE FROM AD_Table WHERE AD_Table_ID=542614;

-- 5.4 Physical table (CASCADE removes FK + PK constraints)
SELECT public.backup_table('externalsystem_scriptedexportconversion_log', '_me03_30088_r12');
DROP TABLE IF EXISTS public.ExternalSystem_ScriptedExportConversion_Log CASCADE;

-- 5.5 Log-specific AD_Element 584953 (PK element — only referenced by the now-dropped log column)
-- (584954 was allocated but never inserted into AD_Element — nothing to drop)
-- Shared elements 584955/584956/584957 are intentionally KEPT.
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=584953;
DELETE FROM AD_Element WHERE AD_Element_ID=584953;
