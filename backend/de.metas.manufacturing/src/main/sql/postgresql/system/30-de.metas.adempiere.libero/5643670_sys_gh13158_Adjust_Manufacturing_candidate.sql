-- 2022-06-15T18:20:58.570Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-06-15 19:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583339
;

-- 2022-06-15T18:24:19.217Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581039,0,'QtyProcessed_OnDate',TO_TIMESTAMP('2022-06-15 19:24:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Menge Summe pro Tag','Menge Summe pro Tag',TO_TIMESTAMP('2022-06-15 19:24:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:24:19.287Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581039 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-06-15T18:25:06.601Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Total Quantity per Day', PrintName='Total Quantity per Day',Updated=TO_TIMESTAMP('2022-06-15 19:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581039 AND AD_Language='en_US'
;

-- 2022-06-15T18:25:06.691Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581039,'en_US') 
;

-- 2022-06-15T18:25:50.322Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583368,581039,0,29,541913,'QtyProcessed_OnDate',TO_TIMESTAMP('2022-06-15 19:25:49','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Menge Summe pro Tag',0,0,TO_TIMESTAMP('2022-06-15 19:25:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-15T18:25:50.393Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583368 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-15T18:25:50.550Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581039) 
;

-- 2022-06-15T18:26:39.499Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN QtyProcessed_OnDate NUMERIC')
;

-- 2022-06-15T18:27:42.331Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583368,700720,0,544794,0,TO_TIMESTAMP('2022-06-15 19:27:37','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Menge Summe pro Tag',0,20,0,1,1,TO_TIMESTAMP('2022-06-15 19:27:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:27:42.398Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700720 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-15T18:27:42.465Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581039) 
;

-- 2022-06-15T18:27:42.543Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700720
;

-- 2022-06-15T18:27:42.604Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(700720)
;

-- 2022-06-15T18:29:03.561Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700720,0,544794,547107,609599,'F',TO_TIMESTAMP('2022-06-15 19:29:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Menge Summe pro Tag',17,0,0,TO_TIMESTAMP('2022-06-15 19:29:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:29:20.063Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-06-15 19:29:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609599
;

-- 2022-06-15T18:29:26.525Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-06-15 19:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609599
;

-- 2022-06-15T18:29:26.787Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-06-15 19:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594541
;

-- 2022-06-15T18:29:27.054Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-06-15 19:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594544
;

-- 2022-06-15T18:29:27.312Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-06-15 19:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594641
;

-- 2022-06-15T18:29:27.581Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-06-15 19:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594543
;

-- 2022-06-15T18:29:28.380Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-06-15 19:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595252
;

-- 2022-06-15T18:29:28.635Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-06-15 19:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594545
;

-- 2022-06-15T18:29:28.884Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-06-15 19:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594546
;

-- 2022-06-15T18:31:41.750Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2022-06-15 19:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583368
;

-- 2022-06-15T18:32:51.858Z
-- URL zum Konzept
INSERT INTO t_alter_column values('pp_order_candidate','QtyProcessed_OnDate','NUMERIC',null,'0')
;

-- 2022-06-15T18:49:22.103Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=581031, Description=NULL, Help=NULL, Name='Sequenz Nr.',Updated=TO_TIMESTAMP('2022-06-15 19:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591256
;

-- 2022-06-15T18:49:22.167Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581031) 
;

-- 2022-06-15T18:49:22.236Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591256
;

-- 2022-06-15T18:49:22.298Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(591256)
;

-- 2022-06-15T18:49:58.287Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542074,549376,TO_TIMESTAMP('2022-06-15 19:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','menge',20,TO_TIMESTAMP('2022-06-15 19:49:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:50:23.905Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591256,0,542102,549376,609600,'F',TO_TIMESTAMP('2022-06-15 19:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sequenz Nr.',10,0,0,TO_TIMESTAMP('2022-06-15 19:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:54:06.616Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583369,581039,0,29,53020,'QtyProcessed_OnDate',TO_TIMESTAMP('2022-06-15 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'N','0','EE01',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Menge Summe pro Tag',0,0,TO_TIMESTAMP('2022-06-15 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-15T18:54:06.691Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583369 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-15T18:54:06.840Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581039) 
;

-- 2022-06-15T18:54:44.917Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN QtyProcessed_OnDate NUMERIC DEFAULT 0')
;

-- 2022-06-15T18:55:22.169Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583369,700721,0,542102,0,TO_TIMESTAMP('2022-06-15 19:55:21','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Menge Summe pro Tag',0,30,0,1,1,TO_TIMESTAMP('2022-06-15 19:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T18:55:22.238Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700721 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-15T18:55:22.305Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581039) 
;

-- 2022-06-15T18:55:22.373Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700721
;

-- 2022-06-15T18:55:22.436Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(700721)
;

-- 2022-06-15T18:55:55.107Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-06-15 19:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609600
;

-- 2022-06-15T18:56:17.805Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700721,0,542102,549376,609601,'F',TO_TIMESTAMP('2022-06-15 19:56:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Menge Summe pro Tag',10,0,0,TO_TIMESTAMP('2022-06-15 19:56:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-15T19:02:00.023Z
-- URL zum Konzept
UPDATE AD_Field SET SortNo=10.000000000000,Updated=TO_TIMESTAMP('2022-06-15 20:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667483
;

-- 2022-06-15T19:02:23.582Z
-- URL zum Konzept
UPDATE AD_Field SET SortNo=20.000000000000,Updated=TO_TIMESTAMP('2022-06-15 20:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667477
;

-- 2022-06-15T19:02:59.897Z
-- URL zum Konzept
UPDATE AD_Field SET SortNo=30.000000000000,Updated=TO_TIMESTAMP('2022-06-15 20:02:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700645
;

-- 2022-06-15T19:03:15.800Z
-- URL zum Konzept
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2022-06-15 20:03:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=700645
;

