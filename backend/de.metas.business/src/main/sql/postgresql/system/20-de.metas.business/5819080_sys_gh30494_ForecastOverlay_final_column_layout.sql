-- Final column layout of the forecast-overlay window "Prognosemenge pro Produkt" (AD_Window 542184).
--
-- Resulting order, form and grid alike: Prognose, Belegstatus, Menge, Zugesagter Termin, Sektion.
--
-- German labels below are the window's own captions; their en_US equivalents are Forecast, Status,
-- Quantity, Date Promised, Organisation and UOM.
--
-- Three changes:
--   1. Menge (Quantity) moves behind Belegstatus (Status); it previously sat directly behind Prognose.
--   2. Maßeinheit (UOM) is hidden -- the quantity is always in the product's own UOM, which the
--      product already carries, so the column only duplicated information.
--   3. Zugesagter Termin (Date Promised) becomes a date-only field. The overlay presents the HEADER's
--      DatePromised, and M_Forecast.DatePromised is AD_Reference_ID=15 (Date). The view column was
--      created with 16 (Date+Time), copied from M_ForecastLine.DatePromised, so the grid rendered a
--      meaningless 00:00:00 on every row.
--
-- All values are absolute rather than relative, so the script converges on any database regardless of
-- which of the earlier layout scripts it has already seen.
--
-- Both layers are renumbered: AD_UI_Element.SeqNo/SeqNoGrid is what the WebUI orders by,
-- AD_Field.SeqNo/SeqNoGrid is the independent legacy ordering. The hidden Maßeinheit keeps a
-- sequence number at the end of the list so it cannot interleave with the visible columns.

-- 1 + 2: AD_UI_Element layer
UPDATE AD_UI_Element SET SeqNo=10, SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-08-14 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653130 -- Prognose
;
UPDATE AD_UI_Element SET SeqNo=20, SeqNoGrid=20, Updated=TO_TIMESTAMP('2026-08-14 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653131 -- Belegstatus
;
UPDATE AD_UI_Element SET SeqNo=30, SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-08-14 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653133 -- Menge
;
UPDATE AD_UI_Element SET SeqNo=40, SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-14 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653132 -- Zugesagter Termin
;
UPDATE AD_UI_Element SET SeqNo=50, SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-14 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653135 -- Sektion
;
UPDATE AD_UI_Element SET SeqNo=60, SeqNoGrid=60, IsDisplayed='N', IsDisplayedGrid='N', Updated=TO_TIMESTAMP('2026-08-14 10:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653134 -- Maßeinheit
;

-- 1 + 2: AD_Field layer
UPDATE AD_Field SET SeqNo=10, SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-08-14 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782280 -- Prognose
;
UPDATE AD_Field SET SeqNo=20, SeqNoGrid=20, Updated=TO_TIMESTAMP('2026-08-14 10:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782281 -- Belegstatus
;
UPDATE AD_Field SET SeqNo=30, SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-08-14 10:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782283 -- Menge
;
UPDATE AD_Field SET SeqNo=40, SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-14 10:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782282 -- Zugesagter Termin
;
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-14 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782285 -- Sektion
;
UPDATE AD_Field SET SeqNo=60, SeqNoGrid=60, IsDisplayed='N', IsDisplayedGrid='N', Updated=TO_TIMESTAMP('2026-08-14 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=782284 -- Maßeinheit
;

-- 3: date-only, mirroring M_Forecast.DatePromised (AD_Column 557887, AD_Reference_ID=15)
UPDATE AD_Column SET AD_Reference_ID=15, Updated=TO_TIMESTAMP('2026-08-14 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=593298 -- M_Forecast_ProductQty_V.DatePromised
;
