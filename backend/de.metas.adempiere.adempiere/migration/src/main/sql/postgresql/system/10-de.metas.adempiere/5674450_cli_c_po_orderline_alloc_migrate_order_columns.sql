UPDATE c_po_orderline_alloc a
SET c_orderPO_id=(SELECT ol.c_order_id FROM c_orderline ol WHERE ol.c_orderline_id = a.c_po_orderline_id)
WHERE c_orderPO_id IS NULL
;

UPDATE c_po_orderline_alloc a
SET c_orderSO_id=(SELECT ol.c_order_id FROM c_orderline ol WHERE ol.c_orderline_id = a.c_so_orderline_id)
WHERE c_orderSO_id IS NULL
;

