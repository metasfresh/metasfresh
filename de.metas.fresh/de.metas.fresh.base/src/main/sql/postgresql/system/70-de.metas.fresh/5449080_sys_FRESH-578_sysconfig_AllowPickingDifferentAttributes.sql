-- 15.08.2016 17:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541004,'O',TO_TIMESTAMP('2016-08-15 17:35:58','YYYY-MM-DD HH24:MI:SS'),100,'See task https://github.com/metasfresh/metasfresh/issues/275','de.metas.handlingunits','Y','de.metas.fresh.picking.form.swing.PickingHUEditorModel.AllowPickingDifferentAttributes',TO_TIMESTAMP('2016-08-15 17:35:58','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 15.08.2016 17:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2016-08-15 17:36:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541004
;

