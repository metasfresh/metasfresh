update m_inout_cost ioc set c_bpartner_id=(select c_bpartner_id from c_order_cost o where o.c_order_cost_id=ioc.c_order_cost_id)
where c_bpartner_id is null;

update m_inout_cost ioc set c_cost_type_id=(select c_cost_type_id from c_order_cost o where o.c_order_cost_id=ioc.c_order_cost_id)
where c_cost_type_id is null;

