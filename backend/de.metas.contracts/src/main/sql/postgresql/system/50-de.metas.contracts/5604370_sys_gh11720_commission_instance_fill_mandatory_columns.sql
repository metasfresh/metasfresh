-- c_invoice_candidate
UPDATE c_commission_instance instace
SET c_currency_id=invoiceCandidate.c_currency_id,
    c_uom_id=invoiceCandidate.c_uom_id,
    qty=invoiceCandidate.qtyOrdered
FROM c_invoice_candidate AS invoiceCandidate
WHERE instace.commissiontrigger_type = 'InvoiceCandidate'
  AND invoiceCandidate.c_invoice_candidate_Id = instace.c_invoice_candidate_id;

-- c_invoiceLine
UPDATE c_commission_instance instance
SET c_currency_id=invoice.c_currency_id,
    c_uom_id=invoiceLine.c_uom_id,
    qty=invoiceLine.qtyinvoiced
FROM c_invoiceline AS invoiceLine
         INNER JOIN c_invoice invoice ON invoiceLine.c_invoice_id = invoice.c_invoice_id
WHERE instance.commissiontrigger_type = 'CustomerInvoice'
   OR instance.commissiontrigger_type = 'CustomerCreditmemo'
    AND invoiceLine.c_invoiceline_id = instance.c_invoiceline_id;

-- c_orderLine
UPDATE c_commission_instance instance
SET c_currency_id=mediatedOrder.c_currency_id,
    c_uom_id=mediatedOrderLine.c_uom_id,
    qty=mediatedOrderLine.qtyentered
FROM c_orderline AS mediatedOrderLine
         INNER JOIN c_order mediatedOrder ON mediatedOrderLine.c_order_id = mediatedOrder.c_order_id
WHERE instance.commissiontrigger_type = 'MediatedOrder'
  AND mediatedOrderLine.c_orderline_id = instance.c_orderline_id;