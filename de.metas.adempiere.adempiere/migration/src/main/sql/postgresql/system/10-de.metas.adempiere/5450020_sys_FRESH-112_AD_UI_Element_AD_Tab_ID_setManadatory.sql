-- 01.09.2016 10:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-09-01 10:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555030
;

-- 01.09.2016 10:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_element','AD_Tab_ID','NUMERIC(10)',null,null)
;

-- 01.09.2016 10:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_element','AD_Tab_ID',null,'NOT NULL',null)
;

