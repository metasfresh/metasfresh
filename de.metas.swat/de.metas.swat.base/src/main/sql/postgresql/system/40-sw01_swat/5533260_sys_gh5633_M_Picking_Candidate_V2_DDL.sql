-- 2019-10-07T14:22:20.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Picking_Config_V2 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsPickingReviewRequired CHAR(1) DEFAULT 'Y' CHECK (IsPickingReviewRequired IN ('Y','N')) NOT NULL, M_Picking_Config_V2_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Picking_Config_V2_Key PRIMARY KEY (M_Picking_Config_V2_ID))
;

