-- 2026-04-27 Cost Detail window: narrow the UOM / Type / Currency / Accounting-Schema
-- grid columns by setting AD_UI_Element.WidgetSize = 'S'.
--
-- Background: most AD_UI_Element rows leave WidgetSize NULL and inherit the default sizing
-- (effectively M / L). Setting 'S' on these four lookup-style columns reclaims horizontal
-- space in the Cost-Detail grid where the value is short (UOM symbol, ref-list code, currency
-- code, schema name) and the default width is overkill.
--
-- These elements live on AD_Tab 748 (the M_CostDetail template), so the change applies to both
-- window 540897 ("Kosten-Detail") and window 344 ("Produktkosten") that share that template —
-- which is the desired behaviour: the columns are equally over-sized in both windows.

UPDATE AD_UI_Element
SET    WidgetSize = 'S',
       Updated    = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy  = 0
WHERE  AD_UI_Element_ID = 575088     -- C_UOM_ID
;

UPDATE AD_UI_Element
SET    WidgetSize = 'S',
       Updated    = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy  = 0
WHERE  AD_UI_Element_ID = 615973     -- M_CostDetail_Type
;

UPDATE AD_UI_Element
SET    WidgetSize = 'S',
       Updated    = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy  = 0
WHERE  AD_UI_Element_ID = 575087     -- C_Currency_ID
;

UPDATE AD_UI_Element
SET    WidgetSize = 'S',
       Updated    = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy  = 0
WHERE  AD_UI_Element_ID = 547573     -- C_AcctSchema_ID
;
