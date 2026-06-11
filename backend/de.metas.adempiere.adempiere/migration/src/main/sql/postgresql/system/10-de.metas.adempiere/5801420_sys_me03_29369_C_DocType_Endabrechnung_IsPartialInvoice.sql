-- 2026-05-08
-- C_DocType — flip Endabrechnung (DocBaseType=API, DocSubType=QI, IsSOTrx=N) to IsPartialInvoice='N'
-- iter-3 https://github.com/metasfresh/me03/issues/29369
--
-- The Endabrechnung doctype is German for "final settlement / final invoice" — its semantic intent
-- is to mark final-invoice flow on the purchase side. Flipping IsPartialInvoice='N' lets the AC #11
-- default mechanism propagate "Final" to C_Invoice automatically when the user picks Endabrechnung
-- in the IC→Generate Invoices process, without requiring a per-invoice manual flag override.
--
-- Defensive triple-constraint (id + DocBaseType + DocSubType + Name): if any of these don't match
-- on the target DB, the UPDATE simply does 0 rows and the migration is a no-op rather than
-- corrupting unrelated data.

UPDATE C_DocType
   SET IsPartialInvoice = 'N',
       Updated          = TO_TIMESTAMP('2026-05-08 00:00', 'YYYY-MM-DD HH24:MI'),
       UpdatedBy        = 100
 WHERE C_DocType_ID = 540940
   AND DocBaseType   = 'API'
   AND DocSubType    = 'QI'
   AND Name          = 'Endabrechnung';
