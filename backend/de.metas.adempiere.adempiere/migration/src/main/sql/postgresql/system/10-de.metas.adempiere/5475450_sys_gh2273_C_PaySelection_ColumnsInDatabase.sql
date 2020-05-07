-- 2017-10-25T13:48:19.483
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_PaySelection','ALTER TABLE public.C_PaySelection ADD COLUMN DocAction CHAR(2) DEFAULT ''CO'' NOT NULL')
;

-- 2017-10-25T13:49:08.216
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_PaySelection','ALTER TABLE public.C_PaySelection ADD COLUMN DocStatus VARCHAR(2) DEFAULT ''DR'' NOT NULL')
;

