

-- 2018-11-27T12:51:09.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN M_AttributeSetInstance_ID NUMERIC(10)')
;

-- 2018-11-27T12:51:10.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner_Product ADD CONSTRAINT MAttributeSetInstance_CBPartnerProduct FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES public.M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED
;
