-- 2022-04-14T11:13:33.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580782,0,'MaxLoadWeight',TO_TIMESTAMP('2022-04-14 12:13:33','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','MaxLoadWeight','MaxLoadWeight',TO_TIMESTAMP('2022-04-14 12:13:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T11:13:33.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580782 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-14T11:14:02.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Max. Traglast', PrintName='Max. Traglast',Updated=TO_TIMESTAMP('2022-04-14 12:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580782 AND AD_Language='de_DE'
;

-- 2022-04-14T11:14:03.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580782,'de_DE') 
;

-- 2022-04-14T11:14:03.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580782,'de_DE') 
;

-- 2022-04-14T11:14:03.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MaxLoadWeight', Name='Max. Traglast', Description=NULL, Help=NULL WHERE AD_Element_ID=580782
;

-- 2022-04-14T11:14:03.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxLoadWeight', Name='Max. Traglast', Description=NULL, Help=NULL, AD_Element_ID=580782 WHERE UPPER(ColumnName)='MAXLOADWEIGHT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-14T11:14:03.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxLoadWeight', Name='Max. Traglast', Description=NULL, Help=NULL WHERE AD_Element_ID=580782 AND IsCentrallyMaintained='Y'
;

-- 2022-04-14T11:14:03.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Max. Traglast', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580782) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580782)
;

-- 2022-04-14T11:14:03.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Max. Traglast', Name='Max. Traglast' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580782)
;

-- 2022-04-14T11:14:03.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Max. Traglast', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580782
;

-- 2022-04-14T11:14:03.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Max. Traglast', Description=NULL, Help=NULL WHERE AD_Element_ID = 580782
;

-- 2022-04-14T11:14:03.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Max. Traglast', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580782
;

-- 2022-04-14T11:14:17.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Max. Traglast', PrintName='Max. Traglast',Updated=TO_TIMESTAMP('2022-04-14 12:14:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580782 AND AD_Language='de_CH'
;

-- 2022-04-14T11:14:17.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580782,'de_CH') 
;

-- 2022-04-14T11:14:38.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Max. Traglast', PrintName='Max. Traglast',Updated=TO_TIMESTAMP('2022-04-14 12:14:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580782 AND AD_Language='nl_NL'
;

-- 2022-04-14T11:14:38.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580782,'nl_NL') 
;

-- 2022-04-14T11:14:43.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Max. Load Weight', PrintName='Max. Load Weight',Updated=TO_TIMESTAMP('2022-04-14 12:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580782 AND AD_Language='en_US'
;

-- 2022-04-14T11:14:43.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580782,'en_US') 
;

