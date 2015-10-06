drop function if exists report.fresh_PriceList_Details_Report(numeric, numeric, numeric, character varying);

CREATE OR REPLACE FUNCTION report.fresh_PriceList_Details_Report(
	p_C_BPartner_ID numeric -- 1
	, p_M_PriceList_Version_ID numeric -- 2
	, p_Alt_PriceList_Version_ID numeric -- 3
	, p_AD_Language character varying -- 4
)
RETURNS TABLE (
		ProductCategory text
		, M_Product_ID integer
		, Value text
		, ProductName text
		, IsSeasonFixedPrice character
		, ItemProductName character varying
		, QtyCUsPerTU numeric
		, PackingMaterialName text
		, PriceStd numeric
		, AltPriceStd numeric
		, HasAltPrice integer
		, UOMSymbol text
		, UOM_X12DE355 text
		, Attributes text
		, M_ProductPrice_ID integer
		, M_ProductPrice_Attribute_ID integer
		, M_HU_PI_Item_Product_ID integer
)
AS
$BODY$
--
	SELECT
		plc.ProductCategory,
		plc.M_Product_ID::integer,
		plc.Value,
		COALESCE( pt.name, plc.ProductName ) AS ProductName,
		plc.IsSeasonFixedPrice,
		plc.ItemProductName,
		plc.QtyCUsPerTU,
		plc.PackingMaterialName,
		plc.PriceStd,
		plc.AltPriceStd,
		plc.HasAltPrice,
		plc.UOMSymbol,
		plc.UOM_X12DE355::text,
		CASE WHEN $4 = 'fr_CH' THEN 	Replace( plc.Attributes, 'AdR', 'DLR') ELSE plc.Attributes END AS Attributes,
		plc.M_ProductPrice_ID::integer,
		plc.M_ProductPrice_Attribute_ID::integer,
		plc.M_HU_PI_Item_Product_ID::integer
	FROM
		RV_fresh_PriceList_Comparison plc
		LEFT OUTER JOIN M_Product_Trl pt ON plc.M_Product_ID = pt.M_Product_ID AND AD_Language = $4
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
ROWS 1000
;
--
ALTER FUNCTION report.fresh_PriceList_Details_Report(numeric, numeric, numeric, character varying)
OWNER TO adempiere;
