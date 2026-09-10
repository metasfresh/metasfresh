-- Cost-monitor window 542175, tab 549352: make the AD metadata tell the truth about Belegstatus.
--
-- NO user-visible change, and that is the point. `DocStatus` was never rendered as a form field on
-- this (or any) window: LayoutFactory hoists DocStatus + DocAction into the document-header
-- ActionButton (SpecialDocumentFieldsCollector claims both column names), so the served window
-- layout carried element 652691 as an element LINE WITH NO ELEMENTS — dead layout, which
-- ElementsLine.js renders as null. Meanwhile the AD metadata claimed IsDisplayed='Y', which is what
-- the AD-driven layout renderers show, and that mismatch has already misled a design review.
--
-- Hidden, not deleted: the AD_Field row stays for callouts/legacy paths. The AD_Field layer is set
-- alongside AD_UI_Element to match this tab's own convention for hidden fields (HasCostDifference,
-- AD_UI_Element 654725 / AD_Field 784959). Grid column (SeqNoGrid 110) and filter are kept.
--
-- Consequently there is nothing for a Playwright assertion to discriminate here: the rendered DOM is
-- identical before and after. The proof is the window-layout REST payload, where the empty
-- elementsLine disappears.

UPDATE AD_UI_Element SET IsDisplayed='N',
    Updated=TO_TIMESTAMP('2026-09-10 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID=652691 /* DocStatus */
;

UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,
    Updated=TO_TIMESTAMP('2026-09-10 10:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781760 /* DocStatus */
;
