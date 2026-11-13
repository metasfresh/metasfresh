-- Run mode: SWING_CLIENT

-- Reference: PP_Product_BOMLine for M_Product
-- Table: PP_Product_BOMLine
-- Key: PP_Product_BOMLine.PP_Product_BOMLine_ID
-- 2026-10-19T09:46:25.708Z
UPDATE AD_Ref_Table SET AD_Key=53365, AD_Table_ID=53019, WhereClause='PP_Product_BOMLine.M_Product_ID = @M_Product_ID/-1@ OR EXISTS (SELECT 1 from PP_Product_BOM b where b.PP_Product_BOM_ID = PP_Product_BOMLine.PP_Product_BOM_ID AND b.M_Product_ID = @M_Product_ID/-1@)',Updated=TO_TIMESTAMP('2026-10-19 09:46:25.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541135
;

