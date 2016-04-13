
--
-- these functions are obsolete
--
DROP FUNCTION IF EXISTS x_mrp_productinfo_attributeval_v(date);
DROP FUNCTION IF EXISTS x_mrp_productinfo_detail_fallback_v(date, date);
DROP FUNCTION IF EXISTS x_mrp_productinfo_detail_mv_refresh();
DROP FUNCTION IF EXISTS x_mrp_productinfo_detail_mv_refresh(date, date);
DROP FUNCTION IF EXISTS x_mrp_productinfo_detail_mv_refresh_workaround_until_20151102(date, numeric, numeric);
DROP FUNCTION IF EXISTS x_mrp_productinfo_detail_v(date, numeric, numeric);
DROP FUNCTION IF EXISTS x_mrp_productinfo_detail_v(date, date);

DROP FUNCTION IF EXISTS "de.metas.fresh".x_mrp_productinfo_detail_update_poor_mans_mrp(date, date);
