-- 2019-09-25T17:20:09.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568973,543939,0,10,208,'ExternalId',TO_TIMESTAMP('2019-09-25 19:20:09','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','External ID',0,0,TO_TIMESTAMP('2019-09-25 19:20:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-25T17:20:09.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568973 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-25T17:20:09.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- 2019-09-25T17:20:11.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN ExternalId VARCHAR(255)')
;

-- 2019-09-25T17:21:00.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568973,588620,0,180,0,TO_TIMESTAMP('2019-09-25 19:21:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','External ID',420,450,0,1,1,TO_TIMESTAMP('2019-09-25 19:21:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-25T17:21:00.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-25T17:21:00.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2019-09-25T17:21:00.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588620
;

-- 2019-09-25T17:21:00.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(588620)
;

-- 2019-09-25T17:23:26.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,588620,0,180,1000040,562878,'F',TO_TIMESTAMP('2019-09-25 19:23:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalId',85,0,0,TO_TIMESTAMP('2019-09-25 19:23:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-25T19:29:59.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-25 21:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='de_DE'
;

-- 2019-09-25T19:29:59.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'de_DE') 
;

-- 2019-09-25T19:29:59.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1675,'de_DE') 
;

-- 2019-09-25T19:29:59.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductValue', Name='Produktschlüssel', Description='', Help=NULL WHERE AD_Element_ID=1675
;

-- 2019-09-25T19:29:59.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductValue', Name='Produktschlüssel', Description='', Help=NULL, AD_Element_ID=1675 WHERE UPPER(ColumnName)='PRODUCTVALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-25T19:29:59.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductValue', Name='Produktschlüssel', Description='', Help=NULL WHERE AD_Element_ID=1675 AND IsCentrallyMaintained='Y'
;

-- 2019-09-25T19:29:59.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktschlüssel', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1675) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1675)
;

-- 2019-09-25T19:29:59.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktschlüssel', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1675
;

-- 2019-09-25T19:29:59.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktschlüssel', Description='', Help=NULL WHERE AD_Element_ID = 1675
;

-- 2019-09-25T19:29:59.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktschlüssel', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1675
;

-- 2019-09-25T19:30:04.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2019-09-25 21:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='nl_NL'
;

-- 2019-09-25T19:30:04.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'nl_NL') 
;

-- 2019-09-25T19:30:15.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Product Value',Updated=TO_TIMESTAMP('2019-09-25 21:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='en_US'
;

-- 2019-09-25T19:30:15.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'en_US') 
;

-- 2019-09-25T19:30:21.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-25 21:30:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='de_CH'
;

-- 2019-09-25T19:30:21.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'de_CH') 
;

-- 2019-09-25T19:37:11.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID ',Updated=TO_TIMESTAMP('2019-09-25 21:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='de_CH'
;

-- 2019-09-25T19:37:11.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'de_CH') 
;

-- 2019-09-25T19:37:33.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',Updated=TO_TIMESTAMP('2019-09-25 21:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='de_DE'
;

-- 2019-09-25T19:37:33.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'de_DE') 
;

-- 2019-09-25T19:37:33.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1675,'de_DE') 
;

-- 2019-09-25T19:37:33.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductValue', Name='Produktschlüssel', Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID', Help=NULL WHERE AD_Element_ID=1675
;

-- 2019-09-25T19:37:33.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductValue', Name='Produktschlüssel', Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID', Help=NULL, AD_Element_ID=1675 WHERE UPPER(ColumnName)='PRODUCTVALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-25T19:37:33.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductValue', Name='Produktschlüssel', Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID', Help=NULL WHERE AD_Element_ID=1675 AND IsCentrallyMaintained='Y'
;

-- 2019-09-25T19:37:33.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktschlüssel', Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1675) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1675)
;

-- 2019-09-25T19:37:33.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktschlüssel', Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1675
;

-- 2019-09-25T19:37:33.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktschlüssel', Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID', Help=NULL WHERE AD_Element_ID = 1675
;

-- 2019-09-25T19:37:33.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktschlüssel', Description = 'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1675
;

-- 2019-09-25T19:37:45.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',Updated=TO_TIMESTAMP('2019-09-25 21:37:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='de_CH'
;

