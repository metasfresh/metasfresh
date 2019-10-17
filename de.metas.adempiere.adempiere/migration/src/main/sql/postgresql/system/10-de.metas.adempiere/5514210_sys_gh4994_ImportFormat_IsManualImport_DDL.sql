
-- 2019-02-28T13:36:14.206
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('AD_ImpFormat','ALTER TABLE public.AD_ImpFormat ADD COLUMN isManualImport CHAR(1) DEFAULT ''N'' CHECK (isManualImport IN (''Y'',''N'')) NOT NULL')
;

