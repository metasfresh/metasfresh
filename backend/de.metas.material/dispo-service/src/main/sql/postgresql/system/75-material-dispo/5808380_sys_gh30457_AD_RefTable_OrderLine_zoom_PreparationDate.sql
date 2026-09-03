-- me03 #30457 follow-up – Change A: switch zoom date anchor from line DatePromised to header PreparationDate
--
-- Target: AD_Ref_Table for AD_Reference_ID=542100 (C_OrderLine → MD_Stock_PerWeek_V zoom).
-- The zoom relation type 540499 (C_OrderLine_MD_Stock_PerWeek) filters weeks by date.
-- Previously used @DatePromised@ from the source C_OrderLine (the line's promised date).
-- Now uses a subquery into C_Order.PreparationDate via the @C_Order_ID@ already in the clause.
--
-- The @C_Order_ID@ token is already proven resolvable in this zoom context (used by
-- the warehouse subquery in the same WhereClause). No Java change needed.
-- COALESCE(..., now()) null-fallback preserved for orders with null PreparationDate.
--
-- Do NOT edit the historical INSERT script 5806220_sys_gh25618_MD_Stock_PerWeek_V_ID_IsKey.sql —
-- migration scripts are immutable once integrated. This UPDATE script is the correct mechanism.

UPDATE ad_ref_table
SET    whereclause =
           'M_Product_ID = @M_Product_ID@'
           || E'\nAND M_Warehouse_ID = MD_getStockWarehouse( (SELECT o.M_Warehouse_ID FROM C_Order o WHERE o.C_Order_ID = @C_Order_ID/0@) )'
           || E'\nAND WeekStartDate >= date_trunc(''week'', COALESCE( (SELECT o.PreparationDate FROM C_Order o WHERE o.C_Order_ID = @C_Order_ID/0@), now()))::date',
       updated    = TO_TIMESTAMP('2026-06-17 11:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby  = 100
WHERE  ad_reference_id = 542100;
