-- 2026-04-27 Cost Detail window: surface DateAcct in the grid and default-sort by it.
--
-- Window 540897 ("Kosten-Detail") shares its tab template (AD_Tab_ID = 748) with window 344
-- ("Produktkosten"), so the AD_UI_Element changes here apply to both windows. The OrderByClause
-- update is on tab 542373 only — that's the tab of window 540897.

-- B1: display DateAcct in the grid view, just after Product and Cost Element so it's
--     immediately visible. The element already exists for the form view.
UPDATE AD_UI_Element
SET    IsDisplayedGrid = 'Y',
       SeqNoGrid       = 15,
       Updated         = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy       = 0
WHERE  AD_UI_Element_ID = 611534    -- DateAcct on AD_Tab_ID = 748 (M_CostDetail template)
;

-- B2: default-sort the Cost Detail window by DateAcct DESC, then by ID for stable
--     tie-breaking among same-day cost details.
UPDATE AD_Tab
SET    OrderByClause = 'DateAcct DESC, M_CostDetail_ID DESC',
       Updated       = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy     = 0
WHERE  AD_Tab_ID = 542373            -- Kosten-Detail tab in window 540897
;
