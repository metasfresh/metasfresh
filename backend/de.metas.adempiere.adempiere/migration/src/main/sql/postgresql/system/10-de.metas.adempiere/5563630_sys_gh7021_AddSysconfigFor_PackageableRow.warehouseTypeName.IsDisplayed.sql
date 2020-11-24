-- 2020-07-16T11:36:13.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541332,'S',TO_TIMESTAMP('2020-07-16 14:36:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.pickingV2.packageable.PackageableRow.field.warehouseTypeName.IsDisplayed',TO_TIMESTAMP('2020-07-16 14:36:13','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2020-07-16T11:39:07.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2020-07-16 14:39:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541332
;
