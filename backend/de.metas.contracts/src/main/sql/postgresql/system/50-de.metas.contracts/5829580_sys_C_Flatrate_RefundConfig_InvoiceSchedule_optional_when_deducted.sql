-- Make C_Flatrate_RefundConfig.C_InvoiceSchedule_ID optional for a refund config that is deducted at payment.
-- A config deducted at payment books its bonus at the payment allocation and is never invoiced by the refund
-- engine, so it needs no invoice schedule. The field stays mandatory (and shown) for every other config.
--   AD_Column 560131 (C_InvoiceSchedule_ID): IsMandatory N, MandatoryLogic @IsDeductedAtPayment@=N
--   physical column: drop the NOT NULL constraint (must match AD_Column.IsMandatory)
--   AD_Field 564193 (Terminplan Rechnung): DisplayLogic @IsDeductedAtPayment@=N (hidden when deducted at payment)

-- 1) AD_Column: no longer unconditionally mandatory; mandatory only when NOT deducted at payment
UPDATE AD_Column
SET IsMandatory='N',
    MandatoryLogic='@IsDeductedAtPayment@=N',
    Updated=TO_TIMESTAMP('2026-10-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=560131;

-- 2) Physical column: drop NOT NULL so a deducted-at-payment config can be saved without an invoice schedule
INSERT INTO t_alter_column values('c_flatrate_refundconfig','C_InvoiceSchedule_ID','NUMERIC(10)',null,null);
INSERT INTO t_alter_column values('c_flatrate_refundconfig','C_InvoiceSchedule_ID',null,'NULL',null);

-- 3) AD_Field: hide the field when the config is deducted at payment
UPDATE AD_Field
SET DisplayLogic='@IsDeductedAtPayment@=N',
    Updated=TO_TIMESTAMP('2026-10-16 10:00:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Field_ID=564193;
