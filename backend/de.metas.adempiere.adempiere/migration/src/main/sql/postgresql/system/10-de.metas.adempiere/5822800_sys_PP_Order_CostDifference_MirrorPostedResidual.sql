-- PP_Order.CostDifference (AD_Column 592970): the exact negation of the residual the post-calculation
-- action posts, read from the same two fields that action itself reads.
--
-- Replaces a two-term formula whose issued leg was RE-DERIVED as quantity x price. That re-derivation is
-- unsound: the main-product row's price is snapshotted when the order's cost rows are created (converted
-- into the BOM line's UOM, rounded to costing precision) and is never refreshed, while cumulatedamt holds
-- the value actually posted. The two therefore disagree whenever the posted value is not exactly the
-- snapshotted price times the recorded quantity - which happens on unit-conversion rounding, and by a
-- material margin for a catch-weight product, whose per-piece value follows the piece's actual weight
-- while the recorded quantity follows the nominal conversion rate.
--
-- The new definition re-derives nothing: PPOrderCost#getResidualCost() is
-- postCalculationAmount - accumulatedAmount on the main-product row, so its negation is
-- cumulatedamt - postcalculationamt on that same row. Where the residual has been discharged the two are
-- equal and the column correctly reads 0 - including on an order closed long ago, which the old formula
-- showed a phantom difference for.
--
-- Two deliberate narrowings, both matching what the action resolves:
--   * only the main-product row (MR) is read. Co-product and by-product rows are excluded because the
--     distributor reads the main-product row alone; their contribution is already inside that row's
--     postcalculationamt, which is set to total inbound cost minus the co-products' share.
--   * the cost element is narrowed to the schema's ACTIVE MATERIAL element (costelementtype='M',
--     isactive='Y') because the action's getMaterialCostElementId() requires exactly one such element and
--     throws otherwise.

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
