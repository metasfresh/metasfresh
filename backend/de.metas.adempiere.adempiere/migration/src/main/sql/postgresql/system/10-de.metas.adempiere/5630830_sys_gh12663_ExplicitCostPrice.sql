-- 2022-03-15T17:55:15.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580714,0,TO_TIMESTAMP('2022-03-15 19:55:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsExplicitCostPrice','IsExplicitCostPrice',TO_TIMESTAMP('2022-03-15 19:55:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-15T17:55:15.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580714 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-15T17:55:42.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsExplicitCostPrice',Updated=TO_TIMESTAMP('2022-03-15 19:55:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714
;

-- 2022-03-15T17:55:42.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExplicitCostPrice', Name='IsExplicitCostPrice', Description=NULL, Help=NULL WHERE AD_Element_ID=580714
;

-- 2022-03-15T17:55:42.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='IsExplicitCostPrice', Description=NULL, Help=NULL, AD_Element_ID=580714 WHERE UPPER(ColumnName)='ISEXPLICITCOSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-15T17:55:42.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='IsExplicitCostPrice', Description=NULL, Help=NULL WHERE AD_Element_ID=580714 AND IsCentrallyMaintained='Y'
;

-- 2022-03-15T17:55:55.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582463,580714,0,20,322,'IsExplicitCostPrice',TO_TIMESTAMP('2022-03-15 19:55:55','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'IsExplicitCostPrice',0,0,TO_TIMESTAMP('2022-03-15 19:55:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-15T17:55:55.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582463 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-15T17:55:56.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580714) 
;

-- 2022-03-15T17:55:56.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InventoryLine','ALTER TABLE public.M_InventoryLine ADD COLUMN IsExplicitCostPrice CHAR(1) DEFAULT ''N'' CHECK (IsExplicitCostPrice IN (''Y'',''N'')) NOT NULL')
;

-- 2022-03-15T17:56:11.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582464,580714,0,20,572,'IsExplicitCostPrice',TO_TIMESTAMP('2022-03-15 19:56:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'IsExplicitCostPrice',0,0,TO_TIMESTAMP('2022-03-15 19:56:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-15T17:56:11.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582464 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-15T17:56:11.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580714) 
;

-- 2022-03-15T17:56:12.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory ADD COLUMN IsExplicitCostPrice CHAR(1) DEFAULT ''N'' CHECK (IsExplicitCostPrice IN (''Y'',''N'')) NOT NULL')
;




-- 2022-03-18T14:37:01.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582463,691414,0,256,0,TO_TIMESTAMP('2022-03-18 16:37:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','IsExplicitCostPrice',0,170,0,1,1,TO_TIMESTAMP('2022-03-18 16:37:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-18T14:37:01.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691414 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-18T14:37:01.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580714) 
;

-- 2022-03-18T14:37:01.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691414
;

-- 2022-03-18T14:37:01.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691414)
;

-- 2022-03-18T14:37:55.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691414,0,256,541513,605252,'F',TO_TIMESTAMP('2022-03-18 16:37:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsExplicitCostPrice',43,0,0,TO_TIMESTAMP('2022-03-18 16:37:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-18T14:38:47.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582464,691415,0,481,0,TO_TIMESTAMP('2022-03-18 16:38:47','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','IsExplicitCostPrice',0,330,0,1,1,TO_TIMESTAMP('2022-03-18 16:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-18T14:38:47.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691415 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-18T14:38:47.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580714) 
;

-- 2022-03-18T14:38:47.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691415
;

-- 2022-03-18T14:38:47.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691415)
;

-- 2022-03-18T14:39:11.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691415,0,481,543224,605253,'F',TO_TIMESTAMP('2022-03-18 16:39:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsExplicitCostPrice',238,0,0,TO_TIMESTAMP('2022-03-18 16:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 2022-03-18T14:54:39.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Explicit Cost Price', PrintName='Explicit Cost Price',Updated=TO_TIMESTAMP('2022-03-18 16:54:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='en_US'
;

-- 2022-03-18T14:54:39.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'en_US') 
;

-- 2022-03-18T15:02:05.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Explicit Cost Price', PrintName='Explicit Cost Price',Updated=TO_TIMESTAMP('2022-03-18 17:02:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_DE'
;

-- 2022-03-18T15:02:05.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_DE') 
;

-- 2022-03-18T15:02:06.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580714,'de_DE') 
;

-- 2022-03-18T15:02:06.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExplicitCostPrice', Name='Explicit Cost Price', Description=NULL, Help=NULL WHERE AD_Element_ID=580714
;

-- 2022-03-18T15:02:06.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Explicit Cost Price', Description=NULL, Help=NULL, AD_Element_ID=580714 WHERE UPPER(ColumnName)='ISEXPLICITCOSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-18T15:02:06.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Explicit Cost Price', Description=NULL, Help=NULL WHERE AD_Element_ID=580714 AND IsCentrallyMaintained='Y'
;

-- 2022-03-18T15:02:06.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Explicit Cost Price', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580714) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580714)
;

-- 2022-03-18T15:02:06.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Explicit Cost Price', Name='Explicit Cost Price' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580714)
;

-- 2022-03-18T15:02:06.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Explicit Cost Price', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:02:06.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Explicit Cost Price', Description=NULL, Help=NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:02:06.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Explicit Cost Price', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:02:08.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Explicit Cost Price', PrintName='Explicit Cost Price',Updated=TO_TIMESTAMP('2022-03-18 17:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_CH'
;

-- 2022-03-18T15:02:08.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_CH') 
;





-- 2022-03-18T15:26:09.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sind Explizite Kosten', PrintName='Sind Explizite Kosten',Updated=TO_TIMESTAMP('2022-03-18 17:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_CH'
;

-- 2022-03-18T15:26:09.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_CH') 
;

-- 2022-03-18T15:26:21.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sind Explizite Kosten', PrintName='Sind Explizite Kosten',Updated=TO_TIMESTAMP('2022-03-18 17:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_DE'
;

-- 2022-03-18T15:26:21.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_DE') 
;

-- 2022-03-18T15:26:21.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580714,'de_DE') 
;

-- 2022-03-18T15:26:21.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExplicitCostPrice', Name='Sind Explizite Kosten', Description=NULL, Help=NULL WHERE AD_Element_ID=580714
;

-- 2022-03-18T15:26:21.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Sind Explizite Kosten', Description=NULL, Help=NULL, AD_Element_ID=580714 WHERE UPPER(ColumnName)='ISEXPLICITCOSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-18T15:26:21.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Sind Explizite Kosten', Description=NULL, Help=NULL WHERE AD_Element_ID=580714 AND IsCentrallyMaintained='Y'
;

-- 2022-03-18T15:26:21.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sind Explizite Kosten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580714) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580714)
;

-- 2022-03-18T15:26:21.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sind Explizite Kosten', Name='Sind Explizite Kosten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580714)
;

-- 2022-03-18T15:26:21.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sind Explizite Kosten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:26:21.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sind Explizite Kosten', Description=NULL, Help=NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:26:21.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sind Explizite Kosten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580714
;










-- 2022-03-18T15:41:15.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Explizite Kosten', PrintName='Explizite Kosten',Updated=TO_TIMESTAMP('2022-03-18 17:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_DE'
;

-- 2022-03-18T15:41:15.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_DE') 
;

-- 2022-03-18T15:41:15.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580714,'de_DE') 
;

-- 2022-03-18T15:41:15.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExplicitCostPrice', Name='Explizite Kosten', Description=NULL, Help=NULL WHERE AD_Element_ID=580714
;

-- 2022-03-18T15:41:15.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Explizite Kosten', Description=NULL, Help=NULL, AD_Element_ID=580714 WHERE UPPER(ColumnName)='ISEXPLICITCOSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-18T15:41:15.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExplicitCostPrice', Name='Explizite Kosten', Description=NULL, Help=NULL WHERE AD_Element_ID=580714 AND IsCentrallyMaintained='Y'
;

-- 2022-03-18T15:41:15.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Explizite Kosten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580714) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580714)
;

