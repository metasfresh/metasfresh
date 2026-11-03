
--
-- change MSV3_Server.FixedQtyAvailableToPromise to int, because only int are valid in MSV3
--
-- 2019-01-21T23:07:09.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', TechnicalNote='Integer according to the MSV3 standard',Updated=TO_TIMESTAMP('2019-01-21 23:07:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559608
;

-- 2019-01-21T23:07:16.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('msv3_server','FixedQtyAvailableToPromise','NUMERIC(10)',null,'0')
;

-- 2019-01-21T23:07:16.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE MSV3_Server SET FixedQtyAvailableToPromise=0 WHERE FixedQtyAvailableToPromise IS NULL
;

