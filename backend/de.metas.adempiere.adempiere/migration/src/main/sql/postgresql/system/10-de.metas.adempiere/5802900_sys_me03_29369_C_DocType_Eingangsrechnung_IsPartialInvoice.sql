-- 2026-05-18
-- C_DocType -- set IsPartialInvoice='Y' default on the vendor Eingangsrechnung doctype (DocBaseType=API, IsSOTrx=N)
-- iter-3 https://github.com/metasfresh/me03/issues/29369
--
-- The iter-3 design (per PR reviewer comment #14) drives C_Invoice.IsPartialInvoice from the
-- C_DocType.IsPartialInvoice default in the BEFORE_NEW interceptor. The Eingangsrechnung doctype
-- (vendor invoice / "incoming invoice") was missing the 'Y' default -- vendor invoices created
-- with this doctype ended up with C_Invoice.IsPartialInvoice=NULL, causing the alloc cascade to
-- treat them as Final (no proration cap), yielding -21000 instead of -12000 in
-- splitPaymentReversalCascade etc.
--
-- The complementary 5801420 migration set Endabrechnung (DocSubType=QI, "final settlement") to 'N'.
-- This migration sets the partial-invoice flow doctype to 'Y'.
--
-- Defensive constraint (Name + DocBaseType + IsSOTrx + IsPartialInvoice IS DISTINCT FROM 'Y'):
-- narrowly targets the single Eingangsrechnung vendor doctype; no-op if already 'Y' or absent.

UPDATE C_DocType
   SET IsPartialInvoice = 'Y',
       Updated          = TO_TIMESTAMP('2026-05-18 00:00', 'YYYY-MM-DD HH24:MI'),
       UpdatedBy        = 100
 WHERE Name              = 'Eingangsrechnung'
   AND DocBaseType       = 'API'
   AND IsSOTrx           = 'N'
   AND IsPartialInvoice IS DISTINCT FROM 'Y'
;