-- 2022-04-14T11:17:50.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582793,580782,0,12,540519,'MaxLoadWeight',TO_TIMESTAMP('2022-04-14 12:17:50','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Max. Traglast',0,0,TO_TIMESTAMP('2022-04-14 12:17:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-14T11:17:50.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582793 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-14T11:17:50.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580782) 
;

-- 2022-04-14T11:18:17.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_PackingMaterial','ALTER TABLE public.M_HU_PackingMaterial ADD COLUMN MaxLoadWeight NUMERIC')
;

-- 2022-04-14T11:21:46.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580783,0,'isQtyLUByMaxLoadWeight',TO_TIMESTAMP('2022-04-14 12:21:46','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','Qty LU By Max. Load Weight','Qty LU By Max. Load Weight',TO_TIMESTAMP('2022-04-14 12:21:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T11:21:46.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580783 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-14T11:22:47.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582794,580783,0,20,540519,'isQtyLUByMaxLoadWeight',TO_TIMESTAMP('2022-04-14 12:22:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Qty LU By Max. Load Weight',0,0,TO_TIMESTAMP('2022-04-14 12:22:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-14T11:22:47.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582794 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-14T11:22:47.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580783) 
;

-- 2022-04-14T11:23:23.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582794,691708,0,540521,0,TO_TIMESTAMP('2022-04-14 12:23:23','YYYY-MM-DD HH24:MI:SS'),100,'',0,'U',0,'Y','Y','Y','N','N','N','N','N','Qty LU By Max. Load Weight',0,200,0,1,1,TO_TIMESTAMP('2022-04-14 12:23:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T11:23:23.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691708 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T11:23:23.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580783) 
;

-- 2022-04-14T11:23:23.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691708
;

-- 2022-04-14T11:23:23.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691708)
;

-- 2022-04-14T11:23:40.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582793,691709,0,540521,0,TO_TIMESTAMP('2022-04-14 12:23:40','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Max. Traglast',0,210,0,1,1,TO_TIMESTAMP('2022-04-14 12:23:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T11:23:40.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691709 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T11:23:40.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580782) 
;

-- 2022-04-14T11:23:40.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691709
;

-- 2022-04-14T11:23:40.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691709)
;

-- 2022-04-14T11:23:46.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-04-14 12:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691709
;

-- 2022-04-14T11:23:49.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-04-14 12:23:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691708
;

-- 2022-04-14T11:25:13.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691709,0,540521,540534,605356,'F',TO_TIMESTAMP('2022-04-14 12:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Max. Traglast',40,0,0,TO_TIMESTAMP('2022-04-14 12:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T11:25:22.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-04-14 12:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605356
;

-- 2022-04-14T11:26:04.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691708,0,540521,540535,605357,'F',TO_TIMESTAMP('2022-04-14 12:26:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Qty LU By Max. Load Weight',30,0,0,TO_TIMESTAMP('2022-04-14 12:26:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T11:26:45.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-04-14 12:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605357
;

-- 2022-04-14T11:26:45.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-04-14 12:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544990
;

-- 2022-04-14T11:26:45.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-04-14 12:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605356
;

-- 2022-04-14T11:29:09.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_PackingMaterial','ALTER TABLE public.M_HU_PackingMaterial ADD COLUMN isQtyLUByMaxLoadWeight CHAR(1) DEFAULT ''N'' CHECK (isQtyLUByMaxLoadWeight IN (''Y'',''N'')) NOT NULL')
;

-- 2022-04-14T14:32:08.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='LU-Menge nach max. Traglast', PrintName='LU-Menge nach max. Traglast',Updated=TO_TIMESTAMP('2022-04-14 15:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580783 AND AD_Language='de_DE'
;

-- 2022-04-14T14:32:08.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580783,'de_DE') 
;

-- 2022-04-14T14:32:08.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580783,'de_DE') 
;

-- 2022-04-14T14:32:08.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='isQtyLUByMaxLoadWeight', Name='LU-Menge nach max. Traglast', Description='', Help=NULL WHERE AD_Element_ID=580783
;

-- 2022-04-14T14:32:08.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='isQtyLUByMaxLoadWeight', Name='LU-Menge nach max. Traglast', Description='', Help=NULL, AD_Element_ID=580783 WHERE UPPER(ColumnName)='ISQTYLUBYMAXLOADWEIGHT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-14T14:32:08.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='isQtyLUByMaxLoadWeight', Name='LU-Menge nach max. Traglast', Description='', Help=NULL WHERE AD_Element_ID=580783 AND IsCentrallyMaintained='Y'
;

-- 2022-04-14T14:32:08.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LU-Menge nach max. Traglast', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580783)
;

-- 2022-04-14T14:32:08.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LU-Menge nach max. Traglast', Name='LU-Menge nach max. Traglast' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580783)
;

-- 2022-04-14T14:32:08.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LU-Menge nach max. Traglast', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580783
;

-- 2022-04-14T14:32:08.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LU-Menge nach max. Traglast', Description='', Help=NULL WHERE AD_Element_ID = 580783
;

-- 2022-04-14T14:32:08.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LU-Menge nach max. Traglast', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580783
;

-- 2022-04-14T14:32:27.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='LU-Menge nach max. Traglast', PrintName='LU-Menge nach max. Traglast',Updated=TO_TIMESTAMP('2022-04-14 15:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580783 AND AD_Language='nl_NL'
;

-- 2022-04-14T14:32:27.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580783,'nl_NL') 
;

-- 2022-04-14T14:32:38.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='LU-Menge nach max. Traglast', PrintName='LU-Menge nach max. Traglast',Updated=TO_TIMESTAMP('2022-04-14 15:32:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580783 AND AD_Language='de_CH'
;

-- 2022-04-14T14:32:38.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580783,'de_CH') 
;

-- 2022-04-19T14:02:51.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.',Updated=TO_TIMESTAMP('2022-04-19 15:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580783 AND AD_Language='de_DE'
;

-- 2022-04-19T14:02:51.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580783,'de_DE')
;

-- 2022-04-19T14:02:52Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580783,'de_DE')
;

-- 2022-04-19T14:02:52.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='isQtyLUByMaxLoadWeight', Name='LU-Menge nach max. Traglast', Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.', Help=NULL WHERE AD_Element_ID=580783
;

-- 2022-04-19T14:02:52.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='isQtyLUByMaxLoadWeight', Name='LU-Menge nach max. Traglast', Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.', Help=NULL, AD_Element_ID=580783 WHERE UPPER(ColumnName)='ISQTYLUBYMAXLOADWEIGHT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-19T14:02:52.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='isQtyLUByMaxLoadWeight', Name='LU-Menge nach max. Traglast', Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.', Help=NULL WHERE AD_Element_ID=580783 AND IsCentrallyMaintained='Y'
;

-- 2022-04-19T14:02:52.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LU-Menge nach max. Traglast', Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580783)
;

-- 2022-04-19T14:02:52.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LU-Menge nach max. Traglast', Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580783
;

-- 2022-04-19T14:02:52.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LU-Menge nach max. Traglast', Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.', Help=NULL WHERE AD_Element_ID = 580783
;

-- 2022-04-19T14:02:52.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LU-Menge nach max. Traglast', Description = 'Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580783
;

-- 2022-04-19T14:03:16.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.',Updated=TO_TIMESTAMP('2022-04-19 15:03:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580783 AND AD_Language='nl_NL'
;

-- 2022-04-19T14:03:16.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580783,'nl_NL')
;

-- 2022-04-19T14:03:19.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird die LU-Menge in der Transport-Dispo anhand des Produktgewichts und des maximalen Ladegewichts bestimmt.',Updated=TO_TIMESTAMP('2022-04-19 15:03:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580783 AND AD_Language='de_CH'
;

-- 2022-04-19T14:03:19.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580783,'de_CH')
;

-- 2022-04-19T14:03:37.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='When Yes, Calculate Qty Ordered LU based on the Packing Materiel''s Max. Load Weight and Product Weight',Updated=TO_TIMESTAMP('2022-04-19 15:03:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580783 AND AD_Language='en_US'
;

-- 2022-04-19T14:03:37.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580783,'en_US')
;

