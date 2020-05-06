-- 2019-04-24T11:35:59.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_config','C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T11:35:59.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_config','C_DocType_ID',null,'NOT NULL',null)
;







-- 2019-04-24T11:37:52.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_config','DocumentLinesNumber','NUMERIC(10)',null,'6')
;

-- 2019-04-24T11:37:52.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Shipment_Declaration_Config SET DocumentLinesNumber=6 WHERE DocumentLinesNumber IS NULL OR DocumentLinesNumber = 0
;





-- 2019-04-24T11:40:30.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','DocumentNo','VARCHAR(40)',null,null)
;

-- 2019-04-24T11:40:30.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','DocumentNo',null,'NOT NULL',null)
;





-- 2019-04-24T11:41:25.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T11:41:25.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','C_DocType_ID',null,'NOT NULL',null)
;






-- 2019-04-24T11:42:09.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T11:42:09.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','C_BPartner_ID',null,'NOT NULL',null)
;








-- 2019-04-24T11:42:15.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T11:42:15.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','C_BPartner_Location_ID',null,'NOT NULL',null)
;




-- 2019-04-24T11:42:31.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','DeliveryDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2019-04-24T11:42:31.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','DeliveryDate',null,'NOT NULL',null)
;





-- 2019-04-24T12:05:30.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','M_InOut_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T12:05:30.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration','M_InOut_ID',null,'NOT NULL',null)
;





-- 2019-04-24T12:07:06.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_config','Name','VARCHAR(40)',null,null)
;

-- 2019-04-24T12:07:06.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_config','Name',null,'NOT NULL',null)
;




-- 2019-04-24T12:07:55.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T12:07:55.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','C_UOM_ID',null,'NOT NULL',null)
;



-- 2019-04-24T12:08:08.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','M_InOutLine_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T12:08:08.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','M_InOutLine_ID',null,'NOT NULL',null)
;





-- 2019-04-24T12:08:15.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T12:08:15.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','M_Product_ID',null,'NOT NULL',null)
;




-- 2019-04-24T12:08:30.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','Qty','NUMERIC',null,null)
;

-- 2019-04-24T12:08:30.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','Qty',null,'NOT NULL',null)
;







-- 2019-04-24T12:53:02.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Shipment_Declaration','ALTER TABLE public.M_Shipment_Declaration ADD COLUMN AD_User_ID NUMERIC(10)')
;

-- 2019-04-24T12:53:02.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Shipment_Declaration ADD CONSTRAINT ADUser_MShipmentDeclaration FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;






-- 2019-04-24T13:09:42.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Shipment_Declaration_Line','ALTER TABLE public.M_Shipment_Declaration_Line ADD COLUMN LineNo NUMERIC(10) DEFAULT 0 NOT NULL')
;





