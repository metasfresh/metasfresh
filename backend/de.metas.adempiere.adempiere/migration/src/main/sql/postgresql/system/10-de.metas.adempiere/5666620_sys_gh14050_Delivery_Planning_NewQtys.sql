

-- Element: ActualLoadQty
-- 2022-11-29T08:27:23.922Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Actual Loaded Quantity', PrintName='Actual Loaded Quantity',Updated=TO_TIMESTAMP('2022-11-29 10:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581690 AND AD_Language='en_US'
;

-- 2022-11-29T08:27:24.082Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581690,'en_US') 
;

-- 2022-11-29T08:27:24.116Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581690,'en_US') 
;

-- 2022-11-29T08:28:31.661Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581794,0,'PlannedLoadedQuantity',TO_TIMESTAMP('2022-11-29 10:28:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Planned Loaded Quantity','Planned Loaded Quantity',TO_TIMESTAMP('2022-11-29 10:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-29T08:28:31.695Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581794 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-29T08:28:54.646Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581795,0,'PlannedDischargeQuantity',TO_TIMESTAMP('2022-11-29 10:28:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Planned Discharge Quantity','Planned Discharge Quantity',TO_TIMESTAMP('2022-11-29 10:28:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-29T08:28:54.675Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581795 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-29T08:29:12.702Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581796,0,'ActualDischargeQuantity',TO_TIMESTAMP('2022-11-29 10:29:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Actual Discharge Quantity','Actual Discharge Quantity',TO_TIMESTAMP('2022-11-29 10:29:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-29T08:29:12.795Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581796 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2022-11-29T08:29:55.838Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585215,581794,0,29,542259,'PlannedLoadedQuantity',TO_TIMESTAMP('2022-11-29 10:29:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Planned Loaded Quantity',0,0,TO_TIMESTAMP('2022-11-29 10:29:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-29T08:29:55.869Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585215 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-29T08:29:55.929Z
/* DDL */  select update_Column_Translation_From_AD_Element(581794) 
;

-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2022-11-29T08:30:40.879Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-11-29 10:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585215
;

-- -- 2022-11-29T08:30:51.955Z
-- /* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN PlannedLoadedQuantity NUMERIC NOT NULL')
-- ;

-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2022-11-29T08:31:17.387Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585216,581795,0,29,542259,'PlannedDischargeQuantity',TO_TIMESTAMP('2022-11-29 10:31:16','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Planned Discharge Quantity',0,0,TO_TIMESTAMP('2022-11-29 10:31:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-29T08:31:17.418Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585216 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-29T08:31:17.481Z
/* DDL */  select update_Column_Translation_From_AD_Element(581795) 
;

-- -- 2022-11-29T08:31:56.964Z
-- /* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN PlannedDischargeQuantity NUMERIC NOT NULL')
-- ;

-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2022-11-29T08:32:18.416Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585217,581796,0,29,542259,'ActualDischargeQuantity',TO_TIMESTAMP('2022-11-29 10:32:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Actual Discharge Quantity',0,0,TO_TIMESTAMP('2022-11-29 10:32:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-29T08:32:18.446Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585217 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-29T08:32:18.508Z
/* DDL */  select update_Column_Translation_From_AD_Element(581796) 
;

-- -- 2022-11-29T08:32:24.457Z
-- /* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN ActualDischargeQuantity NUMERIC NOT NULL')
-- ;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Planned Loaded Quantity
-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2022-11-29T08:33:48.696Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585215,708278,0,546674,TO_TIMESTAMP('2022-11-29 10:33:48','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','Planned Loaded Quantity',TO_TIMESTAMP('2022-11-29 10:33:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-29T08:33:48.727Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708278 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-29T08:33:48.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581794) 
;

-- 2022-11-29T08:33:48.800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708278
;

-- 2022-11-29T08:33:48.831Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708278)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Planned Discharge Quantity
-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2022-11-29T08:33:49.332Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585216,708279,0,546674,TO_TIMESTAMP('2022-11-29 10:33:48','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','Planned Discharge Quantity',TO_TIMESTAMP('2022-11-29 10:33:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-29T08:33:49.361Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708279 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-29T08:33:49.392Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581795) 
;

-- 2022-11-29T08:33:49.424Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708279
;

-- 2022-11-29T08:33:49.454Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708279)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Actual Discharge Quantity
-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2022-11-29T08:33:49.998Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585217,708280,0,546674,TO_TIMESTAMP('2022-11-29 10:33:49','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','Actual Discharge Quantity',TO_TIMESTAMP('2022-11-29 10:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-29T08:33:50.027Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708280 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-29T08:33:50.057Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581796) 
;

-- 2022-11-29T08:33:50.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708280
;

-- 2022-11-29T08:33:50.120Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708280)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Loaded Quantity
-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2022-11-29T08:34:23.370Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708278,0,546674,550032,613647,'F',TO_TIMESTAMP('2022-11-29 10:34:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Planned Loaded Quantity',25,0,0,TO_TIMESTAMP('2022-11-29 10:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Discharge Quantity
-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2022-11-29T08:34:39.183Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708279,0,546674,550032,613648,'F',TO_TIMESTAMP('2022-11-29 10:34:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Planned Discharge Quantity',60,0,0,TO_TIMESTAMP('2022-11-29 10:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Discharge Quantity
-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2022-11-29T08:34:52.174Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708280,0,546674,550032,613649,'F',TO_TIMESTAMP('2022-11-29 10:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Actual Discharge Quantity',70,0,0,TO_TIMESTAMP('2022-11-29 10:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2022-11-29T08:36:45.443Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2022-11-29 10:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585217
;

-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-11-29T08:37:03.878Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2022-11-29 10:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585028
;

-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2022-11-29T08:37:16.960Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2022-11-29 10:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585215
;

-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2022-11-29T08:37:27.950Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2022-11-29 10:37:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585216
;

-- -- 2022-11-29T08:37:32.731Z
-- INSERT INTO t_alter_column values('m_delivery_planning','PlannedDischargeQuantity','NUMERIC',null,'0')
-- ;

-- -- 2022-11-29T08:37:32.766Z
-- UPDATE M_Delivery_Planning SET PlannedDischargeQuantity=0 WHERE PlannedDischargeQuantity IS NULL
-- ;

-- -- 2022-11-29T08:37:45.103Z
-- INSERT INTO t_alter_column values('m_delivery_planning','PlannedLoadedQuantity','NUMERIC',null,'0')
-- ;

-- -- 2022-11-29T08:37:45.137Z
-- UPDATE M_Delivery_Planning SET PlannedLoadedQuantity=0 WHERE PlannedLoadedQuantity IS NULL
-- ;

-- 2022-11-29T08:37:59.251Z
INSERT INTO t_alter_column values('m_delivery_planning','ActualLoadQty','NUMERIC',null,'0')
;

-- 2022-11-29T08:37:59.284Z
UPDATE M_Delivery_Planning SET ActualLoadQty=0 WHERE ActualLoadQty IS NULL
;

-- -- 2022-11-29T08:38:10.712Z
-- INSERT INTO t_alter_column values('m_delivery_planning','ActualDischargeQuantity','NUMERIC',null,'0')
-- ;

-- -- 2022-11-29T08:38:10.745Z
-- UPDATE M_Delivery_Planning SET ActualDischargeQuantity=0 WHERE ActualDischargeQuantity IS NULL
-- ;

-- 2022-11-29T08:41:19.775Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN PlannedDischargeQuantity NUMERIC DEFAULT 0 NOT NULL')
;

-- 2022-11-29T08:41:28.159Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN PlannedLoadedQuantity NUMERIC DEFAULT 0 NOT NULL')
;

-- 2022-11-29T08:41:50.659Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN ActualDischargeQuantity NUMERIC DEFAULT 0 NOT NULL')
;



-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.UOM
-- Column: M_Delivery_Planning.C_UOM_ID
-- 2022-11-29T09:56:42.681Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2022-11-29 11:56:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613566
;



