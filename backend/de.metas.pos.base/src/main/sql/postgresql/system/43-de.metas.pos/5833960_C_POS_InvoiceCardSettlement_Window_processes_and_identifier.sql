-- POS invoice card settlement back-office window (AD_Window 542201): make the window usable.

-- 1. Offer the retry / resolve / refund processes on the settlement window.
-- They were bound to the window's main tab (AD_Tab 549634). A main-tab document and the grid view look up
-- related processes with no tab, so only AD_Table_Process rows with AD_Tab_ID NULL are found.
-- Window-level processes therefore carry AD_Window_ID only, like every other main-window process registration.
UPDATE AD_Table_Process SET AD_Tab_ID=NULL, Updated=TO_TIMESTAMP('2026-11-09 23:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Table_Process_ID=541704 -- RetryBooking
;

UPDATE AD_Table_Process SET AD_Tab_ID=NULL, Updated=TO_TIMESTAMP('2026-11-09 23:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Table_Process_ID=541705 -- MarkResolvedManually
;

UPDATE AD_Table_Process SET AD_Tab_ID=NULL, Updated=TO_TIMESTAMP('2026-11-09 23:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Table_Process_ID=541706 -- MarkRefunded
;

-- 2. Give the table a record identifier (invoice, then status), so lookups and the record summary show
-- which invoice a settlement belongs to and its state, instead of the bare ID.
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10, Updated=TO_TIMESTAMP('2026-11-09 23:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593799 -- C_POS_InvoiceCardSettlement.C_Invoice_ID
;

UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20, Updated=TO_TIMESTAMP('2026-11-09 23:10:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593809 -- C_POS_InvoiceCardSettlement.Status
;
