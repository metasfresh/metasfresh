

-- 2022-08-11T14:46:18.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-11 17:46:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584018
;

-- 2022-08-11T14:46:18.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project','R_StatusCategory_ID','NUMERIC(10)',null,null)
;

-- 2022-08-11T14:46:18.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project','R_StatusCategory_ID',null,'NOT NULL',null)
;

-- 2022-08-11T14:46:33.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-08-11 17:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584017
;

-- 2022-08-11T14:46:34.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_projecttype','R_StatusCategory_ID','NUMERIC(10)',null,null)
;

-- 2022-08-11T14:46:34.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_projecttype','R_StatusCategory_ID',null,'NOT NULL',null)
;

