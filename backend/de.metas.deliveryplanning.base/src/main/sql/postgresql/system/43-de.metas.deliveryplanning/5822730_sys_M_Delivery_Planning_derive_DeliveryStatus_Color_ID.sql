-- M_Delivery_Planning.DeliveryStatus_Color_ID (585615) becomes a virtual column: the delivered state,
-- rendered. The row-varying half of the expression is byte-for-byte the condition IsDelivered (593413)
-- already uses - M_Delivery_Planning.M_InOut_ID is not null - so the grid's colour and its
-- delivered flag cannot disagree; there is one fact and two renderings of it.
--
-- Runs AFTER 5822720, which drops the physical column this replaces.
--
-- The two colour lookups are UNCORRELATED sub-selects on purpose: they reference no column of the
-- outer row, so PostgreSQL hoists each into an InitPlan and evaluates it ONCE per query instead of
-- once per row. A per-row sysconfig + AD_Color lookup would be a real regression on a grid that
-- shows this column first across a whole result set. Verified with EXPLAIN: two InitPlans, and the
-- planning scan itself carries no SubPlan.
--
-- getColor_ID_By_SysConfig is the core function that already resolves "sysconfig name -> colour name
-- -> AD_Color_ID" (and maps a '-' value to no colour), so the resolution rule is not restated here.
-- The two sysconfig rows it reads are seeded by 5822710 with the values that used to be hardcoded
-- fallbacks, so a configuration change still takes effect and no colour id is hardcoded - they are
-- instance-specific.
--
-- IsLazyLoading='N': a lazy column is skipped at load and fetched on access, i.e. one extra query per
-- row on exactly the grid this column leads. IsUpdateable='N' because it is derived - nobody writes
-- it any more (the generated setter now throws).
--
-- No AD_SQLColumn_SourceTableColumn entries, and that is not the usual omission: the only input that
-- varies per row is the planning's OWN M_InOut_ID, so the planning table's own cache reset already
-- refreshes it. The other two inputs (AD_SysConfig, AD_Color) are instance configuration shared by
-- every row - an admin editing them is a config change that needs a cache reset anyway, and no
-- per-record source mapping could express "every planning" meaningfully. Same shape as
-- C_Invoice_Candidate.DeliveryStatusColor_ID (589828), the core precedent for a virtual colour column.

UPDATE AD_Column
SET ColumnSQL     = '(case when M_Delivery_Planning.M_InOut_ID is not null
       then (select getColor_ID_By_SysConfig(''M_DeliveryPlanning.DeliveredColorName''))
       else (select getColor_ID_By_SysConfig(''M_DeliveryPlanning.NotDeliveredColorName'')) end)',
    IsLazyLoading = 'N',
    IsUpdateable  = 'N',
    Updated       = TO_TIMESTAMP('2026-09-04 10:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy     = 100
WHERE AD_Column_ID = 585615;
