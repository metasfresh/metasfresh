-- 2017-12-21T15:23:28.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ModelValidator SET IsActive='N',Updated=TO_TIMESTAMP('2017-12-21 15:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ModelValidator_ID=540118
;

-- 2017-12-21T15:47:05.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ModelValidator WHERE AD_ModelValidator_ID=540118
;

