
SELECT '5575960_sys_gh10446_AD_Column_IsRangeFilter_drop_DDL.sql - Not dropping the column yet, because it is still present in many customer branches;
metas-ts made a reminder for 2021-06-01 to drop it then';

-- 2021-01-11T11:54:51.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ SELECT public.db_alter_table('AD_Column','ALTER TABLE AD_Column DROP COLUMN IF EXISTS IsRangeFilter')
--;

