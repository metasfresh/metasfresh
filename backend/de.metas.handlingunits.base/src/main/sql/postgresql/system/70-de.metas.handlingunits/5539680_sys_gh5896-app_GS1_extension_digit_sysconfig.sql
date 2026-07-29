-- 2019-12-18T16:31:41.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Seven or eight digit code that is assigned to the manifacturer by GS1',Updated=TO_TIMESTAMP('2019-12-18 17:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540313
;

-- 2019-12-18T16:31:54.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='7- or 8-digit code that is assigned to the manifacturer by GS1',Updated=TO_TIMESTAMP('2019-12-18 17:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540313
;

-- 2019-12-18T16:34:41.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541307,'O',TO_TIMESTAMP('2019-12-18 17:34:41','YYYY-MM-DD HH24:MI:SS'),100,'1-digit extension code that can picke by the manufactoring company and be used to increase the capacity of the serial reference.','de.metas.handlingunits','Y','de.metas.handlingunit.GS1ExtensionDigit',TO_TIMESTAMP('2019-12-18 17:34:41','YYYY-MM-DD HH24:MI:SS'),100,'0')
;

-- 2019-12-18T16:56:13.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET IsActive='N',Updated=TO_TIMESTAMP('2019-12-18 17:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540760
;

