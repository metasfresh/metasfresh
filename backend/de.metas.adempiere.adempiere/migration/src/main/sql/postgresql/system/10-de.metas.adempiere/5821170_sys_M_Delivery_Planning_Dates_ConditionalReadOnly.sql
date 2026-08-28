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
-- Whether IsAllocated needs its own AD_Field on AD_Tab 546674 for '@IsAllocated@' to resolve is the
-- load-bearing question here, because getting it wrong fails SILENTLY: a ReadOnlyLogic referencing a
-- variable absent from the Document's field set does not surface an error -- Document#computeFieldReadOnly
-- catches Exception, returns null, and the caller then skips setReadonly entirely, leaving the field
-- exactly as it was. It does NOT need one, and this was settled empirically rather than by inference:
--
--   VERIFIED ON A RUNNING INSTANCE, 2026-08-28 (local app stack on :18080 against the deep_tundra_uat_2
--   DB on :21632), reading AD_Field.readonly straight off GET /rest/api/window/541632/<id>:
--     * planning 1000027, one ACTIVE allocation, Processed='N'  -> ETD/ATD/ETA/ATA readonly = true
--     * the SAME planning with its allocation deactivated        -> readonly = false  (all four)
--     * the SAME planning with the allocation restored           -> readonly = true   (all four)
--     * planning 1000005, never allocated, Processed='N'         -> readonly = false
--   Using one record for the flip is what makes it proof: it holds every other field constant. Note that
--   planning 1000025 looks like a deactivated-allocation case but is NOT usable as one -- it is
--   Processed='Y'/IsClosed='Y', so it reads read-only for an unrelated reason.
--
-- The supporting code path, for a reader who needs the mechanism rather than the result. Two details are
-- easy to get wrong and are spelled out deliberately:
--   * GridFieldVO#getSQL selects between ad_field_v and **ad_field_vt** on Env.isBaseLanguage -- a de_DE
--     session takes the _vt branch. BOTH views LEFT JOIN AD_Field and admit AD_Field_ID IS NULL, so a
--     ColumnSQL column with no AD_Field survives either way; citing only ad_field_v would cover just one
--     of the two branches the runtime actually picks between.
--   * GridFieldVOsLoader#load() indexes the resultset by AD_Column_ID, not AD_Field_ID, with the explicit
--     comment "AD_Field_ID might be null for auto-generated fields" -- handled by design, not filtered.
--   * GridTabVOBasedDocumentEntityDescriptorFactory#createDocumentEntityBuilder() adds every loaded
--     GridFieldVO to the DocumentEntityDescriptor AND to the SQL data-binding (entityBindings.addField).
--     There is no AD_Field_ID or IsDisplayed gate; isVirtualColumn() only selects which SQL the field
--     uses. SqlDocumentEntityDataBindingDescriptor#getSqlSelectAll then emits a select value for every
--     registered field, so the ColumnSQL really is in the row SELECT.
--   * The comparison itself is Boolean-vs-string: IsAllocated is AD_Reference_ID=20 (YesNo), so the
--     Document field value is a Boolean while the expression compares it to 'Y'. It matches because
--     DocumentEvaluatee#convertToString routes Boolean through DisplayType.toBooleanString -> "Y"/"N".
-- This script only updates ReadOnlyLogic on the 4 existing date fields; no AD_Field is created.
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
