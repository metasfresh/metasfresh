--
-- C_Flatrate_Conditions.C_Flatrate_Transition_ID mandatory in general (was just for flat fees)
--

-- 2017-10-10T15:21:35.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', MandatoryLogic='',Updated=TO_TIMESTAMP('2017-10-10 15:21:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546039
;

-- 2017-10-10T15:21:39.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','C_Flatrate_Transition_ID','NUMERIC(10)',null,null)
;

-- 2017-10-10T15:21:39.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','C_Flatrate_Transition_ID',null,'NOT NULL',null)
;

