-- Bind AD_Process C_Order_Split to AD_Table C_Order so the process appears
-- on the sales-order window (as a document action) and on the orders list
-- view (as a view action). Process-to-table binding lives in AD_Table_Process
-- in this codebase (AD_Process itself has no AD_Table_ID column).

INSERT INTO AD_Table_Process (
    AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Process_ID, EntityType,
    WEBUI_DocumentAction, WEBUI_ViewAction,
    WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default,
    WEBUI_IncludedTabTopAction
) VALUES (
    541645 /*From ID Server*/, 0, 0, 'Y',
    NOW(), 100, NOW(), 100,
    259, 585625 /*From ID Server*/, 'de.metas.order',
    'Y', 'Y',
    'N', 'N',
    'N'
);
