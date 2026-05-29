-- Run mode: SWING_CLIENT

-- Column: M_Material_Tracking.M_QualityInsp_LagerKonf_Version_ID
-- 2025-07-01T08:24:50.770Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-07-01 08:24:50.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551273
;

-- 2025-07-01T08:25:14.008Z
INSERT INTO t_alter_column values('m_material_tracking','M_QualityInsp_LagerKonf_Version_ID','NUMERIC(10)',null,null)
;

-- 2025-07-01T08:25:14.061Z
INSERT INTO t_alter_column values('m_material_tracking','M_QualityInsp_LagerKonf_Version_ID',null,'NULL',null)
;

