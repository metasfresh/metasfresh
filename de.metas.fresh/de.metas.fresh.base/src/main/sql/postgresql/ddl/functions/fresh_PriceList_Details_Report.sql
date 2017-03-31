drop function if exists report.fresh_PriceList_Details_Report(numeric, numeric, numeric, character varying);

-- DROP FUNCTION report.fresh_pricelist_details_report(numeric, numeric, numeric, character varying);

CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_report(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_alt_pricelist_version_id numeric, IN p_ad_language character varying)
  RETURNS TABLE(bp_value text, bp_name text, productcategory text, m_product_id integer, value text, customerproductnumber text, productname text, isseasonfixedprice character, itemproductname character varying, qtycuspertu numeric, packingmaterialname text, pricestd numeric, altpricestd numeric, hasaltprice integer, uomsymbol text, uom_x12de355 text, attributes text, m_productprice_id integer, m_attributesetinstance_id integer, m_hu_pi_item_product_id integer) AS
$BODY$
--
	SELECT
		bp.value AS BP_Value,
		bp.name AS BP_Name,
		plc.ProductCategory,
		plc.M_Product_ID::integer,
		plc.Value,
		bpp.ProductNo AS CustomerProductNumber,
		COALESCE( pt.name, plc.ProductName ) AS ProductName,
		plc.IsSeasonFixedPrice,
		CASE WHEN plc.ItemProductName like 'VirtualPI%' THEN NULL ELSE plc.ItemProductName END AS ItemProductName,
		plc.QtyCUsPerTU,
		plc.PackingMaterialName,
		plc.PriceStd,
		plc.AltPriceStd,
		plc.HasAltPrice,
		plc.UOMSymbol,
		plc.UOM_X12DE355::text,
		CASE WHEN $4 = 'fr_CH' THEN 	Replace( plc.Attributes, 'AdR', 'DLR') ELSE plc.Attributes END AS Attributes,
		plc.M_ProductPrice_ID::integer,
		plc.M_AttributeSetInstance_ID::integer,
		plc.M_HU_PI_Item_Product_ID::integer
	FROM
		RV_fresh_PriceList_Comparison plc
		LEFT OUTER JOIN M_Product_Trl pt ON plc.M_Product_ID = pt.M_Product_ID AND AD_Language = $4 AND pt.isActive = 'Y'
		LEFT OUTER JOIN C_BPartner bp ON plc.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND plc.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
	WHERE
		plc.C_BPartner_ID = $1
		AND plc.M_Pricelist_Version_ID = $2
		AND plc.Alt_Pricelist_Version_ID =
			CASE WHEN $3 IS NULL THEN plc.M_Pricelist_Version_ID
			ELSE $3
		END
		AND CASE WHEN $3 IS NOT NULL
			THEN PriceStd != 0
			ELSE PriceStd != 0 OR AltPriceStd != 0
		END
	ORDER BY

		plc.ProductCategoryValue,
		plc.ProductName,
		plc.seqNo,
		plc.attributes,
		plc.ItemProductName
--
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

