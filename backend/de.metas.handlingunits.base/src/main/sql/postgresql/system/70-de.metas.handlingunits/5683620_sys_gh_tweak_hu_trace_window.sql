-- Field: HU Rückverfolgung -> Rückverfolgung -> Rückverfolgbarkeit
-- Column: M_HU_Trace.M_HU_Trace_ID
-- Field: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> Rückverfolgbarkeit
-- Column: M_HU_Trace.M_HU_Trace_ID
-- 2023-03-31T17:53:36.264Z
UPDATE AD_Field SET SortNo=10.000000000000,Updated=TO_TIMESTAMP('2023-03-31 19:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558795
;

-- Field: HU Rückverfolgung -> Rückverfolgung -> Rückverfolgbarkeit
-- Column: M_HU_Trace.M_HU_Trace_ID
-- Field: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> Rückverfolgbarkeit
-- Column: M_HU_Trace.M_HU_Trace_ID
-- 2023-03-31T17:54:31.122Z
UPDATE AD_Field SET SortNo=-2.000000000000,Updated=TO_TIMESTAMP('2023-03-31 19:54:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558795
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.M_HU_Trace_ID
-- Column: M_HU_Trace.M_HU_Trace_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.M_HU_Trace_ID
-- Column: M_HU_Trace.M_HU_Trace_ID
-- 2023-03-31T17:59:00.137Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558795,0,540842,540892,616493,'F',TO_TIMESTAMP('2023-03-31 19:58:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'M_HU_Trace_ID',5,0,0,TO_TIMESTAMP('2023-03-31 19:58:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.M_HU_Trace_ID
-- Column: M_HU_Trace.M_HU_Trace_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.M_HU_Trace_ID
-- Column: M_HU_Trace.M_HU_Trace_ID
-- 2023-03-31T17:59:21.634Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-31 19:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616493
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Zeitpunkt
-- Column: M_HU_Trace.EventTime
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.Zeitpunkt
-- Column: M_HU_Trace.EventTime
-- 2023-03-31T17:59:21.851Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-31 19:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546775
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Typ
-- Column: M_HU_Trace.HUTraceType
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.Typ
-- Column: M_HU_Trace.HUTraceType
-- 2023-03-31T17:59:22.070Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-31 19:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546778
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Produkt
-- Column: M_HU_Trace.M_Product_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.Produkt
-- Column: M_HU_Trace.M_Product_ID
-- 2023-03-31T17:59:22.297Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-31 19:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546768
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Menge
-- Column: M_HU_Trace.Qty
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> type.Menge
-- Column: M_HU_Trace.Qty
-- 2023-03-31T17:59:22.516Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-31 19:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546769
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Handling Unit
-- Column: M_HU_Trace.M_HU_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Handling Unit
-- Column: M_HU_Trace.M_HU_ID
-- 2023-03-31T17:59:22.734Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-31 19:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546771
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Belegart
-- Column: M_HU_Trace.C_DocType_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Belegart
-- Column: M_HU_Trace.C_DocType_ID
-- 2023-03-31T17:59:22.947Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-31 19:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546779
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Lieferung/Wareneingang
-- Column: M_HU_Trace.M_InOut_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Lieferung/Wareneingang
-- Column: M_HU_Trace.M_InOut_ID
-- 2023-03-31T17:59:23.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-31 19:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546783
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Warenbewegung
-- Column: M_HU_Trace.M_Movement_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Warenbewegung
-- Column: M_HU_Trace.M_Movement_ID
-- 2023-03-31T17:59:23.384Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-31 19:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546784
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Transaktionszeile
-- Column: M_HU_Trace.M_HU_Trx_Line_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> doc.Transaktionszeile
-- Column: M_HU_Trace.M_HU_Trx_Line_ID
-- 2023-03-31T17:59:23.593Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-31 19:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546781
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Lieferdisposition
-- Column: M_HU_Trace.M_ShipmentSchedule_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Lieferdisposition
-- Column: M_HU_Trace.M_ShipmentSchedule_ID
-- 2023-03-31T17:59:23.802Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-31 19:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546785
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Manufacturing Cost Collector
-- Column: M_HU_Trace.PP_Cost_Collector_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Manufacturing Cost Collector
-- Column: M_HU_Trace.PP_Cost_Collector_ID
-- 2023-03-31T17:59:24.018Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-31 19:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546786
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Produktionsauftrag
-- Column: M_HU_Trace.PP_Order_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 10 -> details.Produktionsauftrag
-- Column: M_HU_Trace.PP_Order_ID
-- 2023-03-31T17:59:24.239Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-31 19:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546787
;

-- UI Element: HU Rückverfolgung -> Rückverfolgung.Sektion
-- Column: M_HU_Trace.AD_Org_ID
-- UI Element: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> main -> 20 -> org.Sektion
-- Column: M_HU_Trace.AD_Org_ID
-- 2023-03-31T17:59:24.446Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-03-31 19:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546776
;

-- Element: M_HU_Trace_ID
-- 2023-03-31T18:00:56.183Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückverfolgungs-Id', PrintName='Rückverfolgungs-Id',Updated=TO_TIMESTAMP('2023-03-31 20:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543376 AND AD_Language='de_CH'
;

-- 2023-03-31T18:00:56.277Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543376,'de_CH') 
;

-- Element: M_HU_Trace_ID
-- 2023-03-31T18:01:09.742Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückverfolgungs-Id', PrintName='Rückverfolgungs-Id',Updated=TO_TIMESTAMP('2023-03-31 20:01:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543376 AND AD_Language='de_DE'
;

-- 2023-03-31T18:01:09.801Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543376,'de_DE') 
;

-- 2023-03-31T18:01:09.835Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543376,'de_DE') 
;

-- Element: M_HU_Trace_ID
-- 2023-03-31T18:01:58.802Z
UPDATE AD_Element_Trl SET Name='HU Trace Id',Updated=TO_TIMESTAMP('2023-03-31 20:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543376 AND AD_Language='en_US'
;

-- 2023-03-31T18:01:58.859Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543376,'en_US') 
;

-- Element: M_HU_Trace_ID
-- 2023-03-31T18:02:02.322Z
UPDATE AD_Element_Trl SET PrintName='HU Trace Id',Updated=TO_TIMESTAMP('2023-03-31 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543376 AND AD_Language='en_US'
;

-- 2023-03-31T18:02:02.375Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543376,'en_US') 
;

-- Field: HU Rückverfolgung -> Rückverfolgung -> Zeitpunkt
-- Column: M_HU_Trace.EventTime
-- Field: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> Zeitpunkt
-- Column: M_HU_Trace.EventTime
-- 2023-03-31T18:04:41.716Z
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2023-03-31 20:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558801
;

-- Field: HU Rückverfolgung -> Rückverfolgung -> Rückverfolgungs-Id
-- Column: M_HU_Trace.M_HU_Trace_ID
-- Field: HU Rückverfolgung(540353,de.metas.handlingunits) -> Rückverfolgung(540842,de.metas.handlingunits) -> Rückverfolgungs-Id
-- Column: M_HU_Trace.M_HU_Trace_ID
-- 2023-03-31T18:04:53.653Z
UPDATE AD_Field SET SortNo=-10.000000000000,Updated=TO_TIMESTAMP('2023-03-31 20:04:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558795
;

