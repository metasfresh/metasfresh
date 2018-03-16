-- 2018-03-14T14:02:12.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='Y',Updated=TO_TIMESTAMP('2018-03-14 14:02:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541079
;

-- 2018-03-15T11:22:24.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541196,'O',TO_TIMESTAMP('2018-03-15 11:22:24','YYYY-MM-DD HH24:MI:SS'),100,'Y means that the system will automatically print a picking label when a HU is created via "Pick To New HU".','de.metas.picking','Y','de.metas.ui.web.picking.PickingLabel.AutoPrint_Enabled',TO_TIMESTAMP('2018-03-15 11:22:24','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2018-03-15T11:22:55.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.ui.web.picking.PickingLabel.AutoPrint.Enabled',Updated=TO_TIMESTAMP('2018-03-15 11:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541196
;

-- 2018-03-15T11:38:32.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541198,'O',TO_TIMESTAMP('2018-03-15 11:38:32','YYYY-MM-DD HH24:MI:SS'),100,'Y means that the system will automatically print a picking label when a HU is created via "Pick To New HU".','de.metas.picking','Y','de.metas.ui.web.picking.PickingLabel.AD_Process_ID',TO_TIMESTAMP('2018-03-15 11:38:32','YYYY-MM-DD HH24:MI:SS'),100,'540933')
;

-- 2018-03-15T11:38:36.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This specifies the process which the system shall use when printing material receipts labels for HUs. Note: AD_Process_ID=540370 is the process with the value "Wareneingangsetikett LU (Jasper)".',Updated=TO_TIMESTAMP('2018-03-15 11:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541078
;

-- 2018-03-15T11:39:10.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Y means that the system will automatically print a picking label when a HU is created via "Pick To New HU".

This specifies the process which the system shall use when printing picking labels for HUs. Note: AD_Process_ID=540933 is the process with the value "Picking TU Label (Jasper)".',Updated=TO_TIMESTAMP('2018-03-15 11:39:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541198
;

-- 2018-03-15T11:39:26.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Y means that the system will automatically print a material receipt label then a HU is received. This only works if de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID is also set apropriately.',Updated=TO_TIMESTAMP('2018-03-15 11:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541079
;

-- 2018-03-15T11:39:33.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Y means that the system will automatically print a picking label when a HU is created via "Pick To New HU". This only works if de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID is also set apropriately.',Updated=TO_TIMESTAMP('2018-03-15 11:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541196
;

-- 2018-03-15T11:39:56.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Y means that the system will automatically print a picking label when a HU is created via "Pick To New HU". This only works if de.metas.ui.web.picking.PickingLabel.AD_Process_ID is also set apropriately.',Updated=TO_TIMESTAMP('2018-03-15 11:39:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541196
;

-- 2018-03-15T14:00:41.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541199,'O',TO_TIMESTAMP('2018-03-15 14:00:40','YYYY-MM-DD HH24:MI:SS'),100,'Y means that the system will automatically print a picking label when a HU is created via "Pick To New HU". This only works if de.metas.ui.web.picking.PickingLabel.AD_Process_ID is also set apropriately.','U','Y','de.metas.ui.web.picking.PickingLabel.AutoPrint.Copies',TO_TIMESTAMP('2018-03-15 14:00:40','YYYY-MM-DD HH24:MI:SS'),100,'1')
;

-- 2018-03-15T14:00:55.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Number of copies (1 means one printout) to print if de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled is set to Y.',Updated=TO_TIMESTAMP('2018-03-15 14:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541199
;

-- 2018-03-15T14:01:14.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Number of copies (1 means one printout) to print if de.metas.ui.web.picking.PickingLabel.AutoPrint.Enabled is set to Y.', EntityType='de.metas.picking',Updated=TO_TIMESTAMP('2018-03-15 14:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541199
;

