-- 2019-01-23T06:06:58.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541260,'S',TO_TIMESTAMP('2019-01-23 06:06:58','YYYY-MM-DD HH24:MI:SS'),100,'Under the material cockpit''s hood, lists of "empty" products are cached to speed up paging back an forth between different dates.
This value determines the number of cached lists. In order to save memory, you need to set this to a reltively small number if 
the sysconfig "de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.Limit" is relatively big.
The default if not set here is 10.','de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.CacheSize',TO_TIMESTAMP('2019-01-23 06:06:58','YYYY-MM-DD HH24:MI:SS'),100,'10')
;

-- 2019-01-23T06:07:12.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.Limit',Updated=TO_TIMESTAMP('2019-01-23 06:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541259
;

-- 2019-01-23T06:07:26.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='In material cockpit the system will also show rows with products that have no stock, no ordered quantities etc.
This setting specifies how many of those products will be loaded.
A value <= 0 means "no limit" which is convenient but can lead to OutOfMemoryErrors if you have a large product master.
The default value if not specified is 2000.',Updated=TO_TIMESTAMP('2019-01-23 06:07:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541259
;

-- 2019-01-23T06:12:22.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='In material cockpit the system will also show rows with products that have no stock, no ordered quantities etc.
This setting specifies how many of those products will be loaded. The default value if not specified is 2000.
A value <= 0 means "no limit" which is convenient but can lead to OutOfMemoryErrors if you have a large product master.
Also see the sysconfig "de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.CacheSize".',Updated=TO_TIMESTAMP('2019-01-23 06:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541259
;

-- 2019-01-23T06:13:29.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='In material cockpit the system will also show rows with products that have no stock, no ordered quantities etc.
This setting specifies how many of those products will be loaded. The default value if not set here is 2000.
A value <= 0 means "no limit" which is convenient but can lead to OutOfMemoryErrors if you have a large product master.
Also see the sysconfig "de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.CacheSize".',Updated=TO_TIMESTAMP('2019-01-23 06:13:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541259
;

