-- Attach AD_Process 585647 (selection-driven CSV export) to the existing Intrastat window
-- (AD_Window 542107, AD_Table 542587 / Intrastat_Report_Detail_V) so the export action
-- appears in that window's Actions menu.
--
-- Idempotent: the INSERT ... SELECT ... WHERE NOT EXISTS guard ensures a re-run doesn't
-- create a duplicate AD_Table_Process row.

INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Process_ID, AD_Table_ID, EntityType,
    WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction,
    WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
SELECT 541659 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-06 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-06 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    585647, 542587, 'D',
    'Y', 'Y', 'N',
    'N', 'N'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_Table_Process
    WHERE AD_Process_ID = 585647 AND AD_Table_ID = 542587);
