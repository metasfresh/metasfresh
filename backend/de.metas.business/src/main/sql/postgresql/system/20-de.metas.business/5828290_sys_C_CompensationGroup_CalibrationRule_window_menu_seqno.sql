-- Calibration rules window (5828260): give the menu node a SeqNo that is not tied with a sibling.
-- Folder 1000037 already has "Kompensationsgruppe Schema Kategorie" at 8 and "Produkt Kategorie" at 9;
-- 12 is the first free value after the last sibling (11).
--
-- Note on AD_Window.IsSOTrx: the window 542195 deliberately keeps IsSOTrx='Y'. The relation type
-- calibration rule -> sales orders (5828270) leaves the target window empty, so the target window
-- is resolved from the C_Order sales window of the instance, depending on the source document's IsSOTrx.
UPDATE AD_TreeNodeMM
SET SeqNo = 12, Updated = TO_TIMESTAMP('2026-10-06 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE Node_ID = 542365 /* AD_Menu Kalibrierungsregeln */ AND Parent_ID = 1000037
;
