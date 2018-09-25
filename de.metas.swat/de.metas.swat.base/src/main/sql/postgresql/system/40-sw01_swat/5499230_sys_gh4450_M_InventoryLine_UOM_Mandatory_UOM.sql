
-- make sure there are no empty values.
UPDATE M_InventoryLine il
SET C_UOM_ID = ( select p.C_UOM_ID from M_Product p where p.M_Product_ID = il.M_Product_ID) where il.C_UOM_ID is null;

COMMIT; -- avoid "pending trigger events" error

-- 2018-08-13T16:57:15.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventoryline','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2018-08-13T16:57:15.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventoryline','C_UOM_ID',null,'NOT NULL',null)
;

