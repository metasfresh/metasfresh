update m_qtyreservation r set c_order_id=(select ol.c_order_id from c_orderline ol where ol.c_orderline_id=r.c_orderline_id)
where c_order_id is null;

