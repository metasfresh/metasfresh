-- 2022-09-26T12:09:44.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581491,0,'FLO_Identifier',TO_TIMESTAMP('2022-09-26 14:09:44','YYYY-MM-DD HH24:MI:SS'),100,'ID granted by FLOCERT for companies to put it on their products.','D','Y','FLO ID','FLO ID',TO_TIMESTAMP('2022-09-26 14:09:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T12:09:44.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581491 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-26T12:10:04.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-26 14:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581491 AND AD_Language='de_DE'
;

-- 2022-09-26T12:10:04.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581491,'de_DE')
;

-- 2022-09-26T12:10:04.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581491,'de_DE')
;

-- 2022-09-26T12:10:04.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FLO_Identifier', Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help=NULL WHERE AD_Element_ID=581491
;

-- 2022-09-26T12:10:04.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FLO_Identifier', Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help=NULL, AD_Element_ID=581491 WHERE UPPER(ColumnName)='FLO_IDENTIFIER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-26T12:10:04.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FLO_Identifier', Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help=NULL WHERE AD_Element_ID=581491 AND IsCentrallyMaintained='Y'
;

-- 2022-09-26T12:10:04.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581491) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581491)
;

-- 2022-09-26T12:10:05.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581491
;

-- 2022-09-26T12:10:05.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help=NULL WHERE AD_Element_ID = 581491
;

-- 2022-09-26T12:10:05.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'FLO ID', Description = 'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581491
;

-- 2022-09-26T12:10:07.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-26 14:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581491 AND AD_Language='en_US'
;

-- 2022-09-26T12:10:07.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581491,'en_US')
;

-- 2022-09-26T12:13:03.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584455,581491,0,10,208,'FLO_Identifier',TO_TIMESTAMP('2022-09-26 14:13:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FLO ID',0,0,TO_TIMESTAMP('2022-09-26 14:13:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-26T12:13:03.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584455 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-26T12:13:03.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581491)
;

-- 2022-09-26T12:13:06.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN FLO_Identifier VARCHAR(255)')
;

-- 2022-09-26T12:17:22.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584456,581491,0,10,632,'FLO_Identifier',TO_TIMESTAMP('2022-09-26 14:17:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FLO ID',0,0,TO_TIMESTAMP('2022-09-26 14:17:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-26T12:17:22.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584456 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-26T12:17:22.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581491)
;

-- 2022-09-26T12:17:50.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN FLO_Identifier VARCHAR(255)')
;

-- 2022-09-26T12:21:23.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584456,707338,0,562,60,TO_TIMESTAMP('2022-09-26 14:21:23','YYYY-MM-DD HH24:MI:SS'),100,'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.',20,'D',0,'Y','Y','Y','N','N','N','N','N','FLO ID',0,250,0,1,1,TO_TIMESTAMP('2022-09-26 14:21:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T12:21:23.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-26T12:21:23.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581491)
;

-- 2022-09-26T12:21:23.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707338
;

-- 2022-09-26T12:21:23.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(707338)
;

-- 2022-09-26T12:22:26.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707338,0,562,1000021,613037,'F',TO_TIMESTAMP('2022-09-26 14:22:26','YYYY-MM-DD HH24:MI:SS'),100,'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','Y','N','N','Y','N','N','N',0,'FLO ID',54,0,0,TO_TIMESTAMP('2022-09-26 14:22:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T12:23:15.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584455,707339,0,180,60,TO_TIMESTAMP('2022-09-26 14:23:15','YYYY-MM-DD HH24:MI:SS'),100,'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.',20,'D',0,'Y','Y','Y','N','N','N','N','N','FLO ID',0,540,0,1,1,TO_TIMESTAMP('2022-09-26 14:23:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T12:23:15.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-26T12:23:15.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581491)
;

-- 2022-09-26T12:23:15.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707339
;

-- 2022-09-26T12:23:15.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(707339)
;

-- 2022-09-26T12:24:14.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707339,0,180,1000015,613038,'F',TO_TIMESTAMP('2022-09-26 14:24:14','YYYY-MM-DD HH24:MI:SS'),100,'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','Y','N','N','Y','N','N','N',0,'FLO ID',80,0,0,TO_TIMESTAMP('2022-09-26 14:24:14','YYYY-MM-DD HH24:MI:SS'),100)
;

