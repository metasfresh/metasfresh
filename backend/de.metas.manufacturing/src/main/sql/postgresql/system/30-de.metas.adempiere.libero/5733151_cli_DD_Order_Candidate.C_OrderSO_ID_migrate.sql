update dd_order_candidate c set c_orderso_id=(select ol.c_order_ID from c_orderline ol where ol.c_orderline_id=c.c_orderlineso_id);

