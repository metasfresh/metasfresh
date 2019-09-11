-- View: public.edi_cctop_invoic_500_v

DROP VIEW IF EXISTS public.edi_cctop_invoic_500_v;

CREATE OR REPLACE VIEW public.edi_cctop_invoic_500_v AS
 SELECT sum(il.qtyentered) AS qtyinvoiced,
    min(il.c_invoiceline_id) AS edi_cctop_invoic_500_v_id,
    sum(il.linenetamt) AS linenetamt,
    min(il.line) AS line,
    il.c_invoice_id,
    il.c_invoice_id AS edi_cctop_invoic_v_id,
    il.priceactual,
    il.pricelist,
    pp.upc,
    p.value,
    pp.productno AS vendorproductno,
    substr(p.name, 1, 35) AS name,
    substr(p.name, 36, 70) AS name2,
    t.rate,
    CASE
            WHEN u.x12de355 = 'TU' THEN 'PCE'::character varying
            ELSE u.x12de355
    END AS eancom_uom,
    CASE
            WHEN u_price.x12de355 = 'TU' THEN 'PCE'::character varying
            ELSE u_price.x12de355
    END AS eancom_price_uom,
    CASE
            WHEN t.rate = 0::numeric THEN 'Y'
            ELSE ''
    END AS taxfree,
    c.iso_code,
    il.ad_client_id,
    il.ad_org_id,
    min(il.created) AS created,
    min(il.createdby)::numeric(10,0) AS createdby,
    max(il.updated) AS updated,
    max(il.updatedby)::numeric(10,0) AS updatedby,
    il.isactive,
        CASE pc.value
            WHEN 'Leergut' THEN 'P'
            ELSE ''
        END AS leergut,
    COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)::character varying AS productdescription,
    COALESCE(ol.line, il.line) AS orderline,
    COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40) AS orderporeference,
    il.c_orderline_id,
    sum(il.taxamtinfo) AS taxamtinfo
   FROM c_invoiceline il
     LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = 'Y'::bpchar
     LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
     LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
     LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
     LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
     LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
     LEFT JOIN c_bpartner_product pp ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id AND pp.isactive = 'Y'::bpchar
     LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
     LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
     LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
  WHERE true AND il.m_product_id IS NOT NULL AND il.isactive = 'Y'::bpchar AND il.qtyentered <> 0::numeric
  GROUP BY 
  	il.c_invoice_id, 
	il.priceactual, 
	il.pricelist, pp.upc, p.value, pp.productno, 
	(substr(p.name, 1, 35)), 
	(substr(p.name, 36, 70)), 
	t.rate, 
	(CASE
            WHEN u.x12de355 = 'TU' THEN 'PCE'::character varying
            ELSE u.x12de355
        END), 
	(CASE
            WHEN u_price.x12de355 = 'TU' THEN 'PCE'::character varying
            ELSE u_price.x12de355
        END), 
	(CASE
            WHEN t.rate = 0::numeric THEN 'Y'
            ELSE ''
        END), 
	c.iso_code, 
	il.ad_client_id, 
	il.ad_org_id, 
	il.isactive, 
	(CASE pc.value
            WHEN 'Leergut' THEN 'P'
            ELSE ''
        END), 
	(COALESCE(NULLIF(pp.productdescription, ''), NULLIF(pp.description, ''), NULLIF(p.description, ''), p.name)), 
	(COALESCE(NULLIF(o.poreference, ''), i.poreference)), 
	(COALESCE(ol.line, il.line)), 
	il.c_orderline_id
  ORDER BY (COALESCE(ol.line, il.line));

COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
';
