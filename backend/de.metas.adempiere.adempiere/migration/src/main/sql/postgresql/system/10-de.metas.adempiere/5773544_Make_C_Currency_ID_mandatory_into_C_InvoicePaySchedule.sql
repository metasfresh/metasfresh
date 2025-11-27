-- Column: C_InvoicePaySchedule.C_Currency_ID
-- 2025-10-21T14:29:21.290Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-10-21 14:29:21.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591411
;

-- 2025-10-21T14:30:24.994Z
INSERT INTO t_alter_column values('c_invoicepayschedule','C_Currency_ID','NUMERIC(10)',null,null)
;

-- 2025-10-21T14:30:24.997Z
INSERT INTO t_alter_column values('c_invoicepayschedule','C_Currency_ID',null,'NOT NULL',null)
;

