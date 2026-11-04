-- C_Flatrate_Term.DocumentNo had the default value '0' (window "Verträge" 540359), so a term entered there got the number "0"
-- instead of one from the sequence DocumentNo_C_Flatrate_Term: PO only takes a number from the sequence if the field is blank.
-- Like on the other document tables (C_Order, C_Invoice, ...), the column has no default; the number is assigned on save.
UPDATE AD_Column
SET DefaultValue=NULL, Updated=TO_TIMESTAMP('2026-10-06 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 557170 -- C_Flatrate_Term.DocumentNo
;
