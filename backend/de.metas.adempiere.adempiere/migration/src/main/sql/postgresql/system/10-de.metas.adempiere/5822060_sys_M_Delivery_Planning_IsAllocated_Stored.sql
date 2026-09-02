-- M_Delivery_Planning.IsAllocated: convert from a lazy-loading ColumnSQL virtual column (introduced by
-- 5821150_sys_M_Delivery_Planning_Filterability_And_Addresses.sql) into a real, interceptor-maintained
-- stored column.
--
-- Why: the lazy-loading virtual column was the right call while the column was display-only (a WebUI
-- filter), but its usage changed - the working-list precondition now loads a selection as an in-memory
-- list and asks this predicate per row, which turns "lazy loading" into one extra query per row. A real
-- column is read straight off the row: no extra query, no expression, and it is invalidated by its own
-- table's cache reset rather than needing AD_SQLColumn_SourceTableColumn wiring.
--
-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_MigrationScript 5822060 (this file)

-- Business table: the ADD COLUMN + backfill below touch every row, so back it up first.
SELECT backup_table('m_delivery_planning', '_31789_Q7b_IsAllocated');

-- Same physical shape as the table's other boolean flags (IsClosed, Processed): NOT NULL, defaulted
-- 'N', CHECK Y/N.
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning', 'ALTER TABLE public.M_Delivery_Planning ADD COLUMN IsAllocated CHAR(1) DEFAULT ''N'' CHECK (IsAllocated IN (''Y'',''N'')) NOT NULL')
;

-- Backfill: a stored column starts empty (every row just got 'N' from the ADD COLUMN default), so every
-- pre-existing planning must be corrected from the exact EXISTS the ColumnSQL evaluated - otherwise
-- every planning that was already allocated silently reads 'N' after this script runs.
UPDATE M_Delivery_Planning
SET IsAllocated = (CASE
                       WHEN EXISTS (SELECT 1
                                    FROM M_Delivery_Planning_Alloc a
                                    WHERE a.M_Delivery_Planning_ID = M_Delivery_Planning.M_Delivery_Planning_ID
                                      AND a.IsActive = 'Y')
                           THEN 'Y'
                       ELSE 'N'
                   END)
;

-- AD_Column: drop the ColumnSQL, mark non-lazy (the generated getter loses its @Deprecated on the next
-- model regeneration), and sync IsMandatory/DefaultValue to the new physical NOT NULL DEFAULT 'N'
-- column.
UPDATE AD_Column
SET ColumnSQL     = NULL,
    IsLazyLoading = 'N',
    IsMandatory   = 'Y',
    DefaultValue  = 'N',
    Updated       = TO_TIMESTAMP('2026-09-03 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy     = 100
WHERE AD_Column_ID = 593412 /* M_Delivery_Planning.IsAllocated, from 5821150 */
;

-- The cross-table cache-invalidation entry (5821150) existed only because the column read
-- M_Delivery_Planning_Alloc through a ColumnSQL; a real column is invalidated by its own table's cache
-- reset, so the entry is now dead bookkeeping rather than a working wire - retire it instead of leaving
-- it stale.
UPDATE AD_SQLColumn_SourceTableColumn
SET IsActive  = 'N',
    Updated   = TO_TIMESTAMP('2026-09-03 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_SQLColumn_SourceTableColumn_ID = 540225 /* IsAllocated <- M_Delivery_Planning_Alloc, from 5821150 */
;
