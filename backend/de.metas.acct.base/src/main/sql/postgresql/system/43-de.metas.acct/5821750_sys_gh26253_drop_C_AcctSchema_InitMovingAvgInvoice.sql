-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/functions/C_AcctSchema_InitMovingAvgInvoice.sql (deleted in this change)

-- gh26253: drop the function behind the retired "Initialize Moving Average Invoice Costing" process.
-- It seeded M_Cost from the current live cost price, with no cut-off date and no backing
-- M_CostDetail row. That job is done by the M_CostRevaluation document
-- (RevaluationSource=CopyFromCostElement), which seeds as of a cut-off date and leaves a proper
-- cost-detail anchor behind.
DROP FUNCTION IF EXISTS public.C_AcctSchema_InitMovingAvgInvoice(numeric)
;
