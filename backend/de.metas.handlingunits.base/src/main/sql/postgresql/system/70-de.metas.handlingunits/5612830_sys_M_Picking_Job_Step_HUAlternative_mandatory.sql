-- 2021-11-10T00:56:38.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-11-10 02:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578369
;

-- 2021-11-10T00:56:39.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_hualternative','PickFrom_Warehouse_ID','NUMERIC(10)',null,null)
;

-- 2021-11-10T00:56:39.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_hualternative','PickFrom_Warehouse_ID',null,'NOT NULL',null)
;

-- 2021-11-10T00:56:46.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-11-10 02:56:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578370
;

-- 2021-11-10T00:56:47.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_hualternative','PickFrom_Locator_ID','NUMERIC(10)',null,null)
;

-- 2021-11-10T00:56:47.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_hualternative','PickFrom_Locator_ID',null,'NOT NULL',null)
;

-- 2021-11-10T00:56:52.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-11-10 02:56:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578371
;

-- 2021-11-10T00:56:53.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_hualternative','PickFrom_HU_ID','NUMERIC(10)',null,null)
;

-- 2021-11-10T00:56:53.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step_hualternative','PickFrom_HU_ID',null,'NOT NULL',null)
;

