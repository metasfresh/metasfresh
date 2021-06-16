-- Quick Input User Attribute Table
-- 2021-06-14T12:35:39.737Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_Contact_QuickInput_Attributes (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attribute VARCHAR(10), C_BPartner_Contact_QuickInput_Attributes_ID NUMERIC(10) NOT NULL, C_BPartner_Contact_QuickInput_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Contact_QuickInput_Attributes_Key PRIMARY KEY (C_BPartner_Contact_QuickInput_Attributes_ID), CONSTRAINT CBPartnerContactQuickInput_CBPartnerContactQuickInputAttributes FOREIGN KEY (C_BPartner_Contact_QuickInput_ID) REFERENCES public.C_BPartner_Contact_QuickInput DEFERRABLE INITIALLY DEFERRED)
;

-- BPartner Quick Input Attribute Table
-- 2021-06-14T13:38:26.960Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_QuickInput_Attributes (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, attributes VARCHAR(10) NOT NULL, C_BPartner_QuickInput_Attributes_ID NUMERIC(10) NOT NULL, C_BPartner_QuickInput_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_QuickInput_Attributes_Key PRIMARY KEY (C_BPartner_QuickInput_Attributes_ID), CONSTRAINT CBPartnerQuickInput_CBPartnerQuickInputAttributes FOREIGN KEY (C_BPartner_QuickInput_ID) REFERENCES public.C_BPartner_QuickInput DEFERRABLE INITIALLY DEFERRED)
;


-- BPartner Quick Input Attribute2 Table
-- 2021-06-14T13:42:01.991Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_QuickInput_Attributes2 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attributes2 VARCHAR(10) NOT NULL, C_BPartner_QuickInput_Attributes2_ID NUMERIC(10) NOT NULL, C_BPartner_QuickInput_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_QuickInput_Attributes2_Key PRIMARY KEY (C_BPartner_QuickInput_Attributes2_ID), CONSTRAINT CBPartnerQuickInput_CBPartnerQuickInputAttributes2 FOREIGN KEY (C_BPartner_QuickInput_ID) REFERENCES public.C_BPartner_QuickInput DEFERRABLE INITIALLY DEFERRED)
;


-- BPartner Quick Input Attribute3 Table
-- 2021-06-14T13:45:54.568Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_QuickInput_Attributes3 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attributes3 VARCHAR(10) NOT NULL, C_BPartner_QuickInput_Attributes3_ID NUMERIC(10) NOT NULL, C_BPartner_QuickInput_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_QuickInput_Attributes3_Key PRIMARY KEY (C_BPartner_QuickInput_Attributes3_ID), CONSTRAINT CBPartnerQuickInput_CBPartnerQuickInputAttributes3 FOREIGN KEY (C_BPartner_QuickInput_ID) REFERENCES public.C_BPartner_QuickInput DEFERRABLE INITIALLY DEFERRED)
;

-- BPartner Quick Input Attribute4 Table
-- 2021-06-14T13:49:20.899Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_QuickInput_Attributes4 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attributes4 VARCHAR(10) NOT NULL, C_BPartner_QuickInput_Attributes4_ID NUMERIC(10) NOT NULL, C_BPartner_QuickInput_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_QuickInput_Attributes4_Key PRIMARY KEY (C_BPartner_QuickInput_Attributes4_ID), CONSTRAINT CBPartnerQuickInput_CBPartnerQuickInputAttributes4 FOREIGN KEY (C_BPartner_QuickInput_ID) REFERENCES public.C_BPartner_QuickInput DEFERRABLE INITIALLY DEFERRED)
;

-- BPartner Quick Input Attribute5 Table
-- 2021-06-14T13:53:37.398Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_BPartner_QuickInput_Attributes5 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Attributes5 VARCHAR(10) NOT NULL, C_BPartner_QuickInput_Attributes5_ID NUMERIC(10) NOT NULL, C_BPartner_QuickInput_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_QuickInput_Attributes5_Key PRIMARY KEY (C_BPartner_QuickInput_Attributes5_ID), CONSTRAINT CBPartnerQuickInput_CBPartnerQuickInputAttributes5 FOREIGN KEY (C_BPartner_QuickInput_ID) REFERENCES public.C_BPartner_QuickInput DEFERRABLE INITIALLY DEFERRED)
;

