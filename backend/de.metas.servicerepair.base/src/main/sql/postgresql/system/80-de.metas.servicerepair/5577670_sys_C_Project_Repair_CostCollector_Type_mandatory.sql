-- 2021-01-27T21:09:50.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-01-27 23:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572555
;

-- 2021-01-27T21:09:52.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project_repair_costcollector','Type','VARCHAR(3)',null,null)
;

-- 2021-01-27T21:09:52.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project_repair_costcollector','Type',null,'NOT NULL',null)
;

