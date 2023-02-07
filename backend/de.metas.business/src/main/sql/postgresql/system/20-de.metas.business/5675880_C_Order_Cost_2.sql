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

