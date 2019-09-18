-- 2019-09-12T13:51:47.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'S',TO_TIMESTAMP('2019-09-12 16:51:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540044,'MHD Restlaufzeit in Monaten',TO_TIMESTAMP('2019-09-12 16:51:46','YYYY-MM-DD HH24:MI:SS'),100,'MonthsUntilExpiration',0,0)
;

-- 2019-09-12T13:52:15.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsAttrDocumentRelevant='Y', IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2019-09-12 16:52:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540044
;

-- 2019-09-12T13:52:15.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540044)
;

-- 2019-09-12T13:52:26.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET AttributeValueType='N',Updated=TO_TIMESTAMP('2019-09-12 16:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540044
;

-- 2019-09-16T08:19:18.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Value='MonthsUntilExpiry',Updated=TO_TIMESTAMP('2019-09-16 11:19:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540044
;

-- 2019-09-16T09:17:13.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,C_UOM_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,100,TO_TIMESTAMP('2019-09-16 12:17:13','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','N','Y',540044,540046,100,'NONE',9060,TO_TIMESTAMP('2019-09-16 12:17:13','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2019-09-16T13:12:06.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsAttrDocumentRelevant='N',Updated=TO_TIMESTAMP('2019-09-16 16:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540044
;

-- 2019-09-16T15:59:18.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET ValueMax=NULL, ValueMin=NULL,Updated=TO_TIMESTAMP('2019-09-16 18:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540044
;

-- 2019-09-16T16:09:09.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET C_UOM_ID=100,Updated=TO_TIMESTAMP('2019-09-16 19:09:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540044
;

