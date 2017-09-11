-- 2017-09-11T13:09:22.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543413,0,'ContractStartDate',TO_TIMESTAMP('2017-09-11 13:09:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.subscription','Y','Contract Start Date','Contract Start Date',TO_TIMESTAMP('2017-09-11 13:09:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:09:22.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543413 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-11T13:09:43.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543414,0,'ContractEndDate',TO_TIMESTAMP('2017-09-11 13:09:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.subscription','Y','Contract End Date','Contract End Date',TO_TIMESTAMP('2017-09-11 13:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:09:43.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543414 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-11T13:10:19.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.flatrate',Updated=TO_TIMESTAMP('2017-09-11 13:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543413
;

-- 2017-09-11T13:10:57.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.flatrate',Updated=TO_TIMESTAMP('2017-09-11 13:10:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543414
;

---------------------------------------------------------


-- 2017-09-11T13:41:54.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557134,540010,540113,0,TO_TIMESTAMP('2017-09-11 13:41:54','YYYY-MM-DD HH24:MI:SS'),100,'yyyy-MM-dd','D','.','N',0,'Y','ContractStartDate_Contract Start Date',30,3,TO_TIMESTAMP('2017-09-11 13:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:42:07.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557135,540010,540114,0,TO_TIMESTAMP('2017-09-11 13:42:07','YYYY-MM-DD HH24:MI:SS'),100,'yyyy-MM-dd','D','.','N',0,'Y','Contract End Date',30,3,TO_TIMESTAMP('2017-09-11 13:42:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-11T13:42:12.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET Name='Contract Start Date',Updated=TO_TIMESTAMP('2017-09-11 13:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540113
;

-- 2017-09-11T13:42:44.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=80, StartNo=8,Updated=TO_TIMESTAMP('2017-09-11 13:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540113
;

-- 2017-09-11T13:42:52.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=90, StartNo=9,Updated=TO_TIMESTAMP('2017-09-11 13:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540114
;


---------------------------------------------------------

