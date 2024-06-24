-- Field: Wareneingangsdisposition -> Wareneingangsdisposition -> Referenz
-- Column: M_ReceiptSchedule.POReference
-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2024-05-30T08:32:19.147Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588327,728771,0,540526,0,TO_TIMESTAMP('2024-05-30 09:32:18','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',0,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','N','N','Referenz',0,220,0,1,1,TO_TIMESTAMP('2024-05-30 09:32:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-30T08:32:19.150Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-30T08:32:19.167Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2024-05-30T08:32:19.179Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728771
;

-- 2024-05-30T08:32:19.181Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728771)
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Referenz
-- Column: M_ReceiptSchedule.POReference
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2024-05-30T08:33:04.326Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728771,0,540526,540132,624783,'F',TO_TIMESTAMP('2024-05-30 09:33:04','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','N','N','N',0,'Referenz',50,0,0,TO_TIMESTAMP('2024-05-30 09:33:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Referenz
-- Column: M_ReceiptSchedule.POReference
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2024-05-30T08:33:36.693Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624783
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Geschäftspartner
-- Column: M_ReceiptSchedule.C_BPartner_ID
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Geschäftspartner
-- Column: M_ReceiptSchedule.C_BPartner_ID
-- 2024-05-30T08:33:36.701Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541875
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Produkt
-- Column: M_ReceiptSchedule.M_Product_ID
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Produkt
-- Column: M_ReceiptSchedule.M_Product_ID
-- 2024-05-30T08:33:36.710Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541870
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Merkmale
-- Column: M_ReceiptSchedule.M_AttributeSetInstance_ID
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Merkmale
-- Column: M_ReceiptSchedule.M_AttributeSetInstance_ID
-- 2024-05-30T08:33:36.718Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541871
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Bestellte TU-Menge
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Bestellte TU-Menge
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- 2024-05-30T08:33:36.725Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542028
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- 2024-05-30T08:33:36.732Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541847
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- 2024-05-30T08:33:36.740Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541844
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Bestellt
-- Column: M_ReceiptSchedule.QtyOrdered
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Bestellt
-- Column: M_ReceiptSchedule.QtyOrdered
-- 2024-05-30T08:33:36.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541872
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Eingegangen
-- Column: M_ReceiptSchedule.QtyMoved
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Eingegangen
-- Column: M_ReceiptSchedule.QtyMoved
-- 2024-05-30T08:33:36.755Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541874
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Mengeneinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Mengeneinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- 2024-05-30T08:33:36.761Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541873
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> dates.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- 2024-05-30T08:33:36.767Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541878
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2024-05-30T08:33:36.773Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541817
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2024-05-30T08:33:36.779Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541818
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2024-05-30T08:33:36.786Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541883
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2024-05-30T08:33:36.792Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541821
;

-- UI Element: Wareneingangsdisposition -> Wareneingangsdisposition.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2024-05-30T08:33:36.799Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2024-05-30 09:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541880
;

