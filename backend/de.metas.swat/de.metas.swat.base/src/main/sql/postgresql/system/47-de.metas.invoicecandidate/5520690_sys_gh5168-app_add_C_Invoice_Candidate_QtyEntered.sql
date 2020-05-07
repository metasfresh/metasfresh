-- 2019-04-30T08:43:02.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-30 08:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-30 08:43:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540270,'N','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt',567900,'N','N','N','N','N','N','N','N',0,0,2589,'de.metas.invoicecandidate','N','N','QtyEntered','N','Menge','Die Eingegebene Menge basiert auf der gewählten Mengeneinheit')
;

-- 2019-04-30T08:43:02.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567900 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-30T08:43:10.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyEntered NUMERIC')
;

-- 2019-04-30T08:46:52.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,Help,PrintName,Name,Description) VALUES (0,'Y',TO_TIMESTAMP('2019-04-30 08:46:51','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-30 08:46:51','YYYY-MM-DD HH24:MI:SS'),100,576694,0,'QtyOrdered_In_Stocking_UOM','D','','Beauftragt (Lager-UOM)','Beauftragt (Lager-UOM)','Beauftragte Menge in der Lager-UOM des jeweiligen Produktes')
;

-- 2019-04-30T08:46:52.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576694 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-30T08:46:59.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-30 08:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=576694
;

-- 2019-04-30T08:46:59.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576694,'de_CH') 
;

-- 2019-04-30T08:47:02.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-30 08:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=576694
;

-- 2019-04-30T08:47:02.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576694,'de_DE') 
;

-- 2019-04-30T08:47:02.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576694,'de_DE') 
;

-- 2019-04-30T08:48:05.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ordered (Stocking-UOM)', IsTranslated='Y', Name='Ordered (Stocking-UOM)', Description='Ordered quantity in the respective product''s stocking UOM',Updated=TO_TIMESTAMP('2019-04-30 08:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576694
;

-- 2019-04-30T08:48:05.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576694,'en_US') 
;

-- 2019-04-30T08:48:42.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Help='', AD_Name_ID=576694, Name='Beauftragt (Lager-UOM)', Description='Beauftragte Menge in der Lager-UOM des jeweiligen Produktes',Updated=TO_TIMESTAMP('2019-04-30 08:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547551
;

-- 2019-04-30T08:48:42.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576694) 
;

-- 2019-04-30T08:48:42.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=547551
;

-- 2019-04-30T08:48:42.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(547551)
;

-- 2019-04-30T08:51:20.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (540279,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-30 08:51:19','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-04-30 08:51:19','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt',580154,'Y',1100,450,1,1,567900,0,'de.metas.invoicecandidate','Menge','Die Eingegebene Menge basiert auf der gewählten Mengeneinheit')
;

-- 2019-04-30T08:51:20.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-30T08:51:20.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2589) 
;

-- 2019-04-30T08:51:20.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580154
;

-- 2019-04-30T08:51:20.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(580154)
;

-- 2019-04-30T08:51:38.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=721,Updated=TO_TIMESTAMP('2019-04-30 08:51:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=580154
;

-- 2019-04-30T08:53:10.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=354,Updated=TO_TIMESTAMP('2019-04-30 08:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=580154
;

-- 2019-04-30T08:53:19.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=356,Updated=TO_TIMESTAMP('2019-04-30 08:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554200
;

-- 2019-04-30T08:56:18.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='TEST',Updated=TO_TIMESTAMP('2019-04-30 08:56:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540033
;

-- 2019-04-30T08:57:19.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='',Updated=TO_TIMESTAMP('2019-04-30 08:57:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540033
;

-- 2019-04-30T08:58:08.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Language='fr_CH' AND AD_Element_ID=542714
;

-- 2019-04-30T08:58:10.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Language='it_CH' AND AD_Element_ID=542714
;

-- 2019-04-30T08:58:13.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Language='en_GB' AND AD_Element_ID=542714
;

-- 2019-04-30T08:58:18.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-30 08:58:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=542714
;

-- 2019-04-30T08:58:18.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542714,'de_CH') 
;

-- 2019-04-30T08:58:47.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Aggregation Group',Updated=TO_TIMESTAMP('2019-04-30 08:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=542714
;

-- 2019-04-30T08:58:47.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542714,'en_US') 
;

-- 2019-04-30T08:58:54.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-30 08:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=542714
;

-- 2019-04-30T08:58:54.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542714,'de_DE') 
;

-- 2019-04-30T08:58:54.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542714,'de_DE') 
;

-- 2019-04-30T08:59:01.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Aggregation Group eff.', Name='Aggregation Group eff.',Updated=TO_TIMESTAMP('2019-04-30 08:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=542714
;

-- 2019-04-30T08:59:01.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542714,'en_US') 
;

