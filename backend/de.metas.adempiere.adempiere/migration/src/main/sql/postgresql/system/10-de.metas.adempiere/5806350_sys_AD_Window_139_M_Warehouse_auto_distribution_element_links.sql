-- Backfill the missing AD_Element_Link rows for the auto-distribution fields on the BASE Warehouse
-- window (AD_Window 139 / AD_Tab 177).
--
-- The merged base script 5804820_sys_AD_Window_139_M_Warehouse_auto_distribution_fields.sql created
-- AD_Field 780493 (IsAutoDistributionOrder) and AD_Field 780494 (DD_NetworkDistribution_ID) plus their
-- skeleton AD_Field_Trl rows and ran update_TRL_Tables_On_AD_Element_TRL_Update (which already propagated
-- the AD_Field_Trl translations for these centrally-maintained fields). What it did NOT run is the final
-- step of the mandatory post-INSERT sequence for every new AD_Field (see metasfresh-application-dictionary
-- skill, "Mandatory post-INSERT sequence for every new AD_Field"): rebuilding the AD_Element_Link rows.
-- That is the genuinely-missing step this script backfills:
--   DELETE AD_Element_Link + AD_Element_Link_Create_Missing_Field(<field_id>)  -- (re)create element links
--
-- The update_FieldTranslation_From_AD_Name_Element(<element_id>) call below is included only for
-- canonical-sequence completeness; both fields have AD_Name_ID IS NULL, so it runs the standard via-AD_Column
-- path with the COLUMN's AD_Element_ID. It is a no-op here (updates 0 rows) because 5804820's
-- update_TRL_Tables_On_AD_Element_TRL_Update call already propagated the field translations. The call is
-- idempotent and kept for clarity / consistency with the documented pattern.
--
-- PR:    https://github.com/metasfresh/metasfresh/pull/24264 (base feature, already merged)
--
-- IDs allocated from idserver.metas.de on 2026-06-04:
--   AD_MigrationScript   5806350 (migration script prefix)
-- This script creates no new AD_Field/AD_Column/etc. records, but it does (re)create AD_Element_Link rows
-- for the two existing fields via AD_Element_Link_Create_Missing_Field(...).

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
