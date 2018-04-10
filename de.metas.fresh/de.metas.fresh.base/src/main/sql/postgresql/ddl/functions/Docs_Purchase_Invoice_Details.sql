DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
(
	InOuts text,
	DocType character varying,
	InOuts_DateFrom text,
	InOuts_IsDataComplete boolean,
	IsHU boolean,
	line numeric,
	Description character varying(255),
	Name character varying,
	Attributes text,
	HUQty numeric,
	HUName text,
	qtyinvoicedinpriceuom numeric,
	shipped numeric,
	retour numeric,
	PriceActual numeric,
	PriceEntered numeric,
	UOM character varying(10),
	PriceUOM character varying(10),
	StdPrecision numeric(10,0),
	linenetamt numeric,
	rate numeric,
	IsPrintTax character(1),
	bp_product_no character varying(30),
	bp_product_name character varying(100),

	p_value character varying(40),
	p_description character varying(255),
	invoice_description character varying(1024),
	cursymbol character varying(10)
)
AS
$$
SELECT
	COALESCE( io1.DocType, io2.DocType ) || ': ' || COALESCE( io1.DocNo, io2.DocNo ) AS InOuts,
	COALESCE( io1.DocType, io2.DocType ) AS DocType,
	to_char( COALESCE( io1.DateFrom, io2.DateFrom ), 'DD.MM.YYYY' ) AS InOuts_DateFrom,
	COALESCE( io1.DocNo, io2.DocNo ) IS NOT NULL AS InOuts_IsDataComplete,
	pc.IsHU,
	MAX(il.line) AS line,
	-- ts: QnD: appending the invoice line description to the product name.
	-- TODO: create a dedicated field for it etc
	il.Description,
	COALESCE( pt.name, p.name ) AS Name,
	COALESCE(
		CASE WHEN Length( att.Attributes ) > 15
			THEN att.Attributes || E'\n'
			ELSE att.Attributes
		END,
		''
	) AS Attributes,
	SUM(il.QtyenteredTU) 			AS HUQty,
	piip.name				AS HUName,
	SUM(il.QtyInvoicedInPriceUOM) AS qtyinvoicedinpriceuom,
	SUM(CASE
		WHEN il.QtyEntered > 0 THEN il.QtyEntered
		ELSE 0
	END) AS shipped,
	SUM(CASE
		WHEN il.QtyEntered < 0 THEN il.QtyEntered*-1
		ELSE 0
	END) AS retour,
	il.PriceActual,
	il.PriceEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) 	AS UOM,
	COALESCE(puomt.UOMSymbol, puom.UOMSymbol)	AS PriceUOM,
	puom.StdPrecision,
	SUM(il.linenetamt) AS linenetamt,
	t.rate,
	bpg.IsPrintTax,
	-- in case there is no C_BPartner_Product, fallback to the default ones
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,

	p.value AS p_value,
	p.description AS p_description,
	i.description AS invoice_description,
	c.cursymbol
