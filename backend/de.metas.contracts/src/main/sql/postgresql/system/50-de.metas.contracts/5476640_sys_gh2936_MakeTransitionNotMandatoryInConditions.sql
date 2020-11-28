-- 2017-11-08T12:39:34.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-11-08 12:39:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546039
;

-- 2017-11-08T12:39:39.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','C_Flatrate_Transition_ID','NUMERIC(10)',null,null)
;

-- 2017-11-08T12:39:39.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','C_Flatrate_Transition_ID',null,'NULL',null)
;

