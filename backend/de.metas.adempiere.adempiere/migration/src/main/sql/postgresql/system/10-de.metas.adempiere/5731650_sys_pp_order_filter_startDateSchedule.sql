
-- Column: PP_Order.DateStartSchedule
-- Column: PP_Order.DateStartSchedule
-- 2024-08-26T06:40:08.291Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-08-26 08:40:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53643
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Geplanter Starttermin
-- Column: PP_Order.DateStartSchedule
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> dates.Geplanter Starttermin
-- Column: PP_Order.DateStartSchedule
-- 2024-08-26T13:11:12.503Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,54143,0,53054,540947,625296,'F',TO_TIMESTAMP('2024-08-26 15:11:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geplanter Starttermin',35,0,0,TO_TIMESTAMP('2024-08-26 15:11:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.DateStartSchedule
-- Column: PP_Order.DateStartSchedule
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> adv-dates.DateStartSchedule
-- Column: PP_Order.DateStartSchedule
-- 2024-08-26T13:11:34.276Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542307
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Planning status
-- Column: PP_Order.PlanningStatus
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Planning status
-- Column: PP_Order.PlanningStatus
-- 2024-08-26T13:11:34.285Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543290
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Reihenfolge
-- Column: PP_Order.SeqNo
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> dates.Reihenfolge
-- Column: PP_Order.SeqNo
-- 2024-08-26T13:11:34.292Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613400
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Produkt
-- Column: PP_Order.M_Product_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Produkt
-- Column: PP_Order.M_Product_ID
-- 2024-08-26T13:11:34.300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542348
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Merkmale
-- Column: PP_Order.M_AttributeSetInstance_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Merkmale
-- Column: PP_Order.M_AttributeSetInstance_ID
-- 2024-08-26T13:11:34.306Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542349
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Menge
-- Column: PP_Order.QtyEntered
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> menge.Menge
-- Column: PP_Order.QtyEntered
-- 2024-08-26T13:11:34.314Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542363
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Packvorschrift
-- Column: PP_Order.M_HU_PI_Item_Product_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> menge.Packvorschrift
-- Column: PP_Order.M_HU_PI_Item_Product_ID
-- 2024-08-26T13:11:34.321Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609612
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Maßeinheit
-- Column: PP_Order.C_UOM_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> menge.Maßeinheit
-- Column: PP_Order.C_UOM_ID
-- 2024-08-26T13:11:34.328Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542364
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Produktionsressource
-- Column: PP_Order.S_Resource_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Produktionsressource
-- Column: PP_Order.S_Resource_ID
-- 2024-08-26T13:11:34.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542352
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- 2024-08-26T13:11:34.340Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623745
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Bereitstellungsdatum
-- Column: PP_Order.PreparationDate
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> adv-dates.Bereitstellungsdatum
-- Column: PP_Order.PreparationDate
-- 2024-08-26T13:11:34.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542313
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Auftrag
-- Column: PP_Order.C_Order_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> menge.Auftrag
-- Column: PP_Order.C_Order_ID
-- 2024-08-26T13:11:34.350Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542323
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Auftragsposition
-- Column: PP_Order.C_OrderLine_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> advanced edit.Auftragsposition
-- Column: PP_Order.C_OrderLine_ID
-- 2024-08-26T13:11:34.355Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542324
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Lager
-- Column: PP_Order.M_Warehouse_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> bp.Lager
-- Column: PP_Order.M_Warehouse_ID
-- 2024-08-26T13:11:34.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542368
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Projekt
-- Column: PP_Order.C_Project_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: PP_Order.C_Project_ID
-- 2024-08-26T13:11:34.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542327
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Posted
-- Column: PP_Order.Posted
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> posted.Posted
-- Column: PP_Order.Posted
-- 2024-08-26T13:11:34.369Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564739
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Sektion
-- Column: PP_Order.AD_Org_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> org.Sektion
-- Column: PP_Order.AD_Org_ID
-- 2024-08-26T13:11:34.374Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-08-26 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542369
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.DateStartSchedule
-- Column: PP_Order.DateStartSchedule
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> adv-dates.DateStartSchedule
-- Column: PP_Order.DateStartSchedule
-- 2024-08-26T13:21:52.689Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542307
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Geplanter Starttermin
-- Column: PP_Order.DateStartSchedule
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> dates.Geplanter Starttermin
-- Column: PP_Order.DateStartSchedule
-- 2024-08-26T13:22:57.287Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-08-26 15:22:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625296
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Geplanter Starttermin
-- Column: PP_Order.DateStartSchedule
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> dates.Geplanter Starttermin
-- Column: PP_Order.DateStartSchedule
-- 2024-08-26T13:25:31.355Z
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2024-08-26 15:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625296
;

