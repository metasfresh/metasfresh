-- 2023-03-10T09:38:30.175Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582127,0,'IsAllowOnSales',TO_TIMESTAMP('2023-03-10 11:38:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Allow on Sales','Allow on Sales',TO_TIMESTAMP('2023-03-10 11:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-10T09:38:30.181Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582127 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-10T09:38:46.599Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582128,0,'IsAllowOnPurchase',TO_TIMESTAMP('2023-03-10 11:38:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Allow on Purchase','Allow on Purchase',TO_TIMESTAMP('2023-03-10 11:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-10T09:38:46.600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582128 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Cost_Type.IsAllowOnPurchase
-- 2023-03-10T09:40:44.635Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586294,582128,0,20,542294,'IsAllowOnPurchase',TO_TIMESTAMP('2023-03-10 11:40:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow on Purchase',0,0,TO_TIMESTAMP('2023-03-10 11:40:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-10T09:40:44.638Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586294 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-10T09:40:44.680Z
/* DDL */  select update_Column_Translation_From_AD_Element(582128) 
;

-- 2023-03-10T09:40:45.947Z
/* DDL */ SELECT public.db_alter_table('C_Cost_Type','ALTER TABLE public.C_Cost_Type ADD COLUMN IsAllowOnPurchase CHAR(1) DEFAULT ''Y'' CHECK (IsAllowOnPurchase IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_Cost_Type.IsAllowOnSales
-- 2023-03-10T09:41:00.191Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586295,582127,0,20,542294,'IsAllowOnSales',TO_TIMESTAMP('2023-03-10 11:41:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow on Sales',0,0,TO_TIMESTAMP('2023-03-10 11:41:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-10T09:41:00.193Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586295 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-10T09:41:00.197Z
/* DDL */  select update_Column_Translation_From_AD_Element(582127) 
;

-- 2023-03-10T09:41:01.046Z
/* DDL */ SELECT public.db_alter_table('C_Cost_Type','ALTER TABLE public.C_Cost_Type ADD COLUMN IsAllowOnSales CHAR(1) DEFAULT ''Y'' CHECK (IsAllowOnSales IN (''Y'',''N'')) NOT NULL')
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Allow on Purchase
-- Column: C_Cost_Type.IsAllowOnPurchase
-- 2023-03-10T09:41:21.788Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586294,712824,0,546807,TO_TIMESTAMP('2023-03-10 11:41:21','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Allow on Purchase',TO_TIMESTAMP('2023-03-10 11:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-10T09:41:21.790Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-10T09:41:21.792Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582128) 
;

-- 2023-03-10T09:41:21.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712824
;

-- 2023-03-10T09:41:21.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712824)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Allow on Sales
-- Column: C_Cost_Type.IsAllowOnSales
-- 2023-03-10T09:41:21.931Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586295,712825,0,546807,TO_TIMESTAMP('2023-03-10 11:41:21','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Allow on Sales',TO_TIMESTAMP('2023-03-10 11:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-10T09:41:21.932Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-10T09:41:21.934Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582127) 
;

-- 2023-03-10T09:41:21.937Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712825
;

-- 2023-03-10T09:41:21.938Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712825)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20 -> flags.Allow on Purchase
-- Column: C_Cost_Type.IsAllowOnPurchase
-- 2023-03-10T09:41:52.681Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712824,0,546807,550343,615999,'F',TO_TIMESTAMP('2023-03-10 11:41:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Allow on Purchase',20,0,0,TO_TIMESTAMP('2023-03-10 11:41:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20 -> flags.Allow on Sales
-- Column: C_Cost_Type.IsAllowOnSales
-- 2023-03-10T09:41:59.346Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712825,0,546807,550343,616000,'F',TO_TIMESTAMP('2023-03-10 11:41:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Allow on Sales',30,0,0,TO_TIMESTAMP('2023-03-10 11:41:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20 -> flags.Active
-- Column: C_Cost_Type.IsActive
-- 2023-03-10T09:42:06.942Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-03-10 11:42:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615589
;

-- Name: C_Cost_Type filtered by IsSOTrx
-- 2023-03-10T09:56:23.979Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540628,'(''@IsSOTrx/X@''=''N'' and C_Cost_Type.IsAllowOnPurchase=''Y'') or (''@IsSOTrx/X@''=''Y'' and C_Cost_Type.IsAllowOnSales=''Y'')',TO_TIMESTAMP('2023-03-10 11:56:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Cost_Type filtered by IsSOTrx','S',TO_TIMESTAMP('2023-03-10 11:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: C_Cost_Type_ID
-- 2023-03-10T09:56:40.716Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540628,Updated=TO_TIMESTAMP('2023-03-10 11:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542533
;