-- 2019-04-30T09:12:01.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='TEST',Updated=TO_TIMESTAMP('2019-04-30 09:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540033
;

-- 2019-04-30T09:12:13.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='TEST-main-section-name',Updated=TO_TIMESTAMP('2019-04-30 09:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540033
;

-- 2019-04-30T09:13:13.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2019-04-30 09:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-04-30T09:13:56.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2019-04-30 09:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-04-30T09:13:59.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='',Updated=TO_TIMESTAMP('2019-04-30 09:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540033
;

-- 2019-04-30T09:14:23.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyOrdered',Updated=TO_TIMESTAMP('2019-04-30 09:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548108
;

-- 2019-04-30T09:14:51.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount,Name) VALUES (100,559073,580154,'N',0,TO_TIMESTAMP('2019-04-30 09:14:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,541086,TO_TIMESTAMP('2019-04-30 09:14:51','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540279,'F','N','N',0,'QtyEntered')
;

-- 2019-04-30T09:15:11.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (Updated,AD_Client_ID,UpdatedBy,Created,CreatedBy,IsActive,AD_UI_ElementField_ID,AD_UI_Element_ID,AD_Field_ID,SeqNo,AD_Org_ID,Type) VALUES (TO_TIMESTAMP('2019-04-30 09:15:11','YYYY-MM-DD HH24:MI:SS'),0,100,TO_TIMESTAMP('2019-04-30 09:15:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',540346,559073,554200,10,0,'widget')
;

-- 2019-04-30T09:15:45.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N', IsActive='N', IsDisplayed='N', Name='C_UOM',Updated=TO_TIMESTAMP('2019-04-30 09:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541047
;

-- 2019-04-30T09:18:04.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-04-30 09:18:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=580154
;

-- 2019-04-30T09:20:43.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-30 09:20:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-30 09:20:43','YYYY-MM-DD HH24:MI:SS'),100,576695,0,'QtyOrdered_In_Record_UOM','D','Beauftragt','Beauftragt')
;

-- 2019-04-30T09:20:43.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576695 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-30T09:20:46.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-30 09:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=576695
;

-- 2019-04-30T09:20:46.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_CH') 
;

-- 2019-04-30T09:20:49.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-30 09:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=576695
;

-- 2019-04-30T09:20:49.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_DE') 
;

-- 2019-04-30T09:20:49.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576695,'de_DE') 
;

-- 2019-04-30T09:21:13.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ordered', IsTranslated='Y', Name='Ordered', Description='Quantity in the record''s own UOM',Updated=TO_TIMESTAMP('2019-04-30 09:21:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576695
;

-- 2019-04-30T09:21:13.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'en_US') 
;

-- 2019-04-30T09:21:51.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes',Updated=TO_TIMESTAMP('2019-04-30 09:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=576695
;

-- 2019-04-30T09:21:51.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_DE') 
;

-- 2019-04-30T09:21:51.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576695,'de_DE') 
;

-- 2019-04-30T09:21:51.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=576695
;

-- 2019-04-30T09:21:51.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL, AD_Element_ID=576695 WHERE UPPER(ColumnName)='QTYORDERED_IN_RECORD_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-30T09:21:51.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=576695 AND IsCentrallyMaintained='Y'
;

-- 2019-04-30T09:21:51.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576695) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576695)
;

-- 2019-04-30T09:21:51.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576695
;

-- 2019-04-30T09:21:51.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID = 576695
;

-- 2019-04-30T09:21:51.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beauftragt', Description = 'Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576695
;

-- 2019-04-30T09:21:54.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes',Updated=TO_TIMESTAMP('2019-04-30 09:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=576695
;

-- 2019-04-30T09:21:54.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_CH') 
;

-- 2019-04-30T09:24:22.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540346
;

-- 2019-04-30T09:24:22.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=559073
;

-- 2019-04-30T09:25:29.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount,Name) VALUES (100,559074,580154,'N',0,TO_TIMESTAMP('2019-04-30 09:25:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',60,540057,TO_TIMESTAMP('2019-04-30 09:25:29','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540279,'F','N','N',0,'QtyEntered')
;

-- 2019-04-30T09:25:43.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount,Name) VALUES (100,559075,554200,'N',0,TO_TIMESTAMP('2019-04-30 09:25:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',70,540057,TO_TIMESTAMP('2019-04-30 09:25:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540279,'F','N','N',0,'C_UOM_ID')
;

-- 2019-04-30T09:30:32.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Help=NULL, AD_Name_ID=576695, Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes',Updated=TO_TIMESTAMP('2019-04-30 09:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=580154
;

-- 2019-04-30T09:30:32.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576695) 
;

-- 2019-04-30T09:30:32.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580154
;

-- 2019-04-30T09:30:32.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(580154)
;

