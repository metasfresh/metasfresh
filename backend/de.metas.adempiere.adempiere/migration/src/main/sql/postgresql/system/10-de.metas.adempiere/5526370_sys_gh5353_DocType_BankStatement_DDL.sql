-- 2019-06-25T17:52:26.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BankStatement','ALTER TABLE public.C_BankStatement ADD COLUMN C_DocType_ID NUMERIC(10)')
;

-- 2019-06-25T17:52:26.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BankStatement ADD CONSTRAINT CDocType_CBankStatement FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;