-- 2020-07-24T13:47:10.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Shipper (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,M_Shipper_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2020-07-24 16:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540011,'Siro',TO_TIMESTAMP('2020-07-24 16:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Siro')
;

-- 2020-07-24T13:50:19.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'S',TO_TIMESTAMP('2020-07-24 16:50:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540047,'Article Flavor',TO_TIMESTAMP('2020-07-24 16:50:19','YYYY-MM-DD HH24:MI:SS'),100,'Article_Flavor')
;

-- 2020-07-24T13:50:21.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsStorageRelevant='Y',Updated=TO_TIMESTAMP('2020-07-24 16:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540047
;

-- 2020-07-24T13:50:22.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2020-07-24 16:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540047
;

-- 2020-07-24T13:50:22.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540047)
;

-- 2020-07-24T13:50:27.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2020-07-24 16:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540047
;

-- 2020-07-24T13:53:55.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'S',TO_TIMESTAMP('2020-07-24 16:53:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540048,'Expiry Date',TO_TIMESTAMP('2020-07-24 16:53:55','YYYY-MM-DD HH24:MI:SS'),100,'HU_ExpiryDate')
;

-- 2020-07-24T13:54:02.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Value='HU_ExpiryDate',Updated=TO_TIMESTAMP('2020-07-24 16:54:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540048
;

-- 2020-07-24T13:54:14.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2020-07-24 16:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540048
;

-- 2020-07-24T13:54:14.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540048)
;

-- 2020-07-24T13:54:16.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsStorageRelevant='Y',Updated=TO_TIMESTAMP('2020-07-24 16:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540048
;

-- 2020-07-24T13:54:23.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2020-07-24 16:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540048
;

-- 2020-07-24T13:54:26.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET AttributeValueType='D',Updated=TO_TIMESTAMP('2020-07-24 16:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540048
;

