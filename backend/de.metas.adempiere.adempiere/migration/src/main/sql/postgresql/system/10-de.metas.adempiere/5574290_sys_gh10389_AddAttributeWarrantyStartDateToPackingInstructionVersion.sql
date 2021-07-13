-- 2020-12-08T10:05:35.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2020-12-08 12:05:35','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','Y','N','N',540093,540067,101,'TOPD',120,TO_TIMESTAMP('2020-12-08 12:05:35','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2020-12-08T10:06:13.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsInstanceAttribute='N',Updated=TO_TIMESTAMP('2020-12-08 12:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540067
;

-- 2020-12-08T10:06:20.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2020-12-08 12:06:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540067
;

-- 2020-12-08T10:06:34.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-12-08 12:06:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540067
;

-- 2020-12-08T10:06:34.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsActive='N',Updated=TO_TIMESTAMP('2020-12-08 12:06:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540067
;
