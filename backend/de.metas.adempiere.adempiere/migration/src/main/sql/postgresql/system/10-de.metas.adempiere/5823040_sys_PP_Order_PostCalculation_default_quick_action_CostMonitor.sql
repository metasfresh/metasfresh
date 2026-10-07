-- Make "Nachberechnung" (PP_Order_PostCalculation) the default quick action of the
-- "Kostenüberwachung Fertigung" window (AD_Window 542175) -- and ONLY of that window.
--
-- Both rows below are AD_Window_ID-scoped on purpose: the table-wide rows (AD_Window_ID IS NULL) stay
-- untouched, so every other PP_Order window keeps the quick actions it has today. Per process, the
-- window-scoped row wins over the table-wide one.
--
-- Two rows, not one: the button the user sees is the first entry of the actions list, so promoting
-- PP_Order_PostCalculation alone would leave this window with TWO default quick actions, tie-broken by
-- the language-dependent caption. Hence the second row, demoting the other one on this window only.

-- PP_Order_PostCalculation (AD_Process 585649) -> default quick action on AD_Window 542175.
INSERT INTO AD_Table_Process (
    AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, EntityType,
    AD_Table_ID, AD_Process_ID, AD_Window_ID, AD_Tab_ID,
    WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default,
    WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction,
    Created, CreatedBy, Updated, UpdatedBy
) VALUES (
    541689 /*From ID Server*/, 0, 0, 'Y', 'EE01',
    53027, 585649, 542175, NULL,
    'Y', 'Y',
    'Y', 'Y', 'N',
    TO_TIMESTAMP('2026-09-08 09:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-08 09:10:00','YYYY-MM-DD HH24:MI:SS'), 100
);

-- WEBUI_PP_Order_IssueReceipt_Launcher (AD_Process 540772) -> still a quick action on AD_Window 542175,
-- but no longer the DEFAULT one.
INSERT INTO AD_Table_Process (
    AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, EntityType,
    AD_Table_ID, AD_Process_ID, AD_Window_ID, AD_Tab_ID,
    WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default,
    WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction,
    Created, CreatedBy, Updated, UpdatedBy
) VALUES (
    541690 /*From ID Server*/, 0, 0, 'Y', 'de.metas.ui.web',
    53027, 540772, 542175, NULL,
    'Y', 'N',
    'Y', 'Y', 'N',
    TO_TIMESTAMP('2026-09-08 09:10:01','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-08 09:10:01','YYYY-MM-DD HH24:MI:SS'), 100
);
