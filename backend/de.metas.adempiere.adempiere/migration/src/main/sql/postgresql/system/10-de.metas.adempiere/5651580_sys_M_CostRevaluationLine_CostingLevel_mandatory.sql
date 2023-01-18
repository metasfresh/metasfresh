-- 2022-08-18T11:57:00.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-18 14:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584074
;

-- 2022-08-18T11:57:00.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','CostingLevel','CHAR(1)',null,null)
;

-- 2022-08-18T11:57:00.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costrevaluationline','CostingLevel',null,'NOT NULL',null)
;

