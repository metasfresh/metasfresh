-- Run mode: SWING_CLIENT

-- Column: M_PickingSlot.PickingSlot
-- 2025-03-05T12:45:11.509Z
UPDATE AD_Column SET AD_Reference_ID=10, AD_Reference_Value_ID=NULL, FieldLength=255, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-03-05 12:45:11.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549930
;

-- 2025-03-05T12:45:14.027Z
INSERT INTO t_alter_column values('m_pickingslot','PickingSlot','VARCHAR(255)',null,null)
;

-- Name: PickingSlot
-- 2025-03-05T12:48:36.136Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540426
;

-- 2025-03-05T12:48:36.156Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=540426
;

