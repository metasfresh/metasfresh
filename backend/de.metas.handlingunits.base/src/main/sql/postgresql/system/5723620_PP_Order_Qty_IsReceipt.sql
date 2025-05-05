-- Column: PP_Order_Qty.IsReceipt
-- Column: PP_Order_Qty.IsReceipt
-- 2024-05-14T11:16:11.996Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588265,1634,0,20,540807,'IsReceipt',TO_TIMESTAMP('2024-05-14 14:16:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Dies ist eine Verkaufs-Transaktion (Zahlungseingang)','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungseingang',0,0,TO_TIMESTAMP('2024-05-14 14:16:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-05-14T11:16:12.001Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588265 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-14T11:16:12.005Z
/* DDL */  select update_Column_Translation_From_AD_Element(1634) 
;

-- 2024-05-14T11:16:12.719Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Qty','ALTER TABLE public.PP_Order_Qty ADD COLUMN IsReceipt CHAR(1) DEFAULT ''N'' CHECK (IsReceipt IN (''Y'',''N'')) NOT NULL')
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Zahlungseingang
-- Column: PP_Order_Qty.IsReceipt
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Zahlungseingang
-- Column: PP_Order_Qty.IsReceipt
-- 2024-05-14T12:01:23.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588265,728705,0,544877,TO_TIMESTAMP('2024-05-14 15:01:22','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufs-Transaktion (Zahlungseingang)',1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Zahlungseingang',TO_TIMESTAMP('2024-05-14 15:01:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-14T12:01:23.009Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728705 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-14T12:01:23.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1634) 
;

-- 2024-05-14T12:01:23.031Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728705
;

-- 2024-05-14T12:01:23.034Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728705)
;

-- Column: PP_Order_Qty.IsReceipt
-- Column: PP_Order_Qty.IsReceipt
-- 2024-05-14T12:01:46.591Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-14 15:01:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588265
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Zahlungseingang
-- Column: PP_Order_Qty.IsReceipt
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Zahlungseingang
-- Column: PP_Order_Qty.IsReceipt
-- 2024-05-14T12:02:13.998Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2024-05-14 15:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728705
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Bewegungsdatum
-- Column: PP_Order_Qty.MovementDate
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Bewegungsdatum
-- Column: PP_Order_Qty.MovementDate
-- 2024-05-14T12:02:14.005Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669440
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Produkt
-- Column: PP_Order_Qty.M_Product_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Produkt
-- Column: PP_Order_Qty.M_Product_ID
-- 2024-05-14T12:02:14.012Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669442
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Maßeinheit
-- Column: PP_Order_Qty.C_UOM_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Maßeinheit
-- Column: PP_Order_Qty.C_UOM_ID
-- 2024-05-14T12:02:14.019Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669437
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Menge
-- Column: PP_Order_Qty.Qty
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Menge
-- Column: PP_Order_Qty.Qty
-- 2024-05-14T12:02:14.025Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669436
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Lagerort
-- Column: PP_Order_Qty.M_Locator_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Lagerort
-- Column: PP_Order_Qty.M_Locator_ID
-- 2024-05-14T12:02:14.030Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669443
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Handling Unit
-- Column: PP_Order_Qty.M_HU_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Handling Unit
-- Column: PP_Order_Qty.M_HU_ID
-- 2024-05-14T12:02:14.035Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669439
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- 2024-05-14T12:02:14.039Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708975
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Kommissionierkandidaten
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Kommissionierkandidaten
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- 2024-05-14T12:02:14.045Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669444
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- 2024-05-14T12:02:14.050Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669445
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- 2024-05-14T12:02:14.054Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669441
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- 2024-05-14T12:02:14.058Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2024-05-14 15:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669438
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Zahlungseingang
-- Column: PP_Order_Qty.IsReceipt
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Zahlungseingang
-- Column: PP_Order_Qty.IsReceipt
-- 2024-05-14T12:02:20.865Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728705
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Bewegungsdatum
-- Column: PP_Order_Qty.MovementDate
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Bewegungsdatum
-- Column: PP_Order_Qty.MovementDate
-- 2024-05-14T12:02:20.870Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669440
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Produkt
-- Column: PP_Order_Qty.M_Product_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Produkt
-- Column: PP_Order_Qty.M_Product_ID
-- 2024-05-14T12:02:20.874Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669442
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Maßeinheit
-- Column: PP_Order_Qty.C_UOM_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Maßeinheit
-- Column: PP_Order_Qty.C_UOM_ID
-- 2024-05-14T12:02:20.878Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669437
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Menge
-- Column: PP_Order_Qty.Qty
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Menge
-- Column: PP_Order_Qty.Qty
-- 2024-05-14T12:02:20.883Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669436
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Lagerort
-- Column: PP_Order_Qty.M_Locator_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Lagerort
-- Column: PP_Order_Qty.M_Locator_ID
-- 2024-05-14T12:02:20.888Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669443
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Handling Unit
-- Column: PP_Order_Qty.M_HU_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Handling Unit
-- Column: PP_Order_Qty.M_HU_ID
-- 2024-05-14T12:02:20.892Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669439
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> New LU
-- Column: PP_Order_Qty.New_LU_ID
-- 2024-05-14T12:02:20.897Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708975
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Kommissionierkandidaten
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Kommissionierkandidaten
-- Column: PP_Order_Qty.M_Picking_Candidate_ID
-- 2024-05-14T12:02:20.902Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669444
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Order Issue Schedule
-- Column: PP_Order_Qty.PP_Order_IssueSchedule_ID
-- 2024-05-14T12:02:20.907Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669445
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Manufacturing Cost Collector
-- Column: PP_Order_Qty.PP_Cost_Collector_ID
-- 2024-05-14T12:02:20.911Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669441
;

-- Field: Manufacturing order Issue/Receipt quantity -> Manufacturing order Issue/Receipt quantity -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- Field: Manufacturing order Issue/Receipt quantity(541337,de.metas.handlingunits) -> Manufacturing order Issue/Receipt quantity(544877,de.metas.handlingunits) -> Verarbeitet
-- Column: PP_Order_Qty.Processed
-- 2024-05-14T12:02:20.914Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-05-14 15:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669438
;

