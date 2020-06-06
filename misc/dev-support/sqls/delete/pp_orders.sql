delete from pp_order_qty where 1=1;

delete from m_costdetail where pp_cost_collector_id is not null;
delete from m_transaction where pp_cost_collector_id is not null;
delete from m_hu_trace where pp_cost_collector_id is not null;
delete from fact_acct where ad_table_id=get_table_id('PP_Cost_Collector');
delete from pp_order_productattribute where 1=1;
delete from pp_cost_collector where 1=1;

delete from m_picking_candidate_issuetoorder where 1=1;
delete from m_picking_candidate where pickfrom_order_id is not null;

update m_shipmentschedule set pickfrom_order_id=null where pickfrom_order_id is not null;

delete from pp_order_bomline where 1=1;
delete from pp_order_bom where 1=1;
delete from pp_order_cost where 1=1;
update pp_order_workflow set pp_order_node_id=null where pp_order_node_id is not null;
delete from pp_order_node where 1=1;
delete from pp_order_workflow where 1=1;
delete from m_picking_candidate where 1=1;
delete from pp_order where 1=1;
