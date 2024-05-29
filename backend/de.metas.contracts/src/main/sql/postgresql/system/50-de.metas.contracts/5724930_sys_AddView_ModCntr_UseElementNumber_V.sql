DROP VIEW ModCntr_UseElementNumber_V;

CREATE OR REPLACE VIEW ModCntr_UseElementNumber_V AS
SELECT t.modularcontracthandlertype,
       il.productname,
       il.qtyinvoicedinpriceuom,
       il.priceactual,
       il.linenetamt,
       log.datetrx,
       log.qty,
       log.userelementnumber1,
       log.userelementnumber2,
       uom.x12de355 AS uom,
       log.amount,
       p.name       AS initialProduct,
       p.value      AS initialProduceValue,
       c_invoice_id
FROM c_invoiceLine il
         INNER JOIN c_invoice_line_alloc ila ON il.c_invoiceline_id = ila.c_invoiceline_id
         INNER JOIN C_Invoice_candidate ic ON ila.c_invoice_candidate_id = ic.c_invoice_candidate_id
         INNER JOIN modcntr_log log ON ic.c_invoice_candidate_id = log.c_invoice_candidate_id
         INNER JOIN modcntr_type t ON log.modcntr_type_id = t.modcntr_type_id
         INNER JOIN m_product p ON log.initial_product_id = p.m_product_id
         INNER JOIN c_uom uom ON log.c_uom_id = uom.c_uom_id
where t.modularcontracthandlertype='AverageAddedValueOnShippedQuantity'
ORDER BY log.datetrx, p.name
;