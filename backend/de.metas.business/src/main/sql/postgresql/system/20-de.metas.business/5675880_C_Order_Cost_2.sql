-- 2023-02-07T08:53:09.216Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582037,0,'CostCalculation_FixedAmount',TO_TIMESTAMP('2023-02-07 10:53:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fixed Amount','Fixed Amount',TO_TIMESTAMP('2023-02-07 10:53:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T08:53:09.226Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582037 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-02-07T08:53:39.437Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582038,0,'CostCalculation_Percentage',TO_TIMESTAMP('2023-02-07 10:53:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Percentage','Percentage',TO_TIMESTAMP('2023-02-07 10:53:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T08:53:39.439Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582038 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T08:54:00.307Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585931,582037,0,12,542296,'CostCalculation_FixedAmount',TO_TIMESTAMP('2023-02-07 10:54:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fixed Amount',0,0,TO_TIMESTAMP('2023-02-07 10:54:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T08:54:00.309Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585931 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T08:54:00.340Z
/* DDL */  select update_Column_Translation_From_AD_Element(582037) 
;

-- 2023-02-07T08:54:01.336Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost','ALTER TABLE public.C_Order_Cost ADD COLUMN CostCalculation_FixedAmount NUMERIC')
;

-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T08:54:26.260Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585932,582038,0,22,542296,'CostCalculation_Percentage',TO_TIMESTAMP('2023-02-07 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Percentage',0,0,TO_TIMESTAMP('2023-02-07 10:54:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T08:54:26.262Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585932 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T08:54:26.265Z
/* DDL */  select update_Column_Translation_From_AD_Element(582038) 
;

-- 2023-02-07T08:54:27.042Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost','ALTER TABLE public.C_Order_Cost ADD COLUMN CostCalculation_Percentage NUMERIC')
;

-- Table: C_Order_Cost
-- 2023-02-07T08:54:44.653Z
UPDATE AD_Table SET AD_Window_ID=541676,Updated=TO_TIMESTAMP('2023-02-07 10:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542296
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T08:55:00.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585931,712219,0,546808,TO_TIMESTAMP('2023-02-07 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Fixed Amount',TO_TIMESTAMP('2023-02-07 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T08:55:00.469Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712219 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T08:55:00.472Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582037) 
;

-- 2023-02-07T08:55:00.484Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712219
;

-- 2023-02-07T08:55:00.490Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712219)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T08:55:00.589Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585932,712220,0,546808,TO_TIMESTAMP('2023-02-07 10:55:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Percentage',TO_TIMESTAMP('2023-02-07 10:55:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T08:55:00.591Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712220 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T08:55:00.592Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582038) 
;

-- 2023-02-07T08:55:00.595Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712220
;

-- 2023-02-07T08:55:00.595Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712220)
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: cost calculation
-- 2023-02-07T08:55:25.873Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546632,550352,TO_TIMESTAMP('2023-02-07 10:55:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','cost calculation',30,TO_TIMESTAMP('2023-02-07 10:55:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> cost calculation.Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-07T08:55:41.140Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550352, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-07 10:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615594
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> cost calculation.Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T08:55:51.600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712219,0,546808,550352,615615,'F',TO_TIMESTAMP('2023-02-07 10:55:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Fixed Amount',20,0,0,TO_TIMESTAMP('2023-02-07 10:55:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> cost calculation.Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T08:56:01.817Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712220,0,546808,550352,615616,'F',TO_TIMESTAMP('2023-02-07 10:56:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Percentage',30,0,0,TO_TIMESTAMP('2023-02-07 10:56:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: cost distribution
-- 2023-02-07T08:56:09.943Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546632,550353,TO_TIMESTAMP('2023-02-07 10:56:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','cost distribution',40,TO_TIMESTAMP('2023-02-07 10:56:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> cost distribution.Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-07T08:56:22.712Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550353, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-07 10:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615595
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: cost calculation
-- 2023-02-07T08:56:40.240Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2023-02-07 10:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550352
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: cost distribution
-- 2023-02-07T08:56:43.707Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-02-07 10:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550353
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: amounts
-- 2023-02-07T08:56:47.938Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2023-02-07 10:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550346
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T08:57:31.451Z
UPDATE AD_Field SET DisplayLogic='@CostCalculationMethod/-@=F',Updated=TO_TIMESTAMP('2023-02-07 10:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712219
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T08:57:35.361Z
UPDATE AD_Field SET DisplayLogic='@CostCalculationMethod/-@=P',Updated=TO_TIMESTAMP('2023-02-07 10:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712220
;

-- Column: C_Order_Cost_Detail.CostAmount
-- 2023-02-07T10:31:27.709Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585933,582031,0,12,542297,'CostAmount',TO_TIMESTAMP('2023-02-07 12:31:27','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cost Amount',0,0,TO_TIMESTAMP('2023-02-07 12:31:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T10:31:27.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585933 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T10:31:27.716Z
/* DDL */  select update_Column_Translation_From_AD_Element(582031) 
;

-- 2023-02-07T10:31:28.605Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost_Detail','ALTER TABLE public.C_Order_Cost_Detail ADD COLUMN CostAmount NUMERIC DEFAULT 0 NOT NULL')
;

-- Table: C_Order_Cost_Detail
-- 2023-02-07T10:31:37.172Z
UPDATE AD_Table SET AD_Window_ID=541676,Updated=TO_TIMESTAMP('2023-02-07 12:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542297
;

-- Field: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> Cost Amount
-- Column: C_Order_Cost_Detail.CostAmount
-- 2023-02-07T10:31:57.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585933,712221,0,546809,TO_TIMESTAMP('2023-02-07 12:31:56','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount',TO_TIMESTAMP('2023-02-07 12:31:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T10:31:57.026Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712221 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T10:31:57.028Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582031) 
;

-- 2023-02-07T10:31:57.032Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712221
;

-- 2023-02-07T10:31:57.033Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712221)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Cost Amount
-- Column: C_Order_Cost_Detail.CostAmount
-- 2023-02-07T10:32:22.508Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712221,0,546809,550348,615617,'F',TO_TIMESTAMP('2023-02-07 12:32:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Amount',70,0,0,TO_TIMESTAMP('2023-02-07 12:32:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost Detail(546809,D) -> main -> 10 -> primary.Cost Amount
-- Column: C_Order_Cost_Detail.CostAmount
-- 2023-02-07T10:32:30.436Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-07 12:32:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615617
;



















-- Tab: Purchase Order_OLD(181,D) -> Order Cost
-- Table: C_Order_Cost
-- 2023-02-07T11:09:45.671Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582030,0,546810,542296,181,'Y',TO_TIMESTAMP('2023-02-07 13:09:45','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Order_Cost','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Order Cost','N',60,0,TO_TIMESTAMP('2023-02-07 13:09:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:45.673Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546810 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-07T11:09:45.702Z
/* DDL */  select update_tab_translation_from_ad_element(582030) 
;

-- 2023-02-07T11:09:45.716Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546810)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-02-07T11:09:48.274Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585892,712222,0,546810,TO_TIMESTAMP('2023-02-07 13:09:48','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-07 13:09:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:48.275Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712222 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:48.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-07T11:09:50.335Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712222
;

-- 2023-02-07T11:09:50.336Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712222)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-02-07T11:09:50.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585893,712223,0,546810,TO_TIMESTAMP('2023-02-07 13:09:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-07 13:09:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:50.450Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712223 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:50.451Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-07T11:09:51.175Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712223
;

-- 2023-02-07T11:09:51.176Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712223)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Active
-- Column: C_Order_Cost.IsActive
-- 2023-02-07T11:09:51.281Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585896,712224,0,546810,TO_TIMESTAMP('2023-02-07 13:09:51','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-07 13:09:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:51.283Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:51.284Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-07T11:09:51.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712224
;

-- 2023-02-07T11:09:51.970Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712224)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Order Cost
-- Column: C_Order_Cost.C_Order_Cost_ID
-- 2023-02-07T11:09:52.070Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585899,712225,0,546810,TO_TIMESTAMP('2023-02-07 13:09:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Cost',TO_TIMESTAMP('2023-02-07 13:09:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.090Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712225 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.093Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582030) 
;

-- 2023-02-07T11:09:52.096Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712225
;

-- 2023-02-07T11:09:52.097Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712225)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-07T11:09:52.196Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585900,712226,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Type',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.197Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712226 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582023) 
;

-- 2023-02-07T11:09:52.202Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712226
;

-- 2023-02-07T11:09:52.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712226)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-07T11:09:52.301Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585901,712227,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Calculation Method',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.303Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712227 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582025) 
;

-- 2023-02-07T11:09:52.313Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712227
;

-- 2023-02-07T11:09:52.313Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712227)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-07T11:09:52.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585902,712228,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Distribution',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.415Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712228 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.417Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582024) 
;

-- 2023-02-07T11:09:52.420Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712228
;

-- 2023-02-07T11:09:52.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712228)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-02-07T11:09:52.540Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585903,712229,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.541Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712229 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.543Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582031) 
;

-- 2023-02-07T11:09:52.547Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712229
;

-- 2023-02-07T11:09:52.548Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712229)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-07T11:09:52.644Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585904,712230,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.646Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712230 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.647Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-07T11:09:52.704Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712230
;

-- 2023-02-07T11:09:52.705Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712230)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-07T11:09:52.817Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585914,712231,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Sales order',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.818Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712231 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.821Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-02-07T11:09:52.856Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712231
;

-- 2023-02-07T11:09:52.857Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712231)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T11:09:52.962Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585931,712232,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Fixed Amount',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:52.964Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712232 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:52.966Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582037) 
;

-- 2023-02-07T11:09:52.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712232
;

-- 2023-02-07T11:09:52.970Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712232)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T11:09:53.067Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585932,712233,0,546810,TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Percentage',TO_TIMESTAMP('2023-02-07 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:09:53.068Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:09:53.070Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582038) 
;

-- 2023-02-07T11:09:53.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712233
;

-- 2023-02-07T11:09:53.075Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712233)
;

-- Tab: Purchase Order_OLD(181,D) -> Order Cost(546810,D)
-- UI Section: main
-- 2023-02-07T11:10:40.191Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546810,545437,TO_TIMESTAMP('2023-02-07 13:10:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-07 13:10:40','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-07T11:10:40.194Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545437 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> main
-- UI Column: 10
-- 2023-02-07T11:10:48.515Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546635,545437,TO_TIMESTAMP('2023-02-07 13:10:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-07 13:10:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-07T11:10:56.575Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546635,550354,TO_TIMESTAMP('2023-02-07 13:10:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-02-07 13:10:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-07T11:12:13.206Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550354
;

-- UI Section: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> main
-- UI Column: 10
-- 2023-02-07T11:12:16.676Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546635
;

-- Tab: Purchase Order_OLD(181,D) -> Order Cost(546810,D)
-- UI Section: main
-- 2023-02-07T11:12:23.230Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545437
;

-- 2023-02-07T11:12:23.235Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545437
;
































-- Tab: Purchase Order_OLD(181,D) -> Order Cost
-- Table: C_Order_Cost
-- 2023-02-07T11:43:13.571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712222
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-02-07T11:43:13.573Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712222
;

-- 2023-02-07T11:43:13.577Z
DELETE FROM AD_Field WHERE AD_Field_ID=712222
;

-- 2023-02-07T11:43:13.584Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712223
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-02-07T11:43:13.588Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712223
;

-- 2023-02-07T11:43:13.592Z
DELETE FROM AD_Field WHERE AD_Field_ID=712223
;

-- 2023-02-07T11:43:13.598Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712224
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Active
-- Column: C_Order_Cost.IsActive
-- 2023-02-07T11:43:13.601Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712224
;

-- 2023-02-07T11:43:13.606Z
DELETE FROM AD_Field WHERE AD_Field_ID=712224
;

-- 2023-02-07T11:43:13.613Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712225
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Order Cost
-- Column: C_Order_Cost.C_Order_Cost_ID
-- 2023-02-07T11:43:13.615Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712225
;

-- 2023-02-07T11:43:13.622Z
DELETE FROM AD_Field WHERE AD_Field_ID=712225
;

-- 2023-02-07T11:43:13.628Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712226
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-07T11:43:13.630Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712226
;

-- 2023-02-07T11:43:13.634Z
DELETE FROM AD_Field WHERE AD_Field_ID=712226
;

-- 2023-02-07T11:43:13.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712227
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-07T11:43:13.644Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712227
;

-- 2023-02-07T11:43:13.647Z
DELETE FROM AD_Field WHERE AD_Field_ID=712227
;

-- 2023-02-07T11:43:13.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712228
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-07T11:43:13.659Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712228
;

-- 2023-02-07T11:43:13.664Z
DELETE FROM AD_Field WHERE AD_Field_ID=712228
;

-- 2023-02-07T11:43:13.671Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712229
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-02-07T11:43:13.674Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712229
;

-- 2023-02-07T11:43:13.678Z
DELETE FROM AD_Field WHERE AD_Field_ID=712229
;

-- 2023-02-07T11:43:13.683Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712230
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-07T11:43:13.687Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712230
;

-- 2023-02-07T11:43:13.691Z
DELETE FROM AD_Field WHERE AD_Field_ID=712230
;

-- 2023-02-07T11:43:13.697Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712231
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-07T11:43:13.699Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712231
;

-- 2023-02-07T11:43:13.703Z
DELETE FROM AD_Field WHERE AD_Field_ID=712231
;

-- 2023-02-07T11:43:13.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712232
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T11:43:13.712Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712232
;

-- 2023-02-07T11:43:13.715Z
DELETE FROM AD_Field WHERE AD_Field_ID=712232
;

-- 2023-02-07T11:43:13.723Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712233
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546810,D) -> Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T11:43:13.725Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712233
;

-- 2023-02-07T11:43:13.728Z
DELETE FROM AD_Field WHERE AD_Field_ID=712233
;

-- 2023-02-07T11:43:13.731Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=546810
;

-- 2023-02-07T11:43:13.735Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=546810
;





















































-- Tab: Purchase Order_OLD(181,D) -> Order Cost
-- Table: C_Order_Cost
-- 2023-02-07T11:45:51.456Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582030,0,546811,542296,181,'Y',TO_TIMESTAMP('2023-02-07 13:45:51','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Order_Cost','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Order Cost','N',10,0,TO_TIMESTAMP('2023-02-07 13:45:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:51.457Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546811 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-07T11:45:51.485Z
/* DDL */  select update_tab_translation_from_ad_element(582030) 
;

-- 2023-02-07T11:45:51.499Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546811)
;

-- 2023-02-07T11:45:51.504Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546811
;

-- 2023-02-07T11:45:51.505Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546811, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 546808
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-02-07T11:45:51.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585892,712234,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:51','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.',0,'Y','N','N','N','N','N','N','Y','N','Client',10,1,1,TO_TIMESTAMP('2023-02-07 13:45:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:51.644Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:51.646Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-07T11:45:51.843Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712234
;

-- 2023-02-07T11:45:51.844Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712234)
;

-- 2023-02-07T11:45:51.847Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712234
;

-- 2023-02-07T11:45:51.848Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712234, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712189
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-02-07T11:45:51.958Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585893,712235,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:51','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.',0,'Y','N','N','N','N','N','N','N','N','Organisation',20,1,1,TO_TIMESTAMP('2023-02-07 13:45:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:51.959Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:51.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-07T11:45:52.105Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712235
;

-- 2023-02-07T11:45:52.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712235)
;

-- 2023-02-07T11:45:52.110Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712235
;

-- 2023-02-07T11:45:52.111Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712235, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712190
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Active
-- Column: C_Order_Cost.IsActive
-- 2023-02-07T11:45:52.220Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585896,712236,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.',0,'Y','N','N','N','N','N','N','N','N','Active',30,1,1,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:52.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:52.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-07T11:45:52.390Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712236
;

-- 2023-02-07T11:45:52.392Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712236)
;

-- 2023-02-07T11:45:52.395Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712236
;

-- 2023-02-07T11:45:52.396Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712236, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712191
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Order Cost
-- Column: C_Order_Cost.C_Order_Cost_ID
-- 2023-02-07T11:45:52.500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585899,712237,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','N','Order Cost',40,1,1,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:52.501Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:52.503Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582030) 
;

-- 2023-02-07T11:45:52.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712237
;

-- 2023-02-07T11:45:52.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712237)
;

-- 2023-02-07T11:45:52.508Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712237
;

-- 2023-02-07T11:45:52.510Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712237, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712192
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-07T11:45:52.607Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585900,712238,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','N','Cost Type',50,1,1,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:52.609Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:52.610Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582023) 
;

-- 2023-02-07T11:45:52.613Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712238
;

-- 2023-02-07T11:45:52.614Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712238)
;

-- 2023-02-07T11:45:52.616Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712238
;

-- 2023-02-07T11:45:52.617Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712238, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712193
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-07T11:45:52.719Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585901,712239,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','N','N','N','N','N','N','N','N','Calculation Method',60,1,1,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:52.721Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712239 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:52.723Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582025) 
;

-- 2023-02-07T11:45:52.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712239
;

-- 2023-02-07T11:45:52.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712239)
;

-- 2023-02-07T11:45:52.733Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712239
;

-- 2023-02-07T11:45:52.734Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712239, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712194
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-07T11:45:52.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585902,712240,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','N','N','N','N','N','N','N','N','Distribution',70,1,1,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:52.834Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712240 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:52.835Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582024) 
;

-- 2023-02-07T11:45:52.838Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712240
;

-- 2023-02-07T11:45:52.838Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712240)
;

-- 2023-02-07T11:45:52.840Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712240
;

-- 2023-02-07T11:45:52.842Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712240, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712195
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-02-07T11:45:52.942Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585903,712241,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D',0,'Y','N','N','N','N','N','N','N','N','Cost Amount',80,1,1,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:52.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712241 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:52.945Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582031) 
;

-- 2023-02-07T11:45:52.950Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712241
;

-- 2023-02-07T11:45:52.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712241)
;

-- 2023-02-07T11:45:52.957Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712241
;

-- 2023-02-07T11:45:52.959Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712241, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712196
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-07T11:45:53.052Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585904,712242,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record',0,'Y','N','N','N','N','N','N','N','N','Currency',90,1,1,TO_TIMESTAMP('2023-02-07 13:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:53.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712242 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:53.055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-07T11:45:53.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712242
;

-- 2023-02-07T11:45:53.074Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712242)
;

-- 2023-02-07T11:45:53.077Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712242
;

-- 2023-02-07T11:45:53.078Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712242, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712197
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-07T11:45:53.182Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585914,712243,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.',0,'Y','N','N','N','N','N','N','N','N','Sales order',100,1,1,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:53.184Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:53.185Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-02-07T11:45:53.198Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712243
;

-- 2023-02-07T11:45:53.199Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712243)
;

-- 2023-02-07T11:45:53.201Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712243
;

-- 2023-02-07T11:45:53.202Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712243, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712198
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T11:45:53.295Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585931,712244,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,10,'@CostCalculationMethod/-@=F','D',0,'Y','N','N','N','N','N','N','N','N','Fixed Amount',110,1,1,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:53.297Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712244 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:53.298Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582037) 
;

-- 2023-02-07T11:45:53.301Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712244
;

-- 2023-02-07T11:45:53.301Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712244)
;

-- 2023-02-07T11:45:53.303Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712244
;

-- 2023-02-07T11:45:53.304Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712244, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712219
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T11:45:53.401Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585932,712245,0,546811,0,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,10,'@CostCalculationMethod/-@=P','D',0,'Y','N','N','N','N','N','N','N','N','Percentage',120,1,1,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T11:45:53.404Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T11:45:53.407Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582038) 
;

-- 2023-02-07T11:45:53.412Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712245
;

-- 2023-02-07T11:45:53.413Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712245)
;

-- 2023-02-07T11:45:53.416Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712245
;

-- 2023-02-07T11:45:53.418Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712245, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712220
;

-- Tab: Purchase Order_OLD(181,D) -> Order Cost(546811,D)
-- UI Section: main
-- 2023-02-07T11:45:53.557Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546811,545438,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-07T11:45:53.559Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545438 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-02-07T11:45:53.561Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545438
;

-- 2023-02-07T11:45:53.563Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545438, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545435
;

-- UI Section: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main
-- UI Column: 10
-- 2023-02-07T11:45:53.720Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546636,545438,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10
-- UI Element Group: primary
-- 2023-02-07T11:45:53.863Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546636,550355,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> primary.Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-07T11:45:54.028Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712243,0,546811,550355,615618,'F',TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','Y','N','N','Sales order',10,10,0,TO_TIMESTAMP('2023-02-07 13:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> primary.Cost Type
-- Column: C_Order_Cost.C_Cost_Type_ID
-- 2023-02-07T11:45:54.133Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712238,0,546811,550355,615619,'F',TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Cost Type',20,20,0,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10
-- UI Element Group: cost calculation
-- 2023-02-07T11:45:54.227Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546636,550356,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','cost calculation',20,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> cost calculation.Calculation Method
-- Column: C_Order_Cost.CostCalculationMethod
-- 2023-02-07T11:45:54.350Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712239,0,546811,550356,615620,'F',TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Calculation Method',10,0,0,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> cost calculation.Fixed Amount
-- Column: C_Order_Cost.CostCalculation_FixedAmount
-- 2023-02-07T11:45:54.453Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712244,0,546811,550356,615621,'F',TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Fixed Amount',20,0,0,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> cost calculation.Percentage
-- Column: C_Order_Cost.CostCalculation_Percentage
-- 2023-02-07T11:45:54.559Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712245,0,546811,550356,615622,'F',TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Percentage',30,0,0,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10
-- UI Element Group: cost distribution
-- 2023-02-07T11:45:54.659Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546636,550357,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','cost distribution',30,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> cost distribution.Distribution
-- Column: C_Order_Cost.CostDistributionMethod
-- 2023-02-07T11:45:54.779Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712240,0,546811,550357,615623,'F',TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Distribution',10,0,0,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10
-- UI Element Group: amounts
-- 2023-02-07T11:45:54.885Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546636,550358,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts',40,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> amounts.Cost Amount
-- Column: C_Order_Cost.CostAmount
-- 2023-02-07T11:45:54.983Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712241,0,546811,550358,615624,'F',TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Cost Amount',10,40,0,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> amounts.Currency
-- Column: C_Order_Cost.C_Currency_ID
-- 2023-02-07T11:45:55.096Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712242,0,546811,550358,615625,'F',TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','Y','Y','N','N','Currency',20,30,0,TO_TIMESTAMP('2023-02-07 13:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main
-- UI Column: 20
-- 2023-02-07T11:45:55.195Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546637,545438,TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 20
-- UI Element Group: org and client
-- 2023-02-07T11:45:55.296Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546637,550359,TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','org and client',10,TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 20 -> org and client.Organisation
-- Column: C_Order_Cost.AD_Org_ID
-- 2023-02-07T11:45:55.401Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712235,0,546811,550359,615626,'F',TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 20 -> org and client.Client
-- Column: C_Order_Cost.AD_Client_ID
-- 2023-02-07T11:45:55.521Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712234,0,546811,550359,615627,'F',TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N','Client',20,0,0,TO_TIMESTAMP('2023-02-07 13:45:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Purchase Order_OLD(181,D) -> Order Cost
-- Table: C_Order_Cost
-- 2023-02-07T11:46:28.009Z
UPDATE AD_Tab SET AD_Column_ID=585914, Parent_Column_ID=2161, SeqNo=60, TabLevel=1,Updated=TO_TIMESTAMP('2023-02-07 13:46:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546811
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> primary.Sales order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-02-07T11:48:55.181Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615618
;






























