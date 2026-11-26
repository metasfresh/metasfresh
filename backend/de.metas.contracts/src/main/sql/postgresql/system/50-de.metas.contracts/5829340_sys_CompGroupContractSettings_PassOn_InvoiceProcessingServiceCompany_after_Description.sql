-- Compensation group contract settings: move the "Servicegebühr übernehmen von" field behind the description in the form (SeqNo 25 -> 40).
-- The grid position (SeqNoGrid 25, after the schema) is unchanged: the description is not shown in the grid.
UPDATE AD_UI_Element SET SeqNo = 40, Updated = TO_TIMESTAMP('2026-10-13 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 655012
;
