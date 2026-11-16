-- The third relation type into the receipt-disposition window: delivery instruction -> this window.
-- 5823950 shipped the C_Order and M_Delivery_Planning relations; this one waited on the view exposing
-- M_ShipperTransportation_ID, which 5823970 does.
--
-- Source reference 542013 "M_ShipperTransportation" is reused (AD_Key M_ShipperTransportation_ID, already
-- source of 2 relation types) rather than 541069 "Transportation Order ID", which serves only 1.
--
-- Target: AD_Table 542644 with AD_Key 593472 = the view's single IsKey column (integer -- required, since
-- RelationTypeRelatedDocumentsCountSupplier reads it via DB.getSQLValueEx()). The WhereClause is a plain
-- equality on the newly exposed column; no EXISTS is needed because the view now carries the id directly.
--
-- Coverage: BOTH branches. 416 of 4954 view rows carry a transport order -- 326 planned (the planning's own)
-- and 90 unplanned (through the shipping package). So an instruction lists its receipt-disposition rows
-- regardless of planned state, which is what was asked for.
--
-- No test, per the owner's explicit instruction for the relation types.
--
-- Routing: metasfresh repo, EntityType 'D' -- matching the window, view and the two relations already shipped.

INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,ValidationType)
SELECT 0,0,542143 /*From ID Server*/,TO_TIMESTAMP('2026-09-10 21:35:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
       'RV_ReceiptDisposition_DeliveryPlanning_Target_For_M_ShipperTransportation',
       TO_TIMESTAMP('2026-09-10 21:35:00','YYYY-MM-DD HH24:MI:SS'),100,'T'
WHERE NOT EXISTS (SELECT 1 FROM AD_Reference WHERE AD_Reference_ID=542143)
;

INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,AD_Display,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,WhereClause,Updated,UpdatedBy)
SELECT 0,0,542143,542644,593472,NULL,TO_TIMESTAMP('2026-09-10 21:35:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',NULL,
       'RV_ReceiptDisposition_DeliveryPlanning.M_ShipperTransportation_ID = @M_ShipperTransportation_ID/-1@',
       TO_TIMESTAMP('2026-09-10 21:35:00','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_Table WHERE AD_Reference_ID=542143)
;

INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,Created,CreatedBy,Description,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy)
SELECT 0,0,540510 /*From ID Server*/,542013,542143,TO_TIMESTAMP('2026-09-10 21:35:00','YYYY-MM-DD HH24:MI:SS'),100,
       'Lists the receipt-disposition rows this delivery instruction carries, planned and unplanned.','D','Y','N',
       'M_ShipperTransportation -> Wareneingangsdisposition',
       TO_TIMESTAMP('2026-09-10 21:35:00','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_RelationType WHERE AD_RelationType_ID=540510)
;
