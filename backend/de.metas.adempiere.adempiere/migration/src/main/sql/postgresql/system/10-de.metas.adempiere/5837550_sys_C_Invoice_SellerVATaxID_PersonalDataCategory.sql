-- C_Invoice.SellerVATaxID holds the organisation's own VAT ID (or its representative's): not personal data,
-- the same category as C_Fiscal_Representation.VATaxID, where the value comes from.

-- Column: C_Invoice.SellerVATaxID
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2026-11-27 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=593862
;
