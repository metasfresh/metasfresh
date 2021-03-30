-- 2020-12-31T16:47:23.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N', TechnicalNote='This process doesn''t work, mostly because "IsServerProcess=Y" is curerntly not implemented',Updated=TO_TIMESTAMP('2020-12-31 17:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540174
;

-- 2020-12-31T16:47:47.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N', TechnicalNote='This process doesn''t work, mostly because "IsServerProcess=Y" is curerntly not implemented',Updated=TO_TIMESTAMP('2020-12-31 17:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53235
;

-- 2020-12-31T16:49:47.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='This process doesn''t work, because elastic-stuff doesn''t work, and also because "IsServerProcess=Y" is curerntly not implemented',Updated=TO_TIMESTAMP('2020-12-31 17:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540725
;

-- 2020-12-31T16:49:51.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2020-12-31 17:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540725
;

-- 2020-12-31T16:50:16.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='This process doesn''t work, because elastic-stuff doesn''t work, and also because "IsServerProcess=Y" is curerntly not implemented', IsActive='N',Updated=TO_TIMESTAMP('2020-12-31 17:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540981
;

-- 2020-12-31T16:51:57.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='This process doesn''t work, because elastic-stuff doesn''t work, and also because "IsServerProcess=Y" is curerntly not implemented',Updated=TO_TIMESTAMP('2020-12-31 17:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540064
;

-- 2020-12-31T16:51:57.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='This process doesn''t work, because elastic-stuff doesn''t work, and also because "IsServerProcess=Y" is curerntly not implemented', IsActive='Y', Name='DPD-Import Status Data',Updated=TO_TIMESTAMP('2020-12-31 17:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540101
;

delete from ad_menu where ad_process_id=540064;

-- 2020-12-31T16:53:25.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540064
;

-- 2020-12-31T16:53:25.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540064
;

-- 2020-12-31T16:55:02.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2020-12-31 17:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12100
;

