-- 2022-10-07T10:42:20.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-10-07 13:42:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577738
;

-- 2022-10-07T10:42:25.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job','Picking_User_ID','NUMERIC(10)',null,null)
;

-- 2022-10-07T10:42:25.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job','Picking_User_ID',null,'NULL',null)
;

