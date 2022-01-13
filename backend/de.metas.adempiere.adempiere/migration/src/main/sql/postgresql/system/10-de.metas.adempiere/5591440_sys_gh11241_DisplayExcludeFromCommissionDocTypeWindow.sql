-- 2021-06-08T13:23:18.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579312,0,'IsExcludeFromCommision',TO_TIMESTAMP('2021-06-08 16:23:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Exclude From Commission','Exclude From Commission',TO_TIMESTAMP('2021-06-08 16:23:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T13:23:18.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579312 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-08T13:23:47.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574263,579312,0,20,217,'IsExcludeFromCommision',TO_TIMESTAMP('2021-06-08 16:23:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Exclude From Commission',0,0,TO_TIMESTAMP('2021-06-08 16:23:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-08T13:23:47.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574263 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-08T13:23:47.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579312)
;

-- Add the new field for tab and display it in window when the flag is set
-- 2021-06-08T13:26:03.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574263,647477,0,167,0,TO_TIMESTAMP('2021-06-08 16:26:03','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','Exclude From Commission',330,330,0,1,1,TO_TIMESTAMP('2021-06-08 16:26:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T13:26:03.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647477 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T13:26:03.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579312)
;

-- 2021-06-08T13:26:03.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647477
;

-- 2021-06-08T13:26:03.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647477)
;

-- 2021-06-08T13:28:04.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,647477,0,167,585689,540407,'F',TO_TIMESTAMP('2021-06-08 16:28:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Exclude From Commission',60,0,0,TO_TIMESTAMP('2021-06-08 16:28:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T13:29:15.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsExcludeFromCommision@=''Y''',Updated=TO_TIMESTAMP('2021-06-08 16:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647477
;

-- TRL for the new field
-- 2021-06-09T05:51:42.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei Aktivierung werden für die jeweiligen Belegarten keine Provisionsvorgänge erstellt.', IsTranslated='Y', Name='Von Provision ausschließen', PrintName='Von Provision ausschließen',Updated=TO_TIMESTAMP('2021-06-09 08:51:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579312 AND AD_Language='de_CH'
;

-- 2021-06-09T05:51:42.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579312,'de_CH')
;

-- 2021-06-09T05:52:02.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei Aktivierung werden für die jeweiligen Belegarten keine Provisionsvorgänge erstellt.', IsTranslated='Y', Name='Von Provision ausschließen', PrintName='Von Provision ausschließen',Updated=TO_TIMESTAMP('2021-06-09 08:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579312 AND AD_Language='de_DE'
;

-- 2021-06-09T05:52:02.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579312,'de_DE')
;

-- 2021-06-09T05:52:28.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='When activated, no commission instances will be created for the respective document types.', IsTranslated='Y', Name='Exclude from commission', PrintName='Exclude from commission',Updated=TO_TIMESTAMP('2021-06-09 08:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579312 AND AD_Language='en_US'
;

-- 2021-06-09T05:52:28.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579312,'en_US')
;

-- 2021-06-09T05:52:28.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579312,'en_US')
;

-- 2021-06-09T05:52:28.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExcludeFromCommision', Name='Exclude from commission', Description='When activated, no commission instances will be created for the respective document types.', Help=NULL WHERE AD_Element_ID=579312
;

-- 2021-06-09T05:52:28.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludeFromCommision', Name='Exclude from commission', Description='When activated, no commission instances will be created for the respective document types.', Help=NULL, AD_Element_ID=579312 WHERE UPPER(ColumnName)='ISEXCLUDEFROMCOMMISION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-09T05:52:28.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludeFromCommision', Name='Exclude from commission', Description='When activated, no commission instances will be created for the respective document types.', Help=NULL WHERE AD_Element_ID=579312 AND IsCentrallyMaintained='Y'
;

-- 2021-06-09T05:52:28.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exclude from commission', Description='When activated, no commission instances will be created for the respective document types.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579312) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579312)
;

-- 2021-06-09T05:52:28.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exclude from commission', Name='Exclude from commission' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579312)
;

-- 2021-06-09T05:52:28.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Exclude from commission', Description='When activated, no commission instances will be created for the respective document types.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579312
;

-- 2021-06-09T05:52:28.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Exclude from commission', Description='When activated, no commission instances will be created for the respective document types.', Help=NULL WHERE AD_Element_ID = 579312
;

-- 2021-06-09T05:52:28.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Exclude from commission', Description = 'When activated, no commission instances will be created for the respective document types.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579312
;

-- 2021-06-09T05:52:54.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei Aktivierung werden für die jeweiligen Belegarten keine Provisionsvorgänge erstellt.', IsTranslated='Y', Name='Von Provision ausschließen', PrintName='Von Provision ausschließen',Updated=TO_TIMESTAMP('2021-06-09 08:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579312 AND AD_Language='nl_NL'
;

-- 2021-06-09T05:52:54.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579312,'nl_NL')
;
