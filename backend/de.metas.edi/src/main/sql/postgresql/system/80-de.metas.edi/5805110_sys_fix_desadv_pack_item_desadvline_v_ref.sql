--  Fix slow EDI Lieferavis Pack single-record view.
--
-- AD_Column 589364 (M_InOut_DesadvLine_V_ID on EDI_Desadv_Pack_Item) uses AD_Reference_ID=19
-- (Table Direct), which causes the platform to generate a display lookup subquery against
-- M_InOut_DesadvLine_V. Since migration 5803880 (me03 #29231) rewrote that view to use
-- window functions (row_number() OVER), PostgreSQL cannot push the WHERE predicate past the
-- WindowAgg node, forcing a full ~250K-row scan + external disk sort for every display lookup
-- (~930ms per row in the tab). Additionally, ColumnSQL=(m_inoutline_id) now produces a raw
-- m_inoutline_id that no longer matches the view's synthetic composite PK
-- (m_inoutline_id * 10000000 + ordinal), so the lookup always returns 0 rows.
--
-- Fix: change to AD_Reference_ID=18 (Table) + AD_Reference_Value_ID=295 (M_InOutLine).
-- ColumnSQL=(m_inoutline_id) already produces a valid M_InOutLine_ID, so the display lookup
-- becomes a fast PK index scan on M_InOutLine instead of a full scan of the window-function view.

UPDATE AD_Column
SET AD_Reference_ID       = 18,
    AD_Reference_Value_ID = 295,
    Updated               = '2026-05-27 10:00:00.000000',
    UpdatedBy             = 99
WHERE AD_Column_ID = 589364
;


