-- Backfill the mandatory post-INSERT AD_Field sequence omitted by 5804820 for the auto-distribution
-- fields on the BASE Warehouse window (AD_Window 139 / AD_Tab 177).
--
-- The merged base script 5804820_sys_AD_Window_139_M_Warehouse_auto_distribution_fields.sql created
-- AD_Field 780493 (IsAutoDistributionOrder) and AD_Field 780494 (DD_NetworkDistribution_ID) plus their
-- skeleton AD_Field_Trl rows and ran update_TRL_Tables_On_AD_Element_TRL_Update, but did NOT run the two
-- remaining mandatory steps required for every new AD_Field (see metasfresh-application-dictionary skill,
-- "Mandatory post-INSERT sequence for every new AD_Field"):
--   2. update_FieldTranslation_From_AD_Name_Element(<element_id>)  -- propagate element translations → field
--   3. DELETE AD_Element_Link + AD_Element_Link_Create_Missing_Field(<field_id>)  -- rebuild element links
--
-- Both fields have AD_Name_ID IS NULL, so the function is invoked with the COLUMN's AD_Element_ID
-- (standard via-AD_Column path). Translations are already propagated by the base script, so the trailing
-- update_TRL_Tables_On_AD_Element_TRL_Update call is not repeated here.
--
-- PR:    https://github.com/metasfresh/metasfresh/pull/24264 (base feature, already merged)
--
-- IDs allocated from idserver.metas.de on 2026-06-04:
--   AD_MigrationScript   5806350 (migration script prefix)
-- No new AD records are created by this script (only function calls on existing fields).

-- =============================================================================
-- IsAutoDistributionOrder (AD_Field 780493)
-- =============================================================================
SELECT update_FieldTranslation_From_AD_Name_Element((SELECT AD_Element_ID FROM AD_Element WHERE ColumnName='IsAutoDistributionOrder'));
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780493;
SELECT AD_Element_Link_Create_Missing_Field(780493);

-- =============================================================================
-- DD_NetworkDistribution_ID (AD_Field 780494)
-- =============================================================================
SELECT update_FieldTranslation_From_AD_Name_Element((SELECT AD_Element_ID FROM AD_Element WHERE ColumnName='DD_NetworkDistribution_ID'));
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780494;
SELECT AD_Element_Link_Create_Missing_Field(780494);
