-- Column: M_Inventory.M_SectionCode_ID
-- 2023-03-21T10:06:43.559Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586312,581238,0,30,541698,321,'M_SectionCode_ID',TO_TIMESTAMP('2023-03-21 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-03-21 11:06:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-21T10:06:43.561Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586312 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-21T10:06:43.581Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-03-21T10:07:04.022Z
/* DDL */ SELECT public.db_alter_table('M_Inventory','ALTER TABLE public.M_Inventory ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-03-21T10:07:04.212Z
ALTER TABLE M_Inventory ADD CONSTRAINT MSectionCode_MInventory FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InventoryLine.M_SectionCode_ID
-- 2023-03-21T10:08:02.575Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586313,581238,0,30,541698,322,'M_SectionCode_ID',TO_TIMESTAMP('2023-03-21 11:08:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-03-21 11:08:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-21T10:08:02.576Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586313 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-21T10:08:02.578Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-03-21T10:08:18.118Z
/* DDL */ SELECT public.db_alter_table('M_InventoryLine','ALTER TABLE public.M_InventoryLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-03-21T10:08:18.201Z
ALTER TABLE M_InventoryLine ADD CONSTRAINT MSectionCode_MInventoryLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Physical Inventory(168,D) -> Inventory Count(255,D) -> Section Code
-- Column: M_Inventory.M_SectionCode_ID
-- 2023-03-21T10:10:20.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586312,712842,0,255,0,TO_TIMESTAMP('2023-03-21 11:10:20','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,230,0,1,1,TO_TIMESTAMP('2023-03-21 11:10:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-21T10:10:20.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-21T10:10:20.859Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-03-21T10:10:20.894Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712842
;

-- 2023-03-21T10:10:20.895Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712842)
;

-- Field: Physical Inventory(168,D) -> Inventory Count Line(256,D) -> Section Code
-- Column: M_InventoryLine.M_SectionCode_ID
-- 2023-03-21T10:10:37.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586313,712843,0,256,0,TO_TIMESTAMP('2023-03-21 11:10:37','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,180,0,1,1,TO_TIMESTAMP('2023-03-21 11:10:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-21T10:10:37.547Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-21T10:10:37.548Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-03-21T10:10:37.555Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712843
;

-- 2023-03-21T10:10:37.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712843)
;

-- UI Column: Physical Inventory(168,D) -> Inventory Count(255,D) -> main -> 20
-- UI Element Group: section
-- 2023-03-21T10:11:47.630Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540910,550440,TO_TIMESTAMP('2023-03-21 11:11:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','section',15,TO_TIMESTAMP('2023-03-21 11:11:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Physical Inventory(168,D) -> Inventory Count(255,D) -> main -> 20 -> section.Section Code
-- Column: M_Inventory.M_SectionCode_ID
-- 2023-03-21T10:12:07.754Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712842,0,255,550440,616016,'F',TO_TIMESTAMP('2023-03-21 11:12:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',10,0,0,TO_TIMESTAMP('2023-03-21 11:12:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Physical Inventory(168,D) -> Inventory Count Line(256,D) -> main -> 10 -> default.Section Code
-- Column: M_InventoryLine.M_SectionCode_ID
-- 2023-03-21T10:13:48.149Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712843,0,256,541513,616017,'F',TO_TIMESTAMP('2023-03-21 11:13:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',160,0,0,TO_TIMESTAMP('2023-03-21 11:13:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Physical Inventory(168,D) -> Inventory Count Line(256,D) -> main -> 10 -> default.Section Code
-- Column: M_InventoryLine.M_SectionCode_ID
-- 2023-03-21T10:14:04.487Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-21 11:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616017
;

-- UI Element: Physical Inventory(168,D) -> Inventory Count Line(256,D) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2023-03-21T10:14:04.492Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-21 11:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551246
;

-- 2023-03-21T10:20:35.885Z
INSERT INTO t_alter_column values('m_inventoryline','M_SectionCode_ID','NUMERIC(10)',null,null)
;

-- UI Element: Physical Inventory(168,D) -> Inventory Count Line(256,D) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2023-03-21T10:23:20.066Z
UPDATE AD_UI_Element SET SeqNo=500,Updated=TO_TIMESTAMP('2023-03-21 11:23:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551246
;

-- UI Element: Physical Inventory(168,D) -> Inventory Count Line(256,D) -> main -> 10 -> default.Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- 2023-03-21T10:23:27.333Z
UPDATE AD_UI_Element SET SeqNo=600,Updated=TO_TIMESTAMP('2023-03-21 11:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551245
;

-- Column: M_Movement.M_SectionCode_ID
-- 2023-03-21T10:28:45.950Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586314,581238,0,30,541698,323,'M_SectionCode_ID',TO_TIMESTAMP('2023-03-21 11:28:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-03-21 11:28:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-21T10:28:45.951Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586314 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-21T10:28:45.952Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-03-21T10:28:48.299Z
/* DDL */ SELECT public.db_alter_table('M_Movement','ALTER TABLE public.M_Movement ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-03-21T10:28:48.414Z
ALTER TABLE M_Movement ADD CONSTRAINT MSectionCode_MMovement FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_MovementLine.M_SectionCode_ID
-- 2023-03-21T10:29:17.773Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586315,581238,0,30,541698,324,'M_SectionCode_ID',TO_TIMESTAMP('2023-03-21 11:29:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-03-21 11:29:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-21T10:29:17.774Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586315 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-21T10:29:17.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-03-21T10:29:20.474Z
/* DDL */ SELECT public.db_alter_table('M_MovementLine','ALTER TABLE public.M_MovementLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-03-21T10:29:20.555Z
ALTER TABLE M_MovementLine ADD CONSTRAINT MSectionCode_MMovementLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Inventory Move(170,D) -> Move(259,D) -> Section Code
-- Column: M_Movement.M_SectionCode_ID
-- 2023-03-21T10:33:31.222Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586314,712844,0,259,0,TO_TIMESTAMP('2023-03-21 11:33:31','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,330,0,1,1,TO_TIMESTAMP('2023-03-21 11:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-21T10:33:31.224Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-21T10:33:31.227Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-03-21T10:33:31.263Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712844
;

-- 2023-03-21T10:33:31.265Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712844)
;

-- UI Column: Inventory Move(170,D) -> Move(259,D) -> main -> 20
-- UI Element Group: section
-- 2023-03-21T10:33:57.020Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540146,550441,TO_TIMESTAMP('2023-03-21 11:33:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','section',15,TO_TIMESTAMP('2023-03-21 11:33:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventory Move(170,D) -> Move(259,D) -> main -> 20 -> section.Section Code
-- Column: M_Movement.M_SectionCode_ID
-- 2023-03-21T10:34:54.180Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712844,0,259,550441,616018,'F',TO_TIMESTAMP('2023-03-21 11:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',10,0,0,TO_TIMESTAMP('2023-03-21 11:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Inventory Move(170,D) -> Move Line(260,D) -> Section Code
-- Column: M_MovementLine.M_SectionCode_ID
-- 2023-03-21T10:35:20.131Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586315,712845,0,260,0,TO_TIMESTAMP('2023-03-21 11:35:20','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,200,0,1,1,TO_TIMESTAMP('2023-03-21 11:35:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-21T10:35:20.132Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-21T10:35:20.136Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-03-21T10:35:20.152Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712845
;

-- 2023-03-21T10:35:20.155Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712845)
;

-- UI Element: Inventory Move(170,D) -> Move Line(260,D) -> main -> 10 -> main.Sektion
-- Column: M_MovementLine.AD_Org_ID
-- 2023-03-21T10:35:35.982Z
UPDATE AD_UI_Element SET SeqNo=500,Updated=TO_TIMESTAMP('2023-03-21 11:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542591
;

-- UI Element: Inventory Move(170,D) -> Move Line(260,D) -> main -> 10 -> main.Mandant
-- Column: M_MovementLine.AD_Client_ID
-- 2023-03-21T10:35:42.762Z
UPDATE AD_UI_Element SET SeqNo=600,Updated=TO_TIMESTAMP('2023-03-21 11:35:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542590
;

-- UI Element: Inventory Move(170,D) -> Move Line(260,D) -> main -> 10 -> main.Section Code
-- Column: M_MovementLine.M_SectionCode_ID
-- 2023-03-21T10:36:08.128Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712845,0,260,540942,616019,'F',TO_TIMESTAMP('2023-03-21 11:36:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',610,0,0,TO_TIMESTAMP('2023-03-21 11:36:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventory Move(170,D) -> Move Line(260,D) -> main -> 10 -> main.Section Code
-- Column: M_MovementLine.M_SectionCode_ID
-- 2023-03-21T10:36:12.608Z
UPDATE AD_UI_Element SET SeqNo=190,Updated=TO_TIMESTAMP('2023-03-21 11:36:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616019
;

-- UI Element: Inventory Move(170,D) -> Move Line(260,D) -> main -> 10 -> main.Section Code
-- Column: M_MovementLine.M_SectionCode_ID
-- 2023-03-21T10:36:27.735Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-21 11:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616019
;

-- UI Element: Inventory Move(170,D) -> Move Line(260,D) -> main -> 10 -> main.Aktiv
-- Column: M_MovementLine.IsActive
-- 2023-03-21T10:36:27.738Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-21 11:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542596
;

-- UI Element: Inventory Move(170,D) -> Move Line(260,D) -> main -> 10 -> main.Sektion
-- Column: M_MovementLine.AD_Org_ID
-- 2023-03-21T10:36:27.742Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-21 11:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542591
;

-- Column: C_BankStatementLine.M_SectionCode_ID
-- 2023-03-21T10:46:42.933Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586317,581238,0,30,541698,393,'M_SectionCode_ID',TO_TIMESTAMP('2023-03-21 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-03-21 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-21T10:46:42.935Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586317 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-21T10:46:42.936Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-03-21T10:46:44.761Z
/* DDL */ SELECT public.db_alter_table('C_BankStatementLine','ALTER TABLE public.C_BankStatementLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-03-21T10:46:44.809Z
ALTER TABLE C_BankStatementLine ADD CONSTRAINT MSectionCode_CBankStatementLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bank Statement(194,D) -> Statement Line(329,D) -> Section Code
-- Column: C_BankStatementLine.M_SectionCode_ID
-- 2023-03-21T10:47:04.226Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586317,712847,0,329,0,TO_TIMESTAMP('2023-03-21 11:47:04','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,370,0,1,1,TO_TIMESTAMP('2023-03-21 11:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-21T10:47:04.227Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-21T10:47:04.228Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-03-21T10:47:04.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712847
;

-- 2023-03-21T10:47:04.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712847)
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Section Code
-- Column: C_BankStatementLine.M_SectionCode_ID
-- 2023-03-21T10:48:08.205Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712847,0,329,540272,616021,'F',TO_TIMESTAMP('2023-03-21 11:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',260,0,0,TO_TIMESTAMP('2023-03-21 11:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Sektion
-- Column: C_BankStatementLine.AD_Org_ID
-- 2023-03-21T10:48:18.074Z
UPDATE AD_UI_Element SET SeqNo=500,Updated=TO_TIMESTAMP('2023-03-21 11:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543090
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Mandant
-- Column: C_BankStatementLine.AD_Client_ID
-- 2023-03-21T10:48:23.967Z
UPDATE AD_UI_Element SET SeqNo=600,Updated=TO_TIMESTAMP('2023-03-21 11:48:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543089
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Section Code
-- Column: C_BankStatementLine.M_SectionCode_ID
-- 2023-03-21T10:48:40.754Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-03-21 11:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616021
;


-- UI Column: Bankauszug(194,D) -> Bankauszug(328,D) -> main -> 20
-- UI Element Group: section code
-- 2023-03-21T14:10:16.169Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540184,550443,TO_TIMESTAMP('2023-03-21 15:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','section code',50,TO_TIMESTAMP('2023-03-21 15:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Bankauszug(194,D) -> Bankauszug(328,D) -> main -> 20
-- UI Element Group: section code
-- 2023-03-21T14:10:28.692Z
UPDATE AD_UI_ElementGroup SET SeqNo=16,Updated=TO_TIMESTAMP('2023-03-21 15:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550443
;

-- UI Element: Bankauszug(194,D) -> Bankauszug(328,D) -> main -> 20 -> section code.Sektionskennung
-- Column: C_BankStatement.M_SectionCode_ID
-- 2023-03-21T14:11:28.772Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550443, SeqNo=10,Updated=TO_TIMESTAMP('2023-03-21 15:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616015
;

-- Field: Bankauszug(194,D) -> Bankauszug(328,D) -> Sektionskennung
-- Column: C_BankStatement.M_SectionCode_ID
-- 2023-03-21T14:17:13.594Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-03-21 15:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712841
;

-- UI Element: Bankauszug(194,D) -> Bankauszug(328,D) -> main -> 20 -> section code.Sektionskennung
-- Column: C_BankStatement.M_SectionCode_ID
-- 2023-03-21T14:18:22.553Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-21 15:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616015
;

-- UI Element: Bankauszug(194,D) -> Bankauszug(328,D) -> main -> 20 -> org.Sektion
-- Column: C_BankStatement.AD_Org_ID
-- 2023-03-21T14:18:22.559Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-21 15:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543157
;

