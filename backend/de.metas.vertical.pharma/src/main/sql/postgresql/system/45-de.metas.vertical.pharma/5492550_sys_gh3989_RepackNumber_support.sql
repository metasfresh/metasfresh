-- 2018-05-03T19:42:24.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (IsHighVolume,CreatedBy,IsActive,Created,IsStorageRelevant,M_Attribute_ID,IsMandatory,AD_Client_ID,C_UOM_ID,IsPricingRelevant,IsAttrDocumentRelevant,IsInstanceAttribute,AttributeValueType,IsReadOnlyValues,IsTransferWhenNull,Value,AD_Org_ID,Name,UpdatedBy,ValueMax,ValueMin,Updated) VALUES ('N',100,'Y',TO_TIMESTAMP('2018-05-03 19:42:24','YYYY-MM-DD HH24:MI:SS'),'N',540037,'N',0,100,'N','Y','Y','L','N','N','IsRepackNumberRequired',0,'Require Repack Number',100,0,0,TO_TIMESTAMP('2018-05-03 19:42:24','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-03T19:42:36.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (Value,CreatedBy,AD_Client_ID,M_Attribute_ID,M_AttributeValue_ID,IsActive,IsNullFieldValue,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES ('Y',100,0,540037,540017,'Y','N',0,'Yes',100,TO_TIMESTAMP('2018-05-03 19:42:36','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-03 19:42:36','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-03T19:42:44.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (Value,CreatedBy,AD_Client_ID,M_Attribute_ID,M_AttributeValue_ID,IsActive,IsNullFieldValue,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES ('N',100,0,540037,540018,'Y','N',0,'No',100,TO_TIMESTAMP('2018-05-03 19:42:44','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-03 19:42:44','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-03T19:43:13.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (IsHighVolume,CreatedBy,IsActive,Created,IsStorageRelevant,M_Attribute_ID,IsMandatory,AD_Client_ID,C_UOM_ID,IsPricingRelevant,IsAttrDocumentRelevant,IsInstanceAttribute,AttributeValueType,IsReadOnlyValues,IsTransferWhenNull,Value,AD_Org_ID,Name,UpdatedBy,ValueMax,ValueMin,Updated) VALUES ('N',100,'Y',TO_TIMESTAMP('2018-05-03 19:43:13','YYYY-MM-DD HH24:MI:SS'),'N',540038,'N',0,100,'N','N','Y','S','N','N','RepackNumber',0,'Repack Number',100,0,0,TO_TIMESTAMP('2018-05-03 19:43:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-03T19:45:50.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (CreatedBy,IsActive,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SplitterStrategy_JavaClass_ID,IsMandatory,AD_Client_ID,IsReadOnly,M_Attribute_ID,HU_TansferStrategy_JavaClass_ID,UseInASI,IsDisplayed,IsInstanceAttribute,AD_Org_ID,UpdatedBy,Created,SeqNo,Updated) VALUES (100,'Y',540038,100,'TOPD',540017,'N',1000000,'Y',540037,540027,'Y','Y','Y',1000000,100,TO_TIMESTAMP('2018-05-03 19:45:50','YYYY-MM-DD HH24:MI:SS'),9020,TO_TIMESTAMP('2018-05-03 19:45:50','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-03T19:46:17.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_HU_PI_Attribute (CreatedBy,IsActive,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SplitterStrategy_JavaClass_ID,IsMandatory,AD_Client_ID,IsReadOnly,M_Attribute_ID,HU_TansferStrategy_JavaClass_ID,UseInASI,IsDisplayed,IsInstanceAttribute,AD_Org_ID,UpdatedBy,Created,SeqNo,Updated) VALUES (100,'Y',540039,100,'TOPD',540017,'N',1000000,'N',540038,540027,'Y','Y','N',1000000,100,TO_TIMESTAMP('2018-05-03 19:46:17','YYYY-MM-DD HH24:MI:SS'),9030,TO_TIMESTAMP('2018-05-03 19:46:17','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-03T19:46:28.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2018-05-03 19:46:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540039
;

-- 2018-05-04T13:57:33.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JavaClass (CreatedBy,IsActive,AD_JavaClass_Type_ID,Classname,AD_Org_ID,AD_JavaClass_ID,AD_Client_ID,IsInterface,Name,EntityType,UpdatedBy,Created,Updated) VALUES (100,'Y',540026,'de.metas.vertical.pharma.attributes.RepackNumberAttributeCallout',0,540048,0,'N','Repack Number callout','de.metas.vertical.pharma',100,TO_TIMESTAMP('2018-05-04 13:57:33','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-04 13:57:33','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-04T13:58:24.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET AD_JavaClass_ID=540048,Updated=TO_TIMESTAMP('2018-05-04 13:58:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540037
;

-- 2018-05-04T13:58:35.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET AD_JavaClass_ID=540048,Updated=TO_TIMESTAMP('2018-05-04 13:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540038
;

-- 2018-05-04T14:07:47.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',0,'pharma.RepackNumberNotSetForHU','de.metas.vertical.pharma',544713,'pharma.RepackNumberNotSetForHU',0,0,TO_TIMESTAMP('2018-05-04 14:07:47','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-04 14:07:47','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-04T14:07:47.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544713 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-05-04T14:29:45.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E', MsgText='Die Repack Nr. muss erfasst werden für {0}!',Updated=TO_TIMESTAMP('2018-05-04 14:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544713
;

-- 2018-05-04T14:30:03.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-04 14:30:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Die Repack Nr. muss erfasst werden für {0}!' WHERE AD_Message_ID=544713 AND AD_Language='de_CH'
;

-- 2018-05-04T14:30:18.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-04 14:30:18','YYYY-MM-DD HH24:MI:SS'),MsgText='Repack number is mandatory but was not set for {0}!' WHERE AD_Message_ID=544713 AND AD_Language='nl_NL'
;

-- 2018-05-04T14:30:24.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-04 14:30:24','YYYY-MM-DD HH24:MI:SS'),MsgText='Repack number is mandatory but was not set for {0}!' WHERE AD_Message_ID=544713 AND AD_Language='en_US'
;

