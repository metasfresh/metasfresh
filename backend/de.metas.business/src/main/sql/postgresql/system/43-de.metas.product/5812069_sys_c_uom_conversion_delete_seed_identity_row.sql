-- Fixes failure of: 5812070_sys_c_uom_conversion_no_self_conversion_and_generic_uq.sql
--
-- The standard metasfresh seed ships one generic identity C_UOM_Conversion row:
-- C_UOM_Conversion_ID = 540011 (client 1000000, M_Product_ID NULL, non-catch), originally
-- inserted by 5528471_cli_gh5384-app_migrate_uoms.sql as (C_UOM_ID=1000000 -> C_UOM_To_ID=540017);
-- the later consolidation of the deprecated client-level KGM (1000000) into the system KGM
-- (540017) left it as 540017 -> 540017, i.e. an identity conversion. It is dead data: identity
-- conversions are served by a code short-circuit and never read from the conversion map.
--
-- The guard migration 5812070 fails loud on ANY identity row (so the CHECK can be created and
-- the product API can no longer crash). That guard therefore aborts on every clean seed / fresh
-- instance because of this one seed row -> db-apply-migrations goes red. This script removes
-- precisely that seed row, immediately before the guard runs, so the guard applies cleanly
-- everywhere. It does NOT touch customer-specific dirty rows: any OTHER identity row (or active
-- duplicate pair) still makes 5812070 fail loud, so per-instance cleanup stays a manual decision.
--
-- Non-AD data table -> backup first. Idempotent / re-run-safe (0 rows on a clean instance).

SELECT backup_table('c_uom_conversion', '_seed_identity_cleanup');

-- Precise delete: only the known seed row, and only while it is still the dead generic,
-- non-catch identity conversion. Any of the guards failing (row repurposed, made a real
-- conversion, turned into a catch row) makes this a safe no-op.
DELETE FROM c_uom_conversion
WHERE c_uom_conversion_id = 540011
  AND c_uom_id = c_uom_to_id
  AND m_product_id IS NULL
  AND iscatchuomforproduct = 'N';
