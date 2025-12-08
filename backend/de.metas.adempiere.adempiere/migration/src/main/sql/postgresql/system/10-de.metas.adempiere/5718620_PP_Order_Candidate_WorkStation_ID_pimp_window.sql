-- Element: PP_Plant_ID
-- 2024-03-06T13:56:38.748Z
UPDATE AD_Element_Trl SET PrintName='Plant',Updated=TO_TIMESTAMP('2024-03-06 15:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542433 AND AD_Language='en_US'
;

-- 2024-03-06T13:56:38.751Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542433,'en_US') 
;

-- Element: PP_Plant_ID
-- 2024-03-06T13:56:43.188Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-06 15:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542433 AND AD_Language='de_DE'
;

-- 2024-03-06T13:56:43.190Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(542433,'de_DE') 
;

-- 2024-03-06T13:56:43.201Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542433,'de_DE') 
;

-- Element: PP_Plant_ID
-- 2024-03-06T13:56:54.048Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-06 15:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542433 AND AD_Language='de_CH'
;

-- 2024-03-06T13:56:54.052Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542433,'de_CH') 
;

-- Element: PP_Plant_ID
-- 2024-03-06T13:57:01.456Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Plant', PrintName='Plant',Updated=TO_TIMESTAMP('2024-03-06 15:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542433 AND AD_Language='en_GB'
;

-- 2024-03-06T13:57:01.459Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542433,'en_GB') 
;

-- Field: Produktionsdisposition -> Produktionsdisposition -> Produktionsstätte
-- Column: PP_Order_Candidate.S_Resource_ID
-- Field: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> Produktionsstätte
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2024-03-06T13:57:43.303Z
UPDATE AD_Field SET AD_Name_ID=542433, Description=NULL, Help=NULL, Name='Produktionsstätte',Updated=TO_TIMESTAMP('2024-03-06 15:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=667477
;

-- 2024-03-06T13:57:43.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542433) 
;

-- 2024-03-06T13:57:43.317Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=667477
;

-- 2024-03-06T13:57:43.318Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(667477)
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T13:58:22.829Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726562,0,544794,547106,623744,'F',TO_TIMESTAMP('2024-03-06 15:58:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsstation',60,0,0,TO_TIMESTAMP('2024-03-06 15:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T13:58:35.365Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2024-03-06 15:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623744
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T14:02:38.532Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623744
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> main.Lager
-- Column: PP_Order_Candidate.M_Warehouse_ID
-- 2024-03-06T14:02:38.543Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594542
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Menge
-- Column: PP_Order_Candidate.QtyEntered
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Menge
-- Column: PP_Order_Candidate.QtyEntered
-- 2024-03-06T14:02:38.552Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594540
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Quantity To Process
-- Column: PP_Order_Candidate.QtyToProcess
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Quantity To Process
-- Column: PP_Order_Candidate.QtyToProcess
-- 2024-03-06T14:02:38.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595256
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Number of Resources to Process
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Number of Resources to Process
-- Column: PP_Order_Candidate.NumberOfResources_ToProcess
-- 2024-03-06T14:02:38.572Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613397
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Quantity Processed
-- Column: PP_Order_Candidate.QtyProcessed
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Quantity Processed
-- Column: PP_Order_Candidate.QtyProcessed
-- 2024-03-06T14:02:38.581Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595255
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Maßeinheit
-- Column: PP_Order_Candidate.C_UOM_ID
-- 2024-03-06T14:02:38.591Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594541
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> menge.Packvorschrift
-- Column: PP_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2024-03-06T14:02:38.599Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609611
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> dates.Zugesagter Termin
-- Column: PP_Order_Candidate.DatePromised
-- 2024-03-06T14:02:38.607Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594544
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> dates.geplanter Beginn
-- Column: PP_Order_Candidate.DateStartSchedule
-- 2024-03-06T14:02:38.615Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594641
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> bp.Verarbeitet
-- Column: PP_Order_Candidate.Processed
-- 2024-03-06T14:02:38.622Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594543
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> bp.Geschlossen
-- Column: PP_Order_Candidate.IsClosed
-- 2024-03-06T14:02:38.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=595252
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> org.Sektion
-- Column: PP_Order_Candidate.AD_Org_ID
-- 2024-03-06T14:02:38.635Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594545
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> org.Mandant
-- Column: PP_Order_Candidate.AD_Client_ID
-- 2024-03-06T14:02:38.642Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2024-03-06 16:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594546
;

-- Column: PP_Order_Candidate.WorkStation_ID
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T14:02:59.889Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-03-06 16:02:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587967
;

-- Field: Produkt Plandaten -> Product Planning -> Produktionsstätte
-- Column: PP_Product_Planning.S_Resource_ID
-- Field: Produkt Plandaten(540750,D) -> Product Planning(542102,D) -> Produktionsstätte
-- Column: PP_Product_Planning.S_Resource_ID
-- 2024-03-06T14:04:11.249Z
UPDATE AD_Field SET AD_Name_ID=542433, Description=NULL, Help=NULL, Name='Produktionsstätte',Updated=TO_TIMESTAMP('2024-03-06 16:04:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591243
;

-- 2024-03-06T14:04:11.251Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542433) 
;

-- 2024-03-06T14:04:11.257Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591243
;

-- 2024-03-06T14:04:11.259Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(591243)
;

