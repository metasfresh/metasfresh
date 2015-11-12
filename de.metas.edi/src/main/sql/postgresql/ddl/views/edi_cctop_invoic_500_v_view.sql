
--
-- Don't select VendorProductNo (it's deprecated), but ProductNo if a record with UsedForCustomer=Y
--
-- View: edi_cctop_invoic_500_v

DROP VIEW IF EXISTS edi_cctop_invoic_500_v;

CREATE OR REPLACE VIEW edi_cctop_invoic_500_v AS 
SELECT 
	SUM(il.qtyentered) AS qtyinvoiced, -- we output the Qty in the customer's UOM (i.e. QtyEntered)
	MIN(il.c_invoiceline_id) AS edi_cctop_invoic_500_v_id, 
	SUM(il.linenetamt) AS linenetamt, 
	MIN(il.line) AS line, 
	il.c_invoice_id, 
	il.c_invoice_id AS edi_cctop_invoic_v_id, 
	il.priceactual, il.pricelist, 
	pp.upc, -- in the invoic the customer expects the CU-EANs, not the TU-EANs (so we don't select upc_TU here)
	p.value, 
	pp.productno AS vendorproductno, 
	substr(p.name::text, 1, 35) AS name, 
	substr(p.name::text, 36, 70) AS name2, 
	t.rate, 
	CASE
		WHEN u.x12de355::text = 'TU'::text THEN 'PCE'::character varying
		ELSE u.x12de355
	END AS eancom_uom, 
	CASE
		WHEN u_price.x12de355::text = 'TU'::text THEN 'PCE'::character varying
		ELSE u_price.x12de355
	END AS eancom_price_uom, 
	CASE
		WHEN t.rate = 0::numeric THEN 'Y'::text
		ELSE ''::text
	END AS taxfree, 
	c.iso_code, 
	il.ad_client_id, 
	il.ad_org_id, 
	MIN(il.created) AS created, 
	MIN(il.createdby)::numeric(10,0) AS createdBy, 
	MAX(il.updated) AS updated, 
	MAX(il.updatedby)::numeric(10,0) AS updatedby, 
	il.isactive, 
	CASE pc.value
		WHEN 'Leergut'::text THEN 'P'::text
		ELSE ''::text
	END AS leergut, 
	COALESCE(pp.productdescription, pp.description, p.description, p.name) AS ProductDescription, -- fallback from customer's description to our own description

	-- task 09182, showing reference and line for the original order, but falling back to the invoice's own reference and line
	COALESCE(o.POReference, i.POReference) AS OrderPOReference,
	COALESCE(ol.line, il.Line) AS OrderLine,

	il.C_OrderLine_ID, -- grouping by C_OrderLine_ID to make sure that we don't aggregate too much
	SUM(il.taxamtinfo) AS taxamtinfo
FROM c_invoiceline il
   LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.IsActive='Y'
	 LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id -- task 09182
   LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
	 LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
   LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
	LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
   LEFT JOIN c_bpartner_product pp ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id AND pp.IsActive='Y'
   LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
   LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id -- do export the il's UOM because that's the UOM the customere ordered in (and it matches Qtyentered)
   LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE true
	AND il.m_product_id IS NOT NULL
	AND il.IsActive='Y'
GROUP BY 
	il.c_invoice_id,
	il.PriceActual, il.PriceList, 
	pp.upc,
	p.Value, 
	pp.ProductNo,
	substr(p.name::text, 1, 35), -- name
	substr(p.name::text, 36, 70), -- name2
	t.rate,
	eancom_uom, 
	eancom_price_uom,
	taxfree, c.iso_code, il.ad_client_id, il.ad_org_id, 
	il.IsActive, 
	leergut, 
	COALESCE(pp.productdescription, pp.description, p.description, p.name), -- productdescription^
	OrderPOReference,
	OrderLine, 
	il.C_OrderLine_ID -- grouping by C_OrderLine_ID to make sure that we don't aggregate too much
ORDER BY OrderLine
;
COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
';


