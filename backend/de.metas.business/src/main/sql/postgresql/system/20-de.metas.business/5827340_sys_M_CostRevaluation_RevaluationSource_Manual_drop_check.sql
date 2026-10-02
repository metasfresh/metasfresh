-- Revaluation source "Calculated" is renamed to "Manual" (the new cost price is entered by the user; nothing is recalculated).
-- Step 1 of 3 (DDL): drop the value check, so the existing rows can take the new value; the column default becomes 'Manual'.
-- Step 2 (5827350) migrates the data and the AD reference list; step 3 (5827360) re-creates the check.

/* DDL */ SELECT public.db_alter_table('M_CostRevaluation', 'ALTER TABLE public.M_CostRevaluation DROP CONSTRAINT IF EXISTS RevaluationSource_Check')
;

INSERT INTO t_alter_column VALUES ('m_costrevaluation', 'RevaluationSource', 'VARCHAR(40)', NULL, 'Manual')
;
