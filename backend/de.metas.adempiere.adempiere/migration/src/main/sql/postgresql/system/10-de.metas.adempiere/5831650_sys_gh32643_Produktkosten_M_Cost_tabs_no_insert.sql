-- Product cost rows (M_Cost) are created by the system only: a row created by hand would carry a cost price that no cost detail explains.
-- Tab "Produkt aktuelle Kosten" of window Produktkosten (344) and window "Produkt aktuelle Kosten" (540900) no longer allow new records.
-- Existing rows keep their editable fields.

-- window 344, tab "Produkt aktuelle Kosten"
UPDATE AD_Tab SET IsInsertRecord='N', Updated=TO_TIMESTAMP('2026-10-27 16:31:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Tab_ID=701
;

-- window 540900, tab "Produkt aktuelle Kosten"
UPDATE AD_Tab SET IsInsertRecord='N', Updated=TO_TIMESTAMP('2026-10-27 16:31:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Tab_ID=542374
;
