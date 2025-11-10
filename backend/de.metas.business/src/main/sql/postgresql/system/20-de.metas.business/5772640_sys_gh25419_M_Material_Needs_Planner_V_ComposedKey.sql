-- Run mode: SWING_CLIENT

-- Name: M_Material_Needs_Planner_V
-- 2025-10-07T09:26:00.916Z
UPDATE AD_Reference SET Description='M_Material_Needs_Planner_V is a view with a composed key, there is no M_Material_Needs_Planner_V_ID. Please use Warehouse_ID or Product_ID as AD_Key',Updated=TO_TIMESTAMP('2025-10-07 09:26:00.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541982
;

-- 2025-10-07T09:26:00.925Z
UPDATE AD_Reference_Trl trl SET Description='M_Material_Needs_Planner_V is a view with a composed key, there is no M_Material_Needs_Planner_V_ID. Please use Warehouse_ID or Product_ID as AD_Key' WHERE AD_Reference_ID=541982 AND AD_Language='de_DE'
;

-- Reference: M_Material_Needs_Planner_V
-- Table: M_Material_Needs_Planner_V
-- Key: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-10-07T09:26:08.101Z
UPDATE AD_Ref_Table SET AD_Key=589737,Updated=TO_TIMESTAMP('2025-10-07 09:26:08.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541982
;

-- Column: M_Material_Needs_Planner_V.M_Material_Needs_Planner_V_ID
-- 2025-10-07T09:26:40.138Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590905
;

-- 2025-10-07T09:26:40.145Z
DELETE FROM AD_Field WHERE AD_Column_ID=590905
;

-- 2025-10-07T09:26:40.145Z
DELETE FROM AD_Column WHERE AD_Column_ID=590905
;

-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-10-07T09:26:51.694Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-10-07 09:26:51.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589737
;

-- Column: M_Material_Needs_Planner_V.M_Product_ID
-- 2025-10-07T09:26:56.900Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-10-07 09:26:56.900000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589739
;
