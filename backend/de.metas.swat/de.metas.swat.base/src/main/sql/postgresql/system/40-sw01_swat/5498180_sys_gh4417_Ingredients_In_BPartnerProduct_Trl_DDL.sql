-- make sure the translations are not lost with the new parent column : save all the data in a temp table
drop table if exists TEMP_C_BPartner_Product_Trl;




CREATE TABLE TEMP_C_BPartner_Product_Trl
AS
(select * from C_BPartner_Product_Trl);


 -- drop old translations table

DROP TABLE C_BPartner_Product_Trl
;


-- recreate the table with the new primary key and the new ingredients column

-- 2018-07-26T18:49:33.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_BPartner_Product_Trl (AD_Client_ID NUMERIC(10) NOT NULL, AD_Language VARCHAR(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BPartner_Product_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, CustomerLabelName VARCHAR(100), Ingredients VARCHAR(2000), IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, IsTranslated CHAR(1) DEFAULT 'N' CHECK (IsTranslated IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_Product_Trl_Key PRIMARY KEY (AD_Language, C_BPartner_Product_ID), CONSTRAINT ADLangu_CBPartnerProductTrl FOREIGN KEY (AD_Language) REFERENCES public.AD_Language DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartnerProduct_CBPartnerProductTrl FOREIGN KEY (C_BPartner_Product_ID) REFERENCES public.C_BPartner_Product DEFERRABLE INITIALLY DEFERRED)
;



-- populate the recreated table from the temp

insert into C_BPartner_Product_Trl
(
  ad_client_id,
  ad_language ,
  ad_org_id,
  c_bpartner_product_id ,
  created ,
  createdby ,
  customerlabelname ,
  ingredients ,
  isactive ,
  istranslated ,
  updated ,
  updatedby 
  )
  
  select 
   ad_client_id,
  ad_language ,
  ad_org_id,
  c_bpartner_product_id ,
  created ,
  createdby ,
  customerlabelname ,
 '' as ingredients ,
  isactive ,
  istranslated ,
  updated ,
  updatedby 
  
 from TEMP_C_BPartner_Product_Trl;

-- delete the temp table since it's not needed anymore

drop table if exists TEMP_C_BPartner_Product_Trl;