
-- Column: C_BP_BankAccount_InvoiceAutoAllocateRule.C_DocTypeInvoice_ID
-- 2025-10-20T12:57:48.441Z
UPDATE AD_Column SET AD_Val_Rule_ID=540294,Updated=TO_TIMESTAMP('2025-10-20 12:57:48.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=574197
;

-- Column: C_Order.LC_Date
-- 2025-10-20T10:42:51.289Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-10-20 10:42:51.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591278
;

-- Column: C_InvoicePaySchedule.C_Invoice_ID
-- 2025-10-20T11:18:48.166Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N', MandatoryLogic='',Updated=TO_TIMESTAMP('2025-10-20 11:18:48.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8312
;

-- 2025-10-20T11:18:50.116Z
INSERT INTO t_alter_column values('c_invoicepayschedule','C_Invoice_ID','NUMERIC(10)',null,null)
;

-- 2025-10-20T11:18:50.124Z
INSERT INTO t_alter_column values('c_invoicepayschedule','C_Invoice_ID',null,'NOT NULL',null)
;

