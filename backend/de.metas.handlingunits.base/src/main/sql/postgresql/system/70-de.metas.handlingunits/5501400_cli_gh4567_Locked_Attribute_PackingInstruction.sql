-- 2018-09-13T17:08:21.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2018-09-13 17:08:20','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','N','N','N','Y',540039,540040,100,'NONE',9040,TO_TIMESTAMP('2018-09-13 17:08:20','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2018-09-13T17:08:56.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2018-09-13 17:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540040
;

