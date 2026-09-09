-- Drop C_Invoice.IsPartialInvoice field placement from the legacy invoice tabs.
-- Per https://github.com/metasfresh/me03/issues/29369: IsPartialInvoice is a technical
-- field used internally by the iter-3 Partial-vs-Final allocation rule
-- (@DocValidate AFTER_COMPLETE on C_Invoice). Users should not see or edit it on the
-- invoice — they are looking at the document type, which carries the default via
-- C_DocType.IsPartialInvoice. The doctype-level placement on tab 167 (Belegart) stays;
-- the per-invoice placements on tabs 263 (Rechnung) and 290 (Eingangsrechnung_OLD) go.
--
-- Removed AD_Field rows:
--   777448 — AD_Tab_ID=263 (Rechnung, legacy AR invoice window)
--   777449 — AD_Tab_ID=290 (Eingangsrechnung_OLD, legacy AP invoice window)
--
-- Kept AD_Field row (NOT touched by this migration):
--   777445 — AD_Tab_ID=167 (Belegart, C_DocType window) — admin doctype default config

DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (777448, 777449);
DELETE FROM AD_Field     WHERE AD_Field_ID IN (777448, 777449);
