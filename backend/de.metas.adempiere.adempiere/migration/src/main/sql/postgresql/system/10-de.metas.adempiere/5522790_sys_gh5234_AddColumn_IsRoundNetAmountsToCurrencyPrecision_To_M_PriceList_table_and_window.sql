-- 2019-05-28T13:49:26.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576776,0,'IsRoundNetAmountToCurrencyPrecision',TO_TIMESTAMP('2019-05-28 13:49:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsRoundNetAmountToCurrencyPrecision','IsRoundNetAmountToCurrencyPrecision',TO_TIMESTAMP('2019-05-28 13:49:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-28T13:49:26.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576776 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-05-28T13:49:48.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Round Net Amount To Currency Precision', PrintName='Round Net Amount To Currency Precision',Updated=TO_TIMESTAMP('2019-05-28 13:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576776 AND AD_Language='en_US'
;

-- 2019-05-28T13:49:48.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576776,'en_US') 
;

-- 2019-05-28T13:51:07.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568120,576776,0,20,255,'IsRoundNetAmountToCurrencyPrecision',TO_TIMESTAMP('2019-05-28 13:51:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','IsRoundNetAmountToCurrencyPrecision',0,0,TO_TIMESTAMP('2019-05-28 13:51:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-05-28T13:51:07.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568120 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-05-28T13:51:07.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576776) 
;

-- 2019-05-28T13:51:09.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_PriceList','ALTER TABLE public.M_PriceList ADD COLUMN IsRoundNetAmountToCurrencyPrecision CHAR(1) DEFAULT ''Y'' CHECK (IsRoundNetAmountToCurrencyPrecision IN (''Y'',''N'')) NOT NULL')
;

-- 2019-05-28T14:00:32.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Netto-Beträge auf Währungspräzision runden', PrintName='Netto-Beträge auf Währungspräzision runden',Updated=TO_TIMESTAMP('2019-05-28 14:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576776 AND AD_Language='de_CH'
;

-- 2019-05-28T14:00:32.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576776,'de_CH') 
;

-- 2019-05-28T14:00:36.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Netto-Beträge auf Währungspräzision runden', PrintName='Netto-Beträge auf Währungspräzision runden',Updated=TO_TIMESTAMP('2019-05-28 14:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576776 AND AD_Language='de_DE'
;

-- 2019-05-28T14:00:36.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576776,'de_DE') 
;

-- 2019-05-28T14:00:36.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576776,'de_DE') 
;

-- 2019-05-28T14:00:36.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsRoundNetAmountToCurrencyPrecision', Name='Netto-Beträge auf Währungspräzision runden', Description=NULL, Help=NULL WHERE AD_Element_ID=576776
;

-- 2019-05-28T14:00:36.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRoundNetAmountToCurrencyPrecision', Name='Netto-Beträge auf Währungspräzision runden', Description=NULL, Help=NULL, AD_Element_ID=576776 WHERE UPPER(ColumnName)='ISROUNDNETAMOUNTTOCURRENCYPRECISION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-28T14:00:36.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRoundNetAmountToCurrencyPrecision', Name='Netto-Beträge auf Währungspräzision runden', Description=NULL, Help=NULL WHERE AD_Element_ID=576776 AND IsCentrallyMaintained='Y'
;

-- 2019-05-28T14:00:36.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Netto-Beträge auf Währungspräzision runden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576776) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576776)
;

-- 2019-05-28T14:00:36.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Netto-Beträge auf Währungspräzision runden', Name='Netto-Beträge auf Währungspräzision runden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576776)
;

-- 2019-05-28T14:00:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Netto-Beträge auf Währungspräzision runden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576776
;

-- 2019-05-28T14:00:37.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Netto-Beträge auf Währungspräzision runden', Description=NULL, Help=NULL WHERE AD_Element_ID = 576776
;

-- 2019-05-28T14:00:37.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Netto-Beträge auf Währungspräzision runden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576776
;

-- 2019-05-28T14:00:42.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-28 14:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576776 AND AD_Language='de_DE'
;

-- 2019-05-28T14:00:42.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576776,'de_DE') 
;

-- 2019-05-28T14:00:42.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576776,'de_DE') 
;

-- 2019-05-28T14:00:49.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Netto-Beträge auf Währungspräzision runden', PrintName='Netto-Beträge auf Währungspräzision runden',Updated=TO_TIMESTAMP('2019-05-28 14:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576776 AND AD_Language='nl_NL'
;

-- 2019-05-28T14:00:49.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576776,'nl_NL') 
;

-- 2019-05-28T14:31:16.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540321,Updated=TO_TIMESTAMP('2019-05-28 14:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=255
;

-- 2019-05-28T14:33:09.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568120,580718,0,540776,0,TO_TIMESTAMP('2019-05-28 14:33:08','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Netto-Beträge auf Währungspräzision runden',20,20,0,1,1,TO_TIMESTAMP('2019-05-28 14:33:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-28T14:33:09.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-05-28T14:33:09.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576776) 
;

-- 2019-05-28T14:33:09.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580718
;

-- 2019-05-28T14:33:09.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(580718)
;

-- 2019-05-28T14:37:08.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,580718,0,540776,540089,559427,'F',TO_TIMESTAMP('2019-05-28 14:37:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Is Round Net Amount To Currency Precision',60,0,0,TO_TIMESTAMP('2019-05-28 14:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-28T14:51:51.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2019-05-28 14:51:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568120
;

-- 2019-05-28T14:51:53.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_pricelist','IsRoundNetAmountToCurrencyPrecision','CHAR(1)',null,'N')
;

-- 2019-05-28T14:51:53.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_PriceList SET IsRoundNetAmountToCurrencyPrecision='N' WHERE IsRoundNetAmountToCurrencyPrecision IS NULL
;

