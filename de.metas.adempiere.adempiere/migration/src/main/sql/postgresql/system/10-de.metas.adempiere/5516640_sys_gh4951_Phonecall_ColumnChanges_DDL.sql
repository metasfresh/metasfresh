ALTER TABLE C_Phonecall_Schedule RENAME COLUMN Processed to IsOrdered;




-- 2019-03-20T11:51:57.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Phonecall_Schedule','ALTER TABLE public.C_Phonecall_Schedule ADD COLUMN IsCalled CHAR(1) DEFAULT ''N'' CHECK (IsCalled IN (''Y'',''N'')) NOT NULL')
;







-- 2019-03-20T11:55:28.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Phonecall_Schedule','ALTER TABLE public.C_Phonecall_Schedule ADD COLUMN SalesRep_ID NUMERIC(10)')
;

-- 2019-03-20T11:55:28.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Phonecall_Schedule ADD CONSTRAINT SalesRep_CPhonecallSchedule FOREIGN KEY (SalesRep_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;





