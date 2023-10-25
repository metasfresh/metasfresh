-- Run mode: SWING_CLIENT

-- Reference: ModCntr_Log_For_SO
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-09-22T13:31:54.637Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1
             FROM C_OrderLine ol
                      LEFT JOIN C_Flatrate_Term ft ON ol.c_orderline_id = ft.c_orderline_term_id
             WHERE ol.C_Order_ID = @C_Order_ID / -1@
               AND ((ModCntr_Log.ad_table_id = get_table_id(''C_OrderLine'') AND ModCntr_Log.Record_ID = ol.C_OrderLine_ID)
                 OR (ModCntr_Log.C_Flatrate_Term_ID = ft.c_flatrate_term_id)))',Updated=TO_TIMESTAMP('2023-09-22 16:31:54.637','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541805
;

-- Run mode: SWING_CLIENT

-- Reference: C_Order_with_lines_with_C_Flatrate_Conditions_ID
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2023-09-22T13:36:18.762Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_Flatrate_Conditions_ID IS NOT NULL AND c_order.issotrx = ''N'')',Updated=TO_TIMESTAMP('2023-09-22 16:36:18.761','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2023-09-22T13:36:51.038Z
UPDATE AD_Reference_Trl SET Name='C_Order_C_Flatrate_Term (PO)',Updated=TO_TIMESTAMP('2023-09-22 16:36:51.037','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=540754
;

-- 2023-09-22T13:36:53.509Z
UPDATE AD_Reference_Trl SET Name='C_Order_C_Flatrate_Term (PO)',Updated=TO_TIMESTAMP('2023-09-22 16:36:53.508','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=540754
;

-- 2023-09-22T13:36:55.443Z
UPDATE AD_Reference_Trl SET Name='C_ ORDER_WITH_LINES_WITH_C_FLATRATE_CONTItions_id (PO)',Updated=TO_TIMESTAMP('2023-09-22 16:36:55.442','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=540754
;

-- 2023-09-22T13:36:57.265Z
UPDATE AD_Reference_Trl SET Name='C_Order_with_lines_with_C_Flatrate_Conditions_ID (PO)',Updated=TO_TIMESTAMP('2023-09-22 16:36:57.264','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=540754
;

-- 2023-09-22T13:36:57.268Z
UPDATE AD_Reference SET Name='C_Order_with_lines_with_C_Flatrate_Conditions_ID (PO)' WHERE AD_Reference_ID=540754
;

-- 2023-09-22T13:36:59.669Z
UPDATE AD_Reference_Trl SET Name='C_Order_with_lines_with_C_Flatrate_Conditions_ID (PO)',Updated=TO_TIMESTAMP('2023-09-22 16:36:59.668','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Reference_ID=540754
;

-- Run mode: SWING_CLIENT

-- 2023-09-22T15:07:14.454Z
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540422
;

-- Run mode: SWING_CLIENT

-- 2023-09-22T15:07:58.301Z
UPDATE AD_RelationType SET Name='C_Order (PO) -> ModCntr_Log',Updated=TO_TIMESTAMP('2023-09-22 18:07:58.3','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540411
;

-- Run mode: SWING_CLIENT

-- 2023-09-22T15:29:19.607Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541822,Updated=TO_TIMESTAMP('2023-09-22 18:29:19.606','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540411
;

-- Name: ModCntr_Log_For_C_Order
-- 2023-09-22T15:29:30.797Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541791
;

-- 2023-09-22T15:29:30.812Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541791
;

