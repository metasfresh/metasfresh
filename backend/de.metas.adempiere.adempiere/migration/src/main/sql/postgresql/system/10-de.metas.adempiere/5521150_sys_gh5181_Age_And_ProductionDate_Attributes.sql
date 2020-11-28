-- 2019-05-08T14:24:04.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,C_UOM_ID,Created,CreatedBy,Description,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'N',100,TO_TIMESTAMP('2019-05-08 14:24:04','YYYY-MM-DD HH24:MI:SS'),100,'Age in months','Y','N','N','N','N','N','N','N','N',540042,'Age',TO_TIMESTAMP('2019-05-08 14:24:04','YYYY-MM-DD HH24:MI:SS'),100,'Age',0,0)
;

-- 2019-05-08T14:24:10.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2019-05-08 14:24:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540042
;

-- 2019-05-08T14:24:10.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540042)
;

-- 2019-05-08T14:38:35.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsPricingRelevant='Y',Updated=TO_TIMESTAMP('2019-05-08 14:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540042
;

-- 2019-05-08T16:22:33.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'D',TO_TIMESTAMP('2019-05-08 16:22:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','N','N',540043,'Production Date',TO_TIMESTAMP('2019-05-08 16:22:32','YYYY-MM-DD HH24:MI:SS'),100,'ProductionDate',0,0)
;

-- 2019-05-08T16:22:36.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET C_UOM_ID=NULL,Updated=TO_TIMESTAMP('2019-05-08 16:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540042
;

-- 2019-05-08T14:32:29.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,C_UOM_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,SplitterStrategy_JavaClass_ID,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,100,TO_TIMESTAMP('2019-05-08 14:32:29','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','N','N',540042,540043,100,'TOPD',9050,540017,TO_TIMESTAMP('2019-05-08 14:32:29','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2019-05-08T16:38:01.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2019-05-08 16:38:01','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','N','N',540043,540044,100,'TOPD',9060,TO_TIMESTAMP('2019-05-08 16:38:01','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2019-05-08T16:38:11.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET PropagationType='BOTU',Updated=TO_TIMESTAMP('2019-05-08 16:38:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540043
;

-- 2019-05-08T16:38:14.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET PropagationType='NONE',Updated=TO_TIMESTAMP('2019-05-08 16:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540043
;

-- 2019-05-08T16:38:18.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET SplitterStrategy_JavaClass_ID=540017,Updated=TO_TIMESTAMP('2019-05-08 16:38:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540043
;

-- 2019-05-08T16:38:21.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET SplitterStrategy_JavaClass_ID=NULL,Updated=TO_TIMESTAMP('2019-05-08 16:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540043
;

-- 2019-05-08T16:51:09.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsPricingRelevant='N',Updated=TO_TIMESTAMP('2019-05-08 16:51:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540042
;

-- 2019-05-08T16:51:12.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsPricingRelevant='Y',Updated=TO_TIMESTAMP('2019-05-08 16:51:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540042
;

-- 2019-05-09T10:17:25.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET UseInASI='Y',Updated=TO_TIMESTAMP('2019-05-09 10:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540043
;

-- 2019-05-09T10:20:29.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET SeqNo=180,Updated=TO_TIMESTAMP('2019-05-09 10:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540044
;

-- 2019-05-09T10:20:33.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET SeqNo=190,Updated=TO_TIMESTAMP('2019-05-09 10:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540043
;

