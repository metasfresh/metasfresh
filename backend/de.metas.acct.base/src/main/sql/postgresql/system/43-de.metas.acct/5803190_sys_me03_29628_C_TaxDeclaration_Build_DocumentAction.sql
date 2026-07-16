-- Tax Declaration: expose C_TaxDeclaration_Build process in the document's Actions menu
-- AD_Table_Process row links AD_Table 818 (C_TaxDeclaration) <-> AD_Process 585615 (Build)
-- with WEBUI_DocumentAction='Y' so it shows in SingleDocumentActionsMenu.

INSERT INTO AD_Table_Process (
    AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Process_ID,
    WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default, WEBUI_IncludedTabTopAction,
    EntityType
)
VALUES (
    541643 /*From ID Server*/, 0, 0, 'Y',
    TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    818, 585615,
    'Y', 'N', 'N', 'N', 'N',
    'de.metas.acct'
);
