-- 2023-02-15T16:23:15.587Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582072,0,'CostAmountInvoiced',TO_TIMESTAMP('2023-02-15 18:23:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Amount Invoiced','Cost Amount Invoiced',TO_TIMESTAMP('2023-02-15 18:23:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T16:23:15.594Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582072 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InOut_Cost.CostAmountInvoiced
-- 2023-02-15T16:23:38.699Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586053,582072,0,12,542299,'CostAmountInvoiced',TO_TIMESTAMP('2023-02-15 18:23:38','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cost Amount Invoiced',0,0,TO_TIMESTAMP('2023-02-15 18:23:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-15T16:23:38.700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586053 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-15T16:23:38.731Z
/* DDL */  select update_Column_Translation_From_AD_Element(582072) 
;

-- 2023-02-15T16:23:39.749Z
/* DDL */ SELECT public.db_alter_table('M_InOut_Cost','ALTER TABLE public.M_InOut_Cost ADD COLUMN CostAmountInvoiced NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Cost Amount Invoiced
-- Column: M_InOut_Cost.CostAmountInvoiced
-- 2023-02-15T16:24:08.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586053,712604,0,546813,TO_TIMESTAMP('2023-02-15 18:24:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount Invoiced',TO_TIMESTAMP('2023-02-15 18:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T16:24:08.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712604 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T16:24:08.361Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582072) 
;

-- 2023-02-15T16:24:08.373Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712604
;

-- 2023-02-15T16:24:08.375Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712604)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Cost Amount Invoiced
-- Column: M_InOut_Cost.CostAmountInvoiced
-- 2023-02-15T16:24:44.223Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712604,0,546813,550366,615839,'F',TO_TIMESTAMP('2023-02-15 18:24:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Amount Invoiced',50,0,0,TO_TIMESTAMP('2023-02-15 18:24:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Cost Amount Invoiced
-- Column: M_InOut_Cost.CostAmountInvoiced
-- 2023-02-15T16:24:55.626Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-02-15 18:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615839
;

-- Column: M_InOut_Cost.IsInvoiced
-- 2023-02-15T17:00:52.289Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586054,387,0,20,542299,'IsInvoiced',TO_TIMESTAMP('2023-02-15 19:00:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Is this invoiced?','D',0,1,'If selected, invoices are created','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Invoiced',0,0,TO_TIMESTAMP('2023-02-15 19:00:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-15T17:00:52.297Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586054 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-15T17:00:52.302Z
/* DDL */  select update_Column_Translation_From_AD_Element(387) 
;

-- 2023-02-15T17:00:53.113Z
/* DDL */ SELECT public.db_alter_table('M_InOut_Cost','ALTER TABLE public.M_InOut_Cost ADD COLUMN IsInvoiced CHAR(1) DEFAULT ''N'' CHECK (IsInvoiced IN (''Y'',''N'')) NOT NULL')
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Invoiced
-- Column: M_InOut_Cost.IsInvoiced
-- 2023-02-15T17:04:27.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586054,712605,0,546813,TO_TIMESTAMP('2023-02-15 19:04:27','YYYY-MM-DD HH24:MI:SS'),100,'Is this invoiced?',1,'D','If selected, invoices are created','Y','N','N','N','N','N','N','N','Invoiced',TO_TIMESTAMP('2023-02-15 19:04:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T17:04:27.829Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712605 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T17:04:27.831Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(387) 
;

-- 2023-02-15T17:04:27.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712605
;

-- 2023-02-15T17:04:27.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712605)
;

-- UI Column: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20
-- UI Element Group: invoicing
-- 2023-02-15T17:05:04.611Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546641,550395,TO_TIMESTAMP('2023-02-15 19:05:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','invoicing',20,TO_TIMESTAMP('2023-02-15 19:05:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20
-- UI Element Group: org & client
-- 2023-02-15T17:05:06.754Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-02-15 19:05:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550367
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> invoicing.Cost Amount Invoiced
-- Column: M_InOut_Cost.CostAmountInvoiced
-- 2023-02-15T17:05:21.363Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550395, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-15 19:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615839
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> invoicing.Invoiced
-- Column: M_InOut_Cost.IsInvoiced
-- 2023-02-15T17:05:34.455Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712605,0,546813,550395,615840,'F',TO_TIMESTAMP('2023-02-15 19:05:34','YYYY-MM-DD HH24:MI:SS'),100,'Is this invoiced?','If selected, invoices are created','Y','N','Y','N','N','Invoiced',20,0,0,TO_TIMESTAMP('2023-02-15 19:05:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_InOut_Cost.IsInvoiced
-- 2023-02-15T17:06:21.288Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-15 19:06:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586054
;

-- Column: M_InOut_Cost.C_Order_ID
-- 2023-02-15T17:06:37.783Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-15 19:06:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585949
;

-- Column: M_InOut_Cost.M_InOut_ID
-- 2023-02-15T17:06:57.876Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-15 19:06:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585945
;

-- Column: M_InOut_Cost.Reversal_ID
-- 2023-02-15T17:07:16.416Z
UPDATE AD_Column SET FilterOperator='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-15 19:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585955
;

-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-15T17:07:34.168Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-15 19:07:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585958
;

-- Column: M_InOut_Cost.C_Cost_Type_ID
-- 2023-02-15T17:07:44.873Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-15 19:07:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585959
;

