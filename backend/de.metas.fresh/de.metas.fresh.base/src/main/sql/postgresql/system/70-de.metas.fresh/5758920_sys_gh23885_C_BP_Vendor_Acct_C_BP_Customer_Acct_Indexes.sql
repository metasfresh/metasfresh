DROP INDEX IF EXISTS C_BP_Vendor_Acct_C_BPartner_ID
;

DROP INDEX IF EXISTS C_BP_Customer_Acct_C_BPartner_ID
;

CREATE INDEX C_BP_Vendor_Acct_C_BPartner_ID ON C_BP_Vendor_Acct (C_BPartner_ID)
;

CREATE INDEX C_BP_Customer_Acct_C_BPartner_ID ON C_BP_Customer_Acct (C_BPartner_ID)
;
