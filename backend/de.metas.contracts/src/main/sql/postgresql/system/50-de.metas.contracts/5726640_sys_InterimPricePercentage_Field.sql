
-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Akonto Preis %
-- Column: ModCntr_Settings.InterimPricePercent
-- 2024-06-11T12:57:38.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588337,728998,0,547013,TO_TIMESTAMP('2024-06-11 15:57:38.432','YYYY-MM-DD HH24:MI:SS.US'),100,'Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.',14,'de.metas.contracts','Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.','Y','N','N','N','N','N','N','N','Akonto Preis %',TO_TIMESTAMP('2024-06-11 15:57:38.432','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-11T12:57:38.602Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-11T12:57:38.606Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583135)
;

-- 2024-06-11T12:57:38.621Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728998
;

-- 2024-06-11T12:57:38.626Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728998)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Akonto Preis %
-- Column: ModCntr_Settings.InterimPricePercent
-- 2024-06-11T12:58:16.238Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728998,0,547013,551809,624942,'F',TO_TIMESTAMP('2024-06-11 15:58:16.072','YYYY-MM-DD HH24:MI:SS.US'),100,'Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.','Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.','Y','N','N','Y','N','N','N',0,'Akonto Preis %',40,0,0,TO_TIMESTAMP('2024-06-11 15:58:16.072','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: ModCntr_Settings.Name
-- 2024-06-11T13:00:32.900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617984
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Rohprodukt
-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-06-11T13:00:32.908Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.907','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624022
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Verarbeitetes Produkt
-- Column: ModCntr_Settings.M_Processed_Product_ID
-- 2024-06-11T13:00:32.914Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.914','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624023
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Co-Produkt
-- Column: ModCntr_Settings.M_Co_Product_ID
-- 2024-06-11T13:00:32.920Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.92','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624024
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Kalender
-- Column: ModCntr_Settings.C_Calendar_ID
-- 2024-06-11T13:00:32.926Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.926','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617986
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Jahr
-- Column: ModCntr_Settings.C_Year_ID
-- 2024-06-11T13:00:32.931Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.931','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617987
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> pricing.Preissystem
-- Column: ModCntr_Settings.M_PricingSystem_ID
-- 2024-06-11T13:00:32.937Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.937','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617988
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: ModCntr_Settings.IsSOTrx
-- 2024-06-11T13:00:32.943Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.943','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618501
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Lagerkosten Startdatum
-- Column: ModCntr_Settings.StorageCostStartDate
-- 2024-06-11T13:00:32.949Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.949','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624525
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Zusätzlicher Zinstage
-- Column: ModCntr_Settings.AddInterestDays
-- 2024-06-11T13:00:32.955Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.955','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624730
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Zinssatz
-- Column: ModCntr_Settings.InterestRate
-- 2024-06-11T13:00:32.959Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.959','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624731
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Akonto Preis %
-- Column: ModCntr_Settings.InterimPricePercent
-- 2024-06-11T13:00:32.963Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.963','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624942
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> org+client.Organisation
-- Column: ModCntr_Settings.AD_Org_ID
-- 2024-06-11T13:00:32.968Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-06-11 16:00:32.968','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617989
;

