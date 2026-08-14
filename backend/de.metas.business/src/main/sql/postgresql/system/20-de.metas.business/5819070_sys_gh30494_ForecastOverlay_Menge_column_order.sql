-- Move Menge (and the Maßeinheit that qualifies it) directly behind Prognose in the forecast-overlay
-- grid, so the quantity sits BEFORE Zugesagter Termin as the requested layout shows.
--
-- Resulting order: Prognose, Menge, Maßeinheit, Belegstatus, Zugesagter Termin, Sektion.
--
-- Both grid layers are renumbered. AD_UI_Element.SeqNoGrid is what the WebUI orders by; AD_Field.SeqNoGrid
-- is the independent legacy ordering. They are set to the same values here because this tab has no other
-- fields, so there is nothing on either layer to collide with.

UPDATE AD_UI_Element SET SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-08-14 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653130 -- Prognose
;
UPDATE AD_UI_Element SET SeqNoGrid=20, Updated=TO_TIMESTAMP('2026-08-14 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653133 -- Menge
;
UPDATE AD_UI_Element SET SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-08-14 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653134 -- Maßeinheit
;
UPDATE AD_UI_Element SET SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-14 09:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653131 -- Belegstatus
;
UPDATE AD_UI_Element SET SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-14 09:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653132 -- Zugesagter Termin
;
UPDATE AD_UI_Element SET SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-08-14 09:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653135 -- Sektion
;

UPDATE AD_Field SET SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-08-14 09:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782280 -- Prognose
;
UPDATE AD_Field SET SeqNoGrid=20, Updated=TO_TIMESTAMP('2026-08-14 09:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782283 -- Menge
;
UPDATE AD_Field SET SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-08-14 09:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782284 -- Maßeinheit
;
UPDATE AD_Field SET SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-14 09:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782281 -- Belegstatus
;
UPDATE AD_Field SET SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-14 09:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782282 -- Zugesagter Termin
;
UPDATE AD_Field SET SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-08-14 09:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782285 -- Sektion
;
