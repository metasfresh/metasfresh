-- 2020-07-07T12:35:02.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540507,'ESR_Import.IsProcessed=''Y'' AND ESR_Import.IsReconciled=''N''',TO_TIMESTAMP('2020-07-07 15:35:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ESR_Import_EligibleToBeReconciliatedWithBankStatement','S',TO_TIMESTAMP('2020-07-07 15:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-07T12:35:22.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540507,Updated=TO_TIMESTAMP('2020-07-07 15:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541763
;

-- 2020-07-14T13:26:35.743Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='ESR_Import.IsReconciled=''N''',Updated=TO_TIMESTAMP('2020-07-14 16:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540507
;

