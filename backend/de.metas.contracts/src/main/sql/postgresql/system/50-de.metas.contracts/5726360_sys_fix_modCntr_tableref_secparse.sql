-- Run mode: SWING_CLIENT

-- Reference: ModCntr_Log_For_SO
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2024-06-16T06:56:19.364Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS(SELECT 1              from C_OrderLine ol                       LEFT JOIN C_Flatrate_Term ft on (ol.c_orderline_id = ft.c_orderline_term_id)              where ol.C_Order_ID = @C_Order_ID / -1@                AND ((ModCntr_Log.ad_table_id = get_table_id(''C_OrderLine'') AND ModCntr_Log.Record_ID = ol.C_OrderLine_ID)                  OR (ModCntr_Log.C_Flatrate_Term_ID = ft.c_flatrate_term_id)))',Updated=TO_TIMESTAMP('2024-06-16 08:56:19.364','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541805
;

