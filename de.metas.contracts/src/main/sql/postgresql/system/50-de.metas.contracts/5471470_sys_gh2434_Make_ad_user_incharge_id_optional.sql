-- 2017-09-12T16:27:56.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-12 16:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546058
;

-- 2017-09-12T16:28:07.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_term','AD_User_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2017-09-12T16:28:07.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_term','AD_User_InCharge_ID',null,'NULL',null)
;

