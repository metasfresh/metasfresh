-- 30.10.2015 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsInstanceAttribute,IsMandatory,IsMatchHUStorage,IsPricingRelevant,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (1000000,1000000,'L',TO_TIMESTAMP('2015-10-30 17:58:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','N',540021,'Waschprobe',TO_TIMESTAMP('2015-10-30 17:58:27','YYYY-MM-DD HH24:MI:SS'),100,'IsQualityInspection',0,0)
;

-- 30.10.2015 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2015-10-30 17:58:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540021,540008,'Nein',TO_TIMESTAMP('2015-10-30 17:58:52','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 30.10.2015 17:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2015-10-30 17:59:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540021,540009,'Ja',TO_TIMESTAMP('2015-10-30 17:59:05','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 30.10.2015 17:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeValue SET IsNullFieldValue='Y',Updated=TO_TIMESTAMP('2015-10-30 17:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_AttributeValue_ID=540008
;

