-- 2026-03-12
-- Reorder GTIN/EAN_TU UI elements in Packvorschrift Nachweis window (540717)

-- Move GTIN to front (SeqNo=10)
UPDATE AD_UI_Element SET SeqNo = 10, Updated = TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 99
WHERE AD_UI_Element_ID = 563180;

-- Move EAN_TU to advanced edit
UPDATE AD_UI_Element SET IsAdvancedField = 'Y', Updated = TO_TIMESTAMP('2026-03-12 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 99
WHERE AD_UI_Element_ID = 563186;
