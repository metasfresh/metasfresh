-- 2022-02-10T15:33:55.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,IsActive,IsApplyToCUs,IsApplyToLUs,IsApplyToTopLevelHUsOnly,IsApplyToTUs,IsProvideAsUserAction,M_HU_Process_ID,Updated,UpdatedBy) VALUES (0,0,584977,TO_TIMESTAMP('2022-02-10 17:33:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','Y',540019,TO_TIMESTAMP('2022-02-10 17:33:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-10T15:34:10.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToLUs='Y',Updated=TO_TIMESTAMP('2022-02-10 17:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T15:51:06.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541058
;

-- 2022-02-10T15:58:42.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET AD_Process_ID=584980,Updated=TO_TIMESTAMP('2022-02-10 17:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:12:31.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='WEBUI_M_HU_PrintQRCodeLabel',Updated=TO_TIMESTAMP('2022-02-10 18:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584980
;

-- 2022-02-10T16:13:18.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='HU QR barcode',Updated=TO_TIMESTAMP('2022-02-10 18:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584977
;

-- 2022-02-10T16:15:30.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToTUs='Y',Updated=TO_TIMESTAMP('2022-02-10 18:15:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:16:59.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToCUs='Y',Updated=TO_TIMESTAMP('2022-02-10 18:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:17:10.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToCUs='N',Updated=TO_TIMESTAMP('2022-02-10 18:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:25:12.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToTopLevelHUsOnly='Y',Updated=TO_TIMESTAMP('2022-02-10 18:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:25:16.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToCUs='Y', IsApplyToTopLevelHUsOnly='N',Updated=TO_TIMESTAMP('2022-02-10 18:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:26:21.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToTopLevelHUsOnly='Y',Updated=TO_TIMESTAMP('2022-02-10 18:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:28:22.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToCUs='N',Updated=TO_TIMESTAMP('2022-02-10 18:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:42:47.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToCUs='Y',Updated=TO_TIMESTAMP('2022-02-10 18:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

-- 2022-02-10T16:53:24.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_Process SET IsApplyToTopLevelHUsOnly='N',Updated=TO_TIMESTAMP('2022-02-10 18:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_Process_ID=540019
;

