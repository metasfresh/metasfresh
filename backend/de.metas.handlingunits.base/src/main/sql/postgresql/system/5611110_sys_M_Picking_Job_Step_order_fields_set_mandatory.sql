-- 2021-10-29T05:58:35.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-29 08:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578098
;

-- 2021-10-29T05:58:36.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step','C_OrderLine_ID','NUMERIC(10)',null,null)
;

-- 2021-10-29T05:58:36.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step','C_OrderLine_ID',null,'NOT NULL',null)
;

-- 2021-10-29T05:58:41.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-29 08:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578097
;

-- 2021-10-29T05:58:42.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step','C_Order_ID','NUMERIC(10)',null,null)
;

-- 2021-10-29T05:58:42.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_picking_job_step','C_Order_ID',null,'NOT NULL',null)
;

