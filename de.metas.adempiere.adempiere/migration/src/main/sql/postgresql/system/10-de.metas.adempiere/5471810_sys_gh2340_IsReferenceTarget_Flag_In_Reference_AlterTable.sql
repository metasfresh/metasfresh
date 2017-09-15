



-- 2017-09-15T12:02:58.580
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ad_reference','ALTER TABLE public.AD_Reference ADD COLUMN IsReferenceTarget CHAR(1) DEFAULT ''N'' CHECK (IsReferenceTarget IN (''Y'',''N'')) NOT NULL')
;

