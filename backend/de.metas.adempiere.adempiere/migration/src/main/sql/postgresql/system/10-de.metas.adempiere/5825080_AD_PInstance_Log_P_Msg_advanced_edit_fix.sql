-- Fixes a regression in 5824940_AD_PInstance_Log_P_Msg_advanced_edit.sql: that migration set
-- IsAdvancedField='Y' on the EXISTING grid AD_UI_Element (547987) for AD_PInstance_Log.P_Msg on
-- window 332 tab 665 ("Protokoll"). It never touched IsDisplayedGrid (left 'Y'), so it looked
-- harmless from de.metas.ui.web.window.descriptor.factory.standard.LayoutFactory alone -
-- layoutGridView() gates purely on IsDisplayedGrid and never consults IsAdvancedField, and no
-- AD_UI_Element interceptor derives one flag from the other. But that Java-side element list is
-- NOT the final answer: every layout element list (grid, single-row, side-list alike) is converted
-- to JSON via JSONDocumentLayoutElement.ofList(), which additionally filters through
-- JSONDocumentLayoutOptions.documentLayoutElementFilter() - and that predicate drops EVERY element
-- with IsAdvancedField='Y' unless the request explicitly asks showAdvancedFields=true.
-- de.metas.ui.web.view.ViewRestController.newJSONLayoutOptions() (used for ALL grid/table-tab view
-- layouts, e.g. this Protokoll grid) calls JSONDocumentLayoutOptions.of(userSession), which has NO
-- way to pass showAdvancedFields=true for a view/grid request - it is unconditionally false. So
-- IsAdvancedField='Y' on 547987 silently and deterministically stripped P_Msg out of the grid's
-- JSON response on every load, independent of IsDisplayedGrid and independent of any layout cache
-- (confirmed live: a /cacheReset on the affected instance did not restore the column, because
-- there is no cache involved - the filter runs fresh on every request).
--
-- One AD_UI_Element row cannot satisfy both surfaces at once: the grid needs IsAdvancedField='N'
-- (or it is filtered out of every view response, full stop), while reaching the single-row
-- Advanced-Edit view needs IsAdvancedField='Y' AND IsDisplayed='Y' (de.metas.ui.web.window.
-- controller.WindowRestController DOES pass through a request-driven `advanced` flag, so a
-- document's single-row layout can legitimately request showAdvancedFields=true when the user
-- opens Advanced Edit - unlike the view/grid controller). Fix, matching the established metasfresh
-- convention for exactly this split (see e.g. AD_UI_Element 553993/553995, both for
-- AD_Field_ID=569540 on AD_Tab_ID=540474 - grid-only + advanced-only siblings of the same field):
--   1. Revert 547987 to its pre-5824940 state (grid-only, as it always was).
--   2. Insert a NEW AD_UI_Element row for the SAME AD_Field_ID=10521 (P_Msg), in the tab's only
--      AD_UI_ElementGroup (541065), advanced-only. WidgetSize='XXL' + IsMultiLine='N' matches the
--      only other P_Msg AD_UI_Element in the system (578392, window 541040 "Externes System Log")
--      for the same effectively-unbounded-length Text column (AD_Reference_ID=14).
UPDATE AD_UI_Element SET IsAdvancedField='N', IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2026-09-18 08:15:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=547987
;

INSERT INTO AD_UI_Element (
  AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
  AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
  Created, CreatedBy, IsActive, IsAdvancedField,
  IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
  Name, SeqNo, SeqNoGrid, SeqNo_SideList,
  IsMultiLine, MultiLine_LinesCount, WidgetSize,
  Updated, UpdatedBy
) VALUES (
  654782 /*From ID Server*/, 0, 0, 665,
  541065, 10521, 'F',
  TO_TIMESTAMP('2026-09-18 08:15:00','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'Y',
  'Y', 'N', 'N',
  'Process Message', 10, 0, 0,
  'N', 0, 'XXL',
  TO_TIMESTAMP('2026-09-18 08:15:00','YYYY-MM-DD HH24:MI:SS'), 100
)
;
