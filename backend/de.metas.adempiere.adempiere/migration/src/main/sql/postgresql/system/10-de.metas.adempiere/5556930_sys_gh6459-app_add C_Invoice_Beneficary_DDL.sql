
-- 2020-04-10T05:30:26.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN Beneficiary_BPartner_ID NUMERIC(10)')
;

-- 2020-04-10T05:30:27.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice ADD CONSTRAINT BeneficiaryBPartner_CInvoice FOREIGN KEY (Beneficiary_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;



-- 2020-04-10T05:36:18.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN Beneficiary_Location_ID NUMERIC(10)')
;

-- 2020-04-10T05:36:20.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice ADD CONSTRAINT BeneficiaryLocation_CInvoice FOREIGN KEY (Beneficiary_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;



-- 2020-04-10T10:05:07.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice','Beneficiary_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2020-04-10T10:05:11.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN Beneficiary_Contact_ID NUMERIC(10)')
;

-- 2020-04-10T10:05:11.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice ADD CONSTRAINT BeneficiaryContact_CInvoice FOREIGN KEY (Beneficiary_Contact_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2020-04-10T10:05:21.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice','Beneficiary_Location_ID','NUMERIC(10)',null,null)
;

