-- 2019-01-23T17:50:58.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Product_Certificate (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Label VARCHAR(255), LabelCertificationAuthority VARCHAR(255), M_Product_Certificate_ID NUMERIC(10) NOT NULL, M_Product_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Product_Certificate_Key PRIMARY KEY (M_Product_Certificate_ID), CONSTRAINT MProduct_MProductCertificate FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

ALTER TABLE M_Product_Certificate DROP COLUMN M_Product_ID;





-- 2019-01-23T17:59:53.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product_Certificate','ALTER TABLE public.M_Product_Certificate ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2019-01-23T17:59:54.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_Certificate ADD CONSTRAINT MProduct_MProductCertificate FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

