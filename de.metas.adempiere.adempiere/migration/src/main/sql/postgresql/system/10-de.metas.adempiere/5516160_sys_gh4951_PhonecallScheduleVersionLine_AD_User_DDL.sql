
-- 2019-03-14T16:28:52.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Phonecall_Schema_Version_Line','ALTER TABLE public.C_Phonecall_Schema_Version_Line ADD COLUMN AD_User_ID NUMERIC(10)')
;

-- 2019-03-14T16:28:52.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Phonecall_Schema_Version_Line ADD CONSTRAINT ADUser_CPhonecallSchemaVersionLine FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

