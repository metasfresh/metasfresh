-- 2021-06-03T13:28:57.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_BP_BankAccount_InvoiceAutoAllocateRule (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BP_BankAccount_ID NUMERIC(10) NOT NULL, C_BP_BankAccount_InvoiceAutoAllocateRule_ID NUMERIC(10) NOT NULL, C_DocType_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CBPBankAccount_CBPBankAccountInvoiceAutoAllocateRule FOREIGN KEY (C_BP_BankAccount_ID) REFERENCES public.C_BP_BankAccount DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_BP_BankAccount_InvoiceAutoAllocateRule_Key PRIMARY KEY (C_BP_BankAccount_InvoiceAutoAllocateRule_ID), CONSTRAINT CDocType_CBPBankAccountInvoiceAutoAllocateRule FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-06-03T13:50:57.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount_InvoiceAutoAllocateRule','ALTER TABLE C_BP_BankAccount_InvoiceAutoAllocateRule DROP COLUMN IF EXISTS C_DocType_ID')
;

-- 2021-06-03T13:52:31.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount_InvoiceAutoAllocateRule','ALTER TABLE public.C_BP_BankAccount_InvoiceAutoAllocateRule ADD COLUMN C_DocTypeInvoice_ID NUMERIC(10) NOT NULL')
;
