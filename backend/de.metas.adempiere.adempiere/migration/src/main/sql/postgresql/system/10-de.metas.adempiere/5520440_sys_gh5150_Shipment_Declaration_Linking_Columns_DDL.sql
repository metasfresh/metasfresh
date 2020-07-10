




-- 2019-04-24T17:57:18.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Shipment_Declaration','ALTER TABLE public.M_Shipment_Declaration ADD COLUMN M_Shipment_Declaration_Base_ID NUMERIC(10)')
;



-- 2019-04-24T17:57:18.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Shipment_Declaration ADD CONSTRAINT MShipmentDeclarationBase_MShipmentDeclaration FOREIGN KEY (M_Shipment_Declaration_Base_ID) REFERENCES public.M_Shipment_Declaration DEFERRABLE INITIALLY DEFERRED
;


-- 2019-04-24T17:57:42.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Shipment_Declaration','ALTER TABLE public.M_Shipment_Declaration ADD COLUMN M_Shipment_Declaration_Correction_ID NUMERIC(10)')
;

-- 2019-04-24T17:57:42.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Shipment_Declaration ADD CONSTRAINT MShipmentDeclarationCorrection_MShipmentDeclaration FOREIGN KEY (M_Shipment_Declaration_Correction_ID) REFERENCES public.M_Shipment_Declaration DEFERRABLE INITIALLY DEFERRED
;
