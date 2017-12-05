
-- 2017-09-15T17:15:17.998
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ad_ref_table','ALTER TABLE public.AD_Ref_Table ADD COLUMN IsReferenceTarget CHAR(1) DEFAULT ''N'' CHECK (IsReferenceTarget IN (''Y'',''N'')) NOT NULL')
;