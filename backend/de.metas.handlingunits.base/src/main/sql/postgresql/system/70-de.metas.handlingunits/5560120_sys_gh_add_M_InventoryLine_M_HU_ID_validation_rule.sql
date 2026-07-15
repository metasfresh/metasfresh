-- 2020-05-27T13:12:55.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540502,'M_HU.M_Locator_ID=@M_Locator_ID@',TO_TIMESTAMP('2020-05-27 16:12:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_HU_FROM_LOCATOR','S',TO_TIMESTAMP('2020-05-27 16:12:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-27T13:14:01.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540499,Updated=TO_TIMESTAMP('2020-05-27 16:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559545
;

-- 2020-05-27T13:14:10.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540502,Updated=TO_TIMESTAMP('2020-05-27 16:14:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559545
;

