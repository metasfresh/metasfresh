-- Run mode: SWING_CLIENT

-- 2026-01-22T13:55:26.845Z
/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE public.AD_Column ADD COLUMN IsShowFilterInactiveValues CHAR(1) DEFAULT ''N'' CHECK (IsShowFilterInactiveValues IN (''Y'',''N'')) NOT NULL')
;

-- 2026-01-22T14:05:28.927Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN IsShowFilterInactiveValues CHAR(1)')
;

