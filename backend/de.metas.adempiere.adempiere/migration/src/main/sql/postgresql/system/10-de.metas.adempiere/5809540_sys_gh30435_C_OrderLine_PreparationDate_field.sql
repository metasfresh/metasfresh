-- Show the per-line C_OrderLine.PreparationDate override on the sales-order-line tab.
-- Core (standard) sales-order window 143, order-line tab 187: AD_Field 781250 + AD_UI_Element 652370 placed at
-- UI seq 415 (between DatePromised 410 and Vendor 420), immediately after DatePromised (UI seq 410) in element group 1000005 ("main").
-- The field + ui-element are cloned from the sibling DatePromised field (AD_Field 1123 / AD_UI_Element 554396)
-- so every layout attribute (displaylogic, span, widths, grid flags) matches; only the PK, column, name and
-- audit columns are overridden. The AD_UI_Element SeqNo is set to 415 (placing the field right after
-- DatePromised); AD_Field.SeqNo is inherited from the sibling (it is legacy, not the WebUI render path for this
-- UI-section-backed window, where 74 fields share SeqNo=0). Caption resolves from AD_Element 542340 (AD_Name_ID stays NULL).
-- The dt204 override window 541886 / tab 548027 gets the same field in a separate customer-repo script (5809550).
--
-- IDs allocated from idserver.metas.de on 2026-06-24:
--   AD_MigrationScript  5809540 (this script)
--   AD_Field            781250  (C_OrderLine.PreparationDate field on tab 187)
--   AD_UI_Element       652370

-- =============================================================================
-- 1. AD_Field on tab 187 — cloned from sibling DatePromised field 1123, repointed to column 592882
-- =============================================================================
INSERT INTO AD_Field (
  ad_field_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
  name, ad_tab_id, ad_column_id, ad_fieldgroup_id, isdisplayed, displaylogic, displaylength,
  isreadonly, seqno, sortno, issameline, isheading, isfieldonly, isencrypted, entitytype,
  obscuretype, ad_reference_id, ismandatory, included_tab_id, defaultvalue, ad_reference_value_id,
  ad_val_rule_id, infofactoryclass, columndisplaylength, colorlogic, includedtabheight,
  seqnogrid, isdisplayedgrid, spanx, spany, ad_name_id, isexcludefromzoomtargets, readonlylogic,
  isalwaysupdateable, isfilterfield, selectioncolumnseqno, filteroperator, isshowfilterinline,
  filterdefaultvalue, isoverridefilterdefaultvalue, isfacetfilter, facetfilterseqno,
  maxfacetstofetch, ishidegridcolumnifempty, isshowfilterinactivevalues)
SELECT
  781250 /*From ID Server*/, ad_client_id, ad_org_id, 'Y',
  TO_TIMESTAMP('2026-06-24 14:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-24 14:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'Bereitstellungsdatum', ad_tab_id, 592882, ad_fieldgroup_id, isdisplayed, displaylogic, displaylength,
  isreadonly, seqno, sortno, issameline, isheading, isfieldonly, isencrypted, entitytype,
  obscuretype, ad_reference_id, ismandatory, included_tab_id, defaultvalue, ad_reference_value_id,
  ad_val_rule_id, infofactoryclass, columndisplaylength, colorlogic, includedtabheight,
  seqnogrid, isdisplayedgrid, spanx, spany, ad_name_id, isexcludefromzoomtargets, readonlylogic,
  isalwaysupdateable, isfilterfield, selectioncolumnseqno, filteroperator, isshowfilterinline,
  filterdefaultvalue, isoverridefilterdefaultvalue, isfacetfilter, facetfilterseqno,
  maxfacetstofetch, ishidegridcolumnifempty, isshowfilterinactivevalues
FROM AD_Field WHERE ad_field_id = 1123;

-- Skeleton AD_Field_Trl rows (propagation below fills the actual caption from element 542340)
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, f.AD_Field_ID, f.Name, 'N', f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781250
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID);

-- =============================================================================
-- 2. AD_UI_Element at seq 415 (after DatePromised's 410, before Vendor's 420) — cloned from UI element 554396
-- =============================================================================
INSERT INTO AD_UI_Element (
  ad_ui_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
  ad_field_id, ad_ui_elementgroup_id, name, seqno, uistyle, isdisplayed, isdisplayedgrid, seqnogrid,
  isdisplayed_sidelist, seqno_sidelist, ad_tab_id, widgetsize, ad_ui_elementtype, labels_tab_id,
  labels_selector_field_id, isallowfiltering, mediatypes, ismultiline, multiline_linescount,
  inline_tab_id, vieweditmode, ad_name_id, isadvancedfield, description, help)
SELECT
  652370 /*From ID Server*/, ad_client_id, ad_org_id, 'Y',
  TO_TIMESTAMP('2026-06-24 14:02:30','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-24 14:02:30','YYYY-MM-DD HH24:MI:SS'), 100,
  781250, ad_ui_elementgroup_id, 'PreparationDate', 415, uistyle, isdisplayed, isdisplayedgrid, seqnogrid,
  isdisplayed_sidelist, seqno_sidelist, ad_tab_id, widgetsize, ad_ui_elementtype, labels_tab_id,
  labels_selector_field_id, isallowfiltering, mediatypes, ismultiline, multiline_linescount,
  inline_tab_id, vieweditmode, ad_name_id, isadvancedfield, description, help
FROM AD_UI_Element WHERE ad_ui_element_id = 554396;

-- =============================================================================
-- 3. Propagate the caption from AD_Element 542340 to the new AD_Field_Trl rows
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542340 /*PreparationDate*/);
