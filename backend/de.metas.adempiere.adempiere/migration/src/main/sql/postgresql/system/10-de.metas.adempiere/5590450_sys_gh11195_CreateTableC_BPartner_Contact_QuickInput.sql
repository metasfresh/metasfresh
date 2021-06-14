-- 2021-05-28T14:05:28.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_BPartner_Contact_QuickInput (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BPartner_Contact_QuickInput_ID NUMERIC(10) NOT NULL, C_Greeting_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, EMail VARCHAR(255), Firstname VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsMembershipContact CHAR(1) DEFAULT 'N' CHECK (IsMembershipContact IN ('Y','N')) NOT NULL, IsNewsletter CHAR(1) DEFAULT 'N' CHECK (IsNewsletter IN ('Y','N')) NOT NULL, Lastname VARCHAR(255), Phone VARCHAR(40), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Contact_QuickInput_Key PRIMARY KEY (C_BPartner_Contact_QuickInput_ID), CONSTRAINT CGreeting_CBPartnerContactQuickInput FOREIGN KEY (C_Greeting_ID) REFERENCES public.C_Greeting DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-05-28T14:09:01.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Contact_QuickInput','ALTER TABLE public.C_BPartner_Contact_QuickInput ADD COLUMN C_BPartner_QuickInput_ID NUMERIC(10) NOT NULL')
;
