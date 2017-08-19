
DROP FUNCTION IF EXISTS report.cu_label(IN M_HU_ID numeric, IN PP_Order_ID numeric);

CREATE FUNCTION report.cu_label(IN M_HU_ID numeric, IN PP_Order_ID numeric) RETURNS TABLE
	(
	p_name Character Varying, 
	p_name_trl Character Varying, 
	qty Numeric,
	p_value Character Varying,
	p_description Character Varying,
	ingredient text,
	ingredient_trl text,
	best_before text,
	temperature text,
	org_address text,
	lot_nr text,
	isBio text,
	upc Character Varying
	)
AS 
$$

SELECT
	p.name AS p_name,
	pt.name AS p_name_trl,
	p.weight AS qty,
	p.value AS p_value,
	p.description AS p_description,
	ppl.ingredient AS ingredient, 
	ppl.ingredient_trl AS ingredient_trl, 
	
	'null'::text AS best_before, -- leave out for now
	'null'::text AS temperature, -- leave out for now
	(
	SELECT  COALESCE(org_bp.name, '') || ', '|| COALESCE(c.countrycode, '') || '-' || COALESCE(org_l.Postal, '')|| ' '  || COALESCE(org_l.city, '')
			FROM AD_Org org

			INNER JOIN AD_OrgInfo org_info ON org.AD_Org_ID = org_info.AD_Org_ID AND org_info.isActive = 'Y'
			INNER JOIN C_BPartner_Location org_bpl ON org_info.orgBP_Location_ID = org_bpl.C_BPartner_Location_ID AND org_bpl.isActive = 'Y'
			INNER JOIN C_Location org_l ON org_bpl.C_Location_ID = org_l.C_Location_ID AND org_l.isActive = 'Y'
			INNER JOIN c_bpartner org_bp ON org_bpl.C_Bpartner_ID = org_bp.C_BPartner_ID AND org_bp.isActive = 'Y'
			LEFT JOIN C_Country c ON org_l.C_Country_ID = c.C_Country_ID AND c.isActive = 'Y'

			WHERE org.AD_Org_ID = pp.AD_Org_ID AND org.isActive = 'Y'
			limit 1
	) AS org_address,
	
	'Lot-Nr.1 5/'|| '00X' || 'M' || r.Value AS lot_nr, -- leave out for now
	(CASE WHEN hu IS NOT NULL THEN 'Y' ELSE 'N' END) AS isBio,
	p.upc --EAN
	
FROM PP_Order pp

-- product data
INNER JOIN M_Product p ON pp.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
LEFT JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.ad_language = 'fr_CH' AND pt.isActive = 'Y'
INNER JOIN S_Resource r ON pp.S_Resource_ID = r.S_Resource_ID AND r.isActive = 'Y'

--manufacturing data
LEFT JOIN LATERAL
	(SELECT 
		string_agg (pb.name || '(' || COALESCE(tua_country.name, '') || ')' , ', ') AS ingredient,
		string_agg (pbt.name|| '(' || COALESCE(tua_country.name, '') || ')' , ', ') AS ingredient_trl
		
	FROM PP_Order_BOMLine ppl 
	INNER JOIN M_Product pb ON ppl.M_Product_ID = pb.M_Product_ID AND pb.isActive = 'Y'
	LEFT JOIN M_Product_Trl pbt ON pb.M_Product_ID = pbt.M_Product_ID AND pbt.ad_language = 'fr_CH' AND pbt.isActive = 'Y'
		
	-- take the countries from HU attributes
	LEFT JOIN LATERAL(
		SELECT 	DISTINCT string_agg (sub_av.value, ', ') AS name
		FROM	M_HU_Attribute sub_tua
		
		LEFT JOIN PP_Cost_Collector cc ON ppl.PP_Order_BOMLine_ID = cc.PP_Order_BOMLine_ID AND cc.isActive = 'Y' AND cc.costcollectortype = '110'
		LEFT JOIN M_HU_Assignment huas_cc ON cc.PP_Cost_Collector_ID = huas_cc.Record_ID AND huas_cc.AD_Table_ID = get_table_id('PP_Cost_Collector') AND huas_cc.isActive = 'Y'  
		LEFT JOIN M_HU hul ON huas_cc.M_HU_ID = hul.M_HU_ID
		
		INNER JOIN M_AttributeValue sub_av ON sub_tua.value = sub_av.value AND sub_tua.M_Attribute_ID = sub_av.M_Attribute_ID AND sub_av.isActive = 'Y'
		INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
		
		WHERE 	sub_a.value = '1000001' -- Herkunft
				AND sub_tua.isActive = 'Y'
				AND hul.M_HU_ID = sub_tua.M_HU_ID
				AND cc.DocStatus IN ('CO','CL')
				AND sub_tua.M_HU_ID = hul.M_HU_ID
	) tua_country ON TRUE
	
	WHERE pp.PP_Order_ID = ppl.PP_Order_ID AND ppl.isActive = 'Y'
		AND ppl.componenttype = 'CO'
			
	) ppl ON TRUE
	
-- hu data	
LEFT JOIN LATERAL(
		SELECT 	sub_tua.M_HU_ID, sub_av.name
		FROM	M_HU_Attribute sub_tua
		
			LEFT JOIN PP_Cost_Collector cc ON pp.PP_Order_ID = cc.PP_Order_ID AND cc.isActive = 'Y' AND cc.costcollectortype = '100'
			LEFT JOIN M_HU_Assignment huas_cc ON cc.PP_Cost_Collector_ID = huas_cc.Record_ID AND huas_cc.AD_Table_ID = get_table_id('PP_Cost_Collector') AND huas_cc.isActive = 'Y'  
			LEFT JOIN M_HU hu ON huas_cc.M_HU_ID = hu.M_HU_ID
			
			INNER JOIN M_AttributeValue sub_av ON sub_tua.value = sub_av.value AND sub_tua.M_Attribute_ID = sub_av.M_Attribute_ID AND sub_av.isActive = 'Y'
			INNER JOIN M_Attribute sub_a ON sub_tua.M_Attribute_ID = sub_a.M_Attribute_ID AND sub_a.isActive = 'Y'
		WHERE 	sub_a.value = '1000002' -- Label
			AND sub_av.name ILIKE 'bio'
			AND sub_tua.isActive = 'Y'
			AND hu.M_HU_ID = sub_tua.M_HU_ID
			AND cc.DocStatus IN ('CO','CL')
		LIMIT 1	
	) hu ON TRUE 

WHERE 
	pp.isActive = 'Y'
	-- one of $1 and $2 should be mandatory
	AND (CASE WHEN $1 IS NOT NULL THEN hu.m_hu_id = $1 ELSE TRUE END)
	AND (CASE WHEN $2 IS NOT NULL THEN pp.PP_Order_ID = $2 ELSE TRUE END)
	
$$
  LANGUAGE sql STABLE
