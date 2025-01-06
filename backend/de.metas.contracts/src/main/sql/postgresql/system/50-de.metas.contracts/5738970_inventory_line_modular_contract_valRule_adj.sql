-- Name: Modular_Flatrate_Term_For_InventoryLine
-- 2024-11-12T11:58:11.606Z
UPDATE AD_Val_Rule SET Code='c_flatrate_term.c_flatrate_term_id IN (SELECT DISTINCT contract.c_flatrate_term_id FROM M_Warehouse w INNER JOIN C_Order o ON ( w.M_Warehouse_ID = o.M_Warehouse_ID AND o.isSOTrx = ''N'' ) INNER JOIN c_flatrate_term contract ON (o.C_Order_ID = contract.C_Order_Term_ID) INNER JOIN C_Flatrate_Conditions conditions ON (contract.c_flatrate_conditions_id = conditions.c_flatrate_conditions_id) WHERE conditions.type_conditions = ''ModularContract'' AND w.M_Warehouse_ID = @M_Warehouse_ID/-1@)',Updated=TO_TIMESTAMP('2024-11-12 12:58:11.603','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540643
;

