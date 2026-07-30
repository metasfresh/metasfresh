-- Intrastat preview window -- attach Intrastat_Export process as in-window action
--
-- Wires the existing AT RTIC export process (AD_Process_ID = 585508,
-- Intrastat_Export) as a table-level action on the new Intrastat preview
-- table (AD_Table_ID = 542632, Intrastat_Preview_V) so it appears as an
-- action button inside the new preview window.
--
-- Allocated from central ID server:
--   AD_Table_Process_ID = 541656

INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Process_ID, AD_Table_ID, EntityType,
    WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction,
    WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (541656 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    585508, 542632, 'D',
    'Y', 'Y', 'N',
    'N', 'N');
