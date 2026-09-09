-- Show the per-line C_OrderLine.DescriptionAboveLine free-text field on the sales-order-line tab.
-- Core (standard) sales-order window 143, order-line tab 187: AD_Field 784965 + AD_UI_Element 654731 placed at
-- UI seq 152 (between C_Tax_ID 150 and Description 155), immediately before Description (UI seq 155,
-- grid seq 230) in element group 1000005 ("main").
-- The field + ui-element are cloned from the sibling Description field (AD_Field 1126 / AD_UI_Element 549097)
-- so every layout attribute (readonly, span, grid flags, widget) matches the sibling free-text field it sits
-- next to; only the PK, column, name, seqno/seqnogrid and audit columns are overridden. SeqNoGrid is set to
-- 225 (between C_Tax_ID's grid seq 220 and Description's grid seq 230) so the grid column also lands
-- immediately before Description, rather than duplicating Description's own grid slot. Caption resolves
-- from the existing, reusable AD_Element 585448 (ColumnName=DescriptionAboveLine, created for this column
-- in an earlier script on this branch); AD_Name_ID stays NULL (standard column-driven caption path).
-- A customer override window for window 143, where one exists, is placed by a separate script in that
-- customer's own repo -- not part of this core script.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_MigrationScript  5823460 (this script)
--   AD_Field            784965  (C_OrderLine.DescriptionAboveLine field on tab 187)
--   AD_UI_Element       654731

-- =============================================================================
-- 1. AD_Field on tab 187 -- cloned from sibling Description field 1126, repointed to column 593531
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
  784965 /*From ID Server*/, ad_client_id, ad_org_id, 'Y',
  TO_TIMESTAMP('2026-09-09 17:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-09-09 17:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'Freitext über Position', ad_tab_id, 593531, ad_fieldgroup_id, isdisplayed, displaylogic, displaylength,
  isreadonly, seqno, sortno, issameline, isheading, isfieldonly, isencrypted, entitytype,
  obscuretype, ad_reference_id, ismandatory, included_tab_id, defaultvalue, ad_reference_value_id,
  ad_val_rule_id, infofactoryclass, columndisplaylength, colorlogic, includedtabheight,
  seqnogrid, isdisplayedgrid, spanx, spany, ad_name_id, isexcludefromzoomtargets, readonlylogic,
  isalwaysupdateable, isfilterfield, selectioncolumnseqno, filteroperator, isshowfilterinline,
  filterdefaultvalue, isoverridefilterdefaultvalue, isfacetfilter, facetfilterseqno,
  maxfacetstofetch, ishidegridcolumnifempty, isshowfilterinactivevalues
FROM AD_Field WHERE ad_field_id = 1126;

-- Skeleton AD_Field_Trl rows (propagation below fills the actual caption from element 585448)
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, f.AD_Field_ID, f.Name, 'N', f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 784965
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID);

-- =============================================================================
-- 2. AD_UI_Element at seq 152 (after C_Tax_ID's 150, before Description's 155), grid seq 225
--    (after C_Tax_ID's grid seq 220, before Description's grid seq 230) -- cloned from UI element 549097
-- =============================================================================
INSERT INTO AD_UI_Element (
  ad_ui_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
  ad_field_id, ad_ui_elementgroup_id, name, seqno, uistyle, isdisplayed, isdisplayedgrid, seqnogrid,
  isdisplayed_sidelist, seqno_sidelist, ad_tab_id, widgetsize, ad_ui_elementtype, labels_tab_id,
  labels_selector_field_id, isallowfiltering, mediatypes, ismultiline, multiline_linescount,
  inline_tab_id, vieweditmode, ad_name_id, isadvancedfield, description, help)
SELECT
  654731 /*From ID Server*/, ad_client_id, ad_org_id, 'Y',
  TO_TIMESTAMP('2026-09-09 17:02:30','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-09-09 17:02:30','YYYY-MM-DD HH24:MI:SS'), 100,
  784965, ad_ui_elementgroup_id, 'DescriptionAboveLine', 152, uistyle, isdisplayed, isdisplayedgrid, 225,
  isdisplayed_sidelist, seqno_sidelist, ad_tab_id, widgetsize, ad_ui_elementtype, labels_tab_id,
  labels_selector_field_id, isallowfiltering, mediatypes, ismultiline, multiline_linescount,
  inline_tab_id, vieweditmode, ad_name_id, isadvancedfield, description, help
FROM AD_UI_Element WHERE ad_ui_element_id = 549097;

-- =============================================================================
-- 3. Propagate the caption from AD_Element 585448 to the new AD_Field_Trl rows
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585448 /*DescriptionAboveLine*/);
