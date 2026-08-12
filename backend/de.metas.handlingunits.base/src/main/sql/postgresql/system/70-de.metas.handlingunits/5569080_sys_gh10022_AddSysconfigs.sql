-- 2020-09-30T09:30:33.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541340,'S',TO_TIMESTAMP('2020-09-30 12:30:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.handlingunits.attributes.AutomaticallySetLotNumber',TO_TIMESTAMP('2020-09-30 12:30:33','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2020-09-30T09:31:28.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Automatically set HU Attribute Lot Number to HU.Value (HU Barcode) when a HU is created during Manufacturing or Receiving flows.',Updated=TO_TIMESTAMP('2020-09-30 12:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541340
;

-- 2020-09-30T10:17:52.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541341,'S',TO_TIMESTAMP('2020-09-30 13:17:52','YYYY-MM-DD HH24:MI:SS'),100,'Automatically set HU Attribute BestBeforeDate  to (Date Promissed + Product.MHD) when a HU is created during Manufacturing or Receiving flows.','D','Y','de.metas.handlingunits.attributes.AutomaticallySetBestBeforeDate',TO_TIMESTAMP('2020-09-30 13:17:52','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

