-- Run mode: SWING_CLIENT

-- Reference: C_Order_SOTrx_Target_For_C_Order_POTrx
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-10-21T17:48:03.361Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1  from C_Order so  JOIN C_Order po on so.C_Order_ID = po.Link_Order_ID  where po.isSoTrx = ''N''  AND so.IsSoTrx = ''Y'' AND C_Order.C_Order_ID = so.C_Order_ID AND po.C_Order_ID = @C_Order_ID/-1@) OR EXISTS (select 1  from c_orderline sol           inner join c_po_orderline_alloc pAlloc on sol.c_orderline_id = pAlloc.c_so_orderline_id           inner join c_orderline pol on pol.c_orderline_id = pAlloc.c_po_orderline_id           inner join c_order po on po.c_order_id = pol.c_order_id  where C_Order.c_order_id = sol.c_order_id    and po.c_order_id = @C_Order_ID / 1@)',Updated=TO_TIMESTAMP('2025-10-21 17:48:03.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=540683
;

-- Reference: C_Order_POTrx_Target_For_C_Order_SOTrx
-- Table: C_Order
-- Key: C_Order.C_Order_ID
-- 2025-10-21T17:52:39.751Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 from C_Order so JOIN C_Order po on so.C_Order_ID = po.Link_Order_ID where po.isSoTrx = ''N'' AND so.IsSoTrx = ''Y'' AND C_Order.C_Order_ID = po.C_Order_ID AND so.C_Order_ID = @C_Order_ID/-1@) OR EXISTS (select 1  from c_orderline pol           inner join c_po_orderline_alloc pAlloc                      on pol.c_orderline_id = pAlloc.c_po_orderline_id           inner join c_orderline sol on sol.c_orderline_id = pAlloc.c_so_orderline_id           inner join c_order so on so.c_order_id = sol.c_order_id  where C_Order.c_order_id = pol.c_order_id    and so.c_order_id = @C_Order_ID / 1@)',Updated=TO_TIMESTAMP('2025-10-21 17:52:39.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=540682
;

