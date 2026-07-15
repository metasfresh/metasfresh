-- 2020-12-14T23:29:14.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2020-12-15 01:29:13','YYYY-MM-DD HH24:MI:SS'),100,540027,'Y','Y','Y','N','N','Y',540092,540068,100,'NONE',9100,TO_TIMESTAMP('2020-12-15 01:29:13','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2020-12-17T12:00:13.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET PropagationType='TOPD', SplitterStrategy_JavaClass_ID=540017,Updated=TO_TIMESTAMP('2020-12-17 14:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540068
;

