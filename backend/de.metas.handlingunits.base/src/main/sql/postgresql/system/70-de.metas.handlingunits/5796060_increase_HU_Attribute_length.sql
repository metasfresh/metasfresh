-- Run mode: SWING_CLIENT

-- Column: M_HU_Attribute.ValueInitial
-- 2026-03-26T17:09:32.792Z
UPDATE AD_Column SET FieldLength=99999999,Updated=TO_TIMESTAMP('2026-03-26 17:09:32.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550509
;

-- Column: M_HU_Attribute.ValueInitial
-- 2026-03-26T17:09:49.544Z
UPDATE AD_Column SET AD_Reference_ID=36, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-03-26 17:09:49.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550509
;

-- 2026-03-26T17:09:51.436Z
INSERT INTO t_alter_column values('m_hu_attribute','ValueInitial','TEXT',null,null)
;

-- Run mode: SWING_CLIENT

-- Column: M_HU_Trx_Attribute.Value
-- 2026-03-26T17:14:13.240Z
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=99999999, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-03-26 17:14:13.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549305
;

-- 2026-03-26T17:14:14.806Z
INSERT INTO t_alter_column values('m_hu_trx_attribute','Value','TEXT',null,null)
;

-- Column: M_HU_Trx_Attribute.ValueInitial
-- 2026-03-26T17:14:26.074Z
UPDATE AD_Column SET FieldLength=99999999,Updated=TO_TIMESTAMP('2026-03-26 17:14:26.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550511
;

-- Column: M_HU_Trx_Attribute.ValueInitial
-- 2026-03-26T17:14:33.041Z
UPDATE AD_Column SET AD_Reference_ID=36, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-03-26 17:14:33.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550511
;

-- 2026-03-26T17:14:33.708Z
INSERT INTO t_alter_column values('m_hu_trx_attribute','ValueInitial','TEXT',null,null)
;

-- Column: M_HU_Attribute.Value
-- 2026-03-26T17:14:56.615Z
UPDATE AD_Column SET AD_Reference_ID=36, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-03-26 17:14:56.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=549254
;

-- 2026-03-26T17:14:57.361Z
INSERT INTO t_alter_column values('m_hu_attribute','Value','TEXT',null,null)
;

