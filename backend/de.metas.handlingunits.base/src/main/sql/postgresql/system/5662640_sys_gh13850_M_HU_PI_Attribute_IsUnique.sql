-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:24:36.777Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584861,540471,0,20,540507,'IsUnique',TO_TIMESTAMP('2022-10-31 19:24:36.606','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Unique',0,0,TO_TIMESTAMP('2022-10-31 19:24:36.606','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T17:24:36.779Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584861 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T17:24:36.782Z
/* DDL */  select update_Column_Translation_From_AD_Element(540471) 
;

-- 2022-10-31T17:24:37.420Z
/* DDL */ SELECT public.db_alter_table('M_HU_PI_Attribute','ALTER TABLE public.M_HU_PI_Attribute ADD COLUMN IsUnique CHAR(1) DEFAULT ''N'' CHECK (IsUnique IN (''Y'',''N'')) NOT NULL')
;

-- Field: Packvorschrift Version -> Merkmale -> Unique
-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:25:56.727Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584861,707930,0,540825,0,TO_TIMESTAMP('2022-10-31 19:25:56.56','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','N','Unique',0,160,0,1,1,TO_TIMESTAMP('2022-10-31 19:25:56.56','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T17:25:56.728Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707930 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T17:25:56.730Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540471) 
;

-- 2022-10-31T17:25:56.733Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707930
;

-- 2022-10-31T17:25:56.734Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707930)
;

-- UI Element: Packvorschrift Version -> Merkmale.Unique
-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:26:13.950Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707930,0,540825,540437,613363,'F',TO_TIMESTAMP('2022-10-31 19:26:13.797','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Unique',20,0,0,TO_TIMESTAMP('2022-10-31 19:26:13.797','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Packvorschrift Version -> Merkmale.Unique
-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:26:46.819Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-10-31 19:26:46.819','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613363
;

-- UI Element: Packvorschrift Version -> Merkmale.Maßeinheit
-- Column: M_HU_PI_Attribute.C_UOM_ID
-- 2022-10-31T17:26:46.826Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-10-31 19:26:46.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=544344
;

-- UI Element: Packvorschrift Version -> Merkmale.Propagation Type
-- Column: M_HU_PI_Attribute.PropagationType
-- 2022-10-31T17:26:46.831Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-10-31 19:26:46.831','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=544340
;

-- UI Element: Packvorschrift Version -> Merkmale.Aggregation Strategy
-- Column: M_HU_PI_Attribute.AggregationStrategy_JavaClass_ID
-- 2022-10-31T17:26:46.837Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-10-31 19:26:46.837','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=544341
;

-- UI Element: Packvorschrift Version -> Merkmale.Splitter Strategy
-- Column: M_HU_PI_Attribute.SplitterStrategy_JavaClass_ID
-- 2022-10-31T17:26:46.843Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-10-31 19:26:46.843','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=544342
;

-- UI Element: Packvorschrift Version -> Merkmale.HU Transfer Attribute Strategy
-- Column: M_HU_PI_Attribute.HU_TansferStrategy_JavaClass_ID
-- 2022-10-31T17:26:46.848Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-10-31 19:26:46.848','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=544343
;

-- UI Element: Packvorschrift Version -> Merkmale.Sektion
-- Column: M_HU_PI_Attribute.AD_Org_ID
-- 2022-10-31T17:26:46.854Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-10-31 19:26:46.854','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=544333
;

-- 2022-10-31T17:29:38.552Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581629,0,TO_TIMESTAMP('2022-10-31 19:29:38.412','YYYY-MM-DD HH24:MI:SS.US'),100,' When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.','de.metas.handlingunits',' When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.','Y','Unique Attribute','Unique Attribute',TO_TIMESTAMP('2022-10-31 19:29:38.412','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T17:29:38.553Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581629 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-31T17:30:27.088Z
UPDATE AD_Element_Trl SET Name='Unique', PrintName='Unique',Updated=TO_TIMESTAMP('2022-10-31 19:30:27.085','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='de_CH'
;

-- 2022-10-31T17:30:27.094Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'de_CH') 
;

-- 2022-10-31T17:30:35.686Z
UPDATE AD_Element_Trl SET Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.',Updated=TO_TIMESTAMP('2022-10-31 19:30:35.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='de_CH'
;

-- 2022-10-31T17:30:35.687Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'de_CH') 
;

-- 2022-10-31T17:30:46.792Z
UPDATE AD_Element_Trl SET Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Name='Unique', PrintName='Unique',Updated=TO_TIMESTAMP('2022-10-31 19:30:46.79','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='de_DE'
;

-- 2022-10-31T17:30:46.794Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'de_DE') 
;

-- 2022-10-31T17:30:46.814Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581629,'de_DE') 
;

-- 2022-10-31T17:30:46.816Z
UPDATE AD_Column SET ColumnName=NULL, Name='Unique', Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.' WHERE AD_Element_ID=581629
;

-- 2022-10-31T17:30:46.817Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Unique', Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.' WHERE AD_Element_ID=581629 AND IsCentrallyMaintained='Y'
;

-- 2022-10-31T17:30:46.818Z
UPDATE AD_Field SET Name='Unique', Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581629) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581629)
;

-- 2022-10-31T17:30:46.823Z
UPDATE AD_PrintFormatItem pi SET PrintName='Unique', Name='Unique' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581629)
;

-- 2022-10-31T17:30:46.824Z
UPDATE AD_Tab SET Name='Unique', Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', CommitWarning = NULL WHERE AD_Element_ID = 581629
;

-- 2022-10-31T17:30:46.826Z
UPDATE AD_WINDOW SET Name='Unique', Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.' WHERE AD_Element_ID = 581629
;

-- 2022-10-31T17:30:46.827Z
UPDATE AD_Menu SET   Name = 'Unique', Description = 'When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581629
;

-- 2022-10-31T17:30:57.660Z
UPDATE AD_Element_Trl SET Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Name='Unique', PrintName='Unique',Updated=TO_TIMESTAMP('2022-10-31 19:30:57.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='en_US'
;

-- 2022-10-31T17:30:57.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'en_US') 
;

-- 2022-10-31T17:31:09.805Z
UPDATE AD_Element_Trl SET Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Name='Unique', PrintName='Unique',Updated=TO_TIMESTAMP('2022-10-31 19:31:09.803','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='nl_NL'
;

-- 2022-10-31T17:31:09.806Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'nl_NL') 
;

-- Field: Packvorschrift Version -> Merkmale -> Unique
-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:31:37.366Z
UPDATE AD_Field SET AD_Name_ID=581629, Description='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Help='When set to ''Y'', there cannot be more than one active,issued or picked handling unit for a product having the same non null value for this attribute.', Name='Unique',Updated=TO_TIMESTAMP('2022-10-31 19:31:37.366','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=707930
;

-- 2022-10-31T17:31:37.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581629) 
;

-- 2022-10-31T17:31:37.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707930
;

-- 2022-10-31T17:31:37.371Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707930)
;

-- Field: Packvorschrift Version -> Merkmale -> Unique
-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:34:19.670Z
UPDATE AD_Field SET DisplayLogic='@PropagationType/''''@ = ''NoPropagation''',Updated=TO_TIMESTAMP('2022-10-31 19:34:19.669','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=707930
;

-- Field: Packvorschrift Version -> Merkmale -> Unique
-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:35:07.452Z
UPDATE AD_Field SET DisplayLogic='@PropagationType/''''@=''NoPropagation''',Updated=TO_TIMESTAMP('2022-10-31 19:35:07.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=707930
;

-- Field: Packvorschrift Version -> Merkmale -> Unique
-- Column: M_HU_PI_Attribute.IsUnique
-- 2022-10-31T17:35:53.531Z
UPDATE AD_Field SET DisplayLogic='@PropagationType/''''@=''NONE''',Updated=TO_TIMESTAMP('2022-10-31 19:35:53.531','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=707930
;



