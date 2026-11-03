-- Show AD_PInstance_Log.P_Msg as a dedicated field in Advanced Edit (Alt+E) on window 332
-- "Prozess-Revision", tab 665 "Protokoll" - it was already displayed in the grid
-- (AD_UI_Element.IsDisplayedGrid='Y') but had no place in the single-row/advanced-edit form.
UPDATE AD_UI_Element SET IsAdvancedField='Y', SeqNo=10, Updated=now(), UpdatedBy=100 WHERE AD_UI_Element_ID=547987
;
