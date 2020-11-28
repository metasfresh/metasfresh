CREATE OR REPLACE VIEW report.fresh_AttributePrice AS 
SELECT 	
	ppa.M_ProductPrice_ID, 
	ppa.M_ProductPrice_Attribute_ID, 
	ppa.PriceStd, 
	ppa.IsActive, 
	ppa.M_HU_PI_Item_Product_ID, 

	string_agg ( av.value , ', ' ORDER BY av.value ) AS Attributes,
	
	/** Create a column, that allows us to identify Attribute prices with the same AttributeSet 
	 *  That column will be filled with an empty string if all relevant data is null. this is important to prevent 
	 *  attribute prices to be compared with regular prices */
	(
		COALESCE( ppa.M_HU_PI_Item_Product_ID || ' ', '' ) ||
		COALESCE( ppa.isDefault || ' ', '' ) || 
		COALESCE( string_agg( ppal.M_Attribute_ID::text ||' '|| ppal.M_Attributevalue_ID::text, ',' ORDER BY ppal.M_Attribute_ID) , '')
	) AS signature 
FROM 	
	M_ProductPrice_Attribute ppa
	LEFT OUTER JOIN M_ProductPrice_Attribute_Line ppal ON ppa.M_ProductPrice_Attribute_ID = ppal.M_ProductPrice_Attribute_ID AND ppal.isActive = 'Y'
	LEFT OUTER JOIN (
		SELECT	av.isActive, av.M_Attributevalue_ID,
			CASE
				WHEN a.Value = '1000015' AND av.value = '01' THEN NULL -- ADR & Keine/Leer
				WHEN a.Value = '1000015' AND av.value IS NOT NULL THEN 'AdR' -- ADR
				WHEN a.Value = '1000001' THEN av.value -- Herkunft
				ELSE av.name 
			END as value
		FROM	M_Attributevalue av 
			LEFT OUTER JOIN M_Attribute a ON av.M_Attribute_ID = a.M_Attribute_ID
	) av ON ppal.M_Attributevalue_ID = av.M_Attributevalue_ID AND av.IsActive = 'Y' AND av.value IS NOT NULL
WHERE 	
	ppa.IsActive = 'Y'
GROUP BY 
	ppa.M_ProductPrice_ID, ppa.M_ProductPrice_Attribute_ID, ppa.PriceStd, ppa.IsActive, ppa.M_HU_PI_Item_Product_ID
;

COMMENT ON VIEW report.fresh_AttributePrice IS 'This View is supposed to be used by the View RV_fresh_PriceList_Comparison and retrieves the Attribute price together with a rendered list of Attributes and a signature. Attribute prices of different Price list version but with the same attributes and packin instruction config can be matched and therefore compared with this.';








--DROP VIEW IF EXISTS RV_fresh_PriceList_Comparison;

CREATE OR REPLACE VIEW RV_fresh_PriceList_Comparison AS 

SELECT
	-- Mandatory Columns
	pp.AD_Org_ID, pp.AD_Client_ID,
	pp.Created, pp.CreatedBy,
	pp.Updated, pp.UpdatedBy,
	pp.IsActive,

	-- Displayed pricelist data
	pc.Name AS ProductCategory,
	pc.value AS ProductCategoryValue,
	p.M_Product_ID,
	p.Value,
	p.Name AS ProductName,
	pp.IsSeasonFixedPrice,
	ip.Name AS ItemProductName,
	pm.Name AS PackingMaterialName,
	ROUND( COALESCE( ppa.PriceStd, pp.PriceStd ), 2 ) AS PriceStd,
	ROUND( COALESCE( ppa2.PriceStd, pp2.PriceStd ), 2 ) AS AltPriceStd,
	CASE WHEN COALESCE( ppa2.PriceStd, pp2.PriceStd) IS NULL THEN 0 ELSE 1 END AS hasaltprice,
	uom.UOMSymbol,
	COALESCE(ppa.Attributes, '') as attributes,
	pp.seqNo,

	-- Filter Columns
	bp.C_BPartner_ID,
	plv.M_Pricelist_Version_ID,
	plv2.M_Pricelist_Version_ID AS Alt_PriceList_Version_ID,
	
	-- Additional internal infos to be used
	pp.M_ProductPrice_ID,
	ppa.M_ProductPrice_Attribute_ID,
	ip.M_HU_PI_Item_Product_ID,
	uom.X12DE355 as UOM_X12DE355,
	ip.QtyCUsPerTU
