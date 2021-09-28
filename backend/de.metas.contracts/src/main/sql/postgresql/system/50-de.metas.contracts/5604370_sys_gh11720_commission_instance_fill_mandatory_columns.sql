-- c_invoice_candidate
UPDATE c_commission_instance instace
SET c_currency_id=invoiceCandidate.c_currency_id,
    c_uom_id=product.c_uom_id,
    qty=invoiceCandidate.qtyOrdered
FROM c_invoice_candidate invoiceCandidate
         inner join m_product product on product.m_product_id = invoiceCandidate.m_product_id
WHERE instace.commissiontrigger_type = 'InvoiceCandidate'
  AND invoiceCandidate.c_invoice_candidate_Id = instace.c_invoice_candidate_id
;

-- c_invoiceLine
UPDATE c_commission_instance instance
SET c_currency_id=invoice.c_currency_id,
    c_uom_id=product.c_uom_id,
    qty=invoiceLine.qtyinvoiced
FROM c_invoiceline invoiceLine
         INNER JOIN M_Product product on product.m_product_id = invoiceLine.m_product_id
         INNER JOIN c_invoice invoice ON invoiceLine.c_invoice_id = invoice.c_invoice_id
WHERE instance.commissiontrigger_type = 'CustomerInvoice'
   OR instance.commissiontrigger_type = 'CustomerCreditmemo'
    AND invoiceLine.c_invoiceline_id = instance.c_invoiceline_id
;

-- c_orderLine
UPDATE c_commission_instance instance
SET c_currency_id=mediatedOrder.c_currency_id,
    c_uom_id=mediatedOrderLine.c_uom_id,
    qty=mediatedOrderLine.qtyOrdered
FROM c_orderline mediatedOrderLine
         INNER JOIN c_order mediatedOrder ON mediatedOrderLine.c_order_id = mediatedOrder.c_order_id
WHERE instance.commissiontrigger_type = 'MediatedOrder'
  AND mediatedOrderLine.c_orderline_id = instance.c_orderline_id
;

-- existing instances where the commission trigger was meanwhile removed
UPDATE c_commission_instance instance
SET c_currency_id=o.c_currency_id,
    c_uom_id=100, /*PCE*/
    qty=0
FROM c_order o
WHERE o.c_order_id = instance.c_order_id
  AND instance.c_currency_id IS NULL
;
UPDATE c_commission_instance instance
SET c_currency_id=i.c_currency_id,
    c_uom_id=100, /*PCE*/
    qty=0
FROM c_invoice i
WHERE i.c_invoice_id = instance.c_invoice_id
  AND instance.c_currency_id IS NULL
;


-- avoid ERROR:  cannot ALTER TABLE "c_commission_instance" because it has pending trigger events
COMMIT;

-- 2021-09-13T16:38:41.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','Qty',null,'NOT NULL',null)
;

-- 2021-09-13T16:38:25.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','C_Currency_ID',null,'NOT NULL',null)
;


-- 2021-09-13T16:38:16.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','C_UOM_ID',null,'NOT NULL',null)
;