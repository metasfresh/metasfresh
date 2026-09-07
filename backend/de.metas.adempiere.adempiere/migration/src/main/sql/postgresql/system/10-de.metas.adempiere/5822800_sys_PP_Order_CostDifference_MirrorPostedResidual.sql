-- PP_Order.CostDifference (AD_Column 592970): the negation of the residual the post-calculation action
-- posts, i.e. cumulatedamt - postcalculationamt on the main-product row.
--
-- The old formula RE-DERIVED the issued leg as quantity x price. That price is snapshotted when the cost
-- rows are created and never refreshed, so it disagrees with the posted value on unit-conversion rounding,
-- and materially for a catch-weight product (per-piece value follows the actual weight, recorded quantity
-- the nominal rate). Re-deriving nothing also makes a discharged residual correctly read 0, where the old
-- formula showed a phantom difference on long-closed orders.
--
-- Two narrowings, both matching what the action resolves: only the main-product row (MR) - co-/by-product
-- contributions are already inside its postcalculationamt - and only the schema's active material cost
-- element, which getMaterialCostElementId() requires exactly one of.

UPDATE AD_Column SET ColumnSQL=
'(coalesce((select sum(oc.cumulatedamt - oc.postcalculationamt)
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id
    and ce.costingmethod = acs.costingmethod
    and ce.costelementtype = ''M''
    and ce.isactive = ''Y''
   where oc.pp_order_id = PP_Order.PP_Order_ID
     and oc.pp_order_cost_trxtype = ''MR''), 0))',
    Updated=TO_TIMESTAMP('2026-09-04 21:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592970
;
