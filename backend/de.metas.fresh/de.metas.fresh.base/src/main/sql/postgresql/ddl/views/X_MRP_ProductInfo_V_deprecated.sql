
/*
 * IMPORTANT: This view has been deprecated. Only use for QA / regression testing.
 * 
 * Note that we need to sum the fresh_QtyOnHand_Line records, because of different ASIs there can be more than one record per date and product
 */
DROP VIEW IF EXISTS X_MRP_ProductInfo_V;
DROP VIEW IF EXISTS X_MRP_ProductInfo_V_deprecated;
CREATE OR REPLACE VIEW X_MRP_ProductInfo_V_deprecated AS
   SELECT hu_s.ad_client_id, hu_s.ad_org_id, hu_s.m_product_id, hu_s.name, hu_s.qtyreserved, hu_s.qtyordered, hu_s.qtyonhand, hu_s.qtyavailable, hu_s.value, hu_s.ispurchased, hu_s.issold, hu_s.m_product_category_id, 
	hu_s.isactive, 
	COALESCE(ol_d.qtyreserved, 0::numeric) AS qtyreserved_ondate, COALESCE(ol_d.qtyordered, 0::numeric) AS qtyordered_ondate, 
	COALESCE(hu_s.qtyonhand + ol_d.qtypromised, 0::numeric) AS qtypromised, 
	COALESCE(hu_me_d.qtymaterialentnahme, 0::numeric) AS qtymaterialentnahme, 
	COALESCE(qoh_d.qty, 0::numeric) + sum(COALESCE(qohl.qtycount, 0::numeric)) - COALESCE(hu_me_d.qtymaterialentnahme, 0::numeric) AS fresh_qtyonhand_ondate, 
	sum(COALESCE(qohl_s.qtycount, 0::numeric)) AS "fresh_qtyonhand_ondate_stö2", 
	sum(COALESCE(qohl_i.qtycount, 0::numeric)) AS fresh_qtyonhand_ondate_ind9, 
	COALESCE(qoh_d.qty, 0::numeric) + sum(COALESCE(qohl.qtycount, 0::numeric)) - COALESCE(hu_me_d.qtymaterialentnahme, 0::numeric) + COALESCE(ol_d.qtypromised, 0::numeric) AS fresh_qtypromised, 
	sum(COALESCE(mrp.qty, 0::numeric)) AS fresh_qtymrp, 
	dates.currentDate AS DateGeneral
   FROM RV_HU_Quantities_Summary hu_s
	LEFT JOIN (
		SELECT (now() - interval '10 day')::Date as currentDate
		UNION ALL
		SELECT (now() - interval '9 day')::Date
		UNION ALL
		SELECT (now() - interval '8 day')::Date
		UNION ALL
		SELECT (now() - interval '7 day')::Date
		UNION ALL
		SELECT (now() - interval '6 day')::Date
		UNION ALL
		SELECT (now() - interval '5 day')::Date
		UNION ALL
		SELECT (now() - interval '4 day')::Date
		UNION ALL
 		SELECT (now() - interval '3 day')::Date
 		UNION ALL
		SELECT (now() - interval '2 day')::Date
		UNION ALL
		SELECT (now() - interval '1 day')::Date
		UNION ALL
		SELECT (now() + interval '0 day')::Date
		UNION ALL
		SELECT (now() + interval '1 day')::Date
		UNION ALL
		SELECT (now() + interval '2 day')::Date
		UNION ALL
		SELECT (now() + interval '3 day')::Date
		UNION ALL
		SELECT (now() + interval '4 day')::Date
	) dates ON true
   LEFT JOIN rv_c_orderline_qtyorderedreservedpromised_ondate_v ol_d ON ol_d.m_product_id = hu_s.m_product_id AND ol_d.DateGeneral::date=dates.currentDate
   LEFT JOIN rv_hu_qtymaterialentnahme_ondate hu_me_d ON hu_me_d.m_product_id = hu_s.m_product_id AND hu_me_d.updated_date = dates.currentDate
   LEFT JOIN x_fresh_qtyonhand_ondate qoh_d ON qoh_d.movementdate = dates.currentDate AND qoh_d.m_product_id = hu_s.m_product_id
   LEFT JOIN fresh_qtyonhand qoh ON qoh.datedoc = dates.currentDate AND qoh.Processed='Y'
		LEFT JOIN fresh_qtyonhand_line qohl ON qoh.fresh_qtyonhand_id = qohl.fresh_qtyonhand_id AND qohl.m_product_id = hu_s.m_product_id
   LEFT JOIN fresh_qtyonhand qoh_s ON qoh_s.datedoc = dates.currentDate AND qoh_s.Processed='Y'
   		LEFT JOIN fresh_qtyonhand_line qohl_s ON qoh_s.fresh_qtyonhand_id = qohl_s.fresh_qtyonhand_id
   			AND qohl_s.m_product_id = hu_s.m_product_id
   			AND qohl_s.pp_plant_id = 1000001
   LEFT JOIN fresh_qtyonhand qoh_i ON qoh_i.datedoc = dates.currentDate AND qoh_i.Processed='Y'
	   	LEFT JOIN fresh_qtyonhand_line qohl_i ON qoh_i.fresh_qtyonhand_id = qohl_i.fresh_qtyonhand_id 
	   		AND qohl_i.m_product_id = hu_s.m_product_id 
	   		AND qohl_i.pp_plant_id = 1000002
   LEFT JOIN pp_mrp mrp ON mrp.isactive = 'Y'::bpchar 
   		AND mrp.typemrp = 'D'::bpchar 
   		AND mrp.ordertype::text = 'MOP'::text 
   		AND mrp.docstatus::text = 'CO'::text 
   		AND mrp.m_product_id = hu_s.m_product_id 
   		AND trunc(mrp.datepromised, 'DD'::character varying) = trunc(dates.currentDate)
  WHERE true
GROUP BY hu_s.m_product_id, hu_s.name, hu_s.qtyreserved, hu_s.qtyordered, hu_s.qtyonhand, hu_s.qtyavailable, hu_s.value, hu_s.ispurchased, hu_s.issold, hu_s.m_product_category_id, hu_s.ad_client_id, hu_s.ad_org_id, hu_s.isactive, 
	ol_d.qtyreserved, ol_d.qtyordered, ol_d.qtypromised, 
	hu_me_d.qtymaterialentnahme, 
	qoh_d.qty, 
	dates.currentDate;

COMMENT ON VIEW X_MRP_ProductInfo_V_deprecated IS 'IMPORTANT: This view has been deprecated as of task 08681. Only use for QA / regression testing.';
