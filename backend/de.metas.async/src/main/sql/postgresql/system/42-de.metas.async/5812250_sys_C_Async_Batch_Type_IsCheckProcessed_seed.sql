-- Seed IsCheckProcessed='Y' for the exhaustive set of C_Async_Batch_Type rows whose
-- Processed flag is actually consumed downstream (PDF-concat, serial-letter creation,
-- and the two registered note listeners). Idempotent -- keyed on InternalName, not ID.
UPDATE C_Async_Batch_Type
SET IsCheckProcessed='Y', Updated=TO_TIMESTAMP('2026-07-04 10:03:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE InternalName IN ('InvoiceCandidate_Processing','CreateLettersAsync','AutomaticallyInvoicePdfPrinting','PDFPrinting')
;
