drop table if exists M_Securpharm_Log;
drop table if exists M_Securpharm_Action_Result;
drop table if exists M_Securpharm_Productdata_Result;


-- 2019-05-27T11:19:14.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Securpharm_Productdata_Result (ActiveStatus CHAR(1), AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DecommissionedServerTransactionId VARCHAR(36), ExpirationDate TIMESTAMP WITHOUT TIME ZONE, InactiveReason VARCHAR(200), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsDecommissioned CHAR(1) DEFAULT 'N' CHECK (IsDecommissioned IN ('Y','N')) NOT NULL, IsError CHAR(1) DEFAULT 'N' CHECK (IsError IN ('Y','N')) NOT NULL, IsPackVerified CHAR(1) DEFAULT 'N' CHECK (IsPackVerified IN ('Y','N')) NOT NULL, LotNumber VARCHAR(100), M_HU_ID NUMERIC(10), M_Securpharm_Productdata_Result_ID NUMERIC(10) NOT NULL, PackVerificationCode VARCHAR(10), PackVerificationMessage VARCHAR(2000), ProductCode VARCHAR(100), ProductCodeType VARCHAR(10), SerialNumber VARCHAR(200), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MHU_MSecurpharmProductdataResult FOREIGN KEY (M_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Securpharm_Productdata_Result_Key PRIMARY KEY (M_Securpharm_Productdata_Result_ID))
;



-- 2019-05-27T09:41:26.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Securpharm_Action_Result (Action VARCHAR(100) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsError CHAR(1) DEFAULT 'N' CHECK (IsError IN ('Y','N')) NOT NULL, M_Inventory_ID NUMERIC(10), M_Securpharm_Action_Result_ID NUMERIC(10) NOT NULL, M_Securpharm_Productdata_Result_ID NUMERIC(10) NOT NULL, TransactionIDServer VARCHAR(36), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MInventory_MSecurpharmActionResult FOREIGN KEY (M_Inventory_ID) REFERENCES public.M_Inventory DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Securpharm_Action_Result_Key PRIMARY KEY (M_Securpharm_Action_Result_ID), CONSTRAINT MSecurpharmProductdataResult_MSecurpharmActionResult FOREIGN KEY (M_Securpharm_Productdata_Result_ID) REFERENCES public.M_Securpharm_Productdata_Result DEFERRABLE INITIALLY DEFERRED)
;



-- 2019-05-24T16:14:49.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Securpharm_Log (AD_Client_ID NUMERIC(10) NOT NULL, AD_Issue_ID NUMERIC(10), AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, IsError CHAR(1) DEFAULT 'N' CHECK (IsError IN ('Y','N')) NOT NULL, M_Securpharm_Action_Result_ID NUMERIC(10), M_Securpharm_Log_ID NUMERIC(10) NOT NULL, M_Securpharm_Productdata_Result_ID NUMERIC(10), RequestEndTime TIMESTAMP WITH TIME ZONE, RequestMethod VARCHAR(10), RequestStartTime TIMESTAMP WITH TIME ZONE, RequestUrl VARCHAR(1000), ResponseCode NUMERIC(10), ResponseText TEXT, TransactionIDClient VARCHAR(36), TransactionIDServer VARCHAR(36), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MSecurpharmActionResult_MSecurpharmLog FOREIGN KEY (M_Securpharm_Action_Result_ID) REFERENCES public.M_Securpharm_Action_Result DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Securpharm_Log_Key PRIMARY KEY (M_Securpharm_Log_ID), CONSTRAINT MSecurpharmProductdataResult_MSecurpharmLog FOREIGN KEY (M_Securpharm_Productdata_Result_ID) REFERENCES public.M_Securpharm_Productdata_Result DEFERRABLE INITIALLY DEFERRED)
;


-- ------------------------------------------------------------------------------------------------
