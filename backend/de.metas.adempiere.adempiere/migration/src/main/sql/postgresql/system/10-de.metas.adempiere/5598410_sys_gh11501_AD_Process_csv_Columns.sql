-- 2021-07-14T15:03:38.488Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579489,0,'SpreadsheetFormat',TO_TIMESTAMP('2021-07-14 18:03:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SpreadsheetFormat','SpreadsheetFormat',TO_TIMESTAMP('2021-07-14 18:03:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T15:03:38.961Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579489 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-14T15:05:37.752Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541369,TO_TIMESTAMP('2021-07-14 18:05:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SpreadsheetFormat',TO_TIMESTAMP('2021-07-14 18:05:37','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-07-14T15:05:37.853Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541369 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-07-14T15:05:57.077Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541369,542734,TO_TIMESTAMP('2021-07-14 18:05:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Excel',TO_TIMESTAMP('2021-07-14 18:05:56','YYYY-MM-DD HH24:MI:SS'),100,'xls','Excel')
;

-- 2021-07-14T15:05:57.148Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542734 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-07-14T15:06:13.460Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541369,542735,TO_TIMESTAMP('2021-07-14 18:06:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CSV',TO_TIMESTAMP('2021-07-14 18:06:13','YYYY-MM-DD HH24:MI:SS'),100,'csv','CSV')
;

-- 2021-07-14T15:06:13.527Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542735 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-07-14T15:06:53.228Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575032,579489,0,17,541369,284,'SpreadsheetFormat',TO_TIMESTAMP('2021-07-14 18:06:52','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SpreadsheetFormat',0,0,TO_TIMESTAMP('2021-07-14 18:06:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-14T15:06:53.333Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575032 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-14T15:06:53.515Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579489) 
;

-- 2021-07-14T15:07:32.942Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=55,Updated=TO_TIMESTAMP('2021-07-14 18:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575032
;

-- 2021-07-14T15:09:11.442Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2021-07-14 18:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575032
;

-- 2021-07-14T15:09:16.869Z
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='N',Updated=TO_TIMESTAMP('2021-07-14 18:09:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575032
;




-- 2021-07-14T17:31:20.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN SpreadsheetFormat VARCHAR(50)')
;




-- 2021-07-14T17:50:58.373Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575048,543883,0,10,284,'CSVFieldDelimiter',TO_TIMESTAMP('2021-07-14 20:50:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.datev',0,25,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'CSV Field Delimiter',0,0,TO_TIMESTAMP('2021-07-14 20:50:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-14T17:50:58.717Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575048 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-14T17:50:58.828Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543883) 
;





-- 2021-07-14T17:57:57.513Z
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-07-14 20:57:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575048
;







-- 2021-07-14T17:31:20.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN CSVFieldDelimiter VARCHAR(25)')
;



























UPDATE AD_Process SET classname = 'de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess'
WHERE classname = 'de.metas.impexp.excel.process.ExportToExcelProcess';















-- 2021-07-14T18:20:45.966Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='xls',Updated=TO_TIMESTAMP('2021-07-14 21:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575032
;










UPDATE AD_Process SET spreadsheetformat = 'xls'
WHERE spreadsheetformat IS NULL  AND TYPE = 'Excel';;



-- 2021-07-14T18:47:51.293Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575032,650310,0,245,TO_TIMESTAMP('2021-07-14 21:47:50','YYYY-MM-DD HH24:MI:SS'),100,55,'D','Y','N','N','N','N','N','N','N','SpreadsheetFormat',TO_TIMESTAMP('2021-07-14 21:47:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T18:47:51.619Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-14T18:47:51.719Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579489) 
;

-- 2021-07-14T18:47:51.781Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650310
;

-- 2021-07-14T18:47:51.819Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650310)
;

-- 2021-07-14T18:47:52.312Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575048,650311,0,245,TO_TIMESTAMP('2021-07-14 21:47:51','YYYY-MM-DD HH24:MI:SS'),100,25,'D','Y','N','N','N','N','N','N','N','CSV Field Delimiter',TO_TIMESTAMP('2021-07-14 21:47:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T18:47:52.367Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-14T18:47:52.399Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543883) 
;

-- 2021-07-14T18:47:52.436Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650311
;

-- 2021-07-14T18:47:52.468Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650311)
;

-- 2021-07-14T18:50:40.874Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_process','SpreadsheetFormat','VARCHAR(55)',null,'xls')
;



-- 2021-07-14T18:51:54.924Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@Type@=Excel',Updated=TO_TIMESTAMP('2021-07-14 21:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650310
;

-- 2021-07-14T18:52:06.277Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@Type@=Excel',Updated=TO_TIMESTAMP('2021-07-14 21:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650311
;

-- 2021-07-14T18:53:27.506Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=311,Updated=TO_TIMESTAMP('2021-07-14 21:53:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650310
;

-- 2021-07-14T18:53:32.444Z
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=312,Updated=TO_TIMESTAMP('2021-07-14 21:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650311
;





-- 2021-07-14T18:57:08.592Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650310,0,245,541388,587077,'F',TO_TIMESTAMP('2021-07-14 21:57:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SpreadsheetFormat',310,0,0,TO_TIMESTAMP('2021-07-14 21:57:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T18:57:25.409Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650311,0,245,541388,587078,'F',TO_TIMESTAMP('2021-07-14 21:57:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CSV Field Delimiter',320,0,0,TO_TIMESTAMP('2021-07-14 21:57:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-14T18:57:55.438Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=391,Updated=TO_TIMESTAMP('2021-07-14 21:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=587077
;

-- 2021-07-14T18:58:00.092Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=392,Updated=TO_TIMESTAMP('2021-07-14 21:57:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=587078
;

-- 2021-07-14T18:58:31.136Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@SpreadsheetFormat@ = csv',Updated=TO_TIMESTAMP('2021-07-14 21:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650311
;

-- 2021-07-14T18:58:46.313Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@SpreadsheetFormat@=csv',Updated=TO_TIMESTAMP('2021-07-14 21:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650311
;

-- 2021-07-14T19:55:26.398Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-07-14 22:55:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650310
;

-- 2021-07-14T19:55:30.945Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-07-14 22:55:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=650311
;


