CREATE OR REPLACE VIEW public.c_orderline_id_with_missing_shipmentschedule_v AS
SELECT ol.c_orderline_id
FROM c_orderline ol
         JOIN c_order o ON ol.c_order_id = o.c_order_id
         JOIN c_doctype dt ON dt.c_doctype_id = o.c_doctype_id
         JOIN m_product p ON p.m_product_id = ol.m_product_id AND p.producttype = 'I'
WHERE true
  AND ol.qtyordered <> ol.qtydelivered
  AND NOT EXISTS ( SELECT 1 FROM m_shipmentschedule s	WHERE s.c_orderline_id = ol.c_orderline_id)
  AND dt.docbasetype = 'SOO'
  AND (dt.docsubtype NOT IN (
                             'ON', -- Proposal (not binding)
                             'OB', -- Quotation (binding)
                             'WR', -- POS Order
                             'FA', -- Frame Agreement
                             'CE'  -- Cost Estimate
    ))
  AND o.issotrx = 'Y'
  AND o.docstatus = 'CO'
  AND NOT EXISTS (
        SELECT 1
        FROM m_iolcandhandler_log log
        WHERE log.m_iolcandhandler_id = 1000000 AND log.ad_table_id = 260 AND log.record_id = ol.c_orderline_id AND log.isactive = 'Y'
    );

ALTER TABLE public.c_orderline_id_with_missing_shipmentschedule_v OWNER TO metasfresh;
COMMENT ON VIEW public.c_orderline_id_with_missing_shipmentschedule_v
    IS 'Selects C_OrderLines that should have a shipment schedule and do not have one yet.
Issue gh #992:
  * Only consider C_OrderLines whose product type is "I" (Item). Please keep this in sync with IProductBL.isItem() and IProduct.isService().
  * Note that the delivery rule for invoice candidates is already set to "Immediate" for such products.
Also see task 08896';
