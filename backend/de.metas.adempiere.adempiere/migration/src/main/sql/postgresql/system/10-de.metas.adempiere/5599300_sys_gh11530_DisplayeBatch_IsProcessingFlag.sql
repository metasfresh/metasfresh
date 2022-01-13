-- TRL for element IsProcessing
-- 2021-07-21T09:24:45.806Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='In Verarbeitung', PrintName='In Verarbeitung',Updated=TO_TIMESTAMP('2021-07-21 12:24:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=400 AND AD_Language='de_DE'
;

-- 2021-07-21T09:24:46.047Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(400,'de_DE')
;

-- 2021-07-21T09:24:46.432Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(400,'de_DE')
;

-- 2021-07-21T09:24:46.473Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsProcessing', Name='In Verarbeitung', Description=NULL, Help=NULL WHERE AD_Element_ID=400
;

-- 2021-07-21T09:24:46.515Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsProcessing', Name='In Verarbeitung', Description=NULL, Help=NULL, AD_Element_ID=400 WHERE UPPER(ColumnName)='ISPROCESSING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-21T09:24:46.555Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsProcessing', Name='In Verarbeitung', Description=NULL, Help=NULL WHERE AD_Element_ID=400 AND IsCentrallyMaintained='Y'
;

-- 2021-07-21T09:24:46.598Z
-- URL zum Konzept
UPDATE AD_Field SET Name='In Verarbeitung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=400) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 400)
;

-- 2021-07-21T09:24:46.678Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='In Verarbeitung', Name='In Verarbeitung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=400)
;

-- 2021-07-21T09:24:46.717Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='In Verarbeitung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 400
;

-- 2021-07-21T09:24:46.757Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='In Verarbeitung', Description=NULL, Help=NULL WHERE AD_Element_ID = 400
;

-- 2021-07-21T09:24:46.797Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'In Verarbeitung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 400
;

-- 2021-07-21T09:25:18.603Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='In Verarbeitung', PrintName='In Verarbeitung',Updated=TO_TIMESTAMP('2021-07-21 12:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=400 AND AD_Language='nl_NL'
;

-- 2021-07-21T09:25:18.643Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(400,'nl_NL')
;

-- 2021-07-21T09:25:29.114Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='In Verarbeitung', PrintName='In Verarbeitung',Updated=TO_TIMESTAMP('2021-07-21 12:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=400 AND AD_Language='de_CH'
;

-- 2021-07-21T09:25:29.153Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(400,'de_CH')
;

-- 2021-07-21T09:25:33.878Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-21 12:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=400 AND AD_Language='en_GB'
;

-- 2021-07-21T09:25:33.920Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(400,'en_GB')
;

-- 2021-07-21T09:25:40.170Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='In Verarbeitung', PrintName='In Verarbeitung',Updated=TO_TIMESTAMP('2021-07-21 12:25:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=400 AND AD_Language='it_CH'
;

-- 2021-07-21T09:25:40.211Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(400,'it_CH')
;

-- 2021-07-21T09:25:46.454Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='In Verarbeitung', PrintName='In Verarbeitung',Updated=TO_TIMESTAMP('2021-07-21 12:25:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=400 AND AD_Language='fr_CH'
;

-- 2021-07-21T09:25:46.493Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(400,'fr_CH')
;

-- Create IsProcessing column in table
-- 2021-07-21T09:27:23.114Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575173,400,0,20,540620,'IsProcessing',TO_TIMESTAMP('2021-07-21 12:27:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.async',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'In Verarbeitung',0,0,TO_TIMESTAMP('2021-07-21 12:27:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-21T09:27:23.592Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575173 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-21T09:27:23.676Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(400)
;

-- create field and make it read only
-- 2021-07-21T09:30:10.358Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575173,650419,0,540632,0,TO_TIMESTAMP('2021-07-21 12:30:09','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','In Verarbeitung',180,20,0,1,1,TO_TIMESTAMP('2021-07-21 12:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-21T09:30:10.768Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650419 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-21T09:30:10.813Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(400)
;

-- 2021-07-21T09:30:10.897Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650419
;

-- 2021-07-21T09:30:10.939Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650419)
;

-- display the new field
-- 2021-07-21T09:33:57.620Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650419,0,540632,541055,587146,'F',TO_TIMESTAMP('2021-07-21 12:33:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'In Verarbeitung',15,0,0,TO_TIMESTAMP('2021-07-21 12:33:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- change the order of the fields
-- 2021-07-21T09:48:20.755Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-07-21 12:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547900
;

-- 2021-07-21T09:48:34.554Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-07-21 12:48:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547901
;

-- 2021-07-21T09:48:56.449Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-07-21 12:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547900
;

-- 2021-07-21T09:49:08.807Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-07-21 12:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547901
;

-- 2021-07-21T09:49:26.397Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-07-21 12:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547902
;
