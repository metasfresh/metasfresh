-- 2023-03-13T08:05:10.857Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582132,0,'IsAllowInvoicing',TO_TIMESTAMP('2023-03-13 10:05:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Allow Invoicing','Allow Invoicing',TO_TIMESTAMP('2023-03-13 10:05:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:05:10.861Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582132 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Cost_Type.IsAllowInvoicing
-- 2023-03-13T08:05:26.131Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586305,582132,0,20,542294,'IsAllowInvoicing',TO_TIMESTAMP('2023-03-13 10:05:25','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow Invoicing',0,0,TO_TIMESTAMP('2023-03-13 10:05:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-13T08:05:26.134Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586305 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-13T08:05:26.140Z
/* DDL */  select update_Column_Translation_From_AD_Element(582132) 
;

-- 2023-03-13T08:05:27.756Z
/* DDL */ SELECT public.db_alter_table('C_Cost_Type','ALTER TABLE public.C_Cost_Type ADD COLUMN IsAllowInvoicing CHAR(1) DEFAULT ''N'' CHECK (IsAllowInvoicing IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_Cost_Type.M_Product_ID
-- 2023-03-13T08:06:19.009Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586306,454,0,30,542294,'M_Product_ID',TO_TIMESTAMP('2023-03-13 10:06:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Product, Service, Item','D',0,10,'Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Product',0,0,TO_TIMESTAMP('2023-03-13 10:06:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-13T08:06:19.011Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586306 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-13T08:06:19.013Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2023-03-13T08:06:22.591Z
/* DDL */ SELECT public.db_alter_table('C_Cost_Type','ALTER TABLE public.C_Cost_Type ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2023-03-13T08:06:22.599Z
ALTER TABLE C_Cost_Type ADD CONSTRAINT MProduct_CCostType FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Allow Invoicing
-- Column: C_Cost_Type.IsAllowInvoicing
-- 2023-03-13T08:06:40.519Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586305,712835,0,546807,TO_TIMESTAMP('2023-03-13 10:06:40','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Allow Invoicing',TO_TIMESTAMP('2023-03-13 10:06:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:06:40.523Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-13T08:06:40.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582132) 
;

-- 2023-03-13T08:06:40.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712835
;

-- 2023-03-13T08:06:40.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712835)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Product
-- Column: C_Cost_Type.M_Product_ID
-- 2023-03-13T08:06:40.658Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586306,712836,0,546807,TO_TIMESTAMP('2023-03-13 10:06:40','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item',10,'D','Identifies an item which is either purchased or sold in this organization.','Y','N','N','N','N','N','N','N','Product',TO_TIMESTAMP('2023-03-13 10:06:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:06:40.660Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-13T08:06:40.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-03-13T08:06:40.699Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712836
;

-- 2023-03-13T08:06:40.700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712836)
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Product
-- Column: C_Cost_Type.M_Product_ID
-- 2023-03-13T08:07:30.228Z
UPDATE AD_Field SET DisplayLogic='@IsAllowInvoicing/X@=Y', IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2023-03-13 10:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712836
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Product
-- Column: C_Cost_Type.M_Product_ID
-- 2023-03-13T08:07:34.857Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2023-03-13 10:07:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712836
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Product
-- Column: C_Cost_Type.M_Product_ID
-- 2023-03-13T08:07:38.119Z
UPDATE AD_Field SET IsAlwaysUpdateable='N',Updated=TO_TIMESTAMP('2023-03-13 10:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712836
;

-- Column: C_Cost_Type.M_Product_ID
-- 2023-03-13T08:07:49.154Z
UPDATE AD_Column SET MandatoryLogic='@IsAllowInvoicing/X@=Y',Updated=TO_TIMESTAMP('2023-03-13 10:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586306
;

-- UI Column: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20
-- UI Element Group: org&client
-- 2023-03-13T08:08:31.957Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-03-13 10:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550344
;

-- UI Column: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20
-- UI Element Group: invoicing
-- 2023-03-13T08:08:40.924Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546631,550438,TO_TIMESTAMP('2023-03-13 10:08:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','invoicing',20,TO_TIMESTAMP('2023-03-13 10:08:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20 -> invoicing.Allow Invoicing
-- Column: C_Cost_Type.IsAllowInvoicing
-- 2023-03-13T08:08:53.006Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712835,0,546807,550438,616010,'F',TO_TIMESTAMP('2023-03-13 10:08:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Allow Invoicing',10,0,0,TO_TIMESTAMP('2023-03-13 10:08:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 20 -> invoicing.Product
-- Column: C_Cost_Type.M_Product_ID
-- 2023-03-13T08:08:58.932Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712836,0,546807,550438,616011,'F',TO_TIMESTAMP('2023-03-13 10:08:58','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item','Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','Product',20,0,0,TO_TIMESTAMP('2023-03-13 10:08:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: IsAllowInvoicing
-- 2023-03-13T08:12:50.964Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,582132,0,585214,542571,20,'IsAllowInvoicing',TO_TIMESTAMP('2023-03-13 10:12:50','YYYY-MM-DD HH24:MI:SS'),100,'N','1=0','D',1,'Y','N','Y','N','Y','N','Allow Invoicing','1=1',50,TO_TIMESTAMP('2023-03-13 10:12:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:12:50.967Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542571 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-03-13T08:12:50.969Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582132) 
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: IsInvoiced
-- 2023-03-13T08:14:39.556Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,387,0,585214,542572,20,'IsInvoiced',TO_TIMESTAMP('2023-03-13 10:14:39','YYYY-MM-DD HH24:MI:SS'),100,'Is this invoiced?','@IsAllowInvoicing/X@=Y','D',0,'If selected, invoices are created','Y','N','Y','N','N','N','Invoiced',60,TO_TIMESTAMP('2023-03-13 10:14:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:14:39.558Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542572 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-03-13T08:14:39.559Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(387) 
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: M_Product_ID
-- 2023-03-13T08:15:31.802Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585214,542573,30,'M_Product_ID',TO_TIMESTAMP('2023-03-13 10:15:31','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item','@IsInvoiced/X@=Y','D',10,'Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','N','Product',70,TO_TIMESTAMP('2023-03-13 10:15:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:15:31.804Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542573 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-03-13T08:15:31.805Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(454) 
;

-- 2023-03-13T08:56:52.320Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582134,0,'Created_OrderLine_ID',TO_TIMESTAMP('2023-03-13 10:56:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Created Order Line','Created Order Line',TO_TIMESTAMP('2023-03-13 10:56:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:56:52.323Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582134 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order_Cost.Created_OrderLine_ID
-- 2023-03-13T08:57:08.771Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586307,582134,0,30,271,542296,'Created_OrderLine_ID',TO_TIMESTAMP('2023-03-13 10:57:08','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Created Order Line',0,0,TO_TIMESTAMP('2023-03-13 10:57:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-13T08:57:08.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586307 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-13T08:57:08.782Z
/* DDL */  select update_Column_Translation_From_AD_Element(582134) 
;

-- 2023-03-13T08:57:09.575Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost','ALTER TABLE public.C_Order_Cost ADD COLUMN Created_OrderLine_ID NUMERIC(10)')
;

-- 2023-03-13T08:57:09.583Z
ALTER TABLE C_Order_Cost ADD CONSTRAINT CreatedOrderLine_COrderCost FOREIGN KEY (Created_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Created Order Line
-- Column: C_Order_Cost.Created_OrderLine_ID
-- 2023-03-13T08:57:30.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586307,712837,0,546808,TO_TIMESTAMP('2023-03-13 10:57:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Created Order Line',TO_TIMESTAMP('2023-03-13 10:57:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T08:57:30.498Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-13T08:57:30.500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582134) 
;

-- 2023-03-13T08:57:30.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712837
;

-- 2023-03-13T08:57:30.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712837)
;

-- UI Column: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10
-- UI Element Group: created order line
-- 2023-03-13T08:58:17.439Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546632,550439,TO_TIMESTAMP('2023-03-13 10:58:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','created order line',50,TO_TIMESTAMP('2023-03-13 10:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> created order line.Created Order Line
-- Column: C_Order_Cost.Created_OrderLine_ID
-- 2023-03-13T08:58:26.336Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712837,0,546808,550439,616012,'F',TO_TIMESTAMP('2023-03-13 10:58:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Created Order Line',10,0,0,TO_TIMESTAMP('2023-03-13 10:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Created Order Line
-- Column: C_Order_Cost.Created_OrderLine_ID
-- 2023-03-13T08:59:03.987Z
UPDATE AD_Field SET DisplayLogic='@Created_OrderLine_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-13 10:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712837
;

