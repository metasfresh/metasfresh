
-- 2018-01-25T14:52:48.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2018-01-25 14:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550444
;

-- 2018-01-25T17:19:06.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543783,0,'RefM_PackingMaterial_InOutLine_ID',TO_TIMESTAMP('2018-01-25 17:19:06','YYYY-MM-DD HH24:MI:SS'),100,'Reference the in out line from where the packaging material input line was generated','de.metas.inout','Y','RefM_PackingMaterial_InOutLine_ID','RefM_PackingMaterial_InOutLine_ID',TO_TIMESTAMP('2018-01-25 17:19:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-25T17:19:06.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543783 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-25T17:20:50.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', PrintName='Ref_PackingMaterial_InOutLine_ID',Updated=TO_TIMESTAMP('2018-01-25 17:20:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:20:50.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated', Help=NULL WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:20:50.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated', Help=NULL, AD_Element_ID=543783 WHERE UPPER(ColumnName)='REF_PACKINGMATERIAL_INOUTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-25T17:20:50.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated', Help=NULL WHERE AD_Element_ID=543783 AND IsCentrallyMaintained='Y'
;

-- 2018-01-25T17:20:50.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543783)
;

-- 2018-01-25T17:20:50.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543783)
;

-- 2018-01-25T17:21:50.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,558625,543783,0,18,295,540270,190,'N','Ref_PackingMaterial_InOutLine_ID',TO_TIMESTAMP('2018-01-25 17:21:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Reference the in out line from where the packaging material input line was generated','de.metas.inout',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ref_PackingMaterial_InOutLine_ID',0,0,TO_TIMESTAMP('2018-01-25 17:21:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-01-25T17:21:50.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558625 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-01-25T17:21:54.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN Ref_PackingMaterial_InOutLine_ID NUMERIC(10)')
;

-- 2018-01-25T17:21:54.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT RefPackingMaterialInOutLine_CI FOREIGN KEY (Ref_PackingMaterial_InOutLine_ID) REFERENCES public.M_InOutLine DEFERRABLE INITIALLY DEFERRED
;

-- 2018-01-25T17:24:59.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Reference the in out line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.',Updated=TO_TIMESTAMP('2018-01-25 17:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:24:59.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:24:59.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL, AD_Element_ID=543783 WHERE UPPER(ColumnName)='REF_PACKINGMATERIAL_INOUTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-25T17:24:59.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE AD_Element_ID=543783 AND IsCentrallyMaintained='Y'
;

-- 2018-01-25T17:24:59.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the in out line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543783)
;

-- 2018-01-25T17:25:19.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Reference the inout line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.',Updated=TO_TIMESTAMP('2018-01-25 17:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:25:19.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:25:19.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL, AD_Element_ID=543783 WHERE UPPER(ColumnName)='REF_PACKINGMATERIAL_INOUTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-25T17:25:19.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE AD_Element_ID=543783 AND IsCentrallyMaintained='Y'
;

-- 2018-01-25T17:25:19.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packaging material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543783)
;

-- 2018-01-25T17:25:25.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Reference the inout line from where the packing material input line was generated. Is filled up only if the IC is a packing material line.',Updated=TO_TIMESTAMP('2018-01-25 17:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:25:25.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packing material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:25:25.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packing material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL, AD_Element_ID=543783 WHERE UPPER(ColumnName)='REF_PACKINGMATERIAL_INOUTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-25T17:25:25.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ref_PackingMaterial_InOutLine_ID', Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packing material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE AD_Element_ID=543783 AND IsCentrallyMaintained='Y'
;

-- 2018-01-25T17:25:25.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ref_PackingMaterial_InOutLine_ID', Description='Reference the inout line from where the packing material input line was generated. Is filled up only if the IC is a packing material line.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543783)
;

-- 2018-01-25T17:27:55.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2018-01-25 17:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543783
;

-- 2018-01-25T17:28:20.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2018-01-25 17:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558625
;

