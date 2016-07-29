DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.inout_trace_report(IN AD_Org_ID numeric, IN C_Period_St_ID numeric, IN C_Period_End_ID numeric, IN C_Activity_ID numeric, IN C_BPartner_ID numeric, IN M_Product_ID numeric, IN IsSOTrx character varying, IN M_AttributeSetInstance_ID numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.inout_trace_report(IN AD_Org_ID numeric, IN C_Period_St_ID numeric, IN C_Period_End_ID numeric, IN C_Activity_ID numeric, IN C_BPartner_ID numeric, IN M_Product_ID numeric, IN IsSOTrx character varying, IN M_AttributeSetInstance_ID numeric)
	RETURNS TABLE ( 
	movementDate timestamp without time zone,
	OrderDocumentNo character varying(30),
	bp_value character varying(40),
	bp_name character varying(60),
	
	p_value character varying(255),
	p_name character varying(40),
	qty numeric,
	uomsymbol character varying(10),
	link_orderline_id numeric
	)
AS
$$
SELECT
	io.movementDate,
	o.documentNo AS order_documentno,
	bp.value as bp_value,
	bp.name as bp_name,
	p.value as p_value,
	p.name AS p_name,
	
	SUM(iol.qtyEntered),
	uom.uomsymbol,
	CASE WHEN o.issotrx='Y' THEN ol.link_orderline_id ELSE ol.c_orderline_id  END AS link_orderline_id

FROM M_InOut io

INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
INNER JOIN C_UOM uom ON iol.C_UOM_ID = uom.C_UOM_ID 
INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID

INNER JOIN C_Period per_st ON $2 = per_st.C_Period_ID
INNER JOIN C_Period per_end ON $3 = per_end.C_Period_ID

LEFT OUTER JOIN	(SELECT	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
		GROUP BY M_AttributeSetInstance_ID
		) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID

WHERE 
	io.AD_Org_ID = (CASE WHEN $1 IS NULL THEN io.AD_Org_ID ELSE $1 END)
	AND per_st.startdate::date <= io.movementDate::date
	AND per_end.enddate::date >= io.movementDate::date
	AND iol.C_Activity_ID = (CASE WHEN $4 IS NULL THEN iol.C_Activity_ID ELSE $4 END)
	AND io.C_BPartner_ID = (CASE WHEN $5 IS NULL THEN io.C_BPartner_ID ELSE $5 END)
	AND iol.M_Product_ID = (CASE WHEN $6 IS NULL THEN iol.M_Product_ID ELSE $6 END)
	AND iol.M_AttributeSetInstance_ID = (CASE WHEN $8 IS NULL THEN iol.M_AttributeSetInstance_ID ELSE $8 END)
	AND io.IsSOTrx = $7
	AND io.isActive='Y'
	AND io.DocStatus IN ('CO','CL')
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')

GROUP BY 
	io.movementDate,
	o.documentNo,
	bp.value,
	bp.name,
	p.value,
	p.name,
	uom.uomsymbol,
	ol.link_orderline_id,
	o.issotrx,
	ol.c_orderline_id

$$
LANGUAGE sql STABLE;

