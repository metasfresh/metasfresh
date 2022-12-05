-- 2022-09-09T08:22:21.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581435,0,'IsInEffect',TO_TIMESTAMP('2022-09-09 11:22:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','In Effect','In Effect',TO_TIMESTAMP('2022-09-09 11:22:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T08:22:21.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581435 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-09T08:23:05.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584279,581435,0,20,540270,'IsInEffect',TO_TIMESTAMP('2022-09-09 11:23:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','de.metas.invoicecandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'In Effect',0,0,TO_TIMESTAMP('2022-09-09 11:23:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-09T08:23:05.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584279 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-09T08:23:05.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581435) 
;

-- 2022-09-09T08:23:05.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IsInEffect CHAR(1) DEFAULT ''Y'' CHECK (IsInEffect IN (''Y'',''N'')) NOT NULL')
;

-- 2022-09-09T08:25:07.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584279,707136,0,540279,0,TO_TIMESTAMP('2022-09-09 11:25:07','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','In Effect',0,550,0,1,1,TO_TIMESTAMP('2022-09-09 11:25:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T08:25:07.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707136 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-09T08:25:07.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581435) 
;

-- 2022-09-09T08:25:07.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707136
;

-- 2022-09-09T08:25:07.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(707136)
;

-- 2022-09-09T08:25:51.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707136,0,540279,612951,540058,'F',TO_TIMESTAMP('2022-09-09 11:25:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'In Effect',60,0,0,TO_TIMESTAMP('2022-09-09 11:25:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T08:36:09.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-09-09 11:36:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584279
;

-- 2022-09-09T08:36:09.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','IsInEffect','CHAR(1)',null,'Y')
;

-- 2022-09-09T08:36:09.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Invoice_Candidate SET IsInEffect='Y' WHERE IsInEffect IS NULL
;

-- 2022-09-09T11:36:42.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=34,Updated=TO_TIMESTAMP('2022-09-09 14:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583208
;

-- 2022-09-09T11:36:50.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2022-09-09 14:36:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612951
;

-- 2022-09-09T11:41:57.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2022-09-09 14:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575016
;

-- 2022-09-09T11:41:57.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2022-09-09 14:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544920
;

-- 2022-09-09T11:41:57.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2022-09-09 14:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551469
;

-- 2022-09-09T11:41:57.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2022-09-09 14:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569216
;

-- 2022-09-09T11:41:58.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2022-09-09 14:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551072
;

-- 2022-09-09T11:41:58.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2022-09-09 14:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551286
;

-- 2022-09-09T11:41:58.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2022-09-09 14:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546203
;

-- 2022-09-09T11:41:58.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2022-09-09 14:41:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546600
;

-- 2022-09-09T11:41:59.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2022-09-09 14:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551953
;

-- 2022-09-09T11:41:59.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2022-09-09 14:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544913
;

-- 2022-09-09T11:41:59.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2022-09-09 14:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546339
;

-- 2022-09-09T11:41:59.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2022-09-09 14:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551761
;

-- 2022-09-09T11:41:59.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2022-09-09 14:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551293
;

-- 2022-09-09T11:42:00.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=140,Updated=TO_TIMESTAMP('2022-09-09 14:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552564
;

-- 2022-09-09T11:42:00.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=150,Updated=TO_TIMESTAMP('2022-09-09 14:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546227
;

-- 2022-09-09T11:42:00.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=160,Updated=TO_TIMESTAMP('2022-09-09 14:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551796
;

-- 2022-09-09T11:42:01.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=170,Updated=TO_TIMESTAMP('2022-09-09 14:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545771
;

-- 2022-09-09T11:42:01.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=180,Updated=TO_TIMESTAMP('2022-09-09 14:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549847
;

-- 2022-09-09T11:42:01.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=190,Updated=TO_TIMESTAMP('2022-09-09 14:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550515
;

-- 2022-09-09T11:42:01.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=200,Updated=TO_TIMESTAMP('2022-09-09 14:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584279
;

-- 2022-09-09T11:42:02.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=210,Updated=TO_TIMESTAMP('2022-09-09 14:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551762
;

-- 2022-09-09T11:42:02.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=220,Updated=TO_TIMESTAMP('2022-09-09 14:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551290
;

-- 2022-09-09T11:42:02.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=230,Updated=TO_TIMESTAMP('2022-09-09 14:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545852
;

-- 2022-09-09T11:42:02.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=240,Updated=TO_TIMESTAMP('2022-09-09 14:42:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552289
;

-- 2022-09-09T11:42:03.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=250,Updated=TO_TIMESTAMP('2022-09-09 14:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544923
;

-- 2022-09-09T11:42:03.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=260,Updated=TO_TIMESTAMP('2022-09-09 14:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545314
;

-- 2022-09-09T11:42:03.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=270,Updated=TO_TIMESTAMP('2022-09-09 14:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545316
;

-- 2022-09-09T11:42:03.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=280,Updated=TO_TIMESTAMP('2022-09-09 14:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544902
;

-- 2022-09-09T11:53:51.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-09 14:53:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707136
;

-- 2022-09-15T08:20:04.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Name='In Kraft', PrintName='In Kraft',Updated=TO_TIMESTAMP('2022-09-15 11:20:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581435 AND AD_Language='de_CH'
;

-- 2022-09-15T08:20:04.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581435,'de_CH')
;

-- 2022-09-15T08:20:16.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', PrintName='In Kraft',Updated=TO_TIMESTAMP('2022-09-15 11:20:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581435 AND AD_Language='de_DE'
;

-- 2022-09-15T08:20:16.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581435,'de_DE')
;

-- 2022-09-15T08:20:16.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581435,'de_DE')
;

-- 2022-09-15T08:20:16.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInEffect', Name='In Effect', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE AD_Element_ID=581435
;

-- 2022-09-15T08:20:16.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInEffect', Name='In Effect', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL, AD_Element_ID=581435 WHERE UPPER(ColumnName)='ISINEFFECT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T08:20:16.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInEffect', Name='In Effect', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE AD_Element_ID=581435 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T08:20:16.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='In Effect', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581435) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581435)
;

-- 2022-09-15T08:20:16.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='In Kraft', Name='In Effect' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581435)
;

-- 2022-09-15T08:20:16.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='In Effect', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581435
;

-- 2022-09-15T08:20:16.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='In Effect', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE AD_Element_ID = 581435
;

-- 2022-09-15T08:20:16.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'In Effect', Description = 'Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581435
;

-- 2022-09-15T08:20:24.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='In Kraft',Updated=TO_TIMESTAMP('2022-09-15 11:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581435 AND AD_Language='de_DE'
;

-- 2022-09-15T08:20:24.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581435,'de_DE')
;

-- 2022-09-15T08:20:24.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581435,'de_DE')
;

-- 2022-09-15T08:20:24.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInEffect', Name='In Kraft', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE AD_Element_ID=581435
;

-- 2022-09-15T08:20:24.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInEffect', Name='In Kraft', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL, AD_Element_ID=581435 WHERE UPPER(ColumnName)='ISINEFFECT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-15T08:20:24.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInEffect', Name='In Kraft', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE AD_Element_ID=581435 AND IsCentrallyMaintained='Y'
;

-- 2022-09-15T08:20:24.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='In Kraft', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581435) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581435)
;

-- 2022-09-15T08:20:24.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='In Kraft', Name='In Kraft' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581435)
;

-- 2022-09-15T08:20:24.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='In Kraft', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581435
;

-- 2022-09-15T08:20:24.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='In Kraft', Description='Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', Help=NULL WHERE AD_Element_ID = 581435
;

-- 2022-09-15T08:20:24.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'In Kraft', Description = 'Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581435
;

-- 2022-09-15T08:20:27.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Invoice candidates that are not in effect are no longer eligible for invoicing. Normally, an invoice candidates goes out of effect when the source document is reopened.',Updated=TO_TIMESTAMP('2022-09-15 11:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581435 AND AD_Language='en_US'
;

-- 2022-09-15T08:20:27.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581435,'en_US')
;

