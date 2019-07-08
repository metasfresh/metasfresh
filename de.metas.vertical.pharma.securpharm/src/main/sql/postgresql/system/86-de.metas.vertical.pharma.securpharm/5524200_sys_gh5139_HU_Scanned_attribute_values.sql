-- 2019-06-10T11:44:17.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) 
SELECT 0,0,TO_TIMESTAMP('2019-06-10 11:44:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540040,540039,'Fraud',TO_TIMESTAMP('2019-06-10 11:44:16','YYYY-MM-DD HH24:MI:SS'),100,'F'
WHERE NOT EXISTS (select 1 from M_AttributeValue where M_Attribute_ID=540040 AND Value='F')
;

-- 2019-06-10T11:44:33.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeValue SET IsNullFieldValue='Y',Updated=TO_TIMESTAMP('2019-06-10 11:44:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_AttributeValue_ID=540001
;

