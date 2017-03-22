----------- clean data ---

delete from C_Async_Batch_Type where C_Async_Batch_Type_ID=1000000;

-- 22.09.2016 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Async_Batch_Type (AD_Client_ID,AD_Org_ID,C_Async_Batch_Type_ID,Created,CreatedBy,InternalName,IsActive,IsSendMail,IsSendNotification,Updated,UpdatedBy) VALUES (1000000,0,540003,TO_TIMESTAMP('2016-09-22 18:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Default','Y','N','N',TO_TIMESTAMP('2016-09-22 18:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

