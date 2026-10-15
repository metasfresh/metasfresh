-- The cost-monitor window opens unfiltered, showing the whole work list; applying the
-- HasCostDifference filter is the controller's decision.
--
-- AD_Column.FilterDefaultValue must stay unset on column 593510: it is not a suggestion but a live
-- filter applied on every view-create, and a default of 'Y' would open the window with every BALANCED
-- order hidden - exactly the population the window's bulk-close action exists for.

-- 2026-09-08T00:00:00.000Z
UPDATE AD_Column SET FilterDefaultValue=NULL,Updated=TO_TIMESTAMP('2026-09-08 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593510
;
