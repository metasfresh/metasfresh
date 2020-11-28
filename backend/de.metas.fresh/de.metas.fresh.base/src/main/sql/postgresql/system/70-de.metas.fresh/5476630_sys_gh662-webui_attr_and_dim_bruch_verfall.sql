-- 2017-11-08T10:31:02.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,C_UOM_ID,IsActive,IsAttrDocumentRelevant,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'L',TO_TIMESTAMP('2017-11-08 10:31:02','YYYY-MM-DD HH24:MI:SS'),100,100,'Y','N','N','N','N','N','N','N',540031,'Beschädigt',TO_TIMESTAMP('2017-11-08 10:31:02','YYYY-MM-DD HH24:MI:SS'),100,'HU_Damaged',0,0)
;

-- 2017-11-08T10:31:49.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET C_UOM_ID=NULL, IsInstanceAttribute='Y', IsStorageRelevant='Y',Updated=TO_TIMESTAMP('2017-11-08 10:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540031
;

-- 2017-11-08T10:31:49.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540031)
;


-- 2017-11-08T10:38:34.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-11-08 10:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540031,540014,'Bruch',TO_TIMESTAMP('2017-11-08 10:38:34','YYYY-MM-DD HH24:MI:SS'),100,'damaged')
;

-- 2017-11-08T10:38:40.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Name='Bruch',Updated=TO_TIMESTAMP('2017-11-08 10:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540031
;

-- 2017-11-08T10:40:45.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,C_UOM_ID,IsActive,IsAttrDocumentRelevant,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'L',TO_TIMESTAMP('2017-11-08 10:40:44','YYYY-MM-DD HH24:MI:SS'),100,100,'Y','N','N','N','N','N','N','N',540032,'Fast Abgelaufen',TO_TIMESTAMP('2017-11-08 10:40:44','YYYY-MM-DD HH24:MI:SS'),100,'HU_Almost_Expired',0,0)
;

-- 2017-11-08T10:50:28.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-11-08 10:50:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540032,540015,'Verfall',TO_TIMESTAMP('2017-11-08 10:50:27','YYYY-MM-DD HH24:MI:SS'),100,'expired')
;

-- 2017-11-08T10:50:42.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM M_AttributeValue WHERE M_AttributeValue_ID=540015
;

-- 2017-11-08T10:50:55.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM M_Attribute WHERE M_Attribute_ID=540032
;

-- 2017-11-08T10:51:23.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,C_UOM_ID,IsActive,IsAttrDocumentRelevant,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'L',TO_TIMESTAMP('2017-11-08 10:51:23','YYYY-MM-DD HH24:MI:SS'),100,100,'Y','N','N','N','N','N','N','N',540033,'Verfall',TO_TIMESTAMP('2017-11-08 10:51:23','YYYY-MM-DD HH24:MI:SS'),100,'HU_Expired',0,0)
;

-- 2017-11-08T10:51:34.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-11-08 10:51:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540033,540016,'Verfall',TO_TIMESTAMP('2017-11-08 10:51:34','YYYY-MM-DD HH24:MI:SS'),100,'expired')
;

-- 2017-11-08T10:52:13.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2017-11-08 10:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540033
;

-- 2017-11-08T10:52:13.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540033)
;

-- 2017-11-08T10:56:25.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DIM_Dimension_Spec (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DIM_Dimension_Spec_ID,DIM_Dimension_Type_ID,IsActive,IsIncludeEmpty,Name,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2017-11-08 10:56:25','YYYY-MM-DD HH24:MI:SS'),100,540008,540000,'Y','Y','Bruch_und_Verfall',TO_TIMESTAMP('2017-11-08 10:56:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-08T10:56:47.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DIM_Dimension_Spec_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,DIM_Dimension_Spec_Attribute_ID,DIM_Dimension_Spec_ID,IsActive,IsIncludeAllAttributeValues,IsValueAggregate,M_Attribute_ID,Updated,UpdatedBy) VALUES (0,0,'L',TO_TIMESTAMP('2017-11-08 10:56:47','YYYY-MM-DD HH24:MI:SS'),100,540016,540008,'Y','Y','N',540031,TO_TIMESTAMP('2017-11-08 10:56:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-08T10:56:58.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DIM_Dimension_Spec_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,DIM_Dimension_Spec_Attribute_ID,DIM_Dimension_Spec_ID,IsActive,IsIncludeAllAttributeValues,IsValueAggregate,M_Attribute_ID,Updated,UpdatedBy) VALUES (0,0,'L',TO_TIMESTAMP('2017-11-08 10:56:58','YYYY-MM-DD HH24:MI:SS'),100,540017,540008,'Y','Y','N',540033,TO_TIMESTAMP('2017-11-08 10:56:58','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE DIM_Dimension_Spec SET InternalName='DIM_damaged_expired' WHERE DIM_Dimension_Spec_ID=540008;
