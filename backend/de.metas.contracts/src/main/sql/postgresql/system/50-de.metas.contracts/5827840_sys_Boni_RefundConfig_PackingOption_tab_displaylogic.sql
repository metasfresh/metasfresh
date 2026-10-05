-- Boni refund engine: show the packaging-option tab (549512) only for refund conditions, like its sibling tab 541106.
-- Type_Conditions is a field of the header tab, so the logic is resolvable at tab level 1.
UPDATE AD_Tab
SET DisplayLogic = '@Type_Conditions@=''Refund''', Updated = TO_TIMESTAMP('2026-10-05 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549512
;
