-- 01.02.2016 17:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsInstanceAttribute,IsMandatory,IsMatchHUStorage,IsPricingRelevant,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,'N',TO_TIMESTAMP('2016-02-01 17:45:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','N',540023,'Bestellzeile',TO_TIMESTAMP('2016-02-01 17:45:39','YYYY-MM-DD HH24:MI:SS'),100,'HU_PurchaseOrderLine_ID')
;

-- 01.02.2016 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsInstanceAttribute,IsMandatory,IsMatchHUStorage,IsPricingRelevant,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,'N',TO_TIMESTAMP('2016-02-01 17:46:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','N',540024,'Wareneingang Zeile',TO_TIMESTAMP('2016-02-01 17:46:16','YYYY-MM-DD HH24:MI:SS'),100,'HU_ReceiptInOutLine_ID')
;

-- 01.02.2016 17:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsInstanceAttribute,IsMandatory,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,SplitterStrategy_JavaClass_ID,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2016-02-01 17:54:26','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','N','Y',540023,540032,100,'TOPD',9000,540017,TO_TIMESTAMP('2016-02-01 17:54:26','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 01.02.2016 17:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsInstanceAttribute,IsMandatory,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,SplitterStrategy_JavaClass_ID,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2016-02-01 17:54:43','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','N','Y',540024,540033,100,'TOPD',9010,540017,TO_TIMESTAMP('2016-02-01 17:54:43','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 01.02.2016 18:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsDisplayed='N',Updated=TO_TIMESTAMP('2016-02-01 18:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540032
;

-- 01.02.2016 18:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsDisplayed='N',Updated=TO_TIMESTAMP('2016-02-01 18:31:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540033
;

