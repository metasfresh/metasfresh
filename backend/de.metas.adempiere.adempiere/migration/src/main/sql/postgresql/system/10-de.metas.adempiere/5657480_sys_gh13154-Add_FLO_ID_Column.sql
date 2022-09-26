-- 2022-09-26T09:14:37.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581489,0,'th173_FLO_ID',TO_TIMESTAMP('2022-09-26 11:14:36','YYYY-MM-DD HH24:MI:SS'),100,'ID granted by FLOCERT for companies to put it on their products.','D','Y','FLO ID','FLO ID',TO_TIMESTAMP('2022-09-26 11:14:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T09:14:37.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581489 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-26T09:16:56.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-26 11:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581489 AND AD_Language='de_DE'
;

-- 2022-09-26T09:16:56.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581489,'de_DE')
;

-- 2022-09-26T09:16:57.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581489,'de_DE')
;

-- 2022-09-26T09:16:57.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='th173_FLO_ID', Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.' WHERE AD_Element_ID=581489
;

-- 2022-09-26T09:16:57.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='th173_FLO_ID', Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', AD_Element_ID=581489 WHERE UPPER(ColumnName)='TH173_FLO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-26T09:16:57.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='th173_FLO_ID', Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.' WHERE AD_Element_ID=581489 AND IsCentrallyMaintained='Y'
;

-- 2022-09-26T09:16:57.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581489) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581489)
;

-- 2022-09-26T09:16:57.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', CommitWarning = NULL WHERE AD_Element_ID = 581489
;

-- 2022-09-26T09:16:57.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='FLO ID', Description='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', Help='Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.' WHERE AD_Element_ID = 581489
;

-- 2022-09-26T09:16:57.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'FLO ID', Description = 'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581489
;

-- 2022-09-26T09:17:04.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-26 11:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581489 AND AD_Language='en_US'
;

-- 2022-09-26T09:17:04.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581489,'en_US')
;

-- 2022-09-26T09:19:22.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584453,581489,0,10,208,'th173_FLO_ID',TO_TIMESTAMP('2022-09-26 11:19:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','D',0,255,'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FLO ID',0,0,TO_TIMESTAMP('2022-09-26 11:19:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-26T09:19:22.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584453 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-26T09:19:22.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581489)
;

-- 2022-09-26T09:19:33.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN th173_FLO_ID VARCHAR(255)')
;

-- 2022-09-26T09:23:42.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584453,707337,0,180,60,TO_TIMESTAMP('2022-09-26 11:23:42','YYYY-MM-DD HH24:MI:SS'),100,'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.',20,'D','Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.',0,'Y','Y','Y','N','N','N','N','N','FLO ID',0,530,0,1,1,TO_TIMESTAMP('2022-09-26 11:23:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T09:23:42.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-26T09:23:42.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581489)
;

-- 2022-09-26T09:23:42.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707337
;

-- 2022-09-26T09:23:42.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(707337)
;

-- 2022-09-26T09:26:39.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707337,0,180,1000015,613035,'F',TO_TIMESTAMP('2022-09-26 11:26:39','YYYY-MM-DD HH24:MI:SS'),100,'Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','Eine von FLOCERT erteilte ID, die für Unternehmen zur Kennzeichnung ihrer Produkte vergeben wird.','Y','Y','N','Y','N','N','N',0,'FLO ID',70,0,0,TO_TIMESTAMP('2022-09-26 11:26:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T09:26:48.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2022-09-26 11:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613035
;

