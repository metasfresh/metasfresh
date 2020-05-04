-- Function: de_metas_endcustomer_fresh_reports.docs_sales_invoice_details_compensation_subgroup(numeric, character varying, numeric)

--DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_invoice_details_compensation_subgroup(numeric, character varying, numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_sales_invoice_details_compensation_subgroup(
    IN c_invoice_id numeric,
    IN ad_language character varying,
    IN c_order_compensationgroup_id numeric)
  RETURNS TABLE(c_order_compensationgroup_id numeric, groupname character varying, isgroupcompensationline character, groupcompensationpercentage numeric, groupcompensationbaseamt numeric, groupcompensationamttype character varying, current_compensation_number bigint, number_of_compensations bigint, linenetamt numeric, QtyInvoicedInPriceUOM numeric, p_name character varying, iscountable character, total_per_line numeric, description character varying, productdescription character varying, last_total numeric) AS
$BODY$
SELECT rez.*,
SUM(case when current_compensation_number = number_of_compensations THEN rez.total_per_line
ELSE 0 END) OVER()

as last_total 
FROM(
SELECT
	ol.c_order_compensationgroup_id,
	cg.name as groupname,
	ol.isgroupcompensationline,
	ol.groupcompensationpercentage,
	ol.groupcompensationbaseamt,
	ol.groupcompensationamttype,
	row_number() over (partition by ol.c_order_compensationgroup_id order by ol.line) AS current_compensation_number,
	count(ol.c_order_compensationgroup_id) over (partition by ol.c_order_compensationgroup_id) as number_of_compensations,
	il.linenetamt,
	il.QtyInvoicedInPriceUOM ,
	p.name,
	
	(CASE WHEN ol.groupcompensationamttype = 'P' AND (ol.groupcompensationpercentage is null or ol.groupcompensationpercentage = 0) THEN 'N'
	     WHEN ol.groupcompensationamttype = 'Q'  AND (il.QtyInvoicedInPriceUOM is null or il.QtyInvoicedInPriceUOM = 0) THEN 'N'
	     ELSE 'Y' END) as isCountable,
	ol.groupcompensationbaseamt + ol.linenetamt as total_per_line,
	il.description,
	il.productdescription
FROM
	C_InvoiceLine il
	
	
	-- Get Flatrateterm
	LEFT JOIN c_invoice_line_alloc ila on ila.c_invoiceline_id = il.c_invoiceline_id 
	LEFT JOIN c_invoice_candidate ic on ila.C_invoice_candidate_id = ic.C_invoice_candidate_id
	LEFT JOIN c_flatrate_term ft on ic.ad_table_id = get_table_id('C_Flatrate_Term') and ic.record_id = ft.c_flatrate_term_id
	
	--Get orderline
	LEFT JOIN C_OrderLine ol ON COALESCE(il.C_OrderLine_ID,ft.C_OrderLine_Term_ID) = ol.C_OrderLine_ID AND ol.isActive = 'Y'
	LEFT JOIN c_order_compensationgroup cg ON ol.c_order_compensationgroup_id = cg.c_order_compensationgroup_id  AND cg.isActive = 'Y' 

	INNER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
	
WHERE
	il.C_Invoice_ID = $1 AND il.isActive = 'Y'
	AND ol.isgroupcompensationline = 'Y' and ol.c_order_compensationgroup_id = $3

ORDER BY
	il.line
) rez 
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;