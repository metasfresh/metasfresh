DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details(IN record_id numeric, IN AD_Language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Vendor_Returns_Details(IN record_id numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
(
	Attributes text,
	Name character varying, 
	HUQty numeric,
	HUName text,
	MovementQty numeric,
	UOMSymbol character varying, 
	StdPrecision numeric(10,0),
	QualityDiscountPercent numeric,
	QualityNote character varying, 
	Description Character Varying,
	bp_product_no character varying(30),
	bp_product_name character varying(100),
	line numeric
)
AS
$$	

SELECT 
	Attributes,
	Name, -- product
	SUM( HUQty ) AS HUQty,
	HUName,
	SUM( MovementQty ) AS MovementQty,
	UOMSymbol,
	StdPrecision,
	QualityDiscountPercent,
	QualityNote,
	iol.Description,
	bp_product_no,
	bp_product_name,
	max(iol.line) as line

FROM

(
SELECT
	a.Attributes,
	COALESCE(pt.Name, p.name) AS Name,
	pi.HUQty AS HUQty,
	CASE WHEN pi.HUQty IS NULL THEN NULL ELSE pi.name END AS HUName,
	iol.MovementQty AS MovementQty,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	uom.StdPrecision,
	orig_iol.QualityDiscountPercent,
	orig_iol.QualityNote,
	iol.Description,
	-- in case there is no C_BPartner_Product, fallback to the default ones
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,

	iol.line as line
	

FROM M_Inout io --vendor return

INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID

INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
				AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'

-- Unit of measurement & its translation
INNER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'

LEFT OUTER JOIN LATERAL
		(
			SELECT 	String_Agg( DISTINCT pifb.name, E'\n' ORDER BY pifb.name ) AS name, SUM(lutuconf.qtytu) as HUQty
			FROM	M_HU_Assignment asgn
				INNER JOIN M_HU lu ON asgn.M_HU_ID = lu.M_HU_ID --
				INNER JOIN M_HU_LUTU_Configuration lutuconf ON lu.M_HU_LUTU_Configuration_ID = lutuconf.M_HU_LUTU_Configuration_ID
				INNER JOIN M_HU_PI_Item_Product pifb ON lutuconf.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
			WHERE	asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) )) AND asgn.isActive = 'Y' AND asgn.Record_ID = iol.M_InOutLine_ID
					AND pifb.name != 'VirtualPI'
		) pi ON TRUE

-- Attributes
LEFT OUTER JOIN LATERAL
	(
	SELECT	/** Jasper Servlet runs under linux, jasper client under windows (mostly). both have different fonts therefore, when
		  * having more than 2 lines, the field is too short to display all lines in the windows font to avoid this I add an extra
		  * line as soon as the attributes string has more than 15 characters (which is still very likely to fit in two lines)
		  */
		CASE WHEN Length(Attributes) > 15 THEN Attributes || E'\n' ELSE Attributes END AS Attributes, M_AttributeSetInstance_ID, M_InOutLine_ID
	FROM	(
			SELECT 	String_agg ( att.ai_value, ', ' ORDER BY att.M_AttributeSetInstance_ID, length(att.ai_value), att.ai_value) AS Attributes,
				att.M_AttributeSetInstance_ID, iol.M_InOutLine_ID
			FROM 	Report.fresh_Attributes att
			WHERE 	-- Label, Herkunft, Aktionen, Marke (ADR), HU_BestBeforeDate, MHD, M_Material_Tracking_ID
				att.at_Value IN ('1000002', '1000001', '1000030', '1000015', 'HU_BestBeforeDate', '1000021', 'M_Material_Tracking_ID')
				/* currently those flags are set to be correct for purchase invoices. we need something
				 * more flexible for all kinds of documents
				 * att.at_IsAttrDocumentRelevant = 'Y' */
				  AND iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
			GROUP BY	att.M_AttributeSetInstance_ID, iol.M_InOutLine_ID
		) x
) a ON true


LEFT JOIN LATERAL
(
	SELECT ic.QualityDiscountPercent, rs.QualityNote
	FROM M_InOutLine original_iol
	INNER JOIN M_InOutLine dispute_iol ON dispute_iol.M_InOut_ID = original_iol.M_InOut_ID AND dispute_iol.C_OrderLine_ID = original_iol.C_OrderLine_ID AND dispute_iol.isInDispute='Y' AND dispute_iol.isActive = 'Y'
	INNER JOIN M_ReceiptSchedule_Alloc rsa ON dispute_iol.M_InOutLine_ID = rsa.M_InOutLine_ID AND rsa.isActive = 'Y'
	-- QualityNote (is taken from receipt schedule because there the quality is already aggregated)
	INNER JOIN M_ReceiptSchedule rs ON rsa.M_ReceiptSchedule_ID = rs.M_ReceiptSchedule_ID AND rs.isActive = 'Y'
	-- Quality (is taken from Invoice candidates because there the quality is already aggregated)
	LEFT OUTER JOIN C_InvoiceCandidate_InOutLine iciol ON original_iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iciol.isActive = 'Y'
	LEFT OUTER JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID = iciol.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
	
	WHERE iol.vendorreturn_origin_inoutline_id = original_iol.M_InOutLine_ID AND original_iol.isActive = 'Y'
)
orig_iol ON TRUE




WHERE
		pc.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID) AND io.M_InOut_ID = $1
)iol


GROUP BY 
	Attributes,
	Name, -- product
	HUName,
	UOMSymbol,
	StdPrecision,
	QualityDiscountPercent,
	QualityNote,
	iol.Description,
	bp_product_no,
	bp_product_name	
$$
LANGUAGE sql STABLE;