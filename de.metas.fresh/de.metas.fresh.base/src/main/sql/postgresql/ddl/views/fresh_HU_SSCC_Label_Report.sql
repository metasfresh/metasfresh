--DROP VIEW IF EXISTS report.fresh_HU_SSCC_Label_Report;

CREATE OR REPLACE VIEW report.fresh_HU_SSCC_Label_Report as
SELECT
	/*
	 * NOTE to developer: please keep in sync with fresh_EDI_DesadvLine_SSCC_Label_Report
	 */
	 ( 
		SELECT  COALESCE(org_bp.name, '') || ', ' || COALESCE(org_l.Postal, '')|| ' '  || COALESCE(org_l.city, '')
			FROM AD_Org org

			-- INFO I NEED
			INNER JOIN AD_OrgInfo org_info ON org.AD_Org_ID = org_info.AD_Org_ID AND org_info.isActive = 'Y'
			INNER JOIN C_BPartner_Location org_bpl ON org_info.orgBP_Location_ID = org_bpl.C_BPartner_Location_ID AND org_bpl.isActive = 'Y'
			INNER JOIN C_Location org_l ON org_bpl.C_Location_ID = org_l.C_Location_ID AND org_l.isActive = 'Y'
			INNER JOIN c_bpartner org_bp ON org_bpl.C_Bpartner_ID = org_bp.C_BPartner_ID AND org_bp.isActive = 'Y'

			-- LINKING
			LEFT OUTER JOIN M_HU_Assignment huas ON lu.M_HU_ID = huas.M_HU_ID AND huas.AD_Table_ID IN (Get_Table_ID('M_ReceiptSchedule'), Get_Table_ID('PP_Order'), Get_Table_ID('PP_Order_BOMLine'),Get_Table_ID('M_InOutLine')) AND huas.isActive = 'Y'
			LEFT OUTER JOIN M_ReceiptSchedule rs ON huas.AD_Table_ID = Get_Table_ID('M_ReceiptSchedule') and rs.M_ReceiptSchedule_ID = huas.Record_ID and rs.AD_Org_ID = org.AD_Org_ID AND rs.isActive = 'Y'
			LEFT OUTER JOIN PP_Order ppo ON huas.AD_Table_ID = Get_Table_ID('PP_Order') and ppo.PP_Order_ID = huas.Record_ID AND ppo.AD_Org_ID = org.AD_Org_ID AND ppo.isActive = 'Y'
			LEFT OUTER JOIN PP_Order_BOMLine ppl on huas.AD_Table_ID = Get_Table_ID('PP_Order_BOMLine') and ppl.PP_Order_BOMLine_ID = huas.Record_ID AND ppl.AD_Org_ID = org.AD_Org_ID AND ppl.isActive = 'Y'
			LEFT OUTER JOIN M_InOutLine iol on huas.AD_Table_ID = Get_Table_ID('M_InOutLine') and iol.M_InOutLine_ID = huas.Record_ID AND iol.AD_Org_ID = org.AD_Org_ID AND iol.isActive = 'Y'


			WHERE CASE WHEN huas.M_HU_ID IS NOT NULL 
				THEN huas.M_HU_ID =lu.M_HU_ID AND (iol.AD_Org_ID = org.AD_Org_ID OR ppl.ad_org_id = org.AD_Org_ID OR ppo.AD_Org_ID = org.AD_Org_ID OR rs.AD_Org_ID = org.AD_Org_ID)
			
				ELSE lu.AD_Org_ID = org.AD_Org_ID END
				AND org.isActive = 'Y'
			limit 1	
	) as org_address,
	lua_sscc.value AS SSCC,
	bpp.productno AS p_customervalue,
	(
		SELECT 	avg(ol.priceactual)
		FROM	M_HU_Trx_Line hutl
			INNER JOIN M_ReceiptSchedule rs ON hutl.Record_ID = rs.M_ReceiptSchedule_ID AND rs.isActive = 'Y'
			INNER JOIN C_OrderLine ol ON rs.Record_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
		WHERE	hutl.M_HU_ID = lu.M_HU_ID  AND hutl.isActive = 'Y'
			AND hutl.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'M_ReceiptSchedule' AND isActive = 'Y')
			AND rs.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_OrderLine' AND isActive = 'Y')
	)  AS PriceActual,
	COALESCE( pt.name, p.name ) AS p_name,
	qty.CU_per_TU,
	qty.TU_per_LU,
	lua_netw.valuenumber as net_weight,
	lua_grow.valuenumber as gross_weight,
	(
		SELECT 	max(o.documentno)
		FROM	M_HU_Trx_Line hutl
			INNER JOIN M_ReceiptSchedule rs ON hutl.Record_ID = rs.M_ReceiptSchedule_ID AND rs.isActive = 'Y'
			INNER JOIN C_OrderLine ol ON rs.Record_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
			INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
		WHERE	hutl.M_HU_ID = lu.M_HU_ID AND hutl.isActive = 'Y'
			AND hutl.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'M_ReceiptSchedule' AND isActive = 'Y')
			AND rs.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_OrderLine' AND isActive = 'Y')
	) AS order_docno,
	p.value AS P_Value,
	lua_lot.value AS LotCode,
	lu.value AS PaletNo,
	COALESCE(bp.name, '') || ' ' || COALESCE(bpl.name, '') || E'\n' AS Customer,
	-- Adding language. This language is used for the captions in the report 
	bp.ad_Language,
	--
	-- Filtering fields
	lu.M_HU_ID,
	"de.metas.handlingunits".HU_LotNumberDate_ToString(lua_LotNumberDate.valueDate::date) AS LotNumberDate
FROM
	M_HU lu
	INNER JOIN M_HU_Storage lus ON lu.M_HU_ID = lus.M_HU_ID AND lus.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp ON lu.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner_Location bpl ON lu.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID AND bpl.isActive = 'Y'
	INNER JOIN M_Product p ON lus.M_product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_product_ID = pt.M_Product_ID AND bp.AD_Language = pt.AD_Language AND pt.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
	JOIN (
		SELECT 	lui.M_HU_ID, 
		--in case the HUs are aggregated we need to calculate how many TU and Cu are there
		COALESCE( (CASE WHEN val.qty IS NOT NULL THEN (avg(tus.qty) / val.qty)::numeric ELSE avg(tus.qty)  END), 0) AS CU_per_TU,
		COALESCE(val.qty, count(tu.M_HU_ID))::bigint AS TU_per_LU
		FROM 	M_HU_Item lui
			 JOIN M_HU_PI_Item lupii ON lui.M_HU_PI_Item_ID = lupii.M_HU_PI_Item_ID AND lupii.ItemType = 'HU' AND lupii.isActive = 'Y'
			 JOIN M_HU tu ON lui.M_HU_Item_ID = tu.M_HU_Item_Parent_ID
			 JOIN M_HU_Storage tus ON tu.M_HU_ID = tus.M_HU_ID AND tus.isActive = 'Y'
			LEFT JOIN "de.metas.handlingunits".get_TU_Values_From_Aggregation(tu.M_HU_ID) val on true
		WHERE lui.isActive = 'Y'
		GROUP BY lui.M_HU_ID, val.qty
		
	) qty ON lu.M_HU_ID = qty.M_HU_ID

	LEFT OUTER JOIN M_HU_Attribute lua_sscc ON lu.M_HU_ID = lua_sscc.M_HU_ID AND lua_sscc.isActive = 'Y'
		AND lua_sscc.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE name = 'SSCC18' AND isActive = 'Y')
	LEFT OUTER JOIN M_HU_Attribute lua_netw ON lu.M_HU_ID = lua_netw.M_HU_ID AND lua_netw.isActive = 'Y'
		AND lua_netw.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE name = 'Gewicht Netto' AND isActive = 'Y')
	LEFT OUTER JOIN M_HU_Attribute lua_grow ON lu.M_HU_ID = lua_grow.M_HU_ID AND lua_grow.isActive = 'Y'
		AND lua_grow.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE name = 'Gewicht Brutto kg' AND isActive = 'Y')
	LEFT OUTER JOIN M_HU_Attribute lua_lot ON lu.M_HU_ID = lua_lot.M_HU_ID AND lua_lot.isActive = 'Y'
		AND lua_lot.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE name ILIKE 'Lot-Nummer' AND isActive = 'Y')
	LEFT OUTER JOIN M_HU_Attribute lua_LotNumberDate ON lu.M_HU_ID = lua_LotNumberDate.M_HU_ID AND lua_LotNumberDate.isActive = 'Y'
		AND lua_LotNumberDate.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE name = 'Tageslot Datum' AND isActive = 'Y')	
-- WHERE
-- 	lu.M_HU_ID = $P{M_HU_ID}
;

