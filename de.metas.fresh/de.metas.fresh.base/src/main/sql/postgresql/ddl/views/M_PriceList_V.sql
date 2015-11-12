DROP VIEW IF EXISTS m_pricelist_v;

CREATE OR REPLACE VIEW m_pricelist_v AS 
SELECT DISTINCT
	pp.AD_Org_ID,
	pp.AD_Client_ID,
	pp.created,
	pp.createdby,
	pp.updated,
	pp.updatedby,
	pp.isactive,
	p.M_Product_ID,
	p.value AS ProductValue, 
	p.name AS ProductName,
	pp.IsSeasonFixedPrice, 
	COALESCE( ppa.PriceStd, pp.PriceStd) AS PriceStd,
	regexp_Replace( 
		regexp_Replace( ARRAY (SELECT av.name
			FROM	M_ProductPrice_Attribute_Line ppal
				LEFT JOIN M_Attributevalue av ON ppal.M_Attributevalue_ID = av.M_Attributevalue_ID
			WHERE	ppa.M_ProductPrice_Attribute_ID = ppal.M_ProductPrice_Attribute_ID
		)::Character Varying, '[{}]', '','g'
	), ',', ', ', 'g') AS Attributes,
	ip.name::character varying(60) AS PackingMaterialName,
	COALESCE(ppa.M_ProductPrice_ID, pp.M_ProductPrice_ID) as M_ProductPrice_ID,
	pp.M_PriceList_Version_ID,
	pp.SeqNo,
	p.M_Product_Category_ID
FROM
	M_Product p
	JOIN M_ProductPrice pp ON p.M_Product_ID = pp.M_Product_ID
	-- To find prices for products, we prefer attribute prices that are assigned to a Packing instruction (PI) over plain, classical product prices
	LEFT JOIN ( -- Find attribute prices with assigned PIs ...
		SELECT 	sub_p.M_Product_ID, sub_ppa.M_HU_PI_Item_Product_ID, sub_ppa.M_ProductPrice_Attribute_ID as ppa_ID, sub_pp.M_Pricelist_Version_ID
		FROM	M_Product sub_p 
			JOIN M_ProductPrice sub_pp ON sub_p.M_Product_ID = sub_pp.M_Product_ID
			JOIN M_ProductPrice_Attribute sub_ppa ON sub_pp.M_ProductPrice_ID = sub_ppa.M_ProductPrice_ID
		WHERE 	sub_ppa.M_HU_PI_Item_Product_ID IS NOT NULL
	-- ...that match the product and price list of the Product price found above
	) api ON p.M_Product_ID = api.M_Product_ID AND pp.M_Pricelist_Version_ID = api.M_Pricelist_Version_ID
	
	-- now we have Attribute prices but we also need to make sure the PIs related there are valid
	-- -> Find valid PI_Item_Products: Active, valid, no infinite capacity, ...
	LEFT JOIN (
		SELECT * FROM M_HU_PI_Item_Product 
		WHERE isInfiniteCapacity = 'N' AND isActive = 'Y' 
		AND ValidFrom <= now() AND (ValidTo > now() OR ValidTo IS NULL)
	) ip ON CASE 
		-- and join them
		WHEN api.M_HU_PI_Item_Product_ID IS NOT NULL -- Is null when no attribute price with PI was found
			THEN ip.M_HU_PI_Item_Product_ID = api.M_HU_PI_Item_Product_ID
			ELSE ip.M_Product_ID = p.M_Product_ID
	END
	LEFT JOIN M_ProductPrice_Attribute ppa 
		ON CASE WHEN api.ppa_ID IS NOT NULL THEN api.ppa_ID = ppa.M_ProductPrice_Attribute_ID
		ELSE pp.M_ProductPrice_ID = ppa.M_ProductPrice_ID 
	END
	LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = ip.C_BPartner_ID
	LEFT JOIN M_HU_PI_Item it ON ip.M_HU_PI_Item_ID = it.M_HU_PI_Item_ID 
	LEFT JOIN M_HU_PI_Item pmit ON it.M_HU_PI_Version_ID = pmit.M_HU_PI_Version_ID AND pmit.itemType = 'PM'
	LEFT JOIN M_HU_PackingMaterial pm ON pmit.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID
;
