-- 2018-10-04T18:31:59.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-10-04 18:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557015
;

-- 2018-10-04T18:32:02.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_candidate','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2018-10-04T18:32:02.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_candidate','C_UOM_ID',null,'NOT NULL',null)
;

-- 2018-10-04T18:36:30.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-10-04 18:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556980
;

-- 2018-10-04T18:36:34.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_candidate','M_PickingSlot_ID','NUMERIC(10)',null,null)
;

-- 2018-10-04T18:36:34.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_candidate','M_PickingSlot_ID',null,'NULL',null)
;

