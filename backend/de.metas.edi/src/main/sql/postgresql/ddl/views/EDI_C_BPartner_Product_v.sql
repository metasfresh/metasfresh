-- View: EDI_C_BPartner_Product_v
DROP VIEW IF EXISTS EDI_C_BPartner_Product_v;
CREATE OR REPLACE VIEW EDI_C_BPartner_Product_v AS
SELECT
	lookup.*
	, bpp.UPC
	, bpp.ProductNo
FROM (
	SELECT
		iol.M_InOutLine_ID
		, NULL AS C_OrderLine_ID
		, iol.M_Product_ID
		, io.C_BPartner_ID
	FROM M_InOut io
	INNER JOIN M_InOutLine iol ON iol.M_InOut_ID=io.M_InOut_ID -- we have a C_OrderLine per M_InOut
	--
	UNION
	--
	SELECT
		NULL AS M_InOutLine_ID
		, ol.C_OrderLine_ID
		, ol.M_Product_ID
		, o.C_BPartner_ID
	FROM M_InOut io
	   INNER JOIN C_Order o ON (o.C_Order_ID=io.C_Order_ID)
	   INNER JOIN C_OrderLine ol ON (ol.C_Order_ID = o.C_Order_ID)
	   LEFT JOIN M_InOutLine iol ON (iol.M_InOut_ID=io.M_InOut_ID AND iol.C_OrderLine_ID=ol.C_OrderLine_ID)
	WHERE
		-- C_Order_ID is the one from our M_InOut (orders may be partial)
		ol.C_Order_ID=io.C_Order_ID 
		-- we have a C_OrderLine, but not an M_InOutLine for the selected M_InOut (e.g Overdelivery)
		AND iol.M_InOutLine_ID IS NULL
) lookup
LEFT JOIN C_BPartner_Product bpp ON (bpp.C_BPartner_ID = lookup.C_BPartner_ID AND bpp.M_Product_ID=lookup.M_Product_ID)
WHERE lookup.M_Product_ID IS NOT NULL AND (lookup.M_InOutLine_ID IS NOT NULL OR lookup.C_OrderLine_ID IS NOT NULL);
