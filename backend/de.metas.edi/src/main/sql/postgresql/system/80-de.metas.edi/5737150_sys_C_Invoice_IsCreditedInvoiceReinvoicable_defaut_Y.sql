-- Column: C_Invoice.IsCreditedInvoiceReinvoicable
-- Column: C_Invoice.IsCreditedInvoiceReinvoicable
-- 2024-10-16T15:55:23.420Z
UPDATE AD_Column SET DefaultValue='Y', TechnicalNote='Default=Y because if N, then also reversed standalone-CreditMemos (i.e. that are not related to an invoice) are not invoicable again.
With CreditMemos that are not standalone, i.e. are created for an invoice, the value is anyways set be the user.
So, the default-value comes into play only for standalone-CreditMemos',Updated=TO_TIMESTAMP('2024-10-16 15:55:23.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=552530
;

-- 2024-10-16T15:55:25.508Z
INSERT INTO t_alter_column values('c_invoice','IsCreditedInvoiceReinvoicable','CHAR(1)',null,'Y')
;

-- 2024-10-16T15:55:26.751Z
UPDATE C_Invoice SET IsCreditedInvoiceReinvoicable='Y' WHERE IsCreditedInvoiceReinvoicable IS NULL
;

