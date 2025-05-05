UPDATE c_invoice
SET DueDate=paymenttermduedate(c_invoice.c_paymentterm_id, c_invoice.dateinvoiced)
WHERE DueDate IS NULL
;