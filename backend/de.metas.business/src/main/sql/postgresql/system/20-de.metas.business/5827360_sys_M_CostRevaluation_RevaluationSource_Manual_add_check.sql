-- Revaluation source "Calculated" is renamed to "Manual".
-- Step 3 of 3 (DDL): re-create the value check with the new value (dropped by 5827340, data migrated by 5827350).

/* DDL */ SELECT public.db_alter_table('M_CostRevaluation', 'ALTER TABLE public.M_CostRevaluation ADD CONSTRAINT RevaluationSource_Check CHECK (RevaluationSource IN (''Manual'',''CopyFromCostElement''))')
;
