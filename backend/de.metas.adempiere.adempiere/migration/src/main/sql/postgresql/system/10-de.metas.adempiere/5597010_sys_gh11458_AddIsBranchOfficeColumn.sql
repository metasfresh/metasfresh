-- 2021-07-06T14:45:55.700Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BP_Relation','ALTER TABLE public.C_BP_Relation ADD COLUMN IsBranchOffice CHAR(1) DEFAULT ''N'' CHECK (IsBranchOffice IN (''Y'',''N''))')
;
