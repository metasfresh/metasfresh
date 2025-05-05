-- Field: Vertrag(541798,de.metas.endcustomer.is184) -> Vertrag(547531,de.metas.endcustomer.is184) -> Preissystem


-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Produkt
-- Column: C_Flatrate_Term.M_Product_ID
-- 2024-06-14T09:10:21.656Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''X''@=''Subscr''|@Type_Conditions/''X''@=''QualityBsd''|@Type_Conditions/''X''@=''Procuremnt''|@Type_Conditions/''X''@=''Refund''|@Type_Conditions/''X''@=''Commission''|@Type_Conditions/''X''@=''MediatedCommission''|@Type_Conditions/''X''@=''MarginCommission''|@Type_Conditions/''X''@=''ModularContract''|@Type_Conditions/''X''@=''InterimInvoice''',Updated=TO_TIMESTAMP('2024-06-14 12:10:21.656','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=559779
;

-- Field: Vertrag(541798,de.metas.endcustomer.is184) -> Vertrag(547531,de.metas.endcustomer.is184) -> Planmenge pro Maßeinheit
-- Column: C_Flatrate_Term.PlannedQtyPerUnit
-- 2024-06-14T09:11:54.231Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''FlatFee''|@Type_Conditions@=''Subscr''|@Type_Conditions@=''ModularContract''|@Type_Conditions@=''InterimInvoice''',Updated=TO_TIMESTAMP('2024-06-14 12:11:54.231','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727851
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Planmenge pro Maßeinheit
-- Column: C_Flatrate_Term.PlannedQtyPerUnit
-- 2024-06-14T11:31:31.277Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''FlatFee''|@Type_Conditions@=''Subscr|@Type_Conditions@=''ModularContract''|@Type_Conditions@=''InterimInvoice''',Updated=TO_TIMESTAMP('2024-06-14 14:31:31.277','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=559781
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Einzelpreis
-- Column: C_Flatrate_Term.PriceActual
-- 2024-06-14T11:31:45.638Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''Subscr''|@Type_Conditions@=''ModularContract''|@Type_Conditions@=''InterimInvoice''',Updated=TO_TIMESTAMP('2024-06-14 14:31:45.637','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=559765
;





-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Einzelpreis
-- Column: C_Flatrate_Term.PriceActual
-- 2024-06-14T11:40:05.234Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2024-06-14 14:40:05.234','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548258
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Maßeinheit
-- Column: C_Flatrate_Term.C_UOM_ID
-- 2024-06-14T11:40:09.223Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-06-14 14:40:09.223','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548269
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Planmenge pro Maßeinheit
-- Column: C_Flatrate_Term.PlannedQtyPerUnit
-- 2024-06-14T11:40:14.659Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-06-14 14:40:14.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548270
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 10 -> prices.Währung
-- Column: C_Flatrate_Term.C_Currency_ID
-- 2024-06-14T11:40:26.115Z
UPDATE AD_UI_Element SET AD_Field_ID=559766, Description='Die Währung für diesen Eintrag', Help='Bezeichnet die auf Dokumenten oder Berichten verwendete Währung', Name='Währung',Updated=TO_TIMESTAMP('2024-06-14 14:40:26.115','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618256
;

-- UI Column: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20
-- UI Element Group: harvesting details
-- 2024-06-14T11:40:38.992Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540631, SeqNo=40,Updated=TO_TIMESTAMP('2024-06-14 14:40:38.992','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551740
;

-- UI Column: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20
-- UI Element Group: harvesting details
-- 2024-06-14T11:40:49.551Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2024-06-14 14:40:49.551','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551740
;

-- UI Column: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> main -> 20
-- UI Element Group: org
-- 2024-06-14T11:40:51.990Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2024-06-14 14:40:51.99','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541108
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> Währung
-- Column: C_Flatrate_Term.C_Currency_ID
-- 2024-06-14T11:42:43.416Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''Subscr''|@Type_Conditions@=''ModularContract''|@Type_Conditions@=''InterimInvoice''',Updated=TO_TIMESTAMP('2024-06-14 14:42:43.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=559766
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Vertrag(540859,de.metas.contracts) -> advanced edit -> 10 -> advanced edit.Währung
-- Column: C_Flatrate_Term.C_Currency_ID
-- 2024-06-14T11:44:10.056Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548259
;

