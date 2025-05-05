
/*
 We need this UC in order to select individual invoices via API.
 If is should fail, you can identify the culprits with

SELECT externalid, ad_org_id, COUNT(c_invoice_id), ARRAY_AGG(c_invoice_id)
FROM c_invoice
WHERE TRUE
  AND isactive = 'Y'
  AND docstatus IN ('CO', 'CL')
  AND externalid IS NOT NULL
GROUP BY externalid, ad_org_id
HAVING COUNT(c_invoice_id) > 1;
*/
create unique index "c_invoice_uc_externalId_org"
    on c_invoice (externalid, ad_org_id)
    where isactive = 'Y' and docstatus in ('CO', 'CL');

comment on index "c_invoice_uc_externalId_org" is 'externalIds need to be unique among completed/closed invoices';
