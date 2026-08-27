-- M_ShipperTransportation_Delivery_Instructions_V (AD_Table 542287): fix row identity at N>1.
--
-- M_ShipperTransportation_ID (AD_Column 585628) is IsKey='Y', but since 5820820 the view returns
-- one row per active M_Delivery_Planning_Alloc of the instruction, and M_ShipperTransportation_ID
-- is identical across all of an instruction's rows. At N=1 active planning that degenerates to a
-- harmless single row; at N>1 every row of that instruction collides on the same WebUI row id, so
-- only one of the N plannings is ever shown/reachable.
--
-- Fix per the M_Picking_Job_Schedule_view precedent (composed key via AD_Column.IsParent, not a
-- second AD_Column.IsKey='Y' -- the partial unique index ad_column_iskey forbids two per table):
-- clear IsKey on M_ShipperTransportation_ID and mark it, plus the already-selected, already-unique
-- M_Delivery_Planning_ID (585629 -- unique per active allocation via
-- M_Delivery_Planning_Alloc_Planning_UQ), IsParent='Y'.
--
-- Mechanism, verified against GridTabVOBasedDocumentEntityDescriptorFactory.isTreatFieldAsKey():
-- when NO field on a tab has AD_Column.IsKey='Y', the framework falls back to treating every
-- AD_Column.IsParent='Y' field as a composed row-id part (SqlViewDataRepository.retrieveRowId_MultiKey
-- -> DocumentId.ofComposedKeyParts). The same two IsParent='Y' columns are also what
-- GridTabVO.buildLinkColumnNames() offers as parent-link candidates for a child tab, so this
-- cooperates with (rather than conflicts with) AD_Tab 546754's own parent-link migration.
--
-- No AD_Column INSERT needed: M_Delivery_Planning_ID is already selected by the view and already
-- has an AD_Column row (585629) -- adding a new column (e.g. M_ShippingPackage_ID) was considered
-- and rejected as unnecessary; the view's dead `sp.m_shippertransportation_id AS
-- M_Delivery_Planning_Delivery_Instructions_V_ID` select-list entry (copy-pasted from the sibling
-- view's key-alias pattern, itself not unique per row and not backed by any AD_Column) is left
-- untouched -- unrelated cosmetic cruft, out of scope here.
UPDATE AD_Column SET IsKey='N', IsParent='Y',
  Updated=TO_TIMESTAMP('2026-08-27 15:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=585628
;

UPDATE AD_Column SET IsParent='Y',
  Updated=TO_TIMESTAMP('2026-08-27 15:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=585629
;