FROM
	/** Get all BPartner and Product combinations. 
	  * IMPORTANT: Never use the query without BPartner Filter active
	  */
	M_Product p 
	INNER JOIN C_BPartner bp ON true
	
	/** For every BPartner-Product combination, find out if there is a packing instruction limited to the BPartner */
	LEFT OUTER JOIN (
	
		SELECT	ip.M_Product_ID, ip.C_BPartner_ID, COALESCE( Bool_OR( ip.hasPartner ), False ) AS hasSpecificBP
		FROM	/** All currently active and valid Packing instructions */
			((SELECT * FROM Report.Valid_PI_Item_Product_V)) ip 
		GROUP BY ip.M_Product_ID, ip.C_BPartner_ID
		
	) spi ON bp.C_BPartner_ID = spi.C_BPartner_ID AND p.M_Product_ID = spi.M_Product_ID
	
	/** 
	  * We now know if there are packing instructions limited to the BPartner/product-combination. If so,
	  * we will use only those. If not, we will use only the non limited ones
	  */
	LEFT OUTER JOIN 
		((
			SELECT * FROM Report.Valid_PI_Item_Product_V /* WHERE isInfiniteCapacity = 'N' task 09045: we can also export PiiPs with infinite capacity */
		)) ip
			ON ip.M_Product_ID = p.M_Product_ID 
			AND ( ( COALESCE(spi.hasSpecificBP, False ) AND ip.C_BPartner_ID = bp.C_BPartner_ID ) 
			OR ( NOT COALESCE(spi.hasSpecificBP, False ) AND ip.C_BPartner_ID IS NULL ) )
	LEFT OUTER JOIN m_hu_pi_item it ON ip.M_HU_PI_Item_ID = it.M_HU_PI_Item_ID
	LEFT OUTER JOIN m_hu_pi_item pmit ON it.m_hu_pi_version_id = pmit.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT
	LEFT OUTER JOIN m_hu_packingmaterial pm ON pmit.m_hu_packingmaterial_id = pm.m_hu_packingmaterial_id
	
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	/** Getting the product price for all price list versions and all products */
	INNER JOIN M_ProductPrice pp ON p.M_Product_ID = pp.M_Product_ID AND pp.IsActive = 'Y'
	INNER JOIN C_UOM uom ON pp.C_UOM_ID = uom.C_UOM_ID
	/** Attribute Prices */
	LEFT OUTER JOIN 
		(
			SELECT M_ProductPrice_ID, M_ProductPrice_Attribute_ID, PriceStd, IsActive, M_HU_PI_Item_Product_ID, Attributes, Signature FROM report.fresh_AttributePrice			
			/** Add a line without an actual attribute price. Reason: The attribute prices overwrite the regular prices but if there are 
			  * attribute prices we still need to display the regular prices. therefore we also join a line, that will not overwrite the 
			  * regular price.*/
			UNION SELECT M_ProductPrice_ID, NULL, NULL, 'Y', NULL, NULL, NULL FROM M_ProductPrice
		) ppa 
			ON pp.M_ProductPrice_ID = ppa.M_ProductPrice_ID 
			AND (ppa.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID OR ppa.M_HU_PI_Item_Product_ID IS NULL)
	/**
	  * Get Comparison Prices
	  */
	LEFT OUTER JOIN M_PriceList_Version plv ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID
	/** Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
	  * limited to the same PriceList because the Parameter validation rule is enforcing this */
	LEFT OUTER JOIN M_PriceList_Version plv2 ON plv.M_PriceList_ID = plv2.M_PriceList_ID AND plv.IsActive = 'Y'
	LEFT OUTER JOIN M_ProductPrice pp2 ON p.M_Product_ID = pp2.M_Product_ID AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
	/** Joining attribute prices */
	LEFT OUTER JOIN report.fresh_AttributePrice ppa2 ON pp2.M_ProductPrice_ID = ppa2.M_ProductPrice_ID
			/** we have to make sure that only prices with the same attributes and packing instructions are compared. Note: 
			 * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
			 * - If there are no Attribute Prices the signature will be null
			 * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
			AND ppa.signature = ppa2.signature
;

COMMENT ON VIEW RV_fresh_PriceList_Comparison
  IS '06042 Preisliste Drucken Preisänderung (100373416918)
Refactored in Tasks 07833 and 07915
A view for a report that displays the same data as RV_fresh_PriceList but imroved to be able to filter by two Price list versions, to be able to compare them';













DROP VIEW IF EXISTS RV_fresh_PriceList;

CREATE OR REPLACE VIEW RV_fresh_PriceList AS
SELECT
	-- Mandatory Columns
	plc.AD_Org_ID, plc.AD_Client_ID,
	plc.Created, plc.CreatedBy,
	plc.Updated, plc.UpdatedBy,
	plc.IsActive,

	-- Displayed pricelist data
	plc.ProductCategory,
	plc.ProductCategoryValue,
	plc.M_Product_ID,
	plc.Value,
	plc.ProductName,
	plc.IsSeasonFixedPrice,
	plc.ItemProductName,
	plc.PackingMaterialName,
	plc.PriceStd,
	plc.AltPriceStd,
	plc.HasAltPrice,
	plc.UOMSymbol,
	plc.Attributes,

	-- Filter Columns
	plc.C_BPartner_ID,
	plc.M_Pricelist_Version_ID,
	plc.M_ProductPrice_ID
FROM
	RV_fresh_PriceList_Comparison plc 
WHERE
	plc.M_Pricelist_Version_ID = plc.Alt_PriceList_Version_ID
;

COMMENT ON VIEW RV_fresh_PriceList IS '05956 Printformat for Pricelist (109054740508) 
Refactored in Task 07833 and 07915
A view for a report that displays all product prices of a BPartner, including the attribute prices and CU:TU relation';
