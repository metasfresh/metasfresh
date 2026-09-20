-- Fixes a second regression left by 5825080_AD_PInstance_Log_P_Msg_advanced_edit_fix.sql: that fix
-- correctly restored AD_PInstance_Log.P_Msg to the "Protokoll" grid (window 332 tab 665) by
-- reverting AD_UI_Element 547987 to grid-only and adding a separate advanced-only sibling (654782).
-- Live-verified against a real app-server run (not source-reading alone - two earlier passes this
-- session got this wrong from source), the grid column now shows but its VALUE is ALWAYS EMPTY:
-- de.metas.ui.web.window.descriptor.factory.standard.GridTabVOBasedDocumentEntityDescriptorFactory
-- .addFieldsCharacteristic() tags Characteristic.AdvancedField onto the SHARED, field-NAME-keyed
-- DocumentFieldDescriptor for "P_Msg" - not onto the individual AD_UI_Element - the moment ANY
-- AD_UI_Element for that field (654782) is processed with IsAdvancedField='Y'. Tab 665
-- (AD_PInstance_Log) is an INCLUDED DOCUMENT TAB, not a SQL-view-backed grid, so its rows go through
-- de.metas.ui.web.window.controller.WindowRestController.getIncludedTabRows(), which builds each
-- row's field JSON via the same JSONDocumentOptions.documentFieldFilter() used for single-document
-- requests - and that filter drops any field carrying Characteristic.AdvancedField from a
-- non-advanced (grid) response, regardless of which specific AD_UI_Element rendered a non-advanced
-- layout for it. Confirmed live: GET .../window/332/<id>/AD_Tab-665 (grid, default) -> P_Msg key
-- absent from fieldsByName; same call ?advanced=true -> P_Msg present with the correct value;
-- flipping AD_UI_Element_ID=654782.IsAdvancedField 'Y'->'N' made the grid value reappear on the
-- IDENTICAL non-advanced call.
--
-- Conclusion: IsAdvancedField='Y' on ANY AD_UI_Element for a field tab-wide poisons that field's
-- VALUE (not just its layout placement) for every non-advanced response on an included-document
-- tab - there is no migration-only way to have this field populated in the grid AND hidden from the
-- plain single-row form at the same time. Per human decision (2026-09-18, logged in
-- ai-work/29675/pending-questions.md): drop the Advanced-Edit-only goal, keep P_Msg visible AND
-- populated everywhere. This also brings modus_operandi_hotfix's AD_UI_Element state for 547987
-- into parity with what new_dawn_uat/downstream branches already have via
-- 5803990_sys_gh29216_Process_Revision_Window_Update.sql (IsDisplayed='Y') - closing the
-- forward-port UX mismatch flagged in that same pending-questions.md entry, since propagating this
-- fix no longer changes P_Msg's behavior on those branches.
--
-- 1. Deactivate the now-unneeded advanced-only sibling (654782) rather than deleting it - a plain,
--    reversible retirement, matching the "IsActive='N' to hide" convention.
UPDATE AD_UI_Element SET IsActive='N', Updated=TO_TIMESTAMP('2026-09-18 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=654782
;

-- 2. Make 547987 non-advanced and displayed in the plain single-row form too, alongside its
--    siblings Log_ID/P_Date/P_Number (547984/547985/547986, all already IsDisplayed='Y', SeqNo=0) -
--    SeqNo=5 places it right after that header trio and before Warnings/DB-Tabelle (SeqNo=10).
--    IsDisplayedGrid stays 'Y' and SeqNoGrid stays 40 (untouched throughout every migration on this
--    element) and WidgetSize stays 'L' (unchanged since 2018's 5508780) - this is the exact grid
--    configuration that produced the working "release" screenshot showing a full, readable P_Msg
--    column, so it is left alone rather than re-tuned.
UPDATE AD_UI_Element SET IsAdvancedField='N', IsDisplayed='Y', SeqNo=5, Updated=TO_TIMESTAMP('2026-09-18 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=547987
;
