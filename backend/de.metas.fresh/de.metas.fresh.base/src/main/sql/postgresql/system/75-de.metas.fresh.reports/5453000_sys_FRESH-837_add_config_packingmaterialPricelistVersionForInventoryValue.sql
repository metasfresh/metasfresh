-- 07.11.2016 14:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541064,'S',TO_TIMESTAMP('2016-11-07 14:37:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','de.metas.fresh.report.jasper.hu_costprice.packingmaterialPricelistVersionForInventoryValue',TO_TIMESTAMP('2016-11-07 14:37:43','YYYY-MM-DD HH24:MI:SS'),100,'2001277')
;

-- 07.11.2016 14:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='It has the M_PriceList_Version_ID used for taking the HU prices',Updated=TO_TIMESTAMP('2016-11-07 14:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541064
;

