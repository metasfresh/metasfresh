-- 2022-10-06T11:37:02.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541512,'S',TO_TIMESTAMP('2022-10-06 14:37:02','YYYY-MM-DD HH24:MI:SS'),100,'If Y then the barcode component will render a button which when pressed will simulate scanning the eligible barcode.
If the scanning component is not provided with an eligible barcode then no button will be rendered anyways.','D','Y','mobileui.frontend.barcodeScanner.showEligibleBarcodeDebugButton',TO_TIMESTAMP('2022-10-06 14:37:02','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2022-10-06T11:48:20.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='N',Updated=TO_TIMESTAMP('2022-10-06 14:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541512
;

-- 2022-10-06T11:49:05.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='Y',Updated=TO_TIMESTAMP('2022-10-06 14:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541512
;

