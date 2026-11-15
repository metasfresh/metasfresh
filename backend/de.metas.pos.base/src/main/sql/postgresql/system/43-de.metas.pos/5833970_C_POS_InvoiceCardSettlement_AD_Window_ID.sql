-- Register the settlement window (AD_Window 542201) as the default window of C_POS_InvoiceCardSettlement,
-- so zooms and record links to a settlement open that window.
UPDATE AD_Table SET AD_Window_ID=542201, Updated=TO_TIMESTAMP('2026-11-09 23:20:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Table_ID=542659 -- C_POS_InvoiceCardSettlement
;
