-- Run mode: SWING_CLIENT

-- Column: M_Replenish.Level_Min
-- 2025-08-26T09:06:24.954Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-08-26 09:06:24.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=1967
;

-- Column: M_Replenish.Level_Max
-- 2025-08-26T09:06:45.191Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-08-26 09:06:45.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=1968
;

-- 2025-08-26T09:07:00.914Z
INSERT INTO t_alter_column values('m_replenish','Level_Max','NUMERIC',null,null)
;

-- 2025-08-26T09:07:00.929Z
INSERT INTO t_alter_column values('m_replenish','Level_Max',null,'NULL',null)
;

-- 2025-08-26T09:07:32.526Z
INSERT INTO t_alter_column values('m_replenish','Level_Max','NUMERIC',null,null)
;

