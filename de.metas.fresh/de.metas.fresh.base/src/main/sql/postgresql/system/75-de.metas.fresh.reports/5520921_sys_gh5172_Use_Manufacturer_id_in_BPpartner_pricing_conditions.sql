DROP FUNCTION IF EXISTS report.bpartner_pricing_conditions (IN p_C_BPartner_ID numeric, IN p_C_BPartner_Location_ID numeric, IN p_Date date, IN p_ad_language character varying);

CREATE OR REPLACE FUNCTION report.bpartner_pricing_conditions (IN p_C_BPartner_ID numeric, IN p_C_BPartner_Location_ID numeric, IN p_Date date, IN p_ad_language character varying)

RETURNS TABLE
(
	bp_name character varying,
	addressline text,
	p_Value character varying,
	p_Name character varying,
	op character varying,
	me character varying,
	manufacturer character varying,
	base_ps character varying,
	breakdiscount numeric,
	fixum numeric,
	discount numeric,
	pricestd numeric,
	EndPrice numeric
)
AS
$$

SELECT *, 
	(pricestd - (pricestd * breakdiscount / 100) + fixum) - ((pricestd - (pricestd * breakdiscount / 100) + fixum) * discount / 100) AS EndPrice
FROM 
(
	SELECT 
		bp.Name AS bp_name,
		trim(
			COALESCE ( bpl.Name || E'\n', '' ) ||
			COALESCE ( l.address1 || E'\n', '' ) ||
			COALESCE ( l.postal || ', ', '' ) ||
			COALESCE ( l.city, '' )
		)as addressline,
		p.value as p_Value,
		COALESCE(pt.name, p.name) AS p_Name,
		p.packageSize AS op,
		COALESCE(uomt.UOMSymbol,uom.UOMSymbol) AS me,
		manf.Name AS manufacturer,
		ps.name AS base_ps,
		COALeSCE(dsb.breakdiscount, 0) AS breakdiscount,
		COALESCE(dsb.PricingSystemSurchargeAmt, 0) AS fixum,
		p_term.discount as discount,
		COALESCE(pprice.pricestd, 0) AS pricestd
		
		

	FROM C_BPartner bp
	INNER JOIN C_BPartner_Location bpl ON TRUE
	INNER JOIN C_Location l ON bpl.C_Location_ID = l.C_Location_ID
	INNER JOIN M_DiscountSchema ds ON bp.M_DiscountSchema_ID = ds.M_DiscountSchema_ID
	INNER JOIN M_DiscountSchemaBreak dsb ON ds.M_DiscountSchema_ID = dsb.M_DiscountSchema_ID
	INNER JOIN M_Product p ON dsb.M_Product_ID = p.M_Product_ID
	LEFT JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.ad_language = p_ad_language
	LEFT JOIN C_PaymentTerm p_term ON dsb.C_PaymentTerm_ID = p_term.C_PaymentTerm_ID
	
	LEFT JOIN C_BPartner manf ON p.manufacturer_id = manf.C_BPartner_id

	LEFT JOIN C_UOM uom ON p.Package_UOM_ID = uom.C_UOM_ID
	LEFT JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.ad_language = p_ad_language

	LEFT JOIN M_PricingSystem ps ON ps.M_PricingSystem_ID = dsb.Base_PricingSystem_ID
	LEFT JOIN LATERAL
	(
		SELECT pp.pricestd
		FROM M_ProductPrice pp
		JOIN M_PriceList_Version plv ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID
		JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_Pricelist_ID

		WHERE 
			plv.isActive = 'Y' AND plv.validFrom <= p_Date
			AND pl.M_PricingSystem_ID = ps.M_PricingSystem_ID AND (pl.C_Country_ID = l.C_Country_ID OR pl.C_Country_ID IS NULL) 
			AND pp.M_Product_ID = p.M_Product_ID
			
		ORDER BY pl.IsSOPricelist, plv.validFrom DESC
		LIMIT 1	
	)pprice ON TRUE

	WHERE 
		bp.C_BPartner_ID = p_C_BPartner_ID
		AND bpl.C_BPartner_Location_ID = p_C_BPartner_Location_ID
)r

$$
LANGUAGE sql STABLE;