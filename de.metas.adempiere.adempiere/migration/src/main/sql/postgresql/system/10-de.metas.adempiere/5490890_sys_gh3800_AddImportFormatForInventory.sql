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


----------------------------------------




-- 2018-04-16T17:18:51.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET IsActive='Y',Updated=TO_TIMESTAMP('2018-04-16 17:18:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=531098
;

-- 2018-04-16T17:20:08.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=30, StartNo=3,Updated=TO_TIMESTAMP('2018-04-16 17:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=531104
;

-- 2018-04-16T17:20:14.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET Name='PZN',Updated=TO_TIMESTAMP('2018-04-16 17:20:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=531104
;

-- 2018-04-16T17:20:27.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=10, StartNo=1,Updated=TO_TIMESTAMP('2018-04-16 17:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=531106
;

-- 2018-04-16T17:20:59.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=8989, DataType='S', Name='Lagerort-Schlüssel',Updated=TO_TIMESTAMP('2018-04-16 17:20:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=531106
;

-- 2018-04-16T17:22:02.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET Name='QtyInternalUse', SeqNo=130, StartNo=13,Updated=TO_TIMESTAMP('2018-04-16 17:22:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=531105
;

-- 2018-04-16T17:23:12.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,ConstantValue,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559688,531098,540532,0,'1000000',TO_TIMESTAMP('2018-04-16 17:23:12','YYYY-MM-DD HH24:MI:SS'),100,'dd.mm.yyyy','D','.','N',0,'Y','Datum der letzten Inventur',20,2,TO_TIMESTAMP('2018-04-16 17:23:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-16T17:24:15.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,ConstantValue,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559687,531098,540533,0,'1000000',TO_TIMESTAMP('2018-04-16 17:24:15','YYYY-MM-DD HH24:MI:SS'),100,'dd.mm.yyyy','S','.','N',0,'Y','Datum der letzten Inventur',100,10,TO_TIMESTAMP('2018-04-16 17:24:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-16T17:24:50.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,ConstantValue,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559684,531098,540534,0,'1000000',TO_TIMESTAMP('2018-04-16 17:24:50','YYYY-MM-DD HH24:MI:SS'),100,'dd.mm.yyyy','S','.','N',0,'Y','Lot Blocked',110,11,TO_TIMESTAMP('2018-04-16 17:24:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-16T17:24:58.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=8818, Name='Lot_Los-Nr.',Updated=TO_TIMESTAMP('2018-04-16 17:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540533
;

-- 2018-04-16T17:25:31.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,ConstantValue,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559683,531098,540535,0,'1000000',TO_TIMESTAMP('2018-04-16 17:25:31','YYYY-MM-DD HH24:MI:SS'),100,'dd.mm.yyyy','D','.','N',0,'Y','Best Before Date',120,12,TO_TIMESTAMP('2018-04-16 17:25:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-16T17:25:49.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataFormat='mm.yyyy',Updated=TO_TIMESTAMP('2018-04-16 17:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540535
;

-- 2018-04-16T17:26:39.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,ConstantValue,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559687,531098,540536,0,'1000000',TO_TIMESTAMP('2018-04-16 17:26:39','YYYY-MM-DD HH24:MI:SS'),100,'dd.mm.yyyy','S','.','N',0,'Y','TE',90,9,TO_TIMESTAMP('2018-04-16 17:26:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-16T17:27:41.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,ConstantValue,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559685,531098,540537,0,'1000000',TO_TIMESTAMP('2018-04-16 17:27:41','YYYY-MM-DD HH24:MI:SS'),100,'dd.mm.yyyy','D','.','N',0,'Y','Eingangsdatum',160,16,TO_TIMESTAMP('2018-04-16 17:27:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-16T17:28:11.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,ConstantValue,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559690,531098,540538,0,'1000000',TO_TIMESTAMP('2018-04-16 17:28:11','YYYY-MM-DD HH24:MI:SS'),100,'dd.mm.yyyy','S','.','N',0,'Y','Eingangsdatum',210,21,TO_TIMESTAMP('2018-04-16 17:28:11','YYYY-MM-DD HH24:MI:SS'),100)
;

