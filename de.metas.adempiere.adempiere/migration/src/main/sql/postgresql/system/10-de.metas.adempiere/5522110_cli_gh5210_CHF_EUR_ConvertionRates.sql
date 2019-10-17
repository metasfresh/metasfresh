-- 2019-05-21T16:17:01.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Conversion_Rate (AD_Client_ID,AD_Org_ID,C_Conversion_Rate_ID,C_ConversionType_ID,C_Currency_ID,C_Currency_ID_To,Created,CreatedBy,
DivideRate,IsActive,MultiplyRate,Updated,UpdatedBy,ValidFrom,ValidTo) VALUES (1000000,0,540003,114,318,102,
TO_TIMESTAMP('2019-05-21 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,0.884955752212,'Y',1.130000000000,
TO_TIMESTAMP('2019-05-21 16:17:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-21','YYYY-MM-DD'),TO_TIMESTAMP('2056-12-31','YYYY-MM-DD'))
;

-- 2019-05-21T17:30:50.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Conversion_Rate (AD_Client_ID,AD_Org_ID,C_Conversion_Rate_ID,C_ConversionType_ID,C_Currency_ID,C_Currency_ID_To,
Created,CreatedBy,DivideRate,IsActive,MultiplyRate,Updated,UpdatedBy,ValidFrom,ValidTo) VALUES (1000000,0,540004,114,102,318,
TO_TIMESTAMP('2019-05-21 17:30:49','YYYY-MM-DD HH24:MI:SS'),100,1.130000000000,'Y',0.884955752212,
TO_TIMESTAMP('2019-05-21 17:30:49','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-21','YYYY-MM-DD'),TO_TIMESTAMP('2056-12-31','YYYY-MM-DD'))
;


;

-- 2019-05-21T17:54:28.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Conversion_Rate SET DivideRate=1.130000000000, MultiplyRate=0.884955752212,Updated=TO_TIMESTAMP('2019-05-21 17:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 
WHERE C_Conversion_Rate_ID=540003
;

-- 2019-05-21T17:57:46.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Conversion_Rate SET DivideRate=0.884955752212, MultiplyRate=1.130000000000,Updated=TO_TIMESTAMP('2019-05-21 17:57:46','YYYY-MM-DD HH24:MI:SS'),
UpdatedBy=100 WHERE C_Conversion_Rate_ID=540004
;


