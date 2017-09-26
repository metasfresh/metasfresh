


--
-- IsSimulation is only a thing for flat fees and holding fees
--
-- 2017-09-21T09:23:37.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''FlatFee'' | @Type_Conditions@=''HoldingFee''',Updated=TO_TIMESTAMP('2017-09-21 09:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548427
;

--
-- C_Flatrate_Conditions.Type_Conditions is only mandatory for flat fees.
-- from looking at the code, it seems very much as if this was the intention
--
-- 2017-09-21T10:32:50.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions@=''FlatFee''',Updated=TO_TIMESTAMP('2017-09-21 10:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545817
;

-- 2017-09-21T10:32:54.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_conditions','UOMType','VARCHAR(2)',null,null)
;
