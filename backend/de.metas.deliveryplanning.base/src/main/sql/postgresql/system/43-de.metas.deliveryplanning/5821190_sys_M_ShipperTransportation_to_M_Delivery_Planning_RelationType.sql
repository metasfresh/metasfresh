-- Task E5 -- Related Documents: Delivery Instruction -> its ACTIVE Delivery Plannings.
--
-- The forward question ("which plannings are on this instruction right now") is answered by the
-- References panel (Alt+6), not by a tab: AD_Tab 546754 is parked by 5820940 for the future
-- multi-leg / N:N display, and window 541657 keeps its pre-branch two-tab shape (owner, 2026-08-28).
--
-- The opposite direction already exists -- AD_RelationType 540381 "M_Delivery_Planning ->
-- M_Delivery_Instruction", whose target AD_Ref_Table 541708 was re-pointed onto
-- M_Delivery_Planning_Alloc by 5820930. This script adds the missing reverse, built exactly like the
-- in-tree precedent pair for these same two document tables: AD_RelationType 540463
-- "C_Order (PO) -> M_ShipperTransportation" and its reverse 540468
-- "M_ShipperTransportation -> C_Order (PO)" (both IsExplicit='N', both a ValidationType='T' source
-- reference plus a ValidationType='T' target reference carrying the context-variable WhereClause).
--
-- Source reference: the EXISTING generic 542013 ("M_ShipperTransportation", table 540030, AD_Key
-- 540426, no WhereClause) is reused rather than duplicated -- it is the same reference 540468 uses as
-- ITS source, and a second source reference for the same table gets reverted in review.
--
-- Neither relation gets a source WhereClause, and neither needs one even though two windows sit on the
-- one M_ShipperTransportation table: each target WhereClause already scopes itself by data. 540505
-- requires an active M_Delivery_Planning_Alloc, which only a delivery instruction ever has; 540468
-- requires a shipping package pointing at a purchase order, which the Dropship delivery instructions
-- legitimately do have -- so a window or document-type source filter would hide true links.
--
-- Target reference: new, and its WhereClause mirrors 541708's shape (the sibling relation on the same
-- allocation table), only with the two sides swapped -- filter the plannings by the selected
-- instruction instead of the instructions by the selected planning.
--
-- AD_Display is deliberately left NULL: setting it adds an ad_column_reftable_display FK that has to
-- stay valid on every instance the migration runs against, for no gain here.

INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,ValidationType) VALUES
  (0,0,542134 /*From ID Server*/,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Delivery_Planning target for M_ShipperTransportation',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES
  (0,0,542134,542259,584986,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
   'EXISTS (SELECT 1 FROM M_Delivery_Planning_Alloc dpa WHERE dpa.M_ShipperTransportation_ID = @M_ShipperTransportation_ID / -1@ AND dpa.IsActive=''Y'' AND M_Delivery_Planning.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID)')
;

INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsExplicit,IsTableRecordIDTarget,Name,InternalName,AD_Reference_Source_ID,AD_Reference_Target_ID,Updated,UpdatedBy) VALUES
  (0,0,540505 /*From ID Server*/,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','M_ShipperTransportation -> M_Delivery_Planning','M_ShipperTransportation_to_M_Delivery_Planning',542013,542134,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Without this the relation above is dead metadata, and so is the already-shipped 540468.
--
-- SpecificRelationTypeRelatedDocumentsProvider.ZoomProviderDestination.matchesAsSource() bails out with
--   if (!zoomSource.isGenericZoomOrigin() && zoomSource.isSingleKeyRecord()) return false;
-- BEFORE it ever looks at the reference's WhereClause, and isGenericZoomOrigin() reads
-- AD_Column.IsGenericZoomOrigin of the source document's key column
-- (WebuiDocumentReferencesService.DocumentAsZoomSource.extractGenericZoomOrigin ->
-- ADTableDAO.getMinimalColumnInfo). M_ShipperTransportation.M_ShipperTransportation_ID (540426) has it
-- 'N', unlike C_Order.C_Order_ID (2161) and M_Delivery_Planning.M_Delivery_Planning_ID (584986), which
-- are both 'Y' -- 720 of the AD's 1468 key columns carry 'Y'. Reproduced on the local stack before the
-- fix: opening a delivery instruction logged
--   "matchesAsSource - return false because zoomSource.isGenericZoomOrigin()==false;
--    ... adReferenceId=ReferenceId(repoId=542013) ... zoomSource=DocumentAsZoomSource{
--    tableName=M_ShipperTransportation, recordId=1000025, AD_Window_ID=AdWindowId(repoId=541657)}"
-- and the References panel showed no planning group.
--
-- Blast radius, checked rather than assumed: the flag is read in exactly two places
-- (POZoomSource.extractKeyColumnName and DocumentAsZoomSource above), both inside the related-documents
-- machinery, and M_ShipperTransportation has a single key column, so it resolves unambiguously. The
-- relation types that become reachable from a delivery instruction are 540468 (-> C_Order (PO)) and
-- 540505 (above); 540463 and 540381 are also evaluated but their target WhereClauses require
-- @C_Order_ID@ / @M_Delivery_Planning_ID@, which an instruction header does not supply, so they stay
-- filtered out. The GENERIC provider already worked from this window before this change and is
-- unaffected.
UPDATE AD_Column
   SET IsGenericZoomOrigin='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=540426
;

-- ... and drop the generic duplicate the relation above supersedes.
--
-- With M_ShipperTransportation now a valid zoom origin, the References panel of a delivery instruction
-- would list "Delivery Planning" TWICE with identical caption, target window and count: once as
-- AD_RelationType_ID-540505 (above) and once as generic-541632-M_ShipperTransportation_ID, the generic
-- FK-derived entry built by GenericRelatedDocumentsProvider from M_Delivery_Planning
-- .M_ShipperTransportation_ID (AD_Column 585602) via ad_table_related_windows_v. Observed on the local
-- stack: both entries in the "Logistics" group, both "Delivery Planning (#1)".
--
-- The generic entry filters the planning's own legacy 1:1 pointer to the instruction; the relation type
-- goes through M_Delivery_Planning_Alloc with IsActive='Y', which is the authoritative link since the
-- aggregation work and the only one that can answer the question once a planning can sit on more than
-- one instruction. They agree today (verified: 35 plannings carry the legacy FK, 35 have an active
-- allocation, 0 disagree), so this removes a duplicate, not a capability.
--
-- ad_table_related_windows_v resolves the exclusion as
-- COALESCE(NULLIF(AD_Field.IsExcludeFromZoomTargets,''), AD_Column.IsExcludeFromZoomTargets); the field
-- on the planning header tab (710345) leaves it blank, so the AD_Column flag governs. Scope is exactly
-- this one direction -- the view keys the generic entry on (source=M_ShipperTransportation, this
-- column), so nothing else loses a zoom.
UPDATE AD_Column
   SET IsExcludeFromZoomTargets='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=585602
;
