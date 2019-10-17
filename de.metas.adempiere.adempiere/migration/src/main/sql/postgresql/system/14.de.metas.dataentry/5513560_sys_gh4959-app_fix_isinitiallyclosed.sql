-- 2019-02-22T06:42:52.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2019-02-22 06:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564207
;

select db_alter_table ('DataEntry_Section', 'ALTER TABLE DataEntry_Section DROP COLUMN IF EXISTS IsInitiallyClosed;');

-- 2019-02-22T07:40:45.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DataEntry_Section','ALTER TABLE public.DataEntry_Section ADD COLUMN IsInitiallyClosed CHAR(1) DEFAULT ''N'' CHECK (IsInitiallyClosed IN (''Y'',''N'')) NOT NULL')
;

COMMIT; --avoid pending trigger events error in the next stmt

-- 2019-02-21T10:39:59.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DataEntry_Section SET IsInitiallyClosed='N' WHERE IsInitiallyClosed IS NULL
;

