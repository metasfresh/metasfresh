-- 2023-03-06T11:24:15.365Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582120,0,'M_CostDetail_Type',TO_TIMESTAMP('2023-03-06 13:24:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Type','Type',TO_TIMESTAMP('2023-03-06 13:24:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-06T11:24:15.411Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582120 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: M_CostDetail_Type
-- 2023-03-06T11:25:11.120Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541722,TO_TIMESTAMP('2023-03-06 13:25:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_CostDetail_Type',TO_TIMESTAMP('2023-03-06 13:25:10','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-03-06T11:25:11.162Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541722 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_CostDetail_Type
-- Value: M
-- ValueName: Main
-- 2023-03-06T11:25:36.872Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541722,543413,TO_TIMESTAMP('2023-03-06 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Main',TO_TIMESTAMP('2023-03-06 13:25:36','YYYY-MM-DD HH24:MI:SS'),100,'M','Main')
;

-- 2023-03-06T11:25:36.912Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543413 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: M_CostDetail_Type
-- Value: A
-- ValueName: Cost Adjustment
-- 2023-03-06T11:25:49.572Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541722,543414,TO_TIMESTAMP('2023-03-06 13:25:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Adjustment',TO_TIMESTAMP('2023-03-06 13:25:49','YYYY-MM-DD HH24:MI:SS'),100,'A','Cost Adjustment')
;

-- 2023-03-06T11:25:49.611Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543414 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: M_CostDetail_Type
-- Value: S
-- ValueName: Already Shipped
-- 2023-03-06T11:26:01.040Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541722,543415,TO_TIMESTAMP('2023-03-06 13:26:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Already Shipped',TO_TIMESTAMP('2023-03-06 13:26:00','YYYY-MM-DD HH24:MI:SS'),100,'S','Already Shipped')
;

-- 2023-03-06T11:26:01.082Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543415 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: M_CostDetail.M_CostDetail_Type
-- 2023-03-06T11:26:21.777Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586284,582120,0,17,541722,808,'M_CostDetail_Type',TO_TIMESTAMP('2023-03-06 13:26:20','YYYY-MM-DD HH24:MI:SS'),100,'N','M','D',0,25,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Type',0,0,TO_TIMESTAMP('2023-03-06 13:26:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-06T11:26:21.818Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586284 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-06T11:26:21.930Z
/* DDL */  select update_Column_Translation_From_AD_Element(582120) 
;

-- 2023-03-06T11:26:29.850Z
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN M_CostDetail_Type VARCHAR(25) DEFAULT ''M'' NOT NULL')
;

-- Field: Product Cost(344,D) -> Cost Details(748,D) -> Type
-- Column: M_CostDetail.M_CostDetail_Type
-- 2023-03-06T11:28:08.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586284,712778,0,748,TO_TIMESTAMP('2023-03-06 13:28:07','YYYY-MM-DD HH24:MI:SS'),100,25,'D','Y','N','N','N','N','N','N','N','Type',TO_TIMESTAMP('2023-03-06 13:28:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-06T11:28:08.416Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712778 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-06T11:28:08.458Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582120) 
;

-- 2023-03-06T11:28:08.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712778
;

-- 2023-03-06T11:28:08.552Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712778)
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> default.Type
-- Column: M_CostDetail.M_CostDetail_Type
-- 2023-03-06T11:30:54.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712778,0,748,544601,615973,'F',TO_TIMESTAMP('2023-03-06 13:30:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Type',80,0,0,TO_TIMESTAMP('2023-03-06 13:30:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> default.Type
-- Column: M_CostDetail.M_CostDetail_Type
-- 2023-03-06T11:31:05.028Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-06 13:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615973
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> Amount & Qty.Menge
-- Column: M_CostDetail.Qty
-- 2023-03-06T11:31:05.278Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-06 13:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547578
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> Amount & Qty.Maßeinheit
-- Column: M_CostDetail.C_UOM_ID
-- 2023-03-06T11:31:05.527Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-06 13:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575088
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> Amount & Qty.Preis
-- Column: M_CostDetail.Price
-- 2023-03-06T11:31:05.829Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-06 13:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547577
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> Amount & Qty.Betrag
-- Column: M_CostDetail.Amt
-- 2023-03-06T11:31:06.075Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-06 13:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547579
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> Amount & Qty.Währung
-- Column: M_CostDetail.C_Currency_ID
-- 2023-03-06T11:31:06.320Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-06 13:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575087
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Verkaufs-Transaktion
-- Column: M_CostDetail.IsSOTrx
-- 2023-03-06T11:31:06.570Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-06 13:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547591
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Changing costs
-- Column: M_CostDetail.IsChangingCosts
-- 2023-03-06T11:31:06.875Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-06 13:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570143
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Abgleich Bestellung
-- Column: M_CostDetail.M_MatchPO_ID
-- 2023-03-06T11:31:07.120Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-06 13:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570142
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Wareneingangs- Position
-- Column: M_CostDetail.M_InOutLine_ID
-- 2023-03-06T11:31:07.362Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-06 13:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547586
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Inventur-Position
-- Column: M_CostDetail.M_InventoryLine_ID
-- 2023-03-06T11:31:07.609Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-06 13:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547588
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Warenbewegungs- Position
-- Column: M_CostDetail.M_MovementLine_ID
-- 2023-03-06T11:31:07.875Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-03-06 13:31:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547587
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Manufacturing Cost Collector
-- Column: M_CostDetail.PP_Cost_Collector_ID
-- 2023-03-06T11:31:08.117Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-03-06 13:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575081
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Bestellposition
-- Column: M_CostDetail.C_OrderLine_ID
-- 2023-03-06T11:31:08.360Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-03-06 13:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547584
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Abgleich Rechnung
-- Column: M_CostDetail.M_MatchInv_ID
-- 2023-03-06T11:31:08.602Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-03-06 13:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570141
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Reference.Rechnungsposition
-- Column: M_CostDetail.C_InvoiceLine_ID
-- 2023-03-06T11:31:08.889Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-03-06 13:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547585
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> default.Buchführungs-Schema
-- Column: M_CostDetail.C_AcctSchema_ID
-- 2023-03-06T11:31:09.145Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-03-06 13:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547573
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Previous amounts.Previous Current Cost Price
-- Column: M_CostDetail.Prev_CurrentCostPrice
-- 2023-03-06T11:31:09.391Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-03-06 13:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575082
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 20 -> Previous amounts.Previous Current Qty
-- Column: M_CostDetail.Prev_CurrentQty
-- 2023-03-06T11:31:09.636Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-03-06 13:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575084
;

