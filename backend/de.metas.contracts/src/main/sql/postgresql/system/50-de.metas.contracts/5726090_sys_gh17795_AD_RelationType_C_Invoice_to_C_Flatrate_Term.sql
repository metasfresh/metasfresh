
-- 2024-06-13T07:48:20.691Z
UPDATE AD_Ref_Table SET WhereClause='C_Flatrate_Term.C_Flatrate_Term_ID in ( select il.C_Flatrate_Term_ID from C_InvoiceLine il where il.C_Invoice_ID = @C_Invoice_ID/0@ )',Updated=TO_TIMESTAMP('2024-06-13 09:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541872
;
