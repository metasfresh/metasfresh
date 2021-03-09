-- 2019-04-03T13:52:26.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576554,0,'SeqNo_Sequence_ID',TO_TIMESTAMP('2019-04-03 13:52:25','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Serial No. Sequence','Serial No. Sequence',TO_TIMESTAMP('2019-04-03 13:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T13:52:26.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576554 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-03T13:52:31.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-04-03 13:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576554
;

-- 2019-04-03T13:53:14.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567626,576554,0,30,128,53018,'SeqNo_Sequence_ID',TO_TIMESTAMP('2019-04-03 13:53:13','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Serial No. Sequence',0,0,TO_TIMESTAMP('2019-04-03 13:53:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-03T13:53:14.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567626 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T13:53:25.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_BOM','ALTER TABLE public.PP_Product_BOM ADD COLUMN SeqNo_Sequence_ID NUMERIC(10)')
;

-- 2019-04-03T13:53:26.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Product_BOM ADD CONSTRAINT SeqNoSequence_PPProductBOM FOREIGN KEY (SeqNo_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- 2019-04-03T13:54:01.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567626,578422,0,53028,TO_TIMESTAMP('2019-04-03 13:54:01','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Serial No. Sequence',TO_TIMESTAMP('2019-04-03 13:54:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T13:54:01.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578422 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T13:55:35.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-04-03 13:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540449
;

-- 2019-04-03T13:55:50.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540273,542424,TO_TIMESTAMP('2019-04-03 13:55:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','attributes',30,TO_TIMESTAMP('2019-04-03 13:55:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T13:56:51.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578422,0,53028,558192,542424,'F',TO_TIMESTAMP('2019-04-03 13:56:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SerialNo_Sequence_ID',10,0,0,TO_TIMESTAMP('2019-04-03 13:56:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T13:57:03.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='SerialNo_Sequence_ID',Updated=TO_TIMESTAMP('2019-04-03 13:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576554
;

-- 2019-04-03T13:57:03.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SerialNo_Sequence_ID', Name='Serial No. Sequence', Description=NULL, Help=NULL WHERE AD_Element_ID=576554
;

-- 2019-04-03T13:57:03.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerialNo_Sequence_ID', Name='Serial No. Sequence', Description=NULL, Help=NULL, AD_Element_ID=576554 WHERE UPPER(ColumnName)='SERIALNO_SEQUENCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T13:57:03.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerialNo_Sequence_ID', Name='Serial No. Sequence', Description=NULL, Help=NULL WHERE AD_Element_ID=576554 AND IsCentrallyMaintained='Y'
;

alter table PP_Product_BOM rename SeqNo_Sequence_ID to SerialNo_Sequence_ID;

-- 2019-04-03T14:00:26.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567627,576554,0,30,128,53026,'SerialNo_Sequence_ID',TO_TIMESTAMP('2019-04-03 14:00:26','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Serial No. Sequence',0,0,TO_TIMESTAMP('2019-04-03 14:00:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-03T14:00:26.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T14:00:29.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Order_BOM','ALTER TABLE public.PP_Order_BOM ADD COLUMN SerialNo_Sequence_ID NUMERIC(10)')
;

-- 2019-04-03T14:00:30.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Order_BOM ADD CONSTRAINT SerialNoSequence_PPOrderBOM FOREIGN KEY (SerialNo_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- 2019-04-03T14:01:14.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567627,578423,0,53040,TO_TIMESTAMP('2019-04-03 14:01:14','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Serial No. Sequence',TO_TIMESTAMP('2019-04-03 14:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T14:01:14.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578423 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-03T14:02:18.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578423,0,53040,558193,540839,'F',TO_TIMESTAMP('2019-04-03 14:02:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SerialNo_Sequence_ID',155,0,0,TO_TIMESTAMP('2019-04-03 14:02:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T14:02:30.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-04-03 14:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558193
;

-- 2019-04-03T14:02:30.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-04-03 14:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546582
;

-- 2019-04-03T14:10:00.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nummernfolge für Seriennummer', PrintName='Nummernfolge für Seriennummer',Updated=TO_TIMESTAMP('2019-04-03 14:10:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576554 AND AD_Language='de_CH'
;

-- 2019-04-03T14:10:00.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576554,'de_CH') 
;

-- 2019-04-03T14:10:07.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nummernfolge für Seriennummer', PrintName='Nummernfolge für Seriennummer',Updated=TO_TIMESTAMP('2019-04-03 14:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576554 AND AD_Language='de_DE'
;

-- 2019-04-03T14:10:07.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576554,'de_DE') 
;

-- 2019-04-03T14:10:07.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576554,'de_DE') 
;

-- 2019-04-03T14:10:07.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SerialNo_Sequence_ID', Name='Nummernfolge für Seriennummer', Description=NULL, Help=NULL WHERE AD_Element_ID=576554
;

-- 2019-04-03T14:10:07.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerialNo_Sequence_ID', Name='Nummernfolge für Seriennummer', Description=NULL, Help=NULL, AD_Element_ID=576554 WHERE UPPER(ColumnName)='SERIALNO_SEQUENCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-03T14:10:07.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SerialNo_Sequence_ID', Name='Nummernfolge für Seriennummer', Description=NULL, Help=NULL WHERE AD_Element_ID=576554 AND IsCentrallyMaintained='Y'
;

-- 2019-04-03T14:10:07.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nummernfolge für Seriennummer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576554) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576554)
;

-- 2019-04-03T14:10:08.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nummernfolge für Seriennummer', Name='Nummernfolge für Seriennummer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576554)
;

-- 2019-04-03T14:10:08.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nummernfolge für Seriennummer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576554
;

-- 2019-04-03T14:10:08.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nummernfolge für Seriennummer', Description=NULL, Help=NULL WHERE AD_Element_ID = 576554
;

-- 2019-04-03T14:10:08.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nummernfolge für Seriennummer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576554
;

-- 2019-04-03T14:10:13.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-03 14:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576554 AND AD_Language='en_US'
;

-- 2019-04-03T14:10:13.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576554,'en_US') 
;

