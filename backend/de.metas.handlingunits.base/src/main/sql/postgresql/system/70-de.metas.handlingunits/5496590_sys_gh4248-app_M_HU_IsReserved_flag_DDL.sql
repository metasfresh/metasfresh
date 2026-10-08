
-- 2018-06-26T09:54:44.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ SELECT public.db_alter_table('M_HU','ALTER TABLE public.M_HU ADD COLUMN IsReserved CHAR(1) DEFAULT ''N'' CHECK (IsReserved IN (''Y'',''N'')) NOT NULL')
--;

/* DDL */ SELECT public.db_alter_table('M_HU','ALTER TABLE public.M_HU ADD COLUMN IsReserved CHAR(1) CHECK (IsReserved IN (''Y'',''N''))')
;
ALTER TABLE public.m_hu ALTER COLUMN IsReserved SET DEFAULT 'N';

SELECT "de.metas.async".executeSqlAsync(
	'UPDATE M_HU SET IsReserved=''N'' WHERE M_HU_ID IN (select M_HU_ID from M_HU where IsReserved IS NULL ORDER BY M_HU_ID DESC LIMIT 100000)',
	'ALTER TABLE public.m_hu ALTER COLUMN IsReserved SET NOT NULL'
);
