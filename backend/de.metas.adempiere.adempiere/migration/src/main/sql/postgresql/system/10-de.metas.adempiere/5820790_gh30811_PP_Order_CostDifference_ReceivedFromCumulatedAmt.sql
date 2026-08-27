-- gh30811 Manufacturing costing — PP_Order.CostDifference (AD_Column 592970) now reads received-minus-issued
-- straight off the order's PP_Order_Cost rows.
--
-- Supersedes the expression set by 5814670 / 5820560. Two changes:
--
-- 1. The "received" summand moves from postcalculationamt to cumulatedamt on the MR / CO / BY lines.
--    postcalculationamt is written by PPOrderCosts.updatePostCalculationAmountsForCostElement, which
--    distributes the order's total INPUT cost over its outputs — so on the main-product line it holds the
--    ISSUED total, the very figure the second summand already computes from the MaterialIssue lines.
--    Reading it as "received" is therefore wrong. The amount actually received out of the order is
--    cumulatedamt, which is correct on the MR line both before and after the post-calculation fixes.
--
-- 2. The netting of already-distributed amounts out of M_CostDetail (the third summand added by 5820560)
--    is dropped. Discharging an order's residual accumulates it onto the main product's PP_Order_Cost line
--    itself, and reversing the distribution takes it back off, so the first two summands already read zero
--    for a discharged order. Keeping the M_CostDetail summand on top would count the discharge twice and
--    leave a fully discharged order reporting its residual with the opposite sign.
--
-- Verified on a customer-flavored local stack: an order receiving 25 against 10 issued reads 15, one
-- receiving 5 against 10 issued reads -5, and a fully discharged order reads 0.
--
-- Known scope limit: the distribution discharges only the main-product line, so an order carrying
-- co-products or by-products does not net to zero after discharging. Same limit as the expression this
-- supersedes; out of scope here.

UPDATE AD_Column SET ColumnSQL=
'(coalesce((select sum(oc.cumulatedamt)
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id and ce.costingmethod = acs.costingmethod
   where oc.pp_order_id = PP_Order.PP_Order_ID and oc.pp_order_cost_trxtype in (''MR'',''CO'',''BY'')), 0)
 -
 coalesce((select sum(-oc.cumulatedqty * (coalesce(oc.currentcostprice,0) + coalesce(oc.currentcostpricell,0)))
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id and ce.costingmethod = acs.costingmethod
   where oc.pp_order_id = PP_Order.PP_Order_ID and oc.pp_order_cost_trxtype = ''MI''), 0)
)',
    Updated=TO_TIMESTAMP('2026-08-27 15:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592970
;
