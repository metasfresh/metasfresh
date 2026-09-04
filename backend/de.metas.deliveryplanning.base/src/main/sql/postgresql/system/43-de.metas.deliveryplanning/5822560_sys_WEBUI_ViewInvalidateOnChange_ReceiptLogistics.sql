-- Wire up grid invalidation for the receipt-logistics window (AD_Window 542190, backed by the view
-- RV_ReceiptLogistics). The window is a plain grid over a view, so an edit to either source table --
-- M_Delivery_Planning (branch one, "planned" rows) or M_ReceiptSchedule (both branches) -- must
-- invalidate any open view of this window, or the WebUI keeps serving cached rows until the user
-- reloads by hand. This is the WEBUI_ViewInvalidateOnChange mechanism (grid views), not
-- AD_SQLColumn_SourceTableColumn (virtual columns on a real table) -- the two are not interchangeable.
-- Does not touch the existing rows registered for other windows.

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540003 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Delivery_Planning')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Delivery_Planning')
      AND existing.IsActive='Y'
)
;

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540004 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-04 09:00:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-04 09:00:01','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ReceiptSchedule')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ReceiptSchedule')
      AND existing.IsActive='Y'
)
;
