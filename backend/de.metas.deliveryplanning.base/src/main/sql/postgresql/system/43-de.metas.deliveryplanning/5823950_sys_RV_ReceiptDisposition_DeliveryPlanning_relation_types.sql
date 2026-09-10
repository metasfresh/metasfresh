-- Alt+6 "related documents" into the receipt-disposition window (AD_Window 542190, view AD_Table 542644).
-- Before this, NO AD_RelationType referenced the view or the window, so the window was unreachable from any
-- document's related-documents list.
--
-- Two relations here; the third (M_ShipperTransportation -> this window) is deliberately NOT included, because
-- the view exposes no M_ShipperTransportation_ID to filter on -- see the note at the end.
--
-- Both reuse an EXISTING source reference rather than creating one:
--   540676  C_Order_POTrx_Source        WhereClause IsSOTrx = 'N',  AD_Key C_Order_ID  (already used by 10 relation types)
--   541707  M_Delivery_Planning source
--
-- The target side needs one reference per relation because the WhereClause differs. Both target references
-- point at AD_Table 542644 with AD_Key 593472 = RV_ReceiptDisposition_DeliveryPlanning_ID -- the view's single
-- IsKey column, AD_Reference 13 (ID/integer). That integer key is a hard requirement, not a style choice:
-- RelationTypeRelatedDocumentsCountSupplier reads the AD_Key via DB.getSQLValueEx(), so a Date or text key
-- throws "Bad value for type int" at runtime.
--
-- Coverage, measured on the local stack rather than assumed:
--   C_Order              c_order_id is populated on BOTH branches (1616 unplanned + 3338 planned, none null),
--                        so this relation lists planned AND unplanned rows of the order.
--   M_Delivery_Planning  m_delivery_planning_id is populated on the 3338 planned rows and NULL on all 1616
--                        unplanned ones. That is CORRECT, not a gap: an unplanned row is by definition a
--                        receipt schedule no active planning refers to (branch two's NOT EXISTS), so there is
--                        no planning to relate it to. Do not "fix" this by widening the clause.
--
-- Pattern copied from 540463 C_Order_PO_to_M_ShipperTransportation, which is the closest existing analogue
-- (purchase order -> a logistics document). Ours needs no EXISTS: the view exposes the link columns directly.
--
-- Routing: metasfresh repo, EntityType 'D' -- matching the window and view this change set already created as
-- core (window 542190 is EntityType 'D'). No customer override of 542190 can exist: this change set creates it
-- (migration 5822460) and it appears as an AD_Window_ID on no other branch.

-- ---------------------------------------------------------------------------
-- 1. Purchase order -> receipt disposition (planned AND unplanned rows)
-- ---------------------------------------------------------------------------
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,ValidationType)
SELECT 0,0,542141 /*From ID Server*/,TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
       'RV_ReceiptDisposition_DeliveryPlanning_Target_For_C_Order (PO)',
       TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'T'
WHERE NOT EXISTS (SELECT 1 FROM AD_Reference WHERE AD_Reference_ID=542141)
;

INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,AD_Display,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,WhereClause,Updated,UpdatedBy)
SELECT 0,0,542141,542644,593472,NULL,TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',NULL,
       'RV_ReceiptDisposition_DeliveryPlanning.C_Order_ID = @C_Order_ID/-1@',
       TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_Table WHERE AD_Reference_ID=542141)
;

INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,Created,CreatedBy,Description,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy)
SELECT 0,0,540508 /*From ID Server*/,540676,542141,TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       'Lists the receipt-disposition rows of this purchase order, planned and unplanned.','D','Y','N',
       'C_Order (PO) -> Wareneingangsdisposition',
       TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_RelationType WHERE AD_RelationType_ID=540508)
;

-- ---------------------------------------------------------------------------
-- 2. Delivery planning -> receipt disposition (its own planned row)
-- ---------------------------------------------------------------------------
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,ValidationType)
SELECT 0,0,542142 /*From ID Server*/,TO_TIMESTAMP('2026-09-10 21:00:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
       'RV_ReceiptDisposition_DeliveryPlanning_Target_For_M_Delivery_Planning',
       TO_TIMESTAMP('2026-09-10 21:00:01','YYYY-MM-DD HH24:MI:SS'),100,'T'
WHERE NOT EXISTS (SELECT 1 FROM AD_Reference WHERE AD_Reference_ID=542142)
;

INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,AD_Display,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,WhereClause,Updated,UpdatedBy)
SELECT 0,0,542142,542644,593472,NULL,TO_TIMESTAMP('2026-09-10 21:00:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',NULL,
       'RV_ReceiptDisposition_DeliveryPlanning.M_Delivery_Planning_ID = @M_Delivery_Planning_ID/-1@',
       TO_TIMESTAMP('2026-09-10 21:00:01','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_Table WHERE AD_Reference_ID=542142)
;

INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,Created,CreatedBy,Description,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy)
SELECT 0,0,540509 /*From ID Server*/,541707,542142,TO_TIMESTAMP('2026-09-10 21:00:01','YYYY-MM-DD HH24:MI:SS'),100,
       'Lists this delivery planning''s row in the receipt disposition. Planned rows only, by definition.','D','Y','N',
       'M_Delivery_Planning -> Wareneingangsdisposition',
       TO_TIMESTAMP('2026-09-10 21:00:01','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_RelationType WHERE AD_RelationType_ID=540509)
;

-- NOT INCLUDED: M_ShipperTransportation (delivery instruction) -> this window. The view exposes no
-- M_ShipperTransportation_ID, so the target WhereClause would need either that column added to the view
-- (branch one from dp.m_shippertransportation_id, branch two via the existing max() collapse -- well-defined,
-- since a branch-two row reaches exactly one transport order) or an EXISTS correlating through the package as
-- 540463 does. That is a design choice for the owner, not something to pick unilaterally here.
