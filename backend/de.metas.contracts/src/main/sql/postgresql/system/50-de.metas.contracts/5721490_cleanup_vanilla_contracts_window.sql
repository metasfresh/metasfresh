-- Run mode: SWING_CLIENT

--UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-04-11T15:32:14.468Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624012
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-11T15:32:14.495Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624013
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-04-11T15:32:14.520Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624014
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-04-11T15:32:14.546Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624015
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-11T15:32:14.571Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624371
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-04-11T15:32:14.596Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624017
;

-- UI Element: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-04-11T15:32:14.622Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624018
;

-- UI Column: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10
-- UI Element Group: main
-- 2024-04-11T15:32:19.099Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=551739
;

-- UI Section: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028
-- UI Column: 10
-- 2024-04-11T15:32:22.304Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=547430
;

-- Tab: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D)
-- UI Section: 1000028
-- 2024-04-11T15:32:26.625Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=546080
;

-- 2024-04-11T15:32:26.628Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=546080
;

-- 2024-04-11T15:32:32.330Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727304
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-11T15:32:32.331Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727304
;

-- 2024-04-11T15:32:32.334Z
DELETE FROM AD_Field WHERE AD_Field_ID=727304
;

-- 2024-04-11T15:32:36.702Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727296
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Organisation
-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-04-11T15:32:36.704Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727296
;

-- 2024-04-11T15:32:36.707Z
DELETE FROM AD_Field WHERE AD_Field_ID=727296
;

-- 2024-04-11T15:32:36.758Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727297
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Aktiv
-- Column: ModCntr_Specific_Price.IsActive
-- 2024-04-11T15:32:36.760Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727297
;

-- 2024-04-11T15:32:36.763Z
DELETE FROM AD_Field WHERE AD_Field_ID=727297
;

-- 2024-04-11T15:32:36.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727299
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-04-11T15:32:36.811Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727299
;

-- 2024-04-11T15:32:36.814Z
DELETE FROM AD_Field WHERE AD_Field_ID=727299
;

-- 2024-04-11T15:32:36.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727300
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-04-11T15:32:36.861Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727300
;

-- 2024-04-11T15:32:36.863Z
DELETE FROM AD_Field WHERE AD_Field_ID=727300
;

-- 2024-04-11T15:32:36.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727301
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-04-11T15:32:36.908Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727301
;

-- 2024-04-11T15:32:36.911Z
DELETE FROM AD_Field WHERE AD_Field_ID=727301
;

-- 2024-04-11T15:32:36.954Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727302
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-04-11T15:32:36.956Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727302
;

-- 2024-04-11T15:32:36.958Z
DELETE FROM AD_Field WHERE AD_Field_ID=727302
;

-- 2024-04-11T15:32:37.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727303
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-04-11T15:32:37.005Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727303
;

-- 2024-04-11T15:32:37.008Z
DELETE FROM AD_Field WHERE AD_Field_ID=727303
;

-- 2024-04-11T15:32:37.051Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727295
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Mandant
-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-04-11T15:32:37.052Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727295
;

-- 2024-04-11T15:32:37.055Z
DELETE FROM AD_Field WHERE AD_Field_ID=727295
;

-- 2024-04-11T15:32:37.097Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727298
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Contract Specific Prices
-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-04-11T15:32:37.099Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727298
;

-- 2024-04-11T15:32:37.101Z
DELETE FROM AD_Field WHERE AD_Field_ID=727298
;

-- 2024-04-11T15:32:37.143Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727823
;

-- Field: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-11T15:32:37.145Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727823
;

-- 2024-04-11T15:32:37.147Z
DELETE FROM AD_Field WHERE AD_Field_ID=727823
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- Table: ModCntr_Specific_Price
-- Tab: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices(547499,D)
-- Window: Vertrag_OLD(540359,de.metas.contracts)
-- EntityType: de.metas.contracts
-- 2024-04-11T15:33:30.972Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541475
;

-- Tab: Vertrag_OLD(540359,de.metas.contracts) -> Contract Specific Prices
-- Table: ModCntr_Specific_Price
-- 2024-04-11T15:33:42.992Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=547499
;

-- 2024-04-11T15:33:42.994Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=547499
;

