UPDATE C_InvoicePaySchedule
SET c_currency_id=i.c_currency_id, updatedby=999, updated=NOW()
FROM c_invoice i
WHERE i.c_invoice_id = C_InvoicePaySchedule.c_invoice_id;

