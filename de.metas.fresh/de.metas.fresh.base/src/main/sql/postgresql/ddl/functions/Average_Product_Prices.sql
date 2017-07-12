DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.average_product_prices ( IN datefrom DATE, IN dateto DATE, IN issotrx character(1) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.average_product_prices ( IN datefrom DATE, IN dateto DATE, IN issotrx character(1) )
RETURNS TABLE 
(
	ProduktNr character varying(40),
	ProduktName character varying(225),
	Merkmal text,
	Menge numeric,
	Mengenenheit character varying,
	Preis numeric,
	BetragCHF numeric,
	Wahrung character(3),
	Preisenheit character varying,
	ProduktKategorie character varying(60),
	GeschaftspartnerNr character varying(40),
	GeschaftspartnerName character varying(60),
	Country character varying,
	ADR character varying,
	Label character varying,
	BelegNr character varying(30), 
	BewegungsDatum timestamp without time zone
)
AS
$$
SELECT 
	p.Value AS ProduktNr
	, p.Name AS ProduktName 
	, (SELECT String_agg ( att.ai_value, ', ' ORDER BY att.M_AttributeSetInstance_ID, length(att.ai_value), att.ai_value)
		FROM 	Report.fresh_Attributes att
		WHERE att.M_AttributeSetInstance_ID = COALESCE(ol.M_AttributeSetInstance_ID, iol.M_AttributeSetInstance_ID, pp.M_AttributeSetInstance_ID)) 
		AS Merkmal
	, ic.qtyOrdered AS Menge
	, uom.uomsymbol AS Mengenenheit
	, ic.PriceActual_Net_Effective AS Preis
	,(CASE WHEN c.iso_code != 'CHF'
		THEN currencyConvert(ic.PriceActual_Net_Effective * ic.qtyOrdered
			, ic.C_Currency_ID -- p_curfrom_id
			, (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'CHF') -- p_curto_id
			, $2 -- p_convdate -- date to 
			, (SELECT C_ConversionType_ID FROM C_ConversionType where Value='P') -- p_conversiontype_id
			, ic.AD_Client_ID
			, ic.AD_Org_ID --ad_org_id
			)
		ELSE ic.PriceActual_Net_Effective * ic.qtyOrdered 
	END ) AS BetragCHF
	, c.iso_code AS Wahrung 
	, price_uom.uomsymbol AS Preisenheit
	, pc.Name as ProductCategory
	, bp.value
	, bp.name
	,(SELECT ai_value FROM Report.fresh_Attributes att 
		WHERE att.M_AttributeSetInstance_ID = COALESCE(ol.M_AttributeSetInstance_ID, iol.M_AttributeSetInstance_ID, pp.M_AttributeSetInstance_ID)
		AND at_value = '1000001') AS Country 
	,(SELECT ai_value FROM Report.fresh_Attributes att 
		WHERE att.M_AttributeSetInstance_ID = COALESCE(ol.M_AttributeSetInstance_ID, iol.M_AttributeSetInstance_ID, pp.M_AttributeSetInstance_ID)
		AND at_value = '1000015') AS ADR
	,(SELECT ai_value FROM Report.fresh_Attributes att 
		WHERE att.M_AttributeSetInstance_ID = COALESCE(ol.M_AttributeSetInstance_ID, iol.M_AttributeSetInstance_ID, pp.M_AttributeSetInstance_ID)
		AND at_value = '1000002')  AS Label
	-- document number of order if exists. If not then null
	, o.documentNo
	-- receipt date if exists. If not then null
	, ic.deliverydate
	 
FROM C_Invoice_Candidate ic

INNER JOIN M_Product p ON ic.M_Product_ID = p.M_Product_ID
INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

INNER JOIN C_BPartner bp ON ic.Bill_Bpartner_ID = bp.C_BPartner_ID

INNER JOIN C_UOM uom ON ic.C_UOM_ID = uom.C_UOM_ID
INNER JOIN C_UOM price_uom ON ic.Price_UOM_ID = price_uom.C_UOM_ID

INNER JOIN C_Currency c ON ic.C_Currency_ID = c.C_Currency_ID

LEFT OUTER JOIN C_OrderLine ol ON ic.Record_ID = ol.C_OrderLine_ID AND ic.AD_Table_ID = get_Table_ID('C_OrderLine')
LEFT OUTER JOIN M_InOutLine iol ON ic.Record_ID = iol.M_InOutLine_ID AND ic.AD_Table_ID = get_Table_ID('M_InOutLine')
LEFT OUTER JOIN PP_Order pp ON ic.Record_ID = pp.PP_Order_ID AND ic.AD_Table_ID = get_Table_ID('PP_Order')

LEFT OUTER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID

WHERE 
	ic.isSOTrx = $3
	AND ic.DateOrdered >= $1 -- date from
	AND ic.DateOrdered <= $2 --  date to
	--
	AND pc.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
$$
LANGUAGE sql STABLE	
;
