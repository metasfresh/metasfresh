-- 2019-01-22T16:49:01.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541259,'S',TO_TIMESTAMP('2019-01-22 16:49:01','YYYY-MM-DD HH24:MI:SS'),100,'In material cockpit the system will also show rows with products that have no stock, no ordered quantities etc.
This setting specifies how many of those products will be loaded.
A value <= 0 means "no limit" which is convenient but can lead to OutOfMemoryErrors if you have a large product master.
The default value if not specified is -1.','de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProductsLimit',TO_TIMESTAMP('2019-01-22 16:49:01','YYYY-MM-DD HH24:MI:SS'),100,'1000')
;

