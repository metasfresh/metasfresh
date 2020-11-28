--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
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
	isInDispute character(1)
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
	isInDispute
FROM
	-- Sub select to get all in out lines we need. They are in a subselect so we can neatly group by the attributes
	-- (Otherwise we'd have to copy the attributes-sub-select in the group by clause. Hint: That would suck)
	(
	SELECT
		iol.M_InOutLine_ID,
		Attributes,
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
				WHERE EXISTS ( SELECT 0 FROM M_ReceiptSchedule_Alloc rsa
					WHERE rsa.m_receiptschedule_id=rs.m_receiptschedule_id AND rs.C_OrderLine_ID = ic.C_OrderLine_ID
			)
		) END AS QualityNote,
		iol.isInDispute
	FROM
		-- All In Outs linked to the order
		(
		SELECT DISTINCT
			io.*
		FROM
			-- All In Out Lines directly linked to the order
			M_InOutLine iol
			INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
			INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
		WHERE
			ol.C_Order_ID = $1
			AND io.DocStatus IN ('CO','CL')
		) io
		--
		/*
		 * Now, join all in out lines of those in outs. Might be more than the in out lines selected in the previous
		 * sub select because not all in out lines are linked to the order (e.g Packing material). NOTE: Due to the
		 * process we assume, that all lines of one inout belong to only one order
		 */
		INNER JOIN M_InOutLine iol 			ON io.M_InOut_ID = iol.M_InOut_ID
		-- Product and its translation
		INNER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID
		LEFT OUTER JOIN M_Product_Trl pt 		ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2
		INNER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
		-- Unit of measurement & its translation
		INNER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID
		LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
		-- Packing Instruction
		LEFT OUTER JOIN
		(
			SELECT 	String_Agg( DISTINCT pifb.name, E'\n' ORDER BY pifb.name ) AS name,
				iol.M_InOutLine_ID
			FROM	M_InOutLine iol
				INNER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
					AND asgn.Record_ID = iol.M_InOutLine_ID
				INNER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
				INNER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
			WHERE	pifb.name != 'VirtualPI'
				AND  EXISTS (Select 1 from  M_InOutLine iol2 
						INNER JOIN C_OrderLine ol ON iol2.C_OrderLine_ID = ol.C_OrderLine_ID 
					WHERE	ol.C_Order_ID = $1 and iol.M_InOut_ID = iol2.M_InOut_ID)
			GROUP BY M_InOutLine_ID
		) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
		-- Attributes
		LEFT OUTER JOIN
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
					INNER JOIN M_InOutLine iol ON att.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID
					INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
					WHERE 	-- Label, Herkunft, Aktionen, Marke (ADR), MHD, M_Material_Tracking_ID
						att.at_Value IN ('1000002', '1000001', '1000030', '1000015', '1000021', 'M_Material_Tracking_ID')
						/* currently those flags are set to be correct for purchase invoices. we need something
						 * more flexible for all kinds of documents
						 * att.at_IsAttrDocumentRelevant = 'Y' */
						  AND ol.C_Order_ID = $1
					GROUP BY	att.M_AttributeSetInstance_ID, iol.M_InOutLine_ID
				) x
		) a ON iol.M_AttributeSetInstance_ID = a.M_AttributeSetInstance_ID AND iol.M_InOutLine_ID = a.M_InOutLine_ID

		-- Quality (is taken from Invoice candidates because there the quality is already aggregated)
		LEFT OUTER JOIN C_InvoiceCandidate_InOutLine iciol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID
		LEFT OUTER JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID = iciol.C_Invoice_Candidate_ID
	WHERE
		pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	) iol
GROUP BY
	Attributes,
	Name, -- product
	HUName,
	UOMSymbol,
	QualityDiscountPercent,
	QualityNote,
	isInDispute,
	StdPrecision
ORDER BY
	Name, MIN(M_InOutLine_ID)

$$
LANGUAGE sql STABLE	
;
