-- Hide fully-paid invoices from the invoice picker on an ESR import line.
--
-- The rule filtered on organisation only, so every invoice of the org was offered -- including ones
-- already settled (IsPaid='Y'). Those are noise the accountant has to scroll past, and picking one
-- achieves nothing: the shared action handler only allocates when the invoice is NOT paid, so a paid
-- invoice on a line is silently inert. Selecting a settled invoice is never the right answer, not
-- even for a second payment against it -- such a payment is booked as its own unallocated payment
-- and needs no invoice on the line at all.
--
-- C_Invoice.IsPaid is NOT NULL with default 'N', so a plain comparison needs no COALESCE.
--
-- Sole consumer is AD_Column 547647 (ESR_ImportLine.C_Invoice_ID), so this cannot affect any other
-- lookup. The rule is renamed because its name no longer describes what it filters.
UPDATE AD_Val_Rule
   SET Name='ESR_Invoice_Same_Org_NotPaid',
       Code='C_Invoice.AD_Org_ID = @AD_Org_ID@ AND C_Invoice.IsPaid=''N''',
       Updated=TO_TIMESTAMP('2026-08-26 14:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Val_Rule_ID=540186
;
