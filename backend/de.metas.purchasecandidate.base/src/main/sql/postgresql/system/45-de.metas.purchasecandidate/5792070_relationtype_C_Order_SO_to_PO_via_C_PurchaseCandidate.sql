-- Run mode: SWING_CLIENT

-- Name: C_Invoice -> Order (SO)
-- Source Reference: C_Order_SOTrx_Source
-- Target Reference: C_Order (PO) Target for C_Order (SO)
-- 2026-03-05T08:00:41.313Z
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, Description, EntityType, IsActive, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540666, 540768, 540493, TO_TIMESTAMP('2026-03-05 08:00:41.027000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'via C_PurchaseCandidate', 'de.metas.ordercandidate', 'Y', 'N', 'C_Invoice -> Order (SO)', TO_TIMESTAMP('2026-03-05 08:00:41.027000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- Reference: C_Order (PO) Target for C_Order (SO)
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2026-03-05T08:02:09.101Z
UPDATE AD_Ref_Table
SET AD_Display=NULL,
    WhereClause='IsSOTrx = ''N'' AND exists (      select 1 from C_Order po         join C_PurchaseCandidate_Alloc pca on pca.C_OrderPO_ID = po.C_Order_ID       join C_PurchaseCandidate pc on pc.C_PurchaseCandidate_ID = pca.C_PurchaseCandidate_ID       join C_Order so on pc.C_OrderSO_ID = so.C_Order_ID    where so.C_Order_ID = @C_Order_ID/-1@ and so.IsSOTrx = ''Y'' and po.C_Order_ID = C_Order.C_Order_ID )',
    Updated=TO_TIMESTAMP('2026-03-05 08:02:09.101000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Reference_ID = 540768
;

-- Name: C_Order (SO) -> C_Order (PO) via C_PurchaseCandidate
-- Source Reference: C_Order_SOTrx_Source
-- Target Reference: C_Order (PO) Target for C_Order (SO)
-- 2026-03-05T08:14:10.861Z
UPDATE AD_RelationType
SET Description='', Name='C_Order (SO) -> C_Order (PO) via C_PurchaseCandidate', Updated=TO_TIMESTAMP('2026-03-05 08:14:10.861000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_RelationType_ID = 540493
;

