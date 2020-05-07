-- 2019-05-21T18:03:44.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-05-21 18:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559177
;

-- 2019-05-21T18:15:31.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568013,580336,0,257,0,TO_TIMESTAMP('2019-05-21 18:15:31','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Exported To Customs Invoice',520,480,0,1,1,TO_TIMESTAMP('2019-05-21 18:15:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T18:15:31.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580336 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-05-21T18:15:31.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576751) 
;

-- 2019-05-21T18:15:31.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580336
;

-- 2019-05-21T18:15:32.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(580336)
;

-- 2019-05-21T18:15:40.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568012,580337,0,257,0,TO_TIMESTAMP('2019-05-21 18:15:39','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Customs Invoice',530,490,0,1,1,TO_TIMESTAMP('2019-05-21 18:15:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T18:15:40.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-05-21T18:15:40.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576740) 
;

-- 2019-05-21T18:15:40.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580337
;

-- 2019-05-21T18:15:40.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(580337)
;

-- 2019-05-21T18:15:57.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2019-05-21 18:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568013
;

-- 2019-05-21T18:18:07.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000014,542556,TO_TIMESTAMP('2019-05-21 18:18:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','customs',30,TO_TIMESTAMP('2019-05-21 18:18:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T18:18:39.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,580336,0,257,559190,542556,'F',TO_TIMESTAMP('2019-05-21 18:18:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Exported',10,0,0,TO_TIMESTAMP('2019-05-21 18:18:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T18:19:00.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,580337,0,257,559191,542556,'F',TO_TIMESTAMP('2019-05-21 18:19:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'customs invoice',20,0,0,TO_TIMESTAMP('2019-05-21 18:19:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T18:19:40.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540643,Updated=TO_TIMESTAMP('2019-05-21 18:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541360
;

-- 2019-05-21T18:28:54.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zollrechnung', PrintName='Zollrechnung',Updated=TO_TIMESTAMP('2019-05-21 18:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576740 AND AD_Language='de_DE'
;

-- 2019-05-21T18:28:54.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576740,'de_DE') 
;

-- 2019-05-21T18:28:54.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576740,'de_DE') 
;

-- 2019-05-21T18:28:54.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Customs_Invoice_ID', Name='Zollrechnung', Description=NULL, Help=NULL WHERE AD_Element_ID=576740
;

-- 2019-05-21T18:28:54.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customs_Invoice_ID', Name='Zollrechnung', Description=NULL, Help=NULL, AD_Element_ID=576740 WHERE UPPER(ColumnName)='C_CUSTOMS_INVOICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-21T18:28:54.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customs_Invoice_ID', Name='Zollrechnung', Description=NULL, Help=NULL WHERE AD_Element_ID=576740 AND IsCentrallyMaintained='Y'
;

-- 2019-05-21T18:28:54.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zollrechnung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576740) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576740)
;

-- 2019-05-21T18:28:54.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zollrechnung', Name='Zollrechnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576740)
;

-- 2019-05-21T18:28:54.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zollrechnung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576740
;

-- 2019-05-21T18:28:54.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zollrechnung', Description=NULL, Help=NULL WHERE AD_Element_ID = 576740
;

-- 2019-05-21T18:28:54.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zollrechnung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576740
;

-- 2019-05-21T18:30:20.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zollrechnung erstellt', PrintName='Zollrechnung erstellt',Updated=TO_TIMESTAMP('2019-05-21 18:30:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576751 AND AD_Language='de_DE'
;

-- 2019-05-21T18:30:20.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576751,'de_DE') 
;

-- 2019-05-21T18:30:20.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576751,'de_DE') 
;

-- 2019-05-21T18:30:20.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExportedToCustomsInvoice', Name='Zollrechnung erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=576751
;

-- 2019-05-21T18:30:20.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExportedToCustomsInvoice', Name='Zollrechnung erstellt', Description=NULL, Help=NULL, AD_Element_ID=576751 WHERE UPPER(ColumnName)='ISEXPORTEDTOCUSTOMSINVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-21T18:30:20.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExportedToCustomsInvoice', Name='Zollrechnung erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=576751 AND IsCentrallyMaintained='Y'
;

-- 2019-05-21T18:30:20.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zollrechnung erstellt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576751) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576751)
;

-- 2019-05-21T18:30:20.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zollrechnung erstellt', Name='Zollrechnung erstellt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576751)
;

-- 2019-05-21T18:30:20.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zollrechnung erstellt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576751
;

-- 2019-05-21T18:30:20.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zollrechnung erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID = 576751
;

-- 2019-05-21T18:30:20.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zollrechnung erstellt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576751
;

-- 2019-05-21T18:30:25.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zollrechnung erstellt', PrintName='Zollrechnung erstellt',Updated=TO_TIMESTAMP('2019-05-21 18:30:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576751 AND AD_Language='de_CH'
;

-- 2019-05-21T18:30:25.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576751,'de_CH') 
;

-- 2019-05-21T18:31:07.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zollrechnung', PrintName='Zollrechnung',Updated=TO_TIMESTAMP('2019-05-21 18:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576740 AND AD_Language='de_CH'
;

-- 2019-05-21T18:31:07.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576740,'de_CH') 
;

-- 2019-05-21T18:31:50.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zollrechnung erstellen',Updated=TO_TIMESTAMP('2019-05-21 18:31:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541136
;

-- 2019-05-21T18:31:54.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zollrechnung erstellen',Updated=TO_TIMESTAMP('2019-05-21 18:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541136
;



-- 2019-05-21T19:35:05.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=559191
;