-- 2022-03-18T15:41:15.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Explizite Kosten', Name='Explizite Kosten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580714)
;

-- 2022-03-18T15:41:15.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Explizite Kosten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:41:15.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Explizite Kosten', Description=NULL, Help=NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:41:15.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Explizite Kosten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580714
;

-- 2022-03-18T15:41:21.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Explizite Kosten', PrintName='Explizite Kosten',Updated=TO_TIMESTAMP('2022-03-18 17:41:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580714 AND AD_Language='de_CH'
;

-- 2022-03-18T15:41:21.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580714,'de_CH') 
;
















-- 2022-03-21T10:09:59.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580718,0,'ExplicitCostPrice',TO_TIMESTAMP('2022-03-21 12:09:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Explizite Kosten','Explizite Kosten',TO_TIMESTAMP('2022-03-21 12:09:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-21T10:09:59.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580718 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-21T10:16:43.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541571,TO_TIMESTAMP('2022-03-21 12:16:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ExplicitCostPrice',TO_TIMESTAMP('2022-03-21 12:16:43','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-03-21T10:16:43.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541571 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-03-21T10:16:57.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Description='Explizite Kosten', IsTranslated='Y', Name='Explizite Kosten',Updated=TO_TIMESTAMP('2022-03-21 12:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541571
;

-- 2022-03-21T10:17:07.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='Explicit Cost Price',Updated=TO_TIMESTAMP('2022-03-21 12:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541571
;

-- 2022-03-21T10:17:10.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Explizite Kosten',Updated=TO_TIMESTAMP('2022-03-21 12:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541571
;

-- 2022-03-21T10:18:08.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541571,543147,TO_TIMESTAMP('2022-03-21 12:18:08','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Yes',TO_TIMESTAMP('2022-03-21 12:18:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','Yes')
;

-- 2022-03-21T10:18:08.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543147 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-03-21T10:18:21.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541571,543148,TO_TIMESTAMP('2022-03-21 12:18:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','No',TO_TIMESTAMP('2022-03-21 12:18:21','YYYY-MM-DD HH24:MI:SS'),100,'N','No')
;

-- 2022-03-21T10:18:21.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543148 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-03-21T10:18:32.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541571,543149,TO_TIMESTAMP('2022-03-21 12:18:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auto',TO_TIMESTAMP('2022-03-21 12:18:32','YYYY-MM-DD HH24:MI:SS'),100,'A','Auto')
;

-- 2022-03-21T10:18:32.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543149 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-03-21T10:19:17.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The cost price is considered if it''s not null and its value is greated than 0.',Updated=TO_TIMESTAMP('2022-03-21 12:19:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543149
;

-- 2022-03-21T10:23:23.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='The cost price is considered if it''s not null and its value is greated than 0.',Updated=TO_TIMESTAMP('2022-03-21 12:23:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543149
;

-- 2022-03-21T10:23:36.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='The cost price is not considered.',Updated=TO_TIMESTAMP('2022-03-21 12:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543148
;

-- 2022-03-21T10:23:44.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='The cost price is considered.',Updated=TO_TIMESTAMP('2022-03-21 12:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543147
;

-- 2022-03-21T10:29:46.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582467,580718,0,17,541571,572,'ExplicitCostPrice',TO_TIMESTAMP('2022-03-21 12:29:46','YYYY-MM-DD HH24:MI:SS'),100,'N','A','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Explizite Kosten',0,0,TO_TIMESTAMP('2022-03-21 12:29:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-21T10:29:46.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582467 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-21T10:29:46.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580718) 
;

-- 2022-03-21T10:29:47.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory ADD COLUMN ExplicitCostPrice VARCHAR(250) DEFAULT ''A'' NOT NULL')
;












/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE i_inventory DROP COLUMN isexplicitcostprice');









-- 2022-03-21T10:32:40.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=605253
;

-- 2022-03-21T10:32:40.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691415
;

-- 2022-03-21T10:32:40.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=691415
;

-- 2022-03-21T10:32:40.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=691415
;




-- 2022-03-21T10:35:42.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=582464
;

-- 2022-03-21T10:35:42.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=582464
;











-- 2022-03-21T14:26:22.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582467,691417,0,481,0,TO_TIMESTAMP('2022-03-21 16:26:21','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Explizite Kosten',0,330,0,1,1,TO_TIMESTAMP('2022-03-21 16:26:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-21T14:26:22.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691417 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-21T14:26:22.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580718) 
;

-- 2022-03-21T14:26:22.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691417
;

-- 2022-03-21T14:26:22.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691417)
;

-- 2022-03-21T14:27:01.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691417,0,481,543224,605255,'F',TO_TIMESTAMP('2022-03-21 16:27:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Explizite Kosten',229,0,0,TO_TIMESTAMP('2022-03-21 16:27:01','YYYY-MM-DD HH24:MI:SS'),100)
;












-- 2022-03-21T15:08:08.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='The cost price is considered if it''s not null and its value is greater than 0.',Updated=TO_TIMESTAMP('2022-03-21 17:08:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543149
;

-- 2022-03-21T15:10:51.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The cost price is considered if it''s not null and its value is greater than 0.',Updated=TO_TIMESTAMP('2022-03-21 17:10:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543149
;

-- 2022-03-21T16:01:25.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataType='S', StartNo=8,Updated=TO_TIMESTAMP('2022-03-21 18:01:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541711
;

-- 2022-03-22T09:57:58.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The cost price is considered if a value greater than 0 is specified.',Updated=TO_TIMESTAMP('2022-03-22 11:57:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543149
;

-- 2022-03-22T09:58:21.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Der Kostenpreis wird berücksichtigt, wenn ein Wert größer als 0 vorhanden ist.',Updated=TO_TIMESTAMP('2022-03-22 11:58:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543149
;

-- 2022-03-22T09:58:29.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Kostenpreis wird berücksichtigt, wenn ein Wert größer als 0 vorhanden ist.',Updated=TO_TIMESTAMP('2022-03-22 11:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543149
;

-- 2022-03-22T09:58:35.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Kostenpreis wird berücksichtigt, wenn ein Wert größer als 0 vorhanden ist.',Updated=TO_TIMESTAMP('2022-03-22 11:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543149
;

-- 2022-03-22T10:07:18.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Der Kostenpreis wird nicht berücksichtigt.',Updated=TO_TIMESTAMP('2022-03-22 12:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543148
;

-- 2022-03-22T10:07:23.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Kostenpreis wird nicht berücksichtigt.',Updated=TO_TIMESTAMP('2022-03-22 12:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543148
;

-- 2022-03-22T10:07:41.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The cost price is not considered.',Updated=TO_TIMESTAMP('2022-03-22 12:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543148
;

-- 2022-03-22T10:07:57.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Kostenpreis wird nicht berücksichtigt.',Updated=TO_TIMESTAMP('2022-03-22 12:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543148
;

-- 2022-03-22T10:08:20.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Der Kostenpreis wird berücksichtigt.',Updated=TO_TIMESTAMP('2022-03-22 12:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543147
;

-- 2022-03-22T10:08:25.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Kostenpreis wird berücksichtigt.',Updated=TO_TIMESTAMP('2022-03-22 12:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543147
;

-- 2022-03-22T10:08:35.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Kostenpreis wird berücksichtigt.',Updated=TO_TIMESTAMP('2022-03-22 12:08:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543147
;

-- 2022-03-22T10:08:37.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='The cost price is considered.',Updated=TO_TIMESTAMP('2022-03-22 12:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543147
;

-- 2022-03-22T10:08:40.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:08:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543147
;

-- 2022-03-22T10:08:43.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543147
;

-- 2022-03-22T10:08:46.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543147
;

-- 2022-03-22T10:08:58.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543148
;

-- 2022-03-22T10:08:58.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543148
;

-- 2022-03-22T10:09:00.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543148
;

-- 2022-03-22T10:09:10.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543149
;

-- 2022-03-22T10:09:10.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543149
;

-- 2022-03-22T10:09:11.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-03-22 12:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543149
;

