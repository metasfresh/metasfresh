-- Point M_Forecast_ProductQty_V at its window.
--
-- The relation-type overlay resolves the window to open from the target side of the relation: it reads
-- AD_Ref_Table.AD_Window_ID, and failing that AD_Table.AD_Window_ID. With neither set, invoking
-- 'Sprung zu Prognose' dies with
--   PORelationException: Neither reference AD_Reference[ID=542128,Name=Prognosemenge pro Produkt]
--   nor table M_Forecast_ProductQty_V have an AD_Window_ID.
-- The table-level pointer is the right home for it: 542184 is this table's one and only window, so every
-- consumer that needs to know "which window shows this table" gets the same answer.
UPDATE AD_Table
SET AD_Window_ID=542184,
    Updated=TO_TIMESTAMP('2026-08-13 16:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Table_ID=542640
;
