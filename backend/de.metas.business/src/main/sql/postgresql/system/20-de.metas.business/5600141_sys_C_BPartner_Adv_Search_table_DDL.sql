-- drop table c_bpartner_adv_search;

-- 2021-08-06T12:47:02.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_BPartner_Adv_Search (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Address1 VARCHAR(255), C_BP_Contact_ID NUMERIC(10), C_BPartner_ID NUMERIC(10) NOT NULL, C_BPartner_Location_ID NUMERIC(10), City VARCHAR(255), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ES_DocumentId VARCHAR(255) NOT NULL, Firstname VARCHAR(255), IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, IsCompany CHAR(1) DEFAULT 'N' CHECK (IsCompany IN ('Y','N')) NOT NULL, Lastname VARCHAR(255), Name VARCHAR(255), Postal VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(255), CONSTRAINT C_BPartner_Adv_Search_Key PRIMARY KEY (C_BP_Contact_ID, C_BPartner_ID, C_BPartner_Location_ID))
;

