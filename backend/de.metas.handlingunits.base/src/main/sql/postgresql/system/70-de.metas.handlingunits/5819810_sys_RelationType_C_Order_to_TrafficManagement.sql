-- Add a directed "Related Documents" (zoom-across) relation so that from a sales order
-- (C_Order, IsSOTrx='Y') the user can jump to the order's Traffic Management (Picking Job Schedule)
-- rows. Reuses the standard C_Order(SO) source reference 540666; the target opens the Picking Job
-- Schedule window (541929) on the M_Picking_Job_Schedule_view (542514), filtered to the current order
-- via the view's C_OrderSO_ID column (the sales order the shipment schedule belongs to). Generic
-- (EntityType de.metas.handlingunits), one direction only.
--
-- IDs allocated from idserver.metas.de on 2026-08-21:
--   AD_Reference    542133 (target reference "Traffic Management Target for C_Order")
--   AD_RelationType 540504 (C_Order (SO) -> Traffic Management)
--   (AD_Ref_Table shares AD_Reference_ID = 542133 as its key; no separate id)

-- Target reference (ValidationType 'T' = Table)
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542133 /*From ID Server*/,TO_TIMESTAMP('2026-08-21 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','Traffic Management Target for C_Order',TO_TIMESTAMP('2026-08-21 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- Translations for the target reference (copies the base name into every system language)
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,IsActive,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N','Y',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542133
  AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Target ref-table: the Picking Job Schedule view, opening window 541929, filtered to the source order.
-- AD_Key = M_ShipmentSchedule_ID (AD_Column 590664): non-null on every view row (the synthetic
-- to-be-scheduled row carries M_Picking_Job_Schedule_ID=0, so that column is unsuitable as the zoom key).
-- WhereClause matches the view's own C_OrderSO_ID column against the source order's context value
-- (@C_Order_ID/-1@); C_OrderSO_ID is the sales order the shipment schedule belongs to.
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause)
VALUES (0,590664,0,542133,542514,541929,TO_TIMESTAMP('2026-08-21 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N',TO_TIMESTAMP('2026-08-21 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'M_Picking_Job_Schedule_view.C_OrderSO_ID = @C_Order_ID/-1@')
;

-- The directed relation itself: reuse the standard C_Order(SO) source (540666) -> the new target (542133).
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,Updated,UpdatedBy,EntityType,IsActive,IsTableRecordIDTarget,Name,InternalName,AD_Reference_Source_ID,AD_Reference_Target_ID)
VALUES (0,0,540504 /*From ID Server*/,TO_TIMESTAMP('2026-08-21 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-08-21 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','C_Order (SO) -> Traffic Management','C_Order_to_TrafficManagement',540666,542133)
;
