DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details_Without_Attributes ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details_Without_Attributes ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
(
	Name character varying, 
	HUQty numeric,
	HUName text,
	MovementQty numeric,
	UOMSymbol character varying, 
	StdPrecision numeric(10,0),
	QualityDiscountPercent numeric,
	QualityNote character varying, 
	isInDispute character(1),
	Description Character Varying,
	bp_product_no character varying(30),
	bp_product_name character varying(100),
	line numeric,
	best_before_date text,
	lotno character varying,
	p_value character varying(30),
	p_description character varying(255), 
	inout_description character varying(255)
)
AS
$$
SELECT

	Name, -- product
	SUM( HUQty ) AS HUQty,
	HUName,
	SUM( MovementQty ) AS MovementQty,
	UOMSymbol,
	StdPrecision,
	QualityDiscountPercent,
	QualityNote,
	isInDispute,
	iol.Description,
	bp_product_no,
	bp_product_name,
	max(iol.line) as line,
	to_char(best_before_date::date, 'MM.YYYY') AS best_before_date,
	lotno,
	p_value,
	p_description,
	inout_description

FROM
	-- Sub select to get all in out lines we need. They are in a subselect so we can neatly group by the attributes
	-- (Otherwise we'd have to copy the attributes-sub-select in the group by clause. Hint: That would suck)
	(
	SELECT
		iol.M_InOutLine_ID,
		COALESCE(pt.Name, p.name) AS Name,
		iol.QtyEnteredTU AS HUQty,
		CASE WHEN iol.QtyEnteredTU IS NULL THEN NULL ELSE pi.name END AS HUName,
		iol.MovementQty AS MovementQty,
		COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
		uom.StdPrecision,

		CASE WHEN iol.isInDispute = 'N' THEN null ELSE
			COALESCE ( ic.QualityDiscountPercent_Override, ic.QualityDiscountPercent )
		END AS QualityDiscountPercent,
		CASE WHEN iol.isInDispute = 'N' THEN null ELSE
			(
				SELECT rs.QualityNote FROM M_ReceiptSchedule rs
				WHERE rs.isActive = 'Y' AND EXISTS ( SELECT 0 FROM M_ReceiptSchedule_Alloc rsa
					WHERE rsa.m_receiptschedule_id=rs.m_receiptschedule_id AND rs.C_OrderLine_ID = ic.C_OrderLine_ID AND rsa.isActive = 'Y'
			)
		) END AS QualityNote,
		iol.isInDispute,
		CASE WHEN iol.Description IS NOT NULL AND iol.Description != '' THEN  iol.Description ELSE NULL END AS Description,
		-- in case there is no C_BPartner_Product, fallback to the default ones
		COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
		COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
		iol.line,
		a.best_before_date,
		a.lotno,
		p.value AS p_value,
		p.description AS p_description,
		io.inout_description AS inout_description
		
	FROM
		-- All In Outs linked to the order
		(
		SELECT DISTINCT
			io.*, String_agg (io.description, E'\n') over() AS inout_description
		FROM
			-- All In Out Lines directly linked to the order
			M_InOutLine iol
			INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
		WHERE
			ol.C_Order_ID = $1
			AND io.DocStatus IN ('CO','CL') AND iol.isActive = 'Y'
		) io
		--
		/*
		 * Now, join all in out lines of those in outs. Might be more than the in out lines selected in the previous
		 * sub select because not all in out lines are linked to the order (e.g Packing material). NOTE: Due to the
		 * process we assume, that all lines of one inout belong to only one order
		 */
		INNER JOIN M_InOutLine iol 			ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
		LEFT OUTER JOIN C_BPartner bp			ON io.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
		-- Product and its translation
		INNER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
		LEFT OUTER JOIN M_Product_Trl pt 		ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
		LEFT JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
		
		LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
		-- Unit of measurement & its translation
		INNER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
		LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
		-- Packing Instruction
		LEFT OUTER JOIN
		(
			SELECT 	String_Agg( DISTINCT pifb.name, E'\n' ORDER BY pifb.name ) AS name,
				iol.M_InOutLine_ID
			FROM	M_InOutLine iol
				INNER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) )) AND asgn.isActive = 'Y'
					AND asgn.Record_ID = iol.M_InOutLine_ID
				INNER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID --
				INNER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
				INNER JOIN M_HU_PI_Item piit ON pifb.M_HU_PI_Item_ID = piit.M_HU_PI_Item_ID AND piit.isActive = 'Y'
				
			WHERE	piit.M_HU_PI_Version_ID != 101 AND iol.isActive = 'Y'
				AND  EXISTS (Select 1 from  M_InOutLine iol2 
						INNER JOIN C_OrderLine ol ON iol2.C_OrderLine_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
					WHERE	ol.C_Order_ID = $1 and iol.M_InOut_ID = iol2.M_InOut_ID and iol2.isActive = 'Y')
			GROUP BY M_InOutLine_ID
		) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
		-- Attributes
		LEFT OUTER JOIN
		(
			SELECT	
				M_AttributeSetInstance_ID, M_InOutLine_ID, x.best_before_date, x.lotno
			FROM	(
					SELECT
						att.M_AttributeSetInstance_ID, iol.M_InOutLine_ID,
						String_agg (replace(att.ai_value, 'MHD: ', ''), ', ') FILTER (WHERE att.at_value like 'HU_BestBeforeDate') AS best_before_date,
						String_agg(ai_value, ', ') FILTER (WHERE att.at_value like 'Lot-Nummer') AS lotno

					FROM 	Report.fresh_Attributes att
					INNER JOIN M_InOutLine iol ON att.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID AND iol.isActive = 'Y'
					INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID and ol.isActive = 'Y'
					WHERE 	
						att.at_Value IN ('HU_BestBeforeDate','Lot-Nummer')
						
						  AND ol.C_Order_ID = $1
					GROUP BY	att.M_AttributeSetInstance_ID, iol.M_InOutLine_ID
				) x
		) a ON iol.M_AttributeSetInstance_ID = a.M_AttributeSetInstance_ID AND iol.M_InOutLine_ID = a.M_InOutLine_ID

		-- Quality (is taken from Invoice candidates because there the quality is already aggregated)
		LEFT OUTER JOIN C_InvoiceCandidate_InOutLine iciol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iciol.isActive = 'Y'
		LEFT OUTER JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID = iciol.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
	WHERE
		COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
	) iol
GROUP BY

	Name, -- product
	HUName,
	UOMSymbol,
	QualityDiscountPercent,
	QualityNote,
	isInDispute,
	StdPrecision,
	Description,
	bp_product_no,
	bp_product_name,
	best_before_date,
	lotno,
	p_value,
	p_description,
	inout_description

ORDER BY
	Name, MIN(M_InOutLine_ID)

$$
LANGUAGE sql STABLE	
;
