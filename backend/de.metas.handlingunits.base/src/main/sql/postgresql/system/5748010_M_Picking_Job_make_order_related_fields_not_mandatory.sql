-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job.C_BPartner_ID
-- 2025-02-27T07:38:22.199Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-27 07:38:22.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=577734
;

-- 2025-02-27T07:38:22.953Z
INSERT INTO t_alter_column values('m_picking_job','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2025-02-27T07:38:22.958Z
INSERT INTO t_alter_column values('m_picking_job','C_BPartner_ID',null,'NULL',null)
;

-- Column: M_Picking_Job.C_BPartner_Location_ID
-- 2025-02-27T07:38:27.199Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-27 07:38:27.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=577735
;

-- 2025-02-27T07:38:28.618Z
INSERT INTO t_alter_column values('m_picking_job','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- 2025-02-27T07:38:28.629Z
INSERT INTO t_alter_column values('m_picking_job','C_BPartner_Location_ID',null,'NULL',null)
;

-- Column: M_Picking_Job.C_Order_ID
-- 2025-02-27T07:38:34.250Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-27 07:38:34.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=577733
;

-- 2025-02-27T07:38:34.878Z
INSERT INTO t_alter_column values('m_picking_job','C_Order_ID','NUMERIC(10)',null,null)
;

-- 2025-02-27T07:38:34.881Z
INSERT INTO t_alter_column values('m_picking_job','C_Order_ID',null,'NULL',null)
;

-- Column: M_Picking_Job.DeliveryDate
-- 2025-02-27T07:38:41.320Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-27 07:38:41.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=587553
;

-- 2025-02-27T07:38:42.228Z
INSERT INTO t_alter_column values('m_picking_job','DeliveryDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2025-02-27T07:38:42.236Z
INSERT INTO t_alter_column values('m_picking_job','DeliveryDate',null,'NULL',null)
;

-- Column: M_Picking_Job.DeliveryToAddress
-- 2025-02-27T07:38:56.468Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-27 07:38:56.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=577736
;

-- 2025-02-27T07:38:57.056Z
INSERT INTO t_alter_column values('m_picking_job','DeliveryToAddress','VARCHAR(255)',null,null)
;

-- 2025-02-27T07:38:57.059Z
INSERT INTO t_alter_column values('m_picking_job','DeliveryToAddress',null,'NULL',null)
;

-- Column: M_Picking_Job.PreparationDate
-- 2025-02-27T07:39:13.423Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-27 07:39:13.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=577737
;

-- 2025-02-27T07:39:14.110Z
INSERT INTO t_alter_column values('m_picking_job','PreparationDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2025-02-27T07:39:14.114Z
INSERT INTO t_alter_column values('m_picking_job','PreparationDate',null,'NULL',null)
;

