-- 2017-11-03T13:02:30.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='C_Order_CreateCompensationGroup',Updated=TO_TIMESTAMP('2017-11-03 13:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540877
;

-- 2017-11-03T13:05:01.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (Code,Type,AD_Client_ID,IsActive,Created,CreatedBy,AD_Val_Rule_ID,AD_Org_ID,Name,EntityType,Updated,UpdatedBy) VALUES ('M_Product.IsStocked=''N''','S',0,'Y',TO_TIMESTAMP('2017-11-03 13:05:01','YYYY-MM-DD HH24:MI:SS'),100,540374,0,'M_Product_ForCompensationLine','de.metas.order',TO_TIMESTAMP('2017-11-03 13:05:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-03T13:05:16.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540374,Updated=TO_TIMESTAMP('2017-11-03 13:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541228
;