-- 2019-09-25T19:37:45.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'de_CH') 
;

-- 2019-09-25T19:38:30.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Product identifier;', Help='Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',Updated=TO_TIMESTAMP('2019-09-25 21:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='en_US'
;

-- 2019-09-25T19:38:30.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'en_US') 
;

-- 2019-09-25T19:40:27.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Product identifier; "val-<search key>", "ext-<external id>" or internal M_Product_ID', Help='',Updated=TO_TIMESTAMP('2019-09-25 21:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1675 AND AD_Language='en_US'
;

-- 2019-09-25T19:40:27.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1675,'en_US') 
;

-- 2019-09-25T19:40:40.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2019-09-25 21:40:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8824
;

-- 2019-09-25T19:40:43.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_inventory','ProductValue','VARCHAR(255)',null,null)
;

-- 2019-09-25T19:47:50.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568974,543939,0,10,532,'ExternalId',TO_TIMESTAMP('2019-09-25 21:47:50','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','External ID',0,0,TO_TIMESTAMP('2019-09-25 21:47:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-25T19:47:50.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568974 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-25T19:47:50.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- 2019-09-25T19:47:51.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Product','ALTER TABLE public.I_Product ADD COLUMN ExternalId VARCHAR(255)')
;

-- 2019-09-25T19:48:19.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='I_Product',Updated=TO_TIMESTAMP('2019-09-25 21:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=247
;

-- 2019-09-25T19:48:36.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568974,588621,0,442,0,TO_TIMESTAMP('2019-09-25 21:48:36','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','External ID',770,750,0,1,1,TO_TIMESTAMP('2019-09-25 21:48:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-25T19:48:36.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-25T19:48:36.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2019-09-25T19:48:36.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588621
;

-- 2019-09-25T19:48:36.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(588621)
;

-- 2019-09-25T19:50:06.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,588621,0,442,541556,562879,'F',TO_TIMESTAMP('2019-09-25 21:50:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalId',335,0,0,TO_TIMESTAMP('2019-09-25 21:50:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-25T20:03:53.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='', Name='Import - Inventur',Updated=TO_TIMESTAMP('2019-09-25 22:03:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=572
;

-- 2019-09-25T20:04:15.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='I_Inventory',Updated=TO_TIMESTAMP('2019-09-25 22:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=267
;

-- 2019-09-25T20:05:19.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Import von Inventurbelegen', IsTranslated='Y', Name='Import Inventur', PrintName='Import Inventur',Updated=TO_TIMESTAMP('2019-09-25 22:05:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575035 AND AD_Language='de_CH'
;

-- 2019-09-25T20:05:19.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575035,'de_CH') 
;

-- 2019-09-25T20:05:29.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Import inventory documents',Updated=TO_TIMESTAMP('2019-09-25 22:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575035 AND AD_Language='en_US'
;

-- 2019-09-25T20:05:29.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575035,'en_US') 
;

-- 2019-09-25T20:05:46.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Import von Inventurbelegen', IsTranslated='Y', Name='Import Inventur', PrintName='Import Inventur',Updated=TO_TIMESTAMP('2019-09-25 22:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575035 AND AD_Language='de_DE'
;

-- 2019-09-25T20:05:46.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575035,'de_DE') 
;

-- 2019-09-25T20:05:46.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575035,'de_DE') 
;

-- 2019-09-25T20:05:46.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Import Inventur', Description='Import von Inventurbelegen', Help=NULL WHERE AD_Element_ID=575035
;

-- 2019-09-25T20:05:46.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Import Inventur', Description='Import von Inventurbelegen', Help=NULL WHERE AD_Element_ID=575035 AND IsCentrallyMaintained='Y'
;

-- 2019-09-25T20:05:46.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Import Inventur', Description='Import von Inventurbelegen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575035) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575035)
;

-- 2019-09-25T20:05:46.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Import Inventur', Name='Import Inventur' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575035)
;

-- 2019-09-25T20:05:46.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Import Inventur', Description='Import von Inventurbelegen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575035
;

-- 2019-09-25T20:05:46.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Import Inventur', Description='Import von Inventurbelegen', Help=NULL WHERE AD_Element_ID = 575035
;

-- 2019-09-25T20:05:46.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Import Inventur', Description = 'Import von Inventurbelegen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575035
;

