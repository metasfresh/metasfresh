-- 2018-04-16T17:07:47.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,C_UOM_ID,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'S',100,TO_TIMESTAMP('2018-04-16 17:07:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540035,'TE',TO_TIMESTAMP('2018-04-16 17:07:47','YYYY-MM-DD HH24:MI:SS'),100,'HU_TE',0,0)
;

-- 2018-04-16T17:08:50.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'D',TO_TIMESTAMP('2018-04-16 17:08:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','N','N',540036,'DateReceived',TO_TIMESTAMP('2018-04-16 17:08:50','YYYY-MM-DD HH24:MI:SS'),100,'HU_DateReceived',0,0)
;

-- 2018-04-16T17:08:57.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2018-04-16 17:08:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540035
;

-- 2018-04-16T17:08:57.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540035)
;

