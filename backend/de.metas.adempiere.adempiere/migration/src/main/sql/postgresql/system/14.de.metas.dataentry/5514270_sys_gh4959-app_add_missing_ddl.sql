-- 2019-02-28T17:40:46.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DataEntry_Section','ALTER TABLE public.DataEntry_Section ADD COLUMN DataEntry_SubGroup_ID NUMERIC(10) NOT NULL')
;

-- 2019-02-28T17:40:46.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DataEntry_Section ADD CONSTRAINT DataEntrySubGroup_DataEntrySection FOREIGN KEY (DataEntry_SubGroup_ID) REFERENCES public.DataEntry_SubGroup DEFERRABLE INITIALLY DEFERRED
;

-- 2019-02-28T17:41:10.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DataEntry_Field','ALTER TABLE public.DataEntry_Field ADD COLUMN DataEntry_Line_ID NUMERIC(10) NOT NULL')
;

-- 2019-02-28T17:41:10.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DataEntry_Field ADD CONSTRAINT DataEntryLine_DataEntryField FOREIGN KEY (DataEntry_Line_ID) REFERENCES public.DataEntry_Line DEFERRABLE INITIALLY DEFERRED
;

-- 2019-02-28T17:41:19.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-02-28 17:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541182
;

-- 2019-02-28T17:41:22.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-02-28 17:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541179
;

