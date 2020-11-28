update c_invoice_line_alloc ila set qtyinvoicedinuom=il.qtyentered, c_uom_id=il.c_uom_id
from c_invoiceline il
where il.c_invoiceline_id=ila.c_invoiceline_id
and ila.c_uom_id is null;

