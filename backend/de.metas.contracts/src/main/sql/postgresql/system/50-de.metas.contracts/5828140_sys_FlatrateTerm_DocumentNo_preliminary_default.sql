-- C_Flatrate_Term.DocumentNo is mandatory in the window "Verträge" (540359) and in the contract tab of the business partner window.
-- Without a default value (5828100) the WebUI no longer saves a new contract term ("FillMandatory DocumentNo"),
-- because - unlike on orders or invoices - no document type callout sets a preliminary number on a contract term.
-- The default "<>" is the preliminary document number marker: it satisfies the mandatory check,
-- and on the first save a value wrapped in "<" and ">" is replaced by the next number of the sequence DocumentNo_C_Flatrate_Term.
UPDATE AD_Column
SET DefaultValue='<>', Updated=TO_TIMESTAMP('2026-10-06 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 557170 -- C_Flatrate_Term.DocumentNo
;
