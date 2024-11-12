-- Run mode: SWING_CLIENT

-- Column: AD_Client.AD_Archive_Storage_ID
-- 2024-11-12T06:44:09.174Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-11-12 06:44:09.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589383
;

-- 2024-11-12T06:44:10.917Z
INSERT INTO t_alter_column values('ad_client','AD_Archive_Storage_ID','NUMERIC(10)',null,null)
;

-- 2024-11-12T06:44:10.921Z
INSERT INTO t_alter_column values('ad_client','AD_Archive_Storage_ID',null,'NOT NULL',null)
;

