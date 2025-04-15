-- Run mode: SWING_CLIENT

-- Column: AD_Element.Name
-- 2025-04-14T17:25:05.690Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:25:05.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2603
;

-- 2025-04-14T17:25:07.106Z
INSERT INTO t_alter_column values('ad_element','Name','VARCHAR(255)',null,null)
;

-- Column: AD_Element.PrintName
-- 2025-04-14T17:25:26.799Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:25:26.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=4299
;

-- 2025-04-14T17:25:27.431Z
INSERT INTO t_alter_column values('ad_element','PrintName','VARCHAR(255)',null,null)
;

-- Column: AD_Element.PO_Name
-- 2025-04-14T17:25:43.952Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:25:43.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=6285
;

-- 2025-04-14T17:25:44.541Z
INSERT INTO t_alter_column values('ad_element','PO_Name','VARCHAR(255)',null,null)
;

-- Column: AD_Element.PO_PrintName
-- 2025-04-14T17:25:50.231Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:25:50.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=6286
;

-- 2025-04-14T17:25:50.803Z
INSERT INTO t_alter_column values('ad_element','PO_PrintName','VARCHAR(255)',null,null)
;

-- Column: AD_Element_Trl.Name
-- 2025-04-14T17:26:10.709Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:26:10.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2646
;

-- 2025-04-14T17:26:11.293Z
INSERT INTO t_alter_column values('ad_element_trl','Name','VARCHAR(255)',null,null)
;

-- Column: AD_Element_Trl.PO_Description
-- 2025-04-14T17:26:29.100Z
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=2000, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-04-14 17:26:29.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=6448
;

-- 2025-04-14T17:26:29.690Z
INSERT INTO t_alter_column values('ad_element_trl','PO_Description','TEXT',null,null)
;

-- Column: AD_Element_Trl.PO_Description
-- 2025-04-14T17:26:48.418Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2025-04-14 17:26:48.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=6448
;

-- 2025-04-14T17:26:49.004Z
INSERT INTO t_alter_column values('ad_element_trl','PO_Description','VARCHAR(2000)',null,null)
;

-- Column: AD_Element_Trl.PO_Name
-- 2025-04-14T17:26:58.903Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:26:58.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=6450
;

-- 2025-04-14T17:26:59.464Z
INSERT INTO t_alter_column values('ad_element_trl','PO_Name','VARCHAR(255)',null,null)
;

-- Column: AD_Element_Trl.PO_PrintName
-- 2025-04-14T17:27:04.718Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:27:04.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=6451
;

-- 2025-04-14T17:27:05.323Z
INSERT INTO t_alter_column values('ad_element_trl','PO_PrintName','VARCHAR(255)',null,null)
;

-- Column: AD_Element_Trl.PrintName
-- 2025-04-14T17:27:15.722Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-04-14 17:27:15.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=4300
;

-- 2025-04-14T17:27:16.616Z
INSERT INTO t_alter_column values('ad_element_trl','PrintName','VARCHAR(255)',null,null)
;

