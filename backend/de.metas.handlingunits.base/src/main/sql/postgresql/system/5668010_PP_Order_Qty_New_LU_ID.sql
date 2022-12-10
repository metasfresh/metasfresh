-- 2022-12-09T14:24:38.547Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581853,0,'New_LU_ID',TO_TIMESTAMP('2022-12-09 16:24:38','YYYY-MM-DD HH24:MI:SS'),100,'New Loading Unit','de.metas.handlingunits','Y','New LU','New LU',TO_TIMESTAMP('2022-12-09 16:24:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-09T14:24:38.553Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581853 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PP_Order_Qty.New_LU_ID
-- Column: PP_Order_Qty.New_LU_ID
-- 2022-12-09T14:25:01.246Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585309,581853,0,30,540499,540807,'New_LU_ID',TO_TIMESTAMP('2022-12-09 16:25:01','YYYY-MM-DD HH24:MI:SS'),100,'N','New Loading Unit','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'New LU',0,0,TO_TIMESTAMP('2022-12-09 16:25:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-09T14:25:01.248Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585309 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-09T14:25:01.277Z
/* DDL */  select update_Column_Translation_From_AD_Element(581853) 
;

-- 2022-12-09T14:25:03.154Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Qty','ALTER TABLE public.PP_Order_Qty ADD COLUMN New_LU_ID NUMERIC(10)')
;

-- 2022-12-09T14:25:03.162Z
ALTER TABLE PP_Order_Qty ADD CONSTRAINT NewLU_PPOrderQty FOREIGN KEY (New_LU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- 2022-12-09T14:25:28.990Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585309,708975,0,544877,TO_TIMESTAMP('2022-12-09 16:25:28','YYYY-MM-DD HH24:MI:SS'),100,'New Loading Unit',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','New LU',TO_TIMESTAMP('2022-12-09 16:25:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-09T14:25:28.994Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-09T14:25:28.997Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581853) 
;

-- 2022-12-09T14:25:29.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708975
;

-- 2022-12-09T14:25:29.011Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708975)
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- 2022-12-09T14:25:43.419Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2022-12-09 16:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708975
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Picking candidate
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Picking candidate
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- 2022-12-09T14:25:43.428Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2022-12-09 16:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669444
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- 2022-12-09T14:25:43.436Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2022-12-09 16:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669445
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- 2022-12-09T14:25:43.444Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2022-12-09 16:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669441
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- 2022-12-09T14:25:43.451Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2022-12-09 16:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669438
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- 2022-12-09T14:25:50.530Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-12-09 16:25:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708975
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Picking candidate
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Picking candidate
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- 2022-12-09T14:25:50.537Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-12-09 16:25:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669444
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- 2022-12-09T14:25:50.544Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-09 16:25:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669445
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- 2022-12-09T14:25:50.551Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-12-09 16:25:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669441
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- 2022-12-09T14:25:50.558Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-12-09 16:25:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669438
;

-- Column: PP_Order_Qty.New_LU_ID
-- Column: PP_Order_Qty.New_LU_ID
-- 2022-12-09T14:26:01.055Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-09 16:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585309
;

-- Column: PP_Order_Qty.M_HU_ID
-- Column: PP_Order_Qty.M_HU_ID
-- 2022-12-09T14:26:27.607Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-09 16:26:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556461
;

-- Column: PP_Order_Qty.M_Locator_ID
-- Column: PP_Order_Qty.M_Locator_ID
-- 2022-12-09T14:26:47.406Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-09 16:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556465
;

-- Column: PP_Order_Qty.MovementDate
-- Column: PP_Order_Qty.MovementDate
-- 2022-12-09T14:26:59.053Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-09 16:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556462
;

-- Column: PP_Order_Qty.M_Product_ID
-- Column: PP_Order_Qty.M_Product_ID
-- 2022-12-09T14:27:07.018Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-09 16:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556464
;

-- Column: PP_Order_Qty.PP_Order_ID
-- Column: PP_Order_Qty.PP_Order_ID
-- 2022-12-09T14:27:19.473Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-09 16:27:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556456
;

