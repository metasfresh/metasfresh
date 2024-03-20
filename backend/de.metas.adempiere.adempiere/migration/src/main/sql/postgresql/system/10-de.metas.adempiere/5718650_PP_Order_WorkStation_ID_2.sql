-- Field: Produktionsauftrag -> Produktionsauftrag -> Produktionsstätte
-- Column: PP_Order.S_Resource_ID
-- Field: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> Produktionsstätte
-- Column: PP_Order.S_Resource_ID
-- 2024-03-06T14:22:34.116Z
UPDATE AD_Field SET AD_Name_ID=542433, Description=NULL, Help=NULL, Name='Produktionsstätte',Updated=TO_TIMESTAMP('2024-03-06 16:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54133
;

-- 2024-03-06T14:22:34.117Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542433) 
;

-- 2024-03-06T14:22:34.121Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=54133
;

-- 2024-03-06T14:22:34.122Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(54133)
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- 2024-03-06T14:23:22.623Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726563,0,53054,540184,623745,'F',TO_TIMESTAMP('2024-03-06 16:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsstation',100,0,0,TO_TIMESTAMP('2024-03-06 16:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- 2024-03-06T14:23:30.740Z
UPDATE AD_UI_Element SET SeqNo=55,Updated=TO_TIMESTAMP('2024-03-06 16:23:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623745
;

-- Column: PP_Order.WorkStation_ID
-- Column: PP_Order.WorkStation_ID
-- 2024-03-06T14:24:24.672Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-03-06 16:24:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587968
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> default.Arbeitsstation
-- Column: PP_Order.WorkStation_ID
-- 2024-03-06T14:25:06.702Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623745
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Bereitstellungsdatum
-- Column: PP_Order.PreparationDate
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> adv-dates.Bereitstellungsdatum
-- Column: PP_Order.PreparationDate
-- 2024-03-06T14:25:06.721Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542313
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Auftrag
-- Column: PP_Order.C_Order_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> menge.Auftrag
-- Column: PP_Order.C_Order_ID
-- 2024-03-06T14:25:06.730Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542323
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Auftragsposition
-- Column: PP_Order.C_OrderLine_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> advanced edit.Auftragsposition
-- Column: PP_Order.C_OrderLine_ID
-- 2024-03-06T14:25:06.739Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542324
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Lager
-- Column: PP_Order.M_Warehouse_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> bp.Lager
-- Column: PP_Order.M_Warehouse_ID
-- 2024-03-06T14:25:06.748Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542368
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Projekt
-- Column: PP_Order.C_Project_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: PP_Order.C_Project_ID
-- 2024-03-06T14:25:06.755Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542327
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Posted
-- Column: PP_Order.Posted
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> posted.Posted
-- Column: PP_Order.Posted
-- 2024-03-06T14:25:06.767Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564739
;

-- UI Element: Produktionsauftrag -> Produktionsauftrag.Sektion
-- Column: PP_Order.AD_Org_ID
-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 20 -> org.Sektion
-- Column: PP_Order.AD_Org_ID
-- 2024-03-06T14:25:06.775Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-03-06 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542369
;

