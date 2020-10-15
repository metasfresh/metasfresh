-- 2020-09-22T05:32:19.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2020-09-22 08:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571518
;

-- 2020-09-22T05:32:19.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventoryline_hu','M_Inventory_ID','NUMERIC(10)',null,null)
;

-- 2020-09-22T05:32:19.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventoryline_hu','M_Inventory_ID',null,'NOT NULL',null)
;

