-- Nets already-distributed amounts out of the virtual column PP_Order.CostDifference (AD_Column 592970).
--
-- The column shows the order's WIP cost imbalance as received-minus-issued. Once a controller discharges
-- that imbalance, the order must stop reporting it, otherwise the cost-imbalance monitor keeps listing
-- orders that have already been dealt with.
--
-- The discharged amount is the MAIN leg of the cost details of the order's cost-difference-distribution
-- collectors, which carries issued-minus-received - the exact negation of what the first two summands
-- produce, so a fully discharged order reads 0. Reversing such a collector stores the negated legs under
-- the same collector type, so the sum cancels itself and the column reports the gross imbalance again.
--
-- The third summand contributes nothing until cost-difference-distribution collectors exist.

UPDATE AD_Column SET ColumnSQL=
'(coalesce((select sum(oc.postcalculationamt)
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
 +
 coalesce((select sum(cd.amt)
   from m_costdetail cd
   join pp_cost_collector cc on cc.pp_cost_collector_id = cd.pp_cost_collector_id
   join c_acctschema acs on acs.c_acctschema_id = cd.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = cd.m_costelement_id and ce.costingmethod = acs.costingmethod
   where cc.pp_order_id = PP_Order.PP_Order_ID
     and cc.costcollectortype = ''170''
     and cd.m_costdetail_type = ''M''), 0)
)',
    Updated=TO_TIMESTAMP('2026-08-27 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592970
;
