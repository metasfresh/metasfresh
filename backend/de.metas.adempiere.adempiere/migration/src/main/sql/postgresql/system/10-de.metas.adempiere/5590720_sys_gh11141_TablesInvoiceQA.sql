-- 2021-05-24T10:04:51.794Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Invoice_Verification_Set (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Invoice_Verification_Set_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(60) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Invoice_Verification_Set_Key PRIMARY KEY (C_Invoice_Verification_Set_ID))
;

-- 2021-05-24T11:44:37.359Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Invoice_Verification_SetLine (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_DocType_ID NUMERIC(10), C_Invoice_ID NUMERIC(10), C_InvoiceLine_ID NUMERIC(10), C_InvoiceLine_Tax_ID VARCHAR(10), C_Invoice_Verification_Set_ID NUMERIC(10), C_Invoice_Verification_SetLine_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, RelevantDate TIMESTAMP WITHOUT TIME ZONE, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CDocType_CInvoiceVerificationSetLine FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoice_CInvoiceVerificationSetLine FOREIGN KEY (C_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoiceLine_CInvoiceVerificationSetLine FOREIGN KEY (C_InvoiceLine_ID) REFERENCES public.C_InvoiceLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoiceVerificationSet_CInvoiceVerificationSetLine FOREIGN KEY (C_Invoice_Verification_Set_ID) REFERENCES public.C_Invoice_Verification_Set DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Invoice_Verification_SetLine_Key PRIMARY KEY (C_Invoice_Verification_SetLine_ID))
;

-- 2021-05-25T07:36:47.915Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Invoice_Verification_SetLine','ALTER TABLE C_Invoice_Verification_SetLine DROP COLUMN IF EXISTS C_InvoiceLine_Tax_ID')
;

-- 2021-05-25T07:39:21.890Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Invoice_Verification_SetLine','ALTER TABLE public.C_Invoice_Verification_SetLine ADD COLUMN C_InvoiceLine_Tax_ID NUMERIC(10)')
;

-- 2021-05-24T13:03:06.007Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Invoice_Verification_Run (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_PInstance_ID NUMERIC(10), C_Invoice_Verification_Run_ID NUMERIC(10) NOT NULL, C_Invoice_Verification_Set_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateEnd TIMESTAMP WITHOUT TIME ZONE, DateStart TIMESTAMP WITHOUT TIME ZONE, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MovementDate_Override TIMESTAMP WITHOUT TIME ZONE, Note TEXT, Status CHAR(1) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADPInstance_CInvoiceVerificationRun FOREIGN KEY (AD_PInstance_ID) REFERENCES public.AD_PInstance DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Invoice_Verification_Run_Key PRIMARY KEY (C_Invoice_Verification_Run_ID), CONSTRAINT CInvoiceVerificationSet_CInvoiceVerificationRun FOREIGN KEY (C_Invoice_Verification_Set_ID) REFERENCES public.C_Invoice_Verification_Set DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-06-08T08:28:25.756Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Invoice_Verification_RunLine (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Invoice_Verification_Run_ID NUMERIC(10) NOT NULL, C_Invoice_Verification_RunLine_ID NUMERIC(10) NOT NULL, C_Invoice_Verification_Set_ID NUMERIC(10), C_Invoice_Verification_SetLine_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsTaxBoilerPlateMatch CHAR(1) DEFAULT 'N' CHECK (IsTaxBoilerPlateMatch IN ('Y','N')) NOT NULL, IsTaxIdMatch CHAR(1) DEFAULT 'N' CHECK (IsTaxIdMatch IN ('Y','N')) NOT NULL, IsTaxRateMatch CHAR(1) DEFAULT 'N' CHECK (IsTaxRateMatch IN ('Y','N')) NOT NULL, Run_Tax_ID NUMERIC(10), Run_Tax_Lookup_Log TEXT, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CInvoiceVerificationRun_CInvoiceVerificationRunLine FOREIGN KEY (C_Invoice_Verification_Run_ID) REFERENCES public.C_Invoice_Verification_Run DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Invoice_Verification_RunLine_Key PRIMARY KEY (C_Invoice_Verification_RunLine_ID), CONSTRAINT CInvoiceVerificationSet_CInvoiceVerificationRunLine FOREIGN KEY (C_Invoice_Verification_Set_ID) REFERENCES public.C_Invoice_Verification_Set DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoiceVerificationSetLine_CInvoiceVerificationRunLine FOREIGN KEY (C_Invoice_Verification_SetLine_ID) REFERENCES public.C_Invoice_Verification_SetLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT RunTax_CInvoiceVerificationRunLine FOREIGN KEY (Run_Tax_ID) REFERENCES public.C_Tax DEFERRABLE INITIALLY DEFERRED)
;

