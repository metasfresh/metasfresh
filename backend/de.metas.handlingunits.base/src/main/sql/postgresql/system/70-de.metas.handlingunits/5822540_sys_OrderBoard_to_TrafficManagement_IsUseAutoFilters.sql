-- Auftrags-Board -> Traffic Management jump: open the overlay on the schedules that still need a
-- workplace, by letting the target window's own default filters apply.
--
-- The jump process (AD_Process 585657, Value M_Picking_OrderBoard_Overview_v_to_TrafficManagement) was
-- created with IsUseAutoFilters='N', i.e. it showed every schedule the relation resolved. Traffic
-- Management's IsAssigned column carries FilterDefaultValue='N' and is that window's ONLY auto-filter
-- (it is the sole column of M_Picking_Job_Schedule_view with a non-null FilterDefaultValue), so
-- switching this flag to 'Y' makes the overlay open pre-filtered to the not-yet-assigned schedules --
-- intersected, as before, with the relation's own where-clause, which keeps the jump to rows the board
-- itself shows (isassigned='Y' OR qtyonhand>0).
--
-- Deliberately a DEFAULT FILTER rather than a where-clause term: this is a first draft for a customer
-- feedback round, so the restriction has to be the overlay's opening state and NOT a decision baked
-- into the relation. Arriving as a filter value, it is rendered in the overlay's filter bar, and the
-- operator can clear or change it to bring the already-assigned schedules back into view. A
-- where-clause term would give the same opening rows with no way to look past them.
--
-- The relation's target where-clause (AD_Ref_Table for AD_Reference 542136) is deliberately left
-- untouched: its trailing board-visibility test is a hard invariant, not a user preference.
--
-- Ordinary navigation to Traffic Management is unaffected -- IsUseAutoFilters is read per AD_Process
-- when the overlay view is built, not when the window is opened from the menu.

UPDATE AD_Process
SET IsUseAutoFilters = 'Y',
    Updated          = TO_TIMESTAMP('2026-09-03 23:56:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy        = 100
WHERE AD_Process_ID = 585657
;
