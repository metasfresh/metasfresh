WITH invoices AS (SELECT externalid, ad_org_id, COUNT(c_invoice_id), ARRAY_AGG(c_invoice_id) AS ids
                  FROM c_invoice
                  WHERE TRUE
                    AND isactive = 'Y'
                    AND externalid IS NOT NULL
                  GROUP BY externalid, ad_org_id
                  HAVING COUNT(c_invoice_id) > 1),
     invoicesToupdate AS (SELECT inv.c_invoice_id, inv.externalid, inv.docstatus
                          FROM invoices
                                   CROSS JOIN UNNEST(invoices.ids) AS i(id)
                                   JOIN c_invoice inv ON inv.c_invoice_id = i.id)
UPDATE c_invoice
SET externalid=NULL
FROM invoicesToupdate
WHERE c_invoice.c_invoice_id = invoicesToupdate.c_invoice_id
  AND c_invoice.docstatus NOT IN ('CO', 'CL')
;
