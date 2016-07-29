-- gather data from order
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.order_trace_report(IN AD_Org_ID numeric, IN C_Period_St_ID numeric, IN C_Period_End_ID numeric, IN C_Activity_ID numeric, IN C_BPartner_ID numeric, IN M_Product_ID numeric, IN IsSOTrx character varying, IN M_AttributeSetInstance_ID numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.order_trace_report(IN AD_Org_ID numeric, IN C_Period_St_ID numeric, IN C_Period_End_ID numeric, IN C_Activity_ID numeric, IN C_BPartner_ID numeric, IN M_Product_ID numeric, IN IsSOTrx character varying, IN M_AttributeSetInstance_ID numeric)
	RETURNS TABLE ( 
	DateOrdered timestamp without time zone,
	DocumentNo character varying(30),
	bp_value character varying(40),
	bp_name character varying(60),
	p_value character varying(255),
	p_name character varying(40),
	attributes text,
	price numeric,
	currency character(3),
	uomsymbol character varying(10),
	qty numeric,
	link_orderline_id numeric
	)
AS
$$
SELECT 
	o.DateOrdered,
	o.DocumentNo,
	bp.Value AS bp_value,
	bp.Name AS bp_name,
	p.Value AS p_value,
	p.Name AS p_name,
	att.Attributes AS attributes,
	ol.PriceEntered AS price,
	c.iso_code AS currency,
	uom.uomsymbol,
	ol.qtyEntered AS qty,
	CASE WHEN o.issotrx='Y' THEN ol.link_orderline_id ELSE ol.c_orderline_id  END AS link_orderline_id 
	--, ol.linenetamt AS Total

FROM C_Order o

INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
INNER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID 
INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID
LEFT OUTER JOIN	(SELECT	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes, M_AttributeSetInstance_ID FROM Report.fresh_Attributes
		GROUP BY M_AttributeSetInstance_ID
		) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
INNER JOIN C_Period per_st ON $2 = per_st.C_Period_ID
INNER JOIN C_Period per_end ON $3 = per_end.C_Period_ID

WHERE 
	o.AD_Org_ID = (CASE WHEN $1 IS NULL THEN o.AD_Org_ID ELSE $1 END)
	AND per_st.startdate::date <= o.DateOrdered::date
	AND per_end.enddate::date >= o.DateOrdered::date
	AND ol.C_Activity_ID = (CASE WHEN $4 IS NULL THEN ol.C_Activity_ID ELSE $4 END)
	AND o.C_BPartner_ID = (CASE WHEN $5 IS NULL THEN o.C_BPartner_ID ELSE $5 END)
	AND ol.M_Product_ID = (CASE WHEN $6 IS NULL THEN ol.M_Product_ID ELSE $6 END)
	AND ol.M_AttributeSetInstance_ID = (CASE WHEN $8 IS NULL THEN ol.M_AttributeSetInstance_ID ELSE $8 END)
	AND o.isSOTrx=$7
	AND o.isActive ='Y'
	AND o.docStatus IN ('CO', 'CL')
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
$$
LANGUAGE sql STABLE;