-- Run mode: SWING_CLIENT
-- Deactivate the unimplemented PEPPOL EInvoiceType value to prevent users from selecting it.
-- Reference: me03 #30509

UPDATE AD_Ref_List SET IsActive='N', Updated=TO_TIMESTAMP('2026-06-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID=541990 AND Value='P';
