-- 2026-04-29 Cost-Detail window: narrow the DateAcct grid column to match
-- the UOM / Type / Currency / Acct-Schema columns already narrowed by 5799760.
--
-- AD_UI_Element 611534 = DateAcct on AD_Tab 748 (M_CostDetail template).
-- The change applies to both window 540897 ("Kosten-Detail") and window 344
-- ("Produktkosten") that share the template — same as 5799760.

UPDATE AD_UI_Element
SET    WidgetSize = 'S',
       Updated    = TO_TIMESTAMP('2026-04-29 06:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy  = 0
WHERE  AD_UI_Element_ID = 611534                     -- DateAcct
;
