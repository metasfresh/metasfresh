-- DueDate — make C_Invoice.DueDate fields read-only in the UI
-- Server-side enforcement lives in the C_Invoice interceptor; this UPDATE is generic on AD_Column_ID
-- so it covers the base fields and any customer-side field rows.
UPDATE AD_Field
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2026-06-02 00:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=584270 AND IsActive='Y' AND IsReadOnly='N';
