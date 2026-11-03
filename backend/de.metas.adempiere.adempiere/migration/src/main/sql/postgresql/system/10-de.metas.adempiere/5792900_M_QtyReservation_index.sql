drop index if exists m_qtyreservation_orderline;
create index m_qtyreservation_orderline on m_qtyreservation (c_orderline_id);

drop index if exists m_qtyreservation_order;
create index m_qtyreservation_order on m_qtyreservation (c_order_id);
