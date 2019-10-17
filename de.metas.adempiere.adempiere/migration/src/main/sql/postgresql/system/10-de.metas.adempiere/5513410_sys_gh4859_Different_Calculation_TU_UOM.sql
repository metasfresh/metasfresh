DROP FUNCTION de_metas_endcustomer_fresh_reports.average_product_prices(date, date, character);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.average_product_prices(
    IN p_datefrom date,
    IN p_dateto date,
    IN p_issotrx character)
 
 RETURNS TABLE 
(
	ProduktNr character varying(40),
	ProduktName character varying(225),
	Merkmal text,
	Menge numeric,
	Menge_Lieferung numeric,
	Mengenenheit character varying,
	Preis numeric,
	BetragCHF text,
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
 $BODY$


SELECT 
	p.Value AS ProduktNr
	, p.Name AS ProduktName 
	, (SELECT String_agg ( att.ai_value, ', ' ORDER BY att.M_AttributeSetInstance_ID, length(att.ai_value), att.ai_value)
		FROM 	Report.fresh_Attributes att
		WHERE att.M_AttributeSetInstance_ID = COALESCE(ol.M_AttributeSetInstance_ID, iol.M_AttributeSetInstance_ID, pp.M_AttributeSetInstance_ID)) 
		AS Merkmal
	, ic.qtyOrdered AS Menge
	, iol.MovementQty AS Menge_Lieferung
	, uom.uomsymbol AS Mengenenheit
	, round(
		COALESCE( 
			(SELECT avg(il.PriceActual) 
					FROM C_Invoice_Line_Alloc ila 
					JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
					JOIN C_Invoice i ON il.C_Invoice_Id = i.C_Invoice_ID 
					WHERE ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
						AND i.docstatus in ('CO', 'CL')
						AND ila.isActive = 'Y'
						AND i.IsActive = 'Y'
						AND il.IsActive = 'Y'
			),  
			ic.PriceActual_Net_Effective)
		,
		4) 
		AS Preis
	,COALESCE((CASE WHEN c.iso_code != 'CHF'
		THEN ROUND(currencyConvert(COALESCE( 
			(SELECT avg(il.PriceActual) 
					FROM C_Invoice_Line_Alloc ila 
					JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
					JOIN C_Invoice i ON il.C_Invoice_Id = i.C_Invoice_ID 
					WHERE ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
						AND i.docstatus in ('CO', 'CL')
						AND ila.isActive = 'Y'
						AND i.IsActive = 'Y'
						AND il.IsActive = 'Y'
			),  
			ic.PriceActual_Net_Effective) 
			*
			(CASE WHEN (price_uom.uomsymbol = 'TU') 
				THEN COALESCE (uconv.multiplyrate, 1) * iol.MovementQty 
				ELSE uomconvert(p.M_Product_ID, uom.C_UOM_ID, price_uom.C_UOM_ID, iol.MovementQty)
			END )
			
			
			, ic.C_Currency_ID -- p_curfrom_id
			, (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'CHF') -- p_curto_id
			, p_dateto -- p_convdate -- date to 
			, (SELECT C_ConversionType_ID FROM C_ConversionType where Value='P') -- p_conversiontype_id
			, ic.AD_Client_ID
			, ic.AD_Org_ID --ad_org_id
			), 4)
		ELSE ROUND(
		COALESCE( 
			(SELECT avg(il.PriceActual) 
					FROM C_Invoice_Line_Alloc ila 
					JOIN C_InvoiceLine il ON ila.C_InvoiceLine_ID = il.C_InvoiceLine_ID
					JOIN C_Invoice i ON il.C_Invoice_Id = i.C_Invoice_ID 
					WHERE ic.C_Invoice_Candidate_ID = ila.C_Invoice_Candidate_ID
						AND i.docstatus in ('CO', 'CL')
						AND ila.isActive = 'Y'
						AND i.IsActive = 'Y'
						AND il.IsActive = 'Y'
			),  
			
			ic.PriceActual_Net_Effective) * 

	
		
			(CASE WHEN (price_uom.uomsymbol = 'TU') 
				THEN COALESCE (uconv.multiplyrate, 1) * iol.MovementQty 
				ELSE uomconvert(p.M_Product_ID, uom.C_UOM_ID, price_uom.C_UOM_ID, iol.MovementQty)
			END )
			,4)
	END ) :: text, 'Missing Conversion'::text ) AS BetragCHF
	
	
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
INNER JOIN C_InvoiceCandidate_InOutLine iciol ON ic.C_Invoice_Candidate_ID = iciol.C_Invoice_Candidate_ID
INNER JOIN M_InOutLine iol ON iciol.M_InOutLine_ID = iol.M_InOutLine_ID
INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
LEFT OUTER JOIN PP_Order pp ON ic.Record_ID = pp.PP_Order_ID AND ic.AD_Table_ID = get_Table_ID('PP_Order')

LEFT OUTER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID


LEFT OUTER JOIN C_UOM_Conversion uconv ON uconv.C_UOM_ID = iol.C_UOM_ID
												AND uconv.C_UOM_To_ID = price_uom.C_UOM_ID
												AND p.M_Product_ID = uconv.M_Product_ID	


WHERE 
	ic.isSOTrx = p_issotrx
	AND io.MovementDate::date >= p_datefrom -- date from
	AND io.MovementDate::date <= p_dateto --  date to
	--
	AND pc.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
	
	


$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
ALTER FUNCTION de_metas_endcustomer_fresh_reports.average_product_prices(date, date, character)
  OWNER TO metasfresh;
