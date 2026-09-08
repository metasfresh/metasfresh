-- Stop the cost-monitor window opening with the HasCostDifference filter already applied.
--
-- 5823070 set AD_Column.FilterDefaultValue='Y' on column 593510 so the window would open on the
-- orders that carry a difference. That is wrong, and not merely as a matter of taste:
--
-- FilterDefaultValue is not a suggestion the user can ignore, it is applied as a live filter.
-- ViewRestController.createView calls setUseAutoFilters(true) unconditionally on every view-create,
-- SqlViewFactory.createAutoFilters() turns every auto-filter descriptor into a real DocumentFilter,
-- and GridFieldVO sources that initial value straight from this column. So the window would open
-- with HasCostDifference='Y' in force.
--
-- What that hides is the point. The window lists completed-but-not-closed manufacturing orders, and
-- it now carries TWO actions: Nachberechnung, which posts the residual of an unbalanced order, and
-- PP_Order_CloseSelection, which closes orders that have nothing left to post. Defaulting the filter
-- to 'Y' would open the window with every BALANCED order hidden -- exactly the population the close
-- action exists for. A window whose default state hides the orders one of its own actions targets is
-- a trap, not a convenience.
--
-- So the window opens unfiltered and shows the whole work list. The filter stays available; applying
-- it is the controller's decision, not the window's.

-- 2026-09-08T00:00:00.000Z
UPDATE AD_Column SET FilterDefaultValue=NULL,Updated=TO_TIMESTAMP('2026-09-08 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593510
;
