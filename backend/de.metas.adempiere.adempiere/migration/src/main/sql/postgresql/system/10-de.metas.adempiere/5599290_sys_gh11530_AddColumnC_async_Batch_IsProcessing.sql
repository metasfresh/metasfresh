-- 2021-07-21T09:28:12.540Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Async_Batch','ALTER TABLE public.C_Async_Batch ADD COLUMN IsProcessing CHAR(1) DEFAULT ''N'' CHECK (IsProcessing IN (''Y'',''N'')) NOT NULL')
;