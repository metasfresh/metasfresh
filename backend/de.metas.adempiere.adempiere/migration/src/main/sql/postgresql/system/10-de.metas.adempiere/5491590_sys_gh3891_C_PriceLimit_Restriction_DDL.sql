-- 2018-04-23T10:28:12.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_PriceLimit_Restriction (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Base_PricingSystem_ID NUMERIC(10) NOT NULL, C_PriceLimit_Restriction_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description TEXT, Discount NUMERIC, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Std_AddAmt NUMERIC, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT BasePricingSystem_CPriceLimitRestriction FOREIGN KEY (Base_PricingSystem_ID) REFERENCES public.M_PricingSystem DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_PriceLimit_Restriction_Key PRIMARY KEY (C_PriceLimit_Restriction_ID))
;

-- Only one active record
create unique index C_PriceLimit_Restriction_uq on C_PriceLimit_Restriction(IsActive) where IsActive='Y'
;

