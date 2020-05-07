-- 2019-05-17T15:57:08.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_Customs_Invoice_Line (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Customs_Invoice_ID NUMERIC(10), C_Customs_Invoice_Line_ID NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, InvoicedQty NUMERIC, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, LineNetAmt NUMERIC NOT NULL, M_Product_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCustomsInvoice_CCustomsInvoiceLine FOREIGN KEY (C_Customs_Invoice_ID) REFERENCES public.C_Customs_Invoice DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Customs_Invoice_Line_Key PRIMARY KEY (C_Customs_Invoice_Line_ID), CONSTRAINT CUOM_CCustomsInvoiceLine FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProduct_CCustomsInvoiceLine FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2019-05-20T14:25:18.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Customs_Invoice_Line','ALTER TABLE public.C_Customs_Invoice_Line ADD COLUMN LineNo NUMERIC(10) NOT NULL')
;



-- 2019-05-20T15:06:31.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice_line','M_Product_ID','NUMERIC(10)',null,null)
;


-- 2019-05-20T15:06:31.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice_line','M_Product_ID',null,'NOT NULL',null)
;



-- 2019-05-20T15:07:41.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice_line','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2019-05-20T15:07:41.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_customs_invoice_line','C_UOM_ID',null,'NOT NULL',null)
;




