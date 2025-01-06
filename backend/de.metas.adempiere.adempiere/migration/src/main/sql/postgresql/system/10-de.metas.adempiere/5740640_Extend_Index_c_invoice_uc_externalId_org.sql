
-- Based on the index created at 5732601_Extend_Index_c_invoice_uc_externalId_org.
-- The UC can't be applied to all invoices, because then reversing and recreating an invoice wouldn't work anymore.

DROP INDEX IF EXISTS "c_invoice_uc_externalId_org"
;

CREATE UNIQUE INDEX "c_invoice_uc_externalId_org"
    ON c_invoice (externalid, ad_org_id) WHERE DocStatus NOT IN ('IN' /*Invalid*/, 'NA' /*NotApproved*/, 'VO' /*Voided*/, 'RE' /*Reversed*/, '??' /*Unknown*/)
;

COMMENT ON INDEX "c_invoice_uc_externalId_org" IS 'externalIds need to be unique per-org, only for those invooices that are not somehow "discarded" (voided, reversed, etc).'
;
