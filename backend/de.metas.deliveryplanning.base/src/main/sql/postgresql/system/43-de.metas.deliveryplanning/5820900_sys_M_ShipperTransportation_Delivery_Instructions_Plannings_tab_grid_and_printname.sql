-- Follow-up to 5820870 (reactivate the Plannings tab, AD_Tab 546754), two findings from the
-- window-designer validation pass:
--
-- 1) [HIGH] Every AD_UI_Element on 546754 has IsDisplayedGrid='N'/SeqNoGrid=0 -- since the tab is
--    section-backed, AD_UI_Element (not the stale AD_Field.IsDisplayedGrid on "Created") governs,
--    so the reactivated tab's grid would show zero columns: rows are indistinguishable in the list,
--    only readable by opening each one individually. Give it a sensible column set, matching the
--    style of the sibling child tab on the same window (546736 "Versandpaket": Product/Locator/
--    Batch/Qty columns wired via AD_UI_Element.SeqNoGrid).
-- 2) [MEDIUM] 5820870 renamed AD_Element 581962's Name/Description but left PrintName at the old,
--    pre-rename text (still "Lieferanweisungen fuer die Lieferplanung" in de_DE/de_CH -- the exact
--    collision with sibling element 581926 the rename was meant to resolve; en_US PrintName was an
--    even older third value, "Delivery Instruction History"). AD_Tab itself has no PrintName column
--    (update_Tab_Translation_From_AD_Element only ever syncs Name/Description/Help/CommitWarning),
--    so this is pure AD_Element data hygiene -- no AD_PrintFormatItem references this element
--    (verified: zero rows), but leaving stale text there is a latent trap for the next reader.
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10, Updated=TO_TIMESTAMP('2026-08-27 15:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614861; -- DocumentNo
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20, Updated=TO_TIMESTAMP('2026-08-27 15:20:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614863; -- DocStatus
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-08-27 15:20:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614868; -- M_Product_ID
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-27 15:20:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614871; -- M_Locator_ID
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-27 15:20:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614865; -- ETD
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-08-27 15:20:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614867; -- ETA
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-08-27 15:20:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614869; -- PlannedLoadedQuantity
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80, Updated=TO_TIMESTAMP('2026-08-27 15:20:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=614870; -- PlannedDischargeQuantity
;

UPDATE AD_Element SET PrintName='Lieferplanungen der Lieferanweisung',
  Updated=TO_TIMESTAMP('2026-08-27 15:20:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581962
;

UPDATE AD_Element_Trl SET PrintName='Lieferplanungen der Lieferanweisung',
  Updated=TO_TIMESTAMP('2026-08-27 15:20:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581962 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET PrintName='Lieferplanungen der Lieferanweisung',
  Updated=TO_TIMESTAMP('2026-08-27 15:20:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581962 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET PrintName='Delivery Plannings for this Instruction',
  Updated=TO_TIMESTAMP('2026-08-27 15:20:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=581962 AND AD_Language='en_US'
;
