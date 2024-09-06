/*
 We need this UC in order to select individual invoices via API.
 If is should fail, you can identify the culprits with

SELECT externalid, ad_org_id, COUNT(c_invoice_id), ARRAY_AGG(c_invoice_id)
FROM c_invoice
WHERE TRUE
  AND isactive = 'Y'
  AND externalid IS NOT NULL
GROUP BY externalid, ad_org_id
HAVING COUNT(c_invoice_id) > 1;
*/

DROP INDEX "c_invoice_uc_externalId_org"
;

CREATE UNIQUE INDEX "c_invoice_uc_externalId_org"
    ON c_invoice (externalid, ad_org_id)
    WHERE isactive = 'Y'
;

COMMENT ON INDEX "c_invoice_uc_externalId_org" IS 'externalIds need to be unique '
;

