
-- 2019-04-24T17:10:01.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Shipment_Declaration_Config','ALTER TABLE public.M_Shipment_Declaration_Config ADD COLUMN C_DocType_Correction_ID NUMERIC(10)')
;

-- 2019-04-24T17:10:01.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Shipment_Declaration_Config ADD CONSTRAINT CDocTypeCorrection_MShipmentDeclarationConfig FOREIGN KEY (C_DocType_Correction_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

