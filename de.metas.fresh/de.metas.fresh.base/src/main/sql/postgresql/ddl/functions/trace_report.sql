-- Function: de_metas_endcustomer_fresh_reports.trace_report(numeric, numeric, numeric, numeric, numeric, numeric, character varying, numeric)

 DROP FUNCTION de_metas_endcustomer_fresh_reports.trace_report(numeric, numeric, numeric, numeric, numeric, numeric, character varying, numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.trace_report(
    IN ad_org_id numeric,
    IN c_period_st_id numeric,
    IN c_period_end_id numeric,
    IN c_activity_id numeric,
    IN c_bpartner_id numeric,
    IN m_product_id numeric,
    IN issotrx character varying,
    IN m_attributesetinstance_id numeric)
  RETURNS TABLE(
		dateordered timestamp without time zone, 
		documentno character varying, 
		o_bp_value character varying, 
		o_bp_name character varying, 
		o_p_value character varying, 
		o_p_name character varying, 
		attributes text, 
		price numeric, 
		total numeric, 
		currency character, 
		o_uom character varying, 
		o_qty numeric, 
		movementdate timestamp without time zone, 
		orderdocumentno character varying, 
		io_bp_value character varying, 
		io_bp_name character varying, 
		io_qty numeric, 
		io_uom character varying
		) 
AS
$$

SELECT 
	a.DateOrdered,
	a.oDocumentNo,
	a.bp_value,
	a.bp_name,
	a.p_value,
	a.p_name,
	a.attributes,
	a.price,	
	a.total,
	a.currency,
	a.uomsymbol,
	a.qty,

	--inout part
	a.movementdate,
	a.orderdocumentno,
	a.io_bp_value,
	a.io_bp_name,
	SUM(a.io_qty),
	a.io_uom
FROM (
SELECT distinct
	o.DateOrdered,
	o.DocumentNo AS ODocumentNo,
	bp.Value AS bp_value,
	bp.Name AS bp_name,
	p.Value AS p_value,
	p.Name AS p_name,
	(select attributes_value from de_metas_endcustomer_fresh_reports.get_attributes_value(o_iol.M_AttributeSetInstance_ID)) AS attributes,
	ol.PriceEntered AS price,	
	ol.linenetamt AS total,
	c.iso_code AS currency,
	uom.uomsymbol,
	ol.qtyEntered AS qty,

	--inout part
	c_io.movementdate,
	c_o.documentno AS orderdocumentno,
	c_bp.value as io_bp_value,
	c_bp.name as io_bp_name,
	c_iol.qtyentered as io_qty,
	c_uom.uomsymbol as io_uom,

	--order and sales inout docno (for tracing purpose). Not used in reports
	c_io.DocumentNo AS counterdocno
	
	

FROM C_Order o

INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID AND ol.isActive = 'Y'
INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

		
INNER JOIN C_Period per_st ON $2 = per_st.C_Period_ID AND per_st.isActive = 'Y'
INNER JOIN C_Period per_end ON $3 = per_end.C_Period_ID AND per_end.isActive = 'Y'

--order's inout and hus
INNER JOIN M_InOutLine o_iol ON ol.C_OrderLine_ID = o_iol.C_OrderLine_ID AND o_iol.isActive = 'Y'
INNER JOIN M_InOut o_io ON o_iol.M_InOut_ID = o_io.M_InOut_ID AND o_io.isActive = 'Y'
INNER JOIN M_HU_Assignment huas ON huas.ad_table_id = get_table_id('M_InOutLine') AND huas.Record_ID = o_iol.M_InOutLine_ID AND huas.isActive = 'Y'

--counter inout's hus and inout 
								
INNER JOIN M_InOutLine c_iol ON c_iol.M_InOutLine_ID = ANY (ARRAY(SELECT Record_ID from "de.metas.handlingunits".hu_assigment_tracking(huas.m_hu_assignment_id) )) AND c_iol.isActive = 'Y'
INNER JOIN M_InOut c_io ON c_iol.M_InOut_ID = c_io.M_InOut_ID AND c_io.isSOTrx != o_io.isSOTrx AND c_io.isActive = 'Y'
INNER JOIN C_InvoiceCandidate_InOutLine iciol ON c_iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iciol.isActive = 'Y'
INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'


--data for inout 
INNER JOIN C_OrderLine c_ol ON c_iol.C_OrderLine_ID = c_ol.C_OrderLine_ID AND c_ol.isActive = 'Y'
INNER JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID AND c_o.isActive = 'Y'
INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_io.C_BPartner_ID AND c_bp.isActive = 'Y'
INNER JOIN C_UOM c_uom ON ic.price_UOM_ID = c_uom.C_UOM_ID AND c_uom.isActive = 'Y'



WHERE 
	o.AD_Org_ID = (CASE WHEN $1 IS NULL THEN o.AD_Org_ID ELSE $1 END)
	AND per_st.startdate::date <= o.DateOrdered::date
	AND per_end.enddate::date >= o.DateOrdered::date
	AND ol.C_Activity_ID = (CASE WHEN $4 IS NULL THEN ol.C_Activity_ID ELSE $4 END)
	AND o.C_BPartner_ID = (CASE WHEN $5 IS NULL THEN o.C_BPartner_ID ELSE $5 END)
	AND ol.M_Product_ID = (CASE WHEN $6 IS NULL THEN ol.M_Product_ID ELSE $6 END)
	AND o.isSOTrx= $7
	AND o.isActive ='Y'
	AND o.docStatus IN ('CO', 'CL')
	AND o_io.docStatus IN ('CO', 'CL')
	AND c_o.docStatus IN ('CO', 'CL')
	AND c_io.docStatus IN ('CO', 'CL')
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y')
	
	AND
		(CASE WHEN EXISTS ( SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $8 )
			-- ... then apply following filter:
			THEN ( 
			-- Take lines where the attributes of the current InoutLine's asi are in the parameter asi and their Values Match
				EXISTS (
					SELECT	0
					FROM	report.fresh_Attributes_ConcreteADR a -- a = Attributes from inout line, pa = Parameter Attributes
					INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID =6487321
					AND a.at_value = pa.at_value -- same attribute
					AND (CASE WHEN a.at_value = '1000015' THEN  ('%'||substring(a.ai_value from 5)||'%' like '%'||substring(pa.ai_value from 5)||'%' OR '%'||substring(pa.ai_value from 5)||'%' like '%'||substring(a.ai_value from 5)||'%' ) --case of adr containing similar value
					ELSE a.ai_value = pa.ai_value END)  --same value
					WHERE	a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID
					)
					-- Dismiss lines where the Attributes in the Parameter are not in the inoutline's asi
				AND NOT EXISTS (
					SELECT	0
					FROM	report.fresh_Attributes_ConcreteADR pa
						LEFT OUTER JOIN report.fresh_Attributes_ConcreteADR a 
						ON a.at_value = pa.at_value AND
						(CASE WHEN a.at_value = '1000015' THEN  ('%'||substring(a.ai_value from 5)||'%' like '%'||substring(pa.ai_value from 5)||'%' OR '%'||substring(pa.ai_value from 5)||'%' like '%'||substring(a.ai_value from 5)||'%' ) --case of adr containing similar value
					ELSE a.ai_value = pa.ai_value END)
							AND a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID
					WHERE	pa.M_AttributeSetInstance_ID = $8
						AND a.M_AttributeSetInstance_ID IS null
						)
					)	
		ELSE TRUE END)
		
	
)a

GROUP BY 
	a.DateOrdered,
	a.oDocumentNo,
	a.bp_value,
	a.bp_name,
	a.p_value,
	a.p_name,
	a.attributes,
	a.price,	
	a.currency,
	a.uomsymbol,
	a.total,
	a.qty,
	--inout part
	a.movementdate,
	a.orderdocumentno,
	a.io_bp_value,
	a.io_bp_name,
	a.io_uom


ORDER BY 

	a.DateOrdered,
	a.oDocumentNo,
	a.bp_value,
	a.p_value,
	a.movementdate,
	a.orderdocumentno,
	a.io_bp_value
$$
  LANGUAGE sql STABLE;
