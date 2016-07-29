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
	io_p_value character varying(255),
	io_p_name character varying(40),
	io_qty numeric,
	io_uom character varying(10)
	)
AS
$$

select 
o.dateordered as dateordered,
o.documentno as documentno,
o.bp_value as o_bp_value,
o.bp_name as o_bp_name,
o.p_value as o_p_value,
o.p_name as o_p_name,
o.attributes,
o.price,
o.price * o.qty as total,
o.currency,
o.uomsymbol as o_uom,
o.qty as o_qty,
io.movementdate,
io.orderdocumentno,
io.bp_value as io_bp_value,
io.bp_name as io_bp_name,
io.p_value as io_p_value,
io.p_name as io_p_name,
io.qty as io_qty,
io.uomsymbol as io_uom

 from de_metas_endcustomer_fresh_reports.order_trace_report($1, $2, $3, $4, $5, $6, $7, $8) o
JOIN ( select * from de_metas_endcustomer_fresh_reports.inout_trace_report($1, $2, $3, $4, $5, $6, case when $7 = 'Y' THEN 'N' ELSE 'Y' END, $8)
) io ON io.link_orderline_id = o.link_orderline_id


$$
LANGUAGE sql STABLE;