

-- 2019-05-17T13:39:46.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_Customs_Invoice (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_User_ID NUMERIC(10), C_BPartner_ID NUMERIC(10), C_BPartner_Location_ID NUMERIC(10), C_Currency_ID NUMERIC(10), C_Customs_Invoice_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateInvoiced TIMESTAMP WITHOUT TIME ZONE, DocAction CHAR(2) DEFAULT 'CO' NOT NULL, DocStatus VARCHAR(2) DEFAULT 'DR' NOT NULL, DocumentNo VARCHAR(40), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, Processing CHAR(1), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADUser_CCustomsInvoice FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartner_CCustomsInvoice FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartnerLocation_CCustomsInvoice FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CCurrency_CCustomsInvoice FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Customs_Invoice_Key PRIMARY KEY (C_Customs_Invoice_ID))
;




-- 2019-05-20T14:42:23.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Customs_Invoice','ALTER TABLE public.C_Customs_Invoice ADD COLUMN C_DocType_ID NUMERIC(10) NOT NULL')
;






-- 2019-05-20T14:42:23.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Customs_Invoice ADD CONSTRAINT CDocType_CCustomsInvoice FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;





-- 2019-05-20T15:01:41.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice','C_Currency_ID','NUMERIC(10)',null,null)
;

-- 2019-05-20T15:01:41.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice','C_Currency_ID',null,'NOT NULL',null)
;





-- 2019-05-20T15:02:19.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice','DocumentNo','VARCHAR(40)',null,null)
;

-- 2019-05-20T15:02:19.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice','DocumentNo',null,'NOT NULL',null)
;




