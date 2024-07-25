-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2024-07-17T18:11:33.345Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588857,729099,0,547559,TO_TIMESTAMP('2024-07-17 21:11:33','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung',10,'EE01','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','N','N','N','N','N','Lieferweg',TO_TIMESTAMP('2024-07-17 21:11:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-17T18:11:33.350Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-17T18:11:33.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455) 
;

-- 2024-07-17T18:11:33.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729099
;

-- 2024-07-17T18:11:33.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729099)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2024-07-17T18:12:16.646Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729099,0,547559,551860,625013,'F',TO_TIMESTAMP('2024-07-17 21:12:16','YYYY-MM-DD HH24:MI:SS'),100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','N','N','Lieferweg',70,0,0,TO_TIMESTAMP('2024-07-17 21:12:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- 2024-07-17T18:12:40.444Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729077
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- 2024-07-17T18:12:41.744Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729078
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Sektion
-- Column: DD_Order_Candidate.AD_Org_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Sektion
-- Column: DD_Order_Candidate.AD_Org_ID
-- 2024-07-17T18:12:44.577Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729068
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Aktiv
-- Column: DD_Order_Candidate.IsActive
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Aktiv
-- Column: DD_Order_Candidate.IsActive
-- 2024-07-17T18:12:45.843Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729069
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Distribution Order Candidate
-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Distribution Order Candidate
-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-07-17T18:12:46.820Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729070
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- 2024-07-17T18:12:47.766Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729071
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- 2024-07-17T18:12:48.846Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729072
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2024-07-17T18:12:49.911Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729073
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2024-07-17T18:12:50.931Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729074
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- 2024-07-17T18:12:51.955Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729075
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Menge
-- Column: DD_Order_Candidate.QtyEntered
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Menge
-- Column: DD_Order_Candidate.QtyEntered
-- 2024-07-17T18:12:53.267Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:12:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729079
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- 2024-07-17T18:13:11.382Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729080
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- 2024-07-17T18:13:12.924Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729081
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- 2024-07-17T18:13:14.525Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729082
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- 2024-07-17T18:13:16.023Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729083
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2024-07-17T18:13:17.365Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729084
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- 2024-07-17T18:13:18.336Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729085
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- 2024-07-17T18:13:19.333Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729086
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- 2024-07-17T18:13:20.443Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729087
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Packvorschrift
-- Column: DD_Order_Candidate.M_HU_PI_Item_Product_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Packvorschrift
-- Column: DD_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2024-07-17T18:13:21.437Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729088
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2024-07-17T18:13:22.600Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729089
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2024-07-17T18:13:23.547Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729090
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2024-07-17T18:13:26.227Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-17 21:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729099
;

