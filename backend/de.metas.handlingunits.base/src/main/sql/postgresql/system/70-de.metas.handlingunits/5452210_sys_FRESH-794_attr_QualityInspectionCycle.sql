-- Oct 24, 2016 3:38 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsInstanceAttribute,IsMandatory,IsMatchHUStorage,IsPricingRelevant,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (1000000,1000000,'L',TO_TIMESTAMP('2016-10-24 15:38:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','N',540025,'Waschprobe cycle',TO_TIMESTAMP('2016-10-24 15:38:58','YYYY-MM-DD HH24:MI:SS'),100,'QualityInspectionCycle',0,0)
;

update M_Attribute set AD_Org_ID=0 where M_Attribute_ID=540025;

-- Oct 24, 2016 3:40 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2016-10-24 15:40:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540025,540010,'Waschprobe 1',TO_TIMESTAMP('2016-10-24 15:40:40','YYYY-MM-DD HH24:MI:SS'),100,'1')
;

-- Oct 24, 2016 3:41 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsNullFieldValue,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2016-10-24 15:41:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540025,540011,'Waschprobe 2',TO_TIMESTAMP('2016-10-24 15:41:05','YYYY-MM-DD HH24:MI:SS'),100,'2')
;

-- Oct 24, 2016 3:41 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,SplitterStrategy_JavaClass_ID,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2016-10-24 15:41:57','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','Y',540025,540034,100,'TOPD',136,540017,TO_TIMESTAMP('2016-10-24 15:41:57','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- Oct 24, 2016 3:45 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Name='Waschprobe (old)',Updated=TO_TIMESTAMP('2016-10-24 15:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540021
;

-- Oct 24, 2016 3:45 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Name='Waschprobe',Updated=TO_TIMESTAMP('2016-10-24 15:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540025
;

-- Oct 24, 2016 3:46 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Name='Waschprobe alt',Updated=TO_TIMESTAMP('2016-10-24 15:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540021
;

-- Oct 24, 2016 4:32 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeUse (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeSet_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2016-10-24 16:32:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',540025,1000005,65,TO_TIMESTAMP('2016-10-24 16:32:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Oct 24, 2016 4:32 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE M_AttributeSet_ID=1000005 AND IsInstanceAttribute='N' AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y' OR EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y'))
;

-- Oct 24, 2016 4:32 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_AttributeSet mas SET IsInstanceAttribute='N' WHERE M_AttributeSet_ID=1000005 AND IsInstanceAttribute='Y'	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N' AND NOT EXISTS (SELECT * FROM M_AttributeUse mau INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID AND mau.IsActive='Y' AND ma.IsActive='Y' AND ma.IsInstanceAttribute='Y')
;

-- Oct 24, 2016 5:41 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsReadOnly='N',Updated=TO_TIMESTAMP('2016-10-24 17:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540034
;

