-- 2022-08-18T14:52:59.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-18 17:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584083
;

-- 2022-08-18T14:53:00.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','M_CostElement_ID','NUMERIC(10)',null,null)
;

-- 2022-08-18T14:53:00.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','M_CostElement_ID',null,'NOT NULL',null)
;

-- 2022-08-18T14:53:14.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-18 17:53:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584079
;

-- 2022-08-18T14:53:14.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','C_AcctSchema_ID','NUMERIC(10)',null,null)
;

-- 2022-08-18T14:53:14.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','C_AcctSchema_ID',null,'NOT NULL',null)
;

-- 2022-08-18T14:53:25.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-18 17:53:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584080
;

-- 2022-08-18T14:53:26.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','M_CostType_ID','NUMERIC(10)',null,null)
;

-- 2022-08-18T14:53:26.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','M_CostType_ID',null,'NOT NULL',null)
;

