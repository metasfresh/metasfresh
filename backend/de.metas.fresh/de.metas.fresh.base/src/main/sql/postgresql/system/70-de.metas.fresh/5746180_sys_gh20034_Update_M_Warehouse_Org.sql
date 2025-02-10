-- Run mode: SWING_CLIENT

-- Table: M_Warehouse
-- 2025-02-10T08:44:20.589Z

UPDATE M_Warehouse
SET AD_Org_ID = 1000000
WHERE TRUE
;

ALTER TABLE M_Warehouse
    ADD CONSTRAINT M_Warehouse_AD_Org_Check
        CHECK (AD_Org_ID <> 0)
;

COMMENT ON CONSTRAINT M_Warehouse_AD_Org_Check ON M_Warehouse
    IS 'Ensures that AD_Org_ID cannot be set to 0 to prevent MD_Stock_Update_From_M_HUs process to fail.'
;

UPDATE AD_Table SET TechnicalNote='M_Warehouse_AD_Org_Check constraint ensures that AD_Org_ID cannot be set to 0 to prevent MD_Stock_Update_From_M_HUs process to fail.',Updated=TO_TIMESTAMP('2025-02-10 08:44:20.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=190
;

