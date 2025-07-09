-- Run mode: SWING_CLIENT

-- Column: C_PurchaseCandidate.PurchaseDateOrdered
-- 2025-07-09T07:18:38.663Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-07-09 07:18:38.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560663
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Bestelldatum
-- Column: C_PurchaseCandidate.PurchaseDateOrdered
-- 2025-07-09T07:19:31.073Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560663,748946,0,540894,0,TO_TIMESTAMP('2025-07-09 07:19:30.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Bestelldatum',0,0,330,0,1,1,TO_TIMESTAMP('2025-07-09 07:19:30.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-09T07:19:31.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-09T07:19:31.099Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544171)
;

-- 2025-07-09T07:19:31.109Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748946
;

-- 2025-07-09T07:19:31.111Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748946)
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Bestelldatum
-- Column: C_PurchaseCandidate.PurchaseDateOrdered
-- 2025-07-09T07:19:57.166Z
UPDATE AD_Field SET DisplayLength=7, EntityType='de.metas.purchasecandidate',Updated=TO_TIMESTAMP('2025-07-09 07:19:57.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=748946
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> flags.Bestelldatum
-- Column: C_PurchaseCandidate.PurchaseDateOrdered
-- 2025-07-09T07:21:14.686Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,748946,0,540894,541249,634830,'F',TO_TIMESTAMP('2025-07-09 07:21:14.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestelldatum',65,0,0,TO_TIMESTAMP('2025-07-09 07:21:14.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_PurchaseCandidate.PurchaseDateOrdered
-- 2025-07-09T07:26:58.481Z
UPDATE AD_Column SET IsShowFilterInline='Y',Updated=TO_TIMESTAMP('2025-07-09 07:26:58.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560663
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> flags.Bestelldatum
-- Column: C_PurchaseCandidate.PurchaseDateOrdered
-- 2025-07-09T07:31:12.321Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=634830
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Lager
-- Column: C_PurchaseCandidate.M_WarehousePO_ID
-- 2025-07-09T07:31:12.327Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549340
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> order.Auftrag
-- Column: C_PurchaseCandidate.C_OrderSO_ID
-- 2025-07-09T07:31:12.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549341
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> order.Auftragsposition
-- Column: C_PurchaseCandidate.C_OrderLineSO_ID
-- 2025-07-09T07:31:12.339Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549342
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> order.Lieferant
-- Column: C_PurchaseCandidate.Vendor_ID
-- 2025-07-09T07:31:12.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549343
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> flags.Aktiv
-- Column: C_PurchaseCandidate.IsActive
-- 2025-07-09T07:31:12.351Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549345
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> flags.Verarbeitet
-- Column: C_PurchaseCandidate.Processed
-- 2025-07-09T07:31:12.356Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549347
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> flags.Requisition Created
-- Column: C_PurchaseCandidate.IsRequisitionCreated
-- 2025-07-09T07:31:12.362Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=577949
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> flags.Disponiert
-- Column: C_PurchaseCandidate.IsPrepared
-- 2025-07-09T07:31:12.368Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552268
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> org.Sektion
-- Column: C_PurchaseCandidate.AD_Org_ID
-- 2025-07-09T07:31:12.373Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-07-09 07:31:12.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549349
;

-- UI Element: Geschäftspartner Pharma(540409,U) -> Bestell Schema(541103,de.metas.purchasecandidate) -> main -> 10 -> default.Lead time (days)
-- Column: C_BP_PurchaseSchedule.LeadTimeOffset
-- 2025-07-09T08:09:06.065Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=552339
;

-- 2025-07-09T08:09:06.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=565044
;

-- Field: Geschäftspartner Pharma(540409,U) -> Bestell Schema(541103,de.metas.purchasecandidate) -> Lead Time Offset
-- Column: C_BP_PurchaseSchedule.LeadTimeOffset
-- 2025-07-09T08:09:06.072Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=565044
;

-- 2025-07-09T08:09:06.078Z
DELETE FROM AD_Field WHERE AD_Field_ID=565044
;

-- 2025-07-09T08:09:06.080Z
/* DDL */ SELECT public.db_alter_table('C_BP_PurchaseSchedule','ALTER TABLE C_BP_PurchaseSchedule DROP COLUMN IF EXISTS LeadTimeOffset')
;

-- Column: C_BP_PurchaseSchedule.LeadTimeOffset
-- 2025-07-09T08:09:06.108Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560223
;

-- 2025-07-09T08:09:06.113Z
DELETE FROM AD_Column WHERE AD_Column_ID=560223
;

-- 2025-07-09T08:30:08.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=564403
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Lead Time Offset
-- Column: I_BPartner.LeadTimeOffset
-- 2025-07-09T08:30:08.507Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=564403
;

-- 2025-07-09T08:30:08.515Z
DELETE FROM AD_Field WHERE AD_Field_ID=564403
;

-- 2025-07-09T08:30:08.517Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE I_BPartner DROP COLUMN IF EXISTS LeadTimeOffset')
;

-- Column: I_BPartner.LeadTimeOffset
-- 2025-07-09T08:30:08.535Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=560207
;

-- 2025-07-09T08:30:08.540Z
DELETE FROM AD_Column WHERE AD_Column_ID=560207
;

-- 2025-07-09T08:35:13.532Z
--de.metas.purchasecandidate.interceptor.Main
DELETE FROM AD_ModelValidator WHERE AD_ModelValidator_ID=540122
;

-- Column: C_PurchaseCandidate.PurchaseDateOrdered
-- 2025-07-09T09:00:17.020Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2025-07-09 09:00:17.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560663
;

-- Column: C_PurchaseCandidate.PurchaseDatePromised
-- 2025-07-09T09:00:17.288Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2025-07-09 09:00:17.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557861
;

-- Column: C_PurchaseCandidate.Vendor_ID
-- 2025-07-09T09:00:17.556Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2025-07-09 09:00:17.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557859
;

-- Column: C_PurchaseCandidate.M_Product_ID
-- 2025-07-09T09:00:17.837Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2025-07-09 09:00:17.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557864
;

-- Column: C_PurchaseCandidate.M_WarehousePO_ID
-- 2025-07-09T09:00:18.168Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2025-07-09 09:00:18.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557866
;

-- Column: C_PurchaseCandidate.DemandReference
-- 2025-07-09T09:00:18.479Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2025-07-09 09:00:18.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=560251
;

-- Column: C_PurchaseCandidate.Processed
-- 2025-07-09T09:00:18.779Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2025-07-09 09:00:18.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557863
;

