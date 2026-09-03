-- Make C_BPartner.EInvoiceType mandatory in the UI whenever the partner is flagged as an
-- e-invoice recipient (IsEInvoiceRecipeint='Y'). If the recipient flag is set but no type is
-- chosen, invoice completion silently treats the partner as a non-e-invoice recipient, so no
-- XRechnung/ZUGFeRD document is generated and no error is shown. This MandatoryLogic makes the
-- field required in the UI. It is set on the AD_Column (not per field), so it applies to every
-- window that shows EInvoiceType. UI-only guard by design (no server-side interceptor).
--
-- AFFECTED RECORD: AD_Column 591242 (C_BPartner.EInvoiceType), EntityType 'D'.
-- Sets MandatoryLogic = @IsEInvoiceRecipeint/N@=Y (was empty). IsMandatory stays 'N'
-- (conditionally mandatory via the logic expression; no NOT NULL / DDL change).

-- 2026-07-15 12:00:00
UPDATE AD_Column
SET MandatoryLogic='@IsEInvoiceRecipeint/N@=Y',
    Updated=TO_TIMESTAMP('2026-07-15 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID=591242
;