FROM
	C_InvoiceLine il
	INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
	INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y' 

	-- Get Product and its translation
	LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN LATERAL
	(
		SELECT	M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID) AS isHU,
			M_Product_Category_ID
		FROM	M_Product_Category
		WHERE isActive = 'Y'
	) pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
	
	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'

	-- Get Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language =$2 AND uomt.isActive = 'Y'
	LEFT OUTER JOIN C_UOM puom ON il.Price_UOM_ID = puom.C_UOM_ID AND puom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl puomt ON il.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = $2 AND puomt.isActive = 'Y'

	-- Tax rate
	LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'

	LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

	-- Get shipment grouping header
	LEFT OUTER JOIN (
		SELECT
			o.C_InvoiceLine_ID,
			First_Agg( COALESCE (dtt.Printname, dt.Printname) ORDER BY io.DocumentNo ) AS DocType,
			First_agg ( DISTINCT io.DocumentNo ORDER BY io.DocumentNo ) ||
				CASE WHEN Count(DISTINCT io.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo,
			Min ( io.MovementDate ) AS DateFrom
		FROM
			(
				SELECT DISTINCT
					COALESCE (dl_ol.C_Order_ID,  sl_ol.C_Order_ID) AS C_Order_ID,
					ila.C_InvoiceLine_ID
				FROM
					C_InvoiceLine il
					INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID AND ila.isActive = 'Y'
					INNER JOIN C_Invoice_Candidate ic ON ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
					-- Direct Link from IC to OL. Applies for non HU lines
					LEFT OUTER JOIN C_Orderline dl_ol ON ic.AD_Table_ID = (SELECT Get_Table_ID ('C_OrderLine')) AND ic.Record_ID = dl_ol.C_OrderLine_ID AND dl_ol.isActive = 'Y'
					-- Stow away link. For ICs that are created as a result of shipped HUs that weren't in the original order
					LEFT OUTER JOIN M_InOutLine siol ON ic.AD_Table_ID = (SELECT Get_Table_ID ('M_InOutLine')) AND ic.Record_ID = siol.M_InOutLine_ID AND siol.isActive = 'Y'
					LEFT OUTER JOIN M_InOutLine sio_all ON siol.M_InOut_ID = sio_all.M_InOut_ID AND sio_all.isActive = 'Y'
					LEFT OUTER JOIN M_ReceiptSchedule_Alloc rsa ON sio_all.M_InOutLine_ID = rsa.M_InOutLine_ID AND rsa.isActive = 'Y'
					LEFT OUTER JOIN M_ReceiptSchedule rs ON rsa.M_ReceiptSchedule_ID = rs.M_ReceiptSchedule_ID AND rs.isActive = 'Y'
					LEFT OUTER JOIN C_Orderline sl_ol ON rs.AD_Table_ID = (SELECT Get_Table_ID ('C_OrderLine')) AND rs.C_OrderLine_ID = sl_ol.C_OrderLine_ID AND sl_ol.isActive = 'Y'
				WHERE
					COALESCE (dl_ol.C_Order_ID,  sl_ol.C_Order_ID) IS NOT NULL
					AND il.C_Invoice_ID = $1 AND il.isActive = 'Y'
			) o
			INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID AND ol.isActive = 'Y'
			INNER JOIN M_ReceiptSchedule rs ON rs.AD_Table_ID = (SELECT Get_Table_ID ('C_OrderLine')) AND rs.C_OrderLine_ID = ol.C_OrderLine_ID AND rs.isActive = 'Y'
			INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID AND rsa.isActive = 'Y'
			INNER JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
				AND io.DocStatus IN ('CO','CL') /* task 09290 */
			INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
			LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
		GROUP BY
			C_InvoiceLine_ID
	) io1 ON il.C_InvoiceLine_ID = io1.C_InvoiceLine_ID
	-- Get Alternate shipment grouping header
	LEFT OUTER JOIN (
		SELECT DISTINCT
			iliol.C_InvoiceLine_ID,
			First_Agg( COALESCE (dtt.Printname, dt.Printname) ORDER BY io.DocumentNo ) AS DocType,
			First_Agg ( io.DocumentNo ORDER BY io.DocumentNo ) ||
				CASE WHEN Count( io.documentNo ) > 1 THEN ' ff.' ELSE '' END AS DocNo,
			Min ( io.MovementDate ) AS DateFrom
		FROM
			(
				SELECT DISTINCT M_InOut_ID, C_InvoiceLine_ID FROM Report.fresh_IL_to_IOL_V WHERE C_Invoice_ID = $1
			) iliol
			LEFT OUTER JOIN M_InOut io ON iliol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
				AND io.DocStatus IN ('CO','CL') /* task 09290 */
			LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
			LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
		GROUP BY
			C_InvoiceLine_ID
	) io2 ON il.C_InvoiceLine_ID = io2.C_InvoiceLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN (
		SELECT 	String_Agg( Name, E'\n' ORDER BY Name ) AS Name, C_InvoiceLine_ID
		FROM	(
				SELECT DISTINCT
					COALESCE ( pifb.name, pi.name ) AS name,
					C_InvoiceLine_ID
				FROM
					Report.fresh_IL_TO_IOL_V iliol
					INNER JOIN M_InOutLine iol ON iliol.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item_Product pi ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'
					
					LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) )) AND asgn.isActive = 'Y'
						AND asgn.Record_ID = iol.M_InOutLine_ID
					LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
					LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
					--
					LEFT OUTER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND piv.isActive = 'Y'
				WHERE
					piv.M_HU_PI_Version_ID != 101  
					AND iliol.C_Invoice_ID = $1
			) pi
		GROUP BY C_InvoiceLine_ID
	) piip ON il.C_InvoiceLine_ID = piip.C_InvoiceLine_ID

	-- Get Attributes
	-- we join the first M_MatchInv record to get it's M_InOutLine's ASI
	-- if there are many inoutLines with different ASIs, then we can assume that all of them have that same instance values for those attributes that are flagged with M_Attribute.IsAttrDocumentRelevant='Y'
	-- and these are also the only M_Attributes's instance values that we show

	LEFT OUTER JOIN
	(
		SELECT DISTINCT ON (C_InvoiceLine_ID) String_agg (att.ai_value, ', ' ORDER BY length(att.ai_value),att.ai_value ) AS Attributes, att.M_AttributeSetInstance_ID, il.c_invoiceline_id
		FROM c_invoiceline il 
		JOIN m_matchinv mi ON mi.c_invoiceline_id = il.c_invoiceline_id AND mi.isActive = 'Y'
		JOIN Report.fresh_Attributes att ON mi.m_attributesetinstance_id = att.m_attributesetinstance_id
		WHERE att.at_IsAttrDocumentRelevant = 'Y' and il.c_invoice_id = $1 AND il.isActive = 'Y'
		GROUP BY att.M_AttributeSetInstance_ID, il.c_invoiceline_id
	) att ON att.C_InvoiceLine_ID = il.C_InvoiceLine_ID


WHERE
	il.C_Invoice_ID = $1 AND il.isActive = 'Y'

GROUP BY
	InOuts,
	COALESCE( io1.DocType, io2.DocType ),
	to_char( COALESCE( io1.DateFrom, io2.DateFrom ), 'DD.MM.YYYY' ),
	COALESCE( io1.DocNo, io2.DocNo ) IS NOT NULL,
	pc.IsHU,
	-- ts: QnD: appending the invoice line description to the product name.
	-- TODO: create a dedicated field for it etc
	il.Description,
	COALESCE( pt.name, p.name ),
	COALESCE(
		CASE WHEN Length( att.Attributes ) > 15
			THEN att.Attributes || E'\n'
			ELSE att.Attributes
		END,
		''
	),
	piip.name,
	il.PriceActual,
	il.PriceEntered,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol),
	COALESCE(puomt.UOMSymbol, puom.UOMSymbol),
	puom.StdPrecision,
	t.rate,
	bpg.IsPrintTax,

	COALESCE( io1.DateFrom, io2.DateFrom ),
	COALESCE( io1.DocNo, io2.DocNo ),
	
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) ,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name),
	p.value,
	p.description,
	i.description,
	c.cursymbol

ORDER BY
	COALESCE( io1.DateFrom, io2.DateFrom ),
	COALESCE( io1.DocNo, io2.DocNo ),
	pc.isHU,
	Name,
	MAX(line)
$$
LANGUAGE sql STABLE
;