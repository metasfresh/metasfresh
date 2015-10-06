
DROP FUNCTION IF EXISTS refresh_X_MRP_ProductInfo_MV_aux_temp(timestamp with time zone);
CREATE OR REPLACE FUNCTION refresh_X_MRP_ProductInfo_MV_aux_temp(p_DateGeneral timestamp with time zone)
  RETURNS void AS
$BODY$
BEGIN
/*
 * Now, for those MV records that have a date component, but *not* with the current date, create additional records which the date-based values zero, and the current date.
 */
INSERT INTO X_MRP_ProductInfo_MV
SELECT DISTINCT 
	ad_client_id, ad_org_id, m_product_id, name, qtyreserved, qtyordered, qtyonhand, qtyavailable, value, ispurchased, issold, m_product_category_id, isactive,
	0.0, 
	0.0,
	0.0,
	0.0, /* MaterialEntnahme */
	0.0, /* Zählbestand */
	0.0, /* Zählbestand Stöckenteilen */
	0.0, /* Zählbestand Industriestrasse */
	0.0, /* Zusagbar Zählbestand */
	0.0, /* MRP Menge */
	p_DateGeneral::Date
FROM X_MRP_ProductInfo_MV mv
WHERE true
	and mv.IsActive='Y'
	and NOT EXISTS (
		select 1 
		from X_MRP_ProductInfo_MV mv2 
		where mv2.DateGeneral=p_DateGeneral::Date
			and mv2.ad_client_id=mv.ad_client_id AND mv2.ad_org_id=mv.ad_org_id AND mv2.m_product_id=mv.m_product_id
			---, name, qtyreserved, qtyordered, qtyonhand, qtyavailable, value, ispurchased, issold, m_product_category_id, 
			AND mv2.isactive=mv.IsActive
		)
--	and value='P001106'
;
RETURN;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
COMMENT ON FUNCTION refresh_x_mrp_productinfo_mv_aux_temp(timestamp with time zone)
  IS 'Task 08329: helper function that re-inserts records into X_MRP_ProductInfo_MV, if there are existing records with another date, but no respective record with the given date. This function is called from refresh_X_MRP_ProductInfo_MV.';
