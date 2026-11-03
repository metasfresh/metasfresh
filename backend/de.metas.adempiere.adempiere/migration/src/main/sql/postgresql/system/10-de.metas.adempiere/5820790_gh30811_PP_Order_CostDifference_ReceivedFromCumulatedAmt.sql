-- PP_Order.CostDifference (AD_Column 592970): received minus issued, off the order's PP_Order_Cost rows.
--
-- Received reads cumulatedamt, not postcalculationamt: post-calculation distributes the order's total
-- INPUT cost over its outputs, so on the main-product line postcalculationamt holds the ISSUED total.
-- Discharging a residual accumulates onto that same main-product line, so a discharged order already
-- reads 0 without netting M_CostDetail on top.
--
-- Scope limit: only the main-product line is discharged, so orders carrying co-/by-products do not
-- net to zero.

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
