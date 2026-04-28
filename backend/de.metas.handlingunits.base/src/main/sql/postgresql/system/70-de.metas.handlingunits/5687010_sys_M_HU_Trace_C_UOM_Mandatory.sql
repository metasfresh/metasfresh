-- Column: M_HU_Trace.C_UOM_ID
-- Column: M_HU_Trace.C_UOM_ID
-- 2023-05-04T07:28:52.492Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-05-04 10:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586466
;

-- 2023-05-04T07:28:57.410Z
INSERT INTO t_alter_column values('m_hu_trace','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2023-05-04T07:28:57.419Z
INSERT INTO t_alter_column values('m_hu_trace','C_UOM_ID',null,'NOT NULL',null)
;

