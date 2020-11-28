
-- 2019-04-19T17:58:54.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Shipment_Declaration (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BPartner_ID NUMERIC(10), C_BPartner_Location_ID NUMERIC(10), C_DocType_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DeliveryDate TIMESTAMP WITHOUT TIME ZONE, DocAction CHAR(2) DEFAULT 'CO' NOT NULL, DocStatus VARCHAR(2) DEFAULT 'DR' NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_InOut_ID NUMERIC(10), M_Shipment_Declaration_ID NUMERIC(10) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, Processing CHAR(1), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CBPartner_MShipmentDeclaration FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartnerLocation_MShipmentDeclaration FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CDocType_MShipmentDeclaration FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MInOut_MShipmentDeclaration FOREIGN KEY (M_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Shipment_Declaration_Key PRIMARY KEY (M_Shipment_Declaration_ID))
;




-- 2019-04-22T15:42:07.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Shipment_Declaration','ALTER TABLE public.M_Shipment_Declaration ADD COLUMN DocumentNo VARCHAR(40)')
;



