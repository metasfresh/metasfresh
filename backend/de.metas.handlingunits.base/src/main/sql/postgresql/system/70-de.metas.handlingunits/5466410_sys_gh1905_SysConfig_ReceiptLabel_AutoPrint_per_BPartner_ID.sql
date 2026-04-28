
-- 2017-06-28T11:01:54.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541153,'O',TO_TIMESTAMP('2017-06-28 11:01:53','YYYY-MM-DD HH24:MI:SS'),100,'Y means that the system will automatically print a material receipt label then a HU with M_HU.C_BPartner_ID=0 is received.
This C_BPartner specific setting is independent from the generic "de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled" setting.
It only works if de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID is also set appropriately.','de.metas.handlingunits','Y','de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled.C_BPartner_ID_0',TO_TIMESTAMP('2017-06-28 11:01:53','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2017-06-28T11:13:11.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled.C_BPartner_ID_-1',Updated=TO_TIMESTAMP('2017-06-28 11:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541153
;

