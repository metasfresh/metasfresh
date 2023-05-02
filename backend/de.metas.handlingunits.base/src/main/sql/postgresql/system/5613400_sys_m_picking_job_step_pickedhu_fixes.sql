-- 2021-11-12T21:44:19.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-11-12 23:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578403
;

-- 2021-11-12T21:44:20.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_pickedhu','M_Picking_Job_HUAlternative_ID','NUMERIC(10)',null,null)
;

-- 2021-11-12T21:44:20.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_pickedhu','M_Picking_Job_HUAlternative_ID',null,'NULL',null)
;

