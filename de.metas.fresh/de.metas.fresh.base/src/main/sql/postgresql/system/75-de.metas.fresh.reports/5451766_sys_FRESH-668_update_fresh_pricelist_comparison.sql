
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
	INNER JOIN C_BPartner bp ON true AND bp.isActive = 'Y'
	
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
			SELECT * FROM Report.Valid_PI_Item_Product_V /* WHERE isInfiniteCapacity = 'N' task 09045/09788: we can also export PiiPs with infinite capacity */
		)) ip
			ON ip.M_Product_ID = p.M_Product_ID 
			AND ( ( COALESCE(spi.hasSpecificBP, False ) AND ip.C_BPartner_ID = bp.C_BPartner_ID ) 
			OR ( NOT COALESCE(spi.hasSpecificBP, False ) AND ip.C_BPartner_ID IS NULL ) )
	LEFT OUTER JOIN m_hu_pi_item it ON ip.M_HU_PI_Item_ID = it.M_HU_PI_Item_ID AND it.isActive = 'Y'
	LEFT OUTER JOIN m_hu_pi_item pmit ON it.m_hu_pi_version_id = pmit.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND pmit.isActive = 'Y'
	LEFT OUTER JOIN m_hu_packingmaterial pm ON pmit.m_hu_packingmaterial_id = pm.m_hu_packingmaterial_id AND pm.isActive = 'Y'
	
	INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	/** Getting the product price for all price list versions and all products */
	INNER JOIN M_ProductPrice pp ON p.M_Product_ID = pp.M_Product_ID AND pp.IsActive = 'Y'
	INNER JOIN C_UOM uom ON pp.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
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
			AND ppa.isActive = 'Y'
	/**
	  * Get Comparison Prices
	  */
	LEFT OUTER JOIN M_PriceList_Version plv ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID AND plv.IsActive = 'Y'
	/** Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
	  * limited to the same PriceList because the Parameter validation rule is enforcing this */
	LEFT OUTER JOIN M_PriceList_Version plv2 ON plv.M_PriceList_ID = plv2.M_PriceList_ID AND plv2.IsActive = 'Y'
	LEFT OUTER JOIN M_ProductPrice pp2 ON p.M_Product_ID = pp2.M_Product_ID AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID AND pp2.IsActive = 'Y'
	/** Joining attribute prices */
	LEFT OUTER JOIN report.fresh_AttributePrice ppa2 ON pp2.M_ProductPrice_ID = ppa2.M_ProductPrice_ID
			/** we have to make sure that only prices with the same attributes and packing instructions are compared. Note: 
			 * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
			 * - If there are no Attribute Prices the signature will be null
			 * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
			AND ppa.signature = ppa2.signature AND ppa2.IsActive = 'Y'
;


COMMENT ON VIEW RV_fresh_PriceList_Comparison
  IS '06042 Preisliste Drucken Preisänderung (100373416918)
Refactored in Tasks 07833 and 07915
A view for a report that displays the same data as RV_fresh_PriceList but imroved to be able to filter by two Price list versions, to be able to compare them';
