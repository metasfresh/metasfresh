DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Header_QR(IN p_C_Invoice_ID numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Header_QR(IN p_C_Invoice_ID numeric)
    RETURNS text
    STABLE
    LANGUAGE sql
AS
$$
SELECT 'BCD' || E'\n' ||
       '001' || E'\n' ||
       '1' || E'\n' ||
       'SCT' || E'\n' ||
       COALESCE(org_ba.org_bank_swift, '') || E'\n' ||
       COALESCE(org_bp.name, '') || E'\n' ||
       COALESCE(REPLACE(org_ba.org_bank_iban, ' ', ''), '') || E'\n' ||
        cur.iso_code || TO_CHAR(i.grandtotal, 'FM999999999990.00') || E'\n' ||
       E'\n' ||
       i.documentno AS QR_Code

FROM C_Invoice i
         INNER JOIN AD_Org org ON i.AD_Org_ID = org.AD_Org_ID
         INNER JOIN C_BPartner org_bp ON org.AD_Org_ID = org_bp.AD_OrgBP_ID
         INNER JOIN report.Fresh_Org_BankAccount(org.AD_Org_ID) org_ba ON TRUE
         INNER JOIN C_Currency cur ON i.C_Currency_ID = cur.C_Currency_ID

WHERE i.C_Invoice_ID = p_C_Invoice_ID

-- Enforce exactly one result
LIMIT 1;
$$
;
