-- 2022-03-10T13:04:46.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-03-10 14:04:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582453
;

-- 2022-03-10T13:04:55.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_matchinv','M_InOut_ID','NUMERIC(10)',null,null)
;

-- 2022-03-10T13:04:55.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_matchinv','M_InOut_ID',null,'NOT NULL',null)
;

-- 2022-03-10T13:06:05.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-03-10 14:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551380
;

-- 2022-03-10T13:06:08.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_matchinv','C_Invoice_ID','NUMERIC(10)',null,null)
;

-- 2022-03-10T13:06:08.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_matchinv','C_Invoice_ID',null,'NOT NULL',null)
;

