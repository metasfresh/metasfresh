-- Conditional read-only dates on M_Delivery_Planning (AD_Window 541632, AD_Tab 546674
-- "Lieferplanung"). The ETD/ATD/ETA/ATA date fields must stay editable for an unallocated
-- planning (the planner's own dates) and become read-only ONLY while the planning is actively
-- allocated to a delivery instruction (M_Delivery_Planning_Alloc, IsActive='Y') -- never flatly
-- read-only (that treatment was already applied to 7 OTHER fields by a prior task and is
-- explicitly out of scope here: Processed(708911), IsClosed(708910), TransportDirection(708076),
-- QtyOrdered(708090), QtyTotalOpen(708091), M_ShipperTransportation_ID(710345), M_Shipper_ID(708105)
-- -- see 5819010_sys_gh_DeliveryPlanning_readonly_system_fields.sql).
--
-- Mechanism: AD_Field.ReadOnlyLogic='@IsAllocated@=''Y''' evaluated against the WebUI Document's
-- IsAllocated field (AD_Column 593412, ColumnSQL virtual column added by
-- 5821150_sys_M_Delivery_Planning_Filterability_And_Addresses.sql, mirrors
-- DeliveryPlanningRepository.hasActiveAllocation exactly: 'Y' iff an active
-- M_Delivery_Planning_Alloc row references the planning).
--
-- Investigated whether IsAllocated needs its own AD_Field on AD_Tab 546674 for '@IsAllocated@' to
-- resolve at runtime (a ReadOnlyLogic referencing a variable absent from the Document's field set
-- does not throw visibly -- Document#computeFieldReadOnly catches ExpressionEvaluationException,
-- logs a warning, and silently preserves the field's current (non-readonly) state -- see
-- de.metas.ui.web.window.model.Document#computeFieldReadOnly). Traced the WebUI field-loading path:
--   * ad_field_v.sql LEFT JOINs AD_Field onto every AD_Column of the tab's table -- confirmed live:
--     `SELECT * FROM ad_field_v WHERE ad_tab_id=546674 AND columnname='IsAllocated'` returns one row
--     (ad_field_id IS NULL, columnsql populated) even though no AD_Field exists for it.
--   * GridFieldVOsLoader#load() (org.compiere.model) indexes the resultset by AD_Column_ID, not
--     AD_Field_ID, with the explicit comment "AD_Field_ID might be null for auto-generated fields
--     (check the view definition)" -- i.e. this is handled by design, not filtered out.
--   * GridTabVOBasedDocumentEntityDescriptorFactory#createDocumentEntityBuilder() unconditionally
--     iterates every loaded GridFieldVO (gridTabVO.getFields().forEach(...)) and adds each --
--     including virtual/ColumnSQL ones, gated only on gridFieldVO.isVirtualColumn(), never on
--     AD_Field_ID -- to the DocumentEntityDescriptor AND to the entity's SQL data-binding
--     (entityBindings.addField), so the ColumnSQL is included in the row SELECT and the Document
--     carries a live value for it.
--   * DocumentEvaluatee#get_ValueIfExists("IsAllocated") therefore resolves via
--     _document.getFieldViewOrNull("IsAllocated"), which succeeds because of the point above.
-- 5821150 independently reached and recorded the identical conclusion for filter-field resolution
-- off the same ad_field_v LEFT JOIN, citing a live-DB precedent (M_ReceiptSchedule.C_BP_Group_ID:
-- IsSelectionColumn='Y', ColumnSQL virtual, no active AD_Field).
-- Conclusion: no AD_Field for IsAllocated is required. This script only updates ReadOnlyLogic on
-- the 4 existing date fields.
--
-- IDs allocated from idserver.metas.de on 2026-08-28:
--   AD_MigrationScript 5821170 (this file)

UPDATE AD_Field
SET    ReadOnlyLogic = '@IsAllocated@=''Y''',
       Updated       = TO_TIMESTAMP('2026-08-28 09:30:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
WHERE  AD_Field_ID IN (708098 /*ETD*/, 708099 /*ATD*/, 708095 /*ETA*/, 708096 /*ATA*/)
  AND  AD_Tab_ID = 546674
;
