-- Make "Nachberechnung" (PP_Order_PostCalculation) the default quick action of the
-- "Kostenüberwachung Fertigung" window (AD_Window 542175) -- and ONLY of that window.
--
-- Both rows below are AD_Window_ID-scoped on purpose. The pre-existing table-wide rows
-- (AD_Window_ID IS NULL) are left untouched, so the "Produktionsauftrag" window (AD_Window 53009)
-- and every other PP_Order window keep the quick actions they have today.
--
-- Resolution: related processes are looked up per (AD_Table_ID, AD_Window_ID IN (NULL, <window>)),
-- ordered by AD_Window_ID ascending NULLS LAST, and de-duplicated per AD_Process_ID keeping the
-- first hit -- so the window-scoped row wins over the table-wide row for the same process.
--
-- Two rows, not one: the quick-action button the user sees is the FIRST entry of the actions list,
-- which is sorted by (enabled, sortNo, isDefaultQuickAction, isQuickAction, caption). Promoting
-- PP_Order_PostCalculation alone would leave TWO default quick actions on this window, because the
-- table-wide row for WEBUI_PP_Order_IssueReceipt_Launcher is also flagged default and its
-- preconditions are satisfied here. The tie would then be broken by caption, which is
-- language-dependent and resolves the WRONG way in en_US ("Issue/Receipt" < "Post calculation").
-- The second row therefore demotes the launcher to a non-default quick action on this window only.
--
-- IDs allocated from idserver.metas.de on 2026-09-08:
--   AD_Table_Process 541689 (PP_Order_PostCalculation, window-scoped, default quick action)
--   AD_Table_Process 541690 (WEBUI_PP_Order_IssueReceipt_Launcher, window-scoped, demoted)

-- PP_Order_PostCalculation (AD_Process 585649) -> default quick action on AD_Window 542175.
-- EntityType mirrors the table-wide row of the same process (AD_Table_Process 541660, 'EE01').
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
-- but no longer the DEFAULT one. Mirrors the table-wide row AD_Table_Process 540558 in every other
-- respect (EntityType 'de.metas.ui.web', all display places), differing only in the default flag.
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
