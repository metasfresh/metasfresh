-- 2022-06-16T15:48:45.581Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581040,0,'IsSyncStockToShopware6',TO_TIMESTAMP('2022-06-16 18:48:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsSyncStockToShopware6','IsSyncStockToShopware6',TO_TIMESTAMP('2022-06-16 18:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-16T15:48:45.590Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581040 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-06-16T15:49:21.202Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583380,581040,0,20,541585,'IsSyncStockToShopware6',TO_TIMESTAMP('2022-06-16 18:49:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'IsSyncStockToShopware6',0,0,TO_TIMESTAMP('2022-06-16 18:49:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-16T15:49:21.205Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583380 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-16T15:49:21.244Z
/* DDL */  select update_Column_Translation_From_AD_Element(581040) 
;

-- 2022-06-16T15:49:23.450Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN IsSyncStockToShopware6 CHAR(1) DEFAULT ''N'' CHECK (IsSyncStockToShopware6 IN (''Y'',''N'')) NOT NULL')
;

-- Field: External System Config -> Shopware6 -> IsSyncStockToShopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-06-16T15:51:00.925Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583380,700722,0,543435,TO_TIMESTAMP('2022-06-16 18:51:00','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','IsSyncStockToShopware6',TO_TIMESTAMP('2022-06-16 18:51:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-16T15:51:00.929Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700722 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-16T15:51:00.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581040) 
;

-- 2022-06-16T15:51:00.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700722
;

-- 2022-06-16T15:51:00.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700722)
;

-- UI Element: External System Config -> Shopware6.IsSyncStockToShopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-06-16T15:51:35.540Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700722,0,543435,609602,544976,'F',TO_TIMESTAMP('2022-06-16 18:51:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsSyncStockToShopware6',20,0,0,TO_TIMESTAMP('2022-06-16 18:51:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External System Config -> Shopware6.IsSyncStockToShopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-06-16T15:51:58.022Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-06-16 18:51:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609602
;

-- Field: External system config Shopware 6 -> External system config Shopware6 -> IsSyncStockToShopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-06-16T15:52:21.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583380,700723,0,543838,TO_TIMESTAMP('2022-06-16 18:52:20','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','IsSyncStockToShopware6',TO_TIMESTAMP('2022-06-16 18:52:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-16T15:52:21.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700723 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-16T15:52:21.103Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581040) 
;

-- 2022-06-16T15:52:21.109Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700723
;

-- 2022-06-16T15:52:21.114Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(700723)
;

-- UI Element: External system config Shopware 6 -> External system config Shopware6.IsSyncStockToShopware6
-- Column: ExternalSystem_Config_Shopware6.IsSyncStockToShopware6
-- 2022-06-16T15:52:37.367Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,700723,0,543838,609603,545679,'F',TO_TIMESTAMP('2022-06-16 18:52:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsSyncStockToShopware6',20,0,0,TO_TIMESTAMP('2022-06-16 18:52:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Available for sales -> Available for sales
-- Table: MD_Available_For_Sales
-- 2022-06-16T15:53:54.892Z
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-16 18:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546323
;

-- Column: MD_Available_For_Sales.M_Product_ID
-- 2022-06-16T15:55:06.844Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-06-16 18:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583301
;
