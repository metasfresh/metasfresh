-- Boni refund engine: the packaging-option tab (549512) gets the same display logic as its sibling tab 541106 (refund conditions only).
-- Note: the WebUI honours a tab display logic only when it is constant false (LayoutFactory.layoutSingleRow),
-- so this logic has no effect there: the tab shows for every condition type, the same as tab 541106.
UPDATE AD_Tab
SET DisplayLogic = '@Type_Conditions@=''Refund''', Updated = TO_TIMESTAMP('2026-10-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549512
;
