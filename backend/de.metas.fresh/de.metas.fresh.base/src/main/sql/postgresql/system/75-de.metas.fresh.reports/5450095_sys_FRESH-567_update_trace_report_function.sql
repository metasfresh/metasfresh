DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.trace_report(IN AD_Org_ID numeric, IN C_Period_St_ID numeric, IN C_Period_End_ID numeric, IN C_Activity_ID numeric, IN C_BPartner_ID numeric, IN M_Product_ID numeric, IN IsSOTrx character varying, IN M_AttributeSetInstance_ID numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.trace_report(IN AD_Org_ID numeric, IN C_Period_St_ID numeric, IN C_Period_End_ID numeric, IN C_Activity_ID numeric, IN C_BPartner_ID numeric, IN M_Product_ID numeric, IN IsSOTrx character varying, IN M_AttributeSetInstance_ID numeric)
	RETURNS TABLE ( 
	dateordered timestamp without time zone,
	documentno character varying(30),
	o_bp_value character varying(40),
	o_bp_name character varying(60),
	o_p_value character varying(255),
	o_p_name character varying(40),
	attributes text,
	price numeric,
	total numeric,
	currency character(3),
	o_uom character varying(10),
	o_qty numeric,
	movementdate timestamp without time zone,
	orderdocumentno character varying,
	io_bp_value character varying(40),
	io_bp_name character varying(60),
	io_qty numeric,
	io_uom character varying(10)
	)
AS
$$

SELECT distinct
	o.DateOrdered,
	o.DocumentNo,
	bp.Value AS bp_value,
	bp.Name AS bp_name,
	p.Value AS p_value,
	p.Name AS p_name,
	(select attributes_value from de_metas_endcustomer_fresh_reports.get_attributes_value(ol.M_AttributeSetInstance_ID)) AS attributes,
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
	c_uom.uomsymbol as io_uom

FROM C_Order o

INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID
INNER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID 
INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID

		
INNER JOIN C_Period per_st ON $2 = per_st.C_Period_ID 
INNER JOIN C_Period per_end ON $3 = per_end.C_Period_ID 

--order's inout and hus
INNER JOIN M_InOutLine o_iol ON ol.C_OrderLine_ID = o_iol.C_OrderLine_ID
INNER JOIN M_InOut o_io ON o_iol.M_InOut_ID = o_io.M_InOut_ID
INNER JOIN M_HU_Assignment huas ON huas.ad_table_id = get_table_id('M_InOutLine') AND huas.Record_ID = o_iol.M_InOutLine_ID
INNER JOIN M_HU hu ON huas.m_hu_id = hu.m_hu_id

--find splitted hus if exists
LEFT OUTER JOIN M_HU_Trx_Line trx_line ON trx_line.M_HU_ID = huas.M_TU_HU_ID

--counter inout's hus and inout 
INNER JOIN M_HU_Assignment huas_io ON (huas_io.M_HU_ID = huas.M_HU_ID OR huas_io.M_TU_HU_ID IN (select distinct hu_id from "de.metas.handlingunits".recursive_hu_trace(trx_line.M_HU_Trx_Line_ID::integer))) AND huas_io.ad_table_id = get_table_id('M_InOutLine') AND huas_io.M_HU_Assignment_ID != huas.M_HU_Assignment_ID AND huas_io.Record_ID != o_iol.M_InOutLine_ID

INNER JOIN M_InOutLine c_iol ON huas_io.Record_ID = c_iol.M_InOutLine_ID 
INNER JOIN M_InOut c_io ON c_iol.M_InOut_ID = c_io.M_InOut_ID AND c_io.isSOTrx != o_io.isSOTrx


--data for inout 
INNER JOIN C_OrderLine c_ol ON c_iol.C_OrderLine_ID = c_ol.C_OrderLine_ID
INNER JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID
INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_io.C_BPartner_ID
INNER JOIN C_UOM c_uom ON c_uom.C_UOM_ID = c_iol.C_UOM_ID



WHERE 
	o.AD_Org_ID = (CASE WHEN $1 IS NULL THEN o.AD_Org_ID ELSE $1 END)
	AND per_st.startdate::date <= o.DateOrdered::date
	AND per_end.enddate::date >= o.DateOrdered::date
	AND ol.C_Activity_ID = (CASE WHEN $4 IS NULL THEN ol.C_Activity_ID ELSE $4 END)
	AND o.C_BPartner_ID = (CASE WHEN $5 IS NULL THEN o.C_BPartner_ID ELSE $5 END)
	AND ol.M_Product_ID = (CASE WHEN $6 IS NULL THEN ol.M_Product_ID ELSE $6 END)
	AND ol.M_AttributeSetInstance_ID = (CASE WHEN null IS NULL THEN ol.M_AttributeSetInstance_ID ELSE null END)
	AND o.isSOTrx= $7
	AND o.isActive ='Y' AND o_io.isActive ='Y' AND c_o.isActive ='Y' AND c_io.isActive ='Y'
	AND o.docStatus IN ('CO', 'CL')
	AND o_io.docStatus IN ('CO', 'CL')
	AND c_o.docStatus IN ('CO', 'CL')
	AND c_io.docStatus IN ('CO', 'CL')
	AND pc.M_Product_Category_ID != (SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID')
	AND  ol.M_AttributeSetInstance_ID = (CASE WHEN $8 IS NULL THEN ol.M_AttributeSetInstance_ID ELSE $8 END)


ORDER BY 

	o.DateOrdered,
	o.DocumentNo,
	bp.Value,
	p.Value,
	c_io.movementdate,
	c_o.documentno,
	c_bp.value

$$
LANGUAGE sql STABLE;
