SELECT db_drop_functions('*.invoice_rksv_json')
;

CREATE OR REPLACE FUNCTION invoice_rksv_json(p_invoice_id text)
    RETURNS TABLE
            (
                invoice  text,
                amount   numeric,
                customer text,
                payment  char
            )
    LANGUAGE sql
    STABLE
AS
$$
SELECT invoice.documentno,
       invoice.grandtotal,
       CONCAT(bp.name, ', ', location.postal, ' ', location.city),
       invoice.paymentrule
FROM c_invoice invoice
         JOIN c_bpartner bp ON invoice.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN c_bpartner_location blocation ON bp.c_bpartner_id = blocation.c_bpartner_id
         LEFT JOIN c_location location ON blocation.c_location_id = location.c_location_id
WHERE invoice.c_invoice_id = p_invoice_id::numeric
  AND invoice.paymentrule IN ('B', 'K')
  AND invoice.issotrx = 'Y'
  AND invoice.processed = 'Y'
  AND invoice.docstatus IN ('CO', 'CL');
$$
;
