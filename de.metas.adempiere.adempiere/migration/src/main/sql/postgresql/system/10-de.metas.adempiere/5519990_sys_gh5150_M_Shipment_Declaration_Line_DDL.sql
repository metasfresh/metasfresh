
-- 2019-04-22T11:28:22.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Shipment_Declaration_Line (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_InOutLine_ID NUMERIC(10), M_Product_ID NUMERIC(10), M_Shipment_Declaration_ID NUMERIC(10) NOT NULL, M_Shipment_Declaration_Line_ID NUMERIC(10) NOT NULL, PackageSize NUMERIC, Qty NUMERIC, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CUOM_MShipmentDeclarationLine FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MInOutLine_MShipmentDeclarationLine FOREIGN KEY (M_InOutLine_ID) REFERENCES public.M_InOutLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProduct_MShipmentDeclarationLine FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MShipmentDeclaration_MShipmentDeclarationLine FOREIGN KEY (M_Shipment_Declaration_ID) REFERENCES public.M_Shipment_Declaration DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Shipment_Declaration_Line_Key PRIMARY KEY (M_Shipment_Declaration_Line_ID))
;



-- 2019-04-23T13:27:30.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','PackageSize','VARCHAR(14)',null,null)
;

