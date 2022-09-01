-- 2021-11-03T15:39:01.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-11-03 17:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578133
;

-- 2021-11-03T15:39:02.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_node','Name','VARCHAR(255)',null,null)
;

-- 2021-11-03T15:39:02.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_node','Name',null,'NOT NULL',null)
;

