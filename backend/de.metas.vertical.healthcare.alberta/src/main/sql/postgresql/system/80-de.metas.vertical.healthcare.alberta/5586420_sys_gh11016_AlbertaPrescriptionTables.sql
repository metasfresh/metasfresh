-- 2021-04-26T09:29:22.944Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.Alberta_PrescriptionRequest (AccountingMonths VARCHAR(3), AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Alberta_PrescriptionRequest_ID NUMERIC(10) NOT NULL, Archived CHAR(1) DEFAULT 'N' CHECK (Archived IN ('Y','N')) NOT NULL, C_BPartner_Doctor_ID NUMERIC(10) NOT NULL, C_BPartner_Patient_ID NUMERIC(10) NOT NULL, C_BPartner_Pharmacy_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DeliveryDate TIMESTAMP WITH TIME ZONE, EndDate TIMESTAMP WITHOUT TIME ZONE, ExternalId VARCHAR(255), ExternalOrderId VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Note TEXT, PatientBirthday TIMESTAMP WITHOUT TIME ZONE, PrescriptionRequestCreatedAt TIMESTAMP WITH TIME ZONE, PrescriptionRequestCreatedBy NUMERIC(10), PrescriptionRequestTimestamp TIMESTAMP WITH TIME ZONE, PrescriptionRequestUpdateAt TIMESTAMP WITH TIME ZONE, PrescriptionType VARCHAR(2), StartDate TIMESTAMP WITHOUT TIME ZONE, Therapy VARCHAR(2) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Alberta_PrescriptionRequest_Key PRIMARY KEY (Alberta_PrescriptionRequest_ID), CONSTRAINT CBPartnerDoctor_AlbertaPrescriptionRequest FOREIGN KEY (C_BPartner_Doctor_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartnerPatient_AlbertaPrescriptionRequest FOREIGN KEY (C_BPartner_Patient_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartnerPharmacy_AlbertaPrescriptionRequest FOREIGN KEY (C_BPartner_Pharmacy_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PrescriptionRequestCreate_AlbertaPrescriptionRequest FOREIGN KEY (PrescriptionRequestCreatedBy) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-04-26T10:30:55.946Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.Alberta_PrescriptionRequest_Line (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Alberta_PrescriptionRequest_ID NUMERIC(10) NOT NULL, Alberta_PrescriptionRequest_Line_ID NUMERIC(10) NOT NULL, ArticleUnit VARCHAR(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalId VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, LineUpdated TIMESTAMP WITH TIME ZONE, M_Product_ID NUMERIC(10), Note TEXT, Qty NUMERIC, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AlbertaPrescriptionRequest_AlbertaPrescriptionRequestLine FOREIGN KEY (Alberta_PrescriptionRequest_ID) REFERENCES public.Alberta_PrescriptionRequest DEFERRABLE INITIALLY DEFERRED, CONSTRAINT Alberta_PrescriptionRequest_Line_Key PRIMARY KEY (Alberta_PrescriptionRequest_Line_ID), CONSTRAINT MProduct_AlbertaPrescriptionRequestLine FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-04-26T10:49:44.312Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.Alberta_PrescriptionRequest_TherapyType (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Alberta_PrescriptionRequest_TherapyType_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, TherapyType VARCHAR(255) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Alberta_PrescriptionRequest_TherapyType_Key PRIMARY KEY (Alberta_PrescriptionRequest_TherapyType_ID))
;

-- 2021-04-26T10:54:51.502Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('Alberta_PrescriptionRequest','ALTER TABLE public.Alberta_PrescriptionRequest ADD COLUMN TherapyTypeIds VARCHAR(255)')
;

-- 2021-04-26T13:42:36.834Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('Alberta_PrescriptionRequest_TherapyType','ALTER TABLE public.Alberta_PrescriptionRequest_TherapyType ADD COLUMN Alberta_PrescriptionRequest_ID NUMERIC(10) NOT NULL')
;

-- 2021-04-27T06:01:46.941Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.Alberta_PrescriptionRequest_Accounting (AccountingMonths VARCHAR(2), AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Alberta_PrescriptionRequest_Accounting_ID NUMERIC(10) NOT NULL, Alberta_PrescriptionRequest_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Alberta_PrescriptionRequest_Accounting_Key PRIMARY KEY (Alberta_PrescriptionRequest_Accounting_ID), CONSTRAINT AlbertaPrescriptionRequest_AlbertaPrescriptionRequestAccounting FOREIGN KEY (Alberta_PrescriptionRequest_ID) REFERENCES public.Alberta_PrescriptionRequest DEFERRABLE INITIALLY DEFERRED)
;