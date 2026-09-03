-- Run mode: SWING_CLIENT

-- Column: M_AttributeInstance.Value
-- 2025-10-22T22:32:20.447Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-10-22 22:32:20.447000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8501
;

-- 2025-10-22T22:32:21.618Z
INSERT INTO t_alter_column values('m_attributeinstance','Value','VARCHAR(255)',null,null)
;

