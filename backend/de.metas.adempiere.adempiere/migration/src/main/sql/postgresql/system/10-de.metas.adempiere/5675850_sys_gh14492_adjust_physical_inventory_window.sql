-- Column: M_Inventory.DocSubType
-- 2023-02-07T07:22:10.440Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,585928,1018,0,10,321,'DocSubType','(SELECT docsubtype from c_doctype d where d.c_doctype_id = M_Inventory.c_doctype_id)',TO_TIMESTAMP('2023-02-07 08:22:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Document Sub Type','D',0,3,'The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Doc Sub Type',0,0,'used to display some elements only in shortage and overage documents',TO_TIMESTAMP('2023-02-07 08:22:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T07:22:10.443Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T07:22:10.472Z
/* DDL */  select update_Column_Translation_From_AD_Element(1018) 
;

-- Column: M_Inventory.DocSubType
-- Source Table: C_DocType
-- 2023-02-07T07:24:57.761Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585928,0,540119,321,TO_TIMESTAMP('2023-02-07 08:24:57','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',1501,3392,217,TO_TIMESTAMP('2023-02-07 08:24:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T07:32:26.071Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582036,0,'C_Order_ID_Purchase',TO_TIMESTAMP('2023-02-07 08:32:25','YYYY-MM-DD HH24:MI:SS'),100,'Purchase order number','U','Y','PO No.','PO No.',TO_TIMESTAMP('2023-02-07 08:32:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T07:32:26.075Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582036 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-02-07T07:32:53.111Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-07 08:32:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582036
;

-- 2023-02-07T07:32:53.114Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582036,'de_DE') 
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- 2023-02-07T07:47:29.141Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,585929,582036,0,10,321,'C_Order_ID_Purchase','(SELECT DISTINCT dp.c_order_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inout io on (io.m_inout_id = hua.record_id) LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)',TO_TIMESTAMP('2023-02-07 08:47:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Purchase order number','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'PO No.',0,0,'used for shortage and overage docSubTypes',TO_TIMESTAMP('2023-02-07 08:47:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T07:47:29.143Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585929 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T07:47:29.146Z
/* DDL */  select update_Column_Translation_From_AD_Element(582036) 
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_InventoryLine
-- 2023-02-07T07:49:06.669Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585929,0,540120,321,TO_TIMESTAMP('2023-02-07 08:49:06','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3555,322,TO_TIMESTAMP('2023-02-07 08:49:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_HU_Assignment
-- 2023-02-07T07:49:50.022Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585929,0,540121,321,TO_TIMESTAMP('2023-02-07 08:49:49','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',550377,540569,TO_TIMESTAMP('2023-02-07 08:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_InOut
-- 2023-02-07T07:51:03.366Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585929,0,540122,321,TO_TIMESTAMP('2023-02-07 08:51:03','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3521,319,TO_TIMESTAMP('2023-02-07 08:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_Delivery_Planning
-- 2023-02-07T07:52:30.309Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585929,0,540123,321,TO_TIMESTAMP('2023-02-07 08:52:30','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',584986,542259,TO_TIMESTAMP('2023-02-07 08:52:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_BPartner_ID
-- 2023-02-07T07:57:21.810Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,585930,187,0,18,541252,321,'C_BPartner_ID','(SELECT DISTINCT dp.c_bpartner_id from m_inventoryline il LEFT JOIN m_hu_assignment hua on (hua.m_hu_id = il.m_hu_id) LEFT JOIN m_inout io on (io.m_inout_id = hua.record_id) LEFT JOIN m_delivery_planning dp on (io.m_delivery_planning_id = dp.m_delivery_planning_id) where (il.m_inventory_id = M_Inventory.m_inventory_id) LIMIT 1)',TO_TIMESTAMP('2023-02-07 08:57:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Geschäftspartner',0,0,'used for shortage and overage docSubTypes',TO_TIMESTAMP('2023-02-07 08:57:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T07:57:21.811Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585930 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T07:57:21.814Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: M_Inventory.C_BPartner_ID
-- Source Table: M_InventoryLine
-- 2023-02-07T08:08:48.816Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585930,0,540124,321,TO_TIMESTAMP('2023-02-07 09:08:48','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3563,322,TO_TIMESTAMP('2023-02-07 09:08:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_BPartner_ID
-- Source Table: M_HU_Assignment
-- 2023-02-07T08:09:51.827Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585930,0,540125,321,TO_TIMESTAMP('2023-02-07 09:09:51','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',550378,540569,TO_TIMESTAMP('2023-02-07 09:09:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_BPartner_ID
-- Source Table: M_InOut
-- 2023-02-07T08:11:31.884Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585930,0,540126,321,TO_TIMESTAMP('2023-02-07 09:11:31','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3521,319,TO_TIMESTAMP('2023-02-07 09:11:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_BPartner_ID
-- Source Table: M_Delivery_Planning
-- 2023-02-07T08:12:17.905Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585930,0,540127,321,TO_TIMESTAMP('2023-02-07 09:12:17','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',584986,585012,542259,TO_TIMESTAMP('2023-02-07 09:12:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_InventoryLine
-- 2023-02-07T08:13:48.294Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=3563,Updated=TO_TIMESTAMP('2023-02-07 09:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540120
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_HU_Assignment
-- 2023-02-07T08:14:23.212Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=550378,Updated=TO_TIMESTAMP('2023-02-07 09:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540121
;

-- Column: M_Inventory.C_Order_ID_Purchase
-- Source Table: M_Delivery_Planning
-- 2023-02-07T08:14:42.569Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=585047,Updated=TO_TIMESTAMP('2023-02-07 09:14:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540123
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> PO No.
-- Column: M_Inventory.C_Order_ID_Purchase
-- 2023-02-07T08:34:02.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585929,712217,0,18,290,255,540235,0,TO_TIMESTAMP('2023-02-07 09:34:02','YYYY-MM-DD HH24:MI:SS'),100,'Purchase order number',0,'@DocSubType@=''ISD'' | @DocSubType@=''IOD''','D',0,'Y','Y','Y','N','N','N','N','N','PO No.',0,220,0,1,1,TO_TIMESTAMP('2023-02-07 09:34:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T08:34:02.950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712217 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T08:34:02.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582036) 
;

-- 2023-02-07T08:34:02.964Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712217
;

-- 2023-02-07T08:34:02.969Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712217)
;

-- Field: Inventur(168,D) -> Bestandszählung(255,D) -> Geschäftspartner
-- Column: M_Inventory.C_BPartner_ID
-- 2023-02-07T08:35:45.338Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585930,712218,0,18,541252,255,0,TO_TIMESTAMP('2023-02-07 09:35:45','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',0,'@DocSubType@=''ISD'' | @DocSubType@=''IOD''','D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','Y','N','N','N','N','N','Geschäftspartner',0,220,0,1,1,TO_TIMESTAMP('2023-02-07 09:35:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T08:35:45.339Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712218 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-07T08:35:45.341Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-07T08:35:45.403Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712218
;

-- 2023-02-07T08:35:45.405Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712218)
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Inventory.C_BPartner_ID
-- 2023-02-07T08:38:23.025Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712218,0,255,541512,615613,'F',TO_TIMESTAMP('2023-02-07 09:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',50,0,0,TO_TIMESTAMP('2023-02-07 09:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur(168,D) -> Bestandszählung(255,D) -> main -> 20 -> document.PO No.
-- Column: M_Inventory.C_Order_ID_Purchase
-- 2023-02-07T08:40:43.676Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712217,0,255,541514,615614,'F',TO_TIMESTAMP('2023-02-07 09:40:43','YYYY-MM-DD HH24:MI:SS'),100,'Purchase order number','Y','N','N','Y','N','N','N',0,'PO No.',40,0,0,TO_TIMESTAMP('2023-02-07 09:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

