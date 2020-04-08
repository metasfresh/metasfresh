UPDATE c_invoiceline il
SET c_uom_id = (SELECT mp.c_uom_id FROM m_product mp WHERE il.m_product_id = mp.m_product_id)
WHERE (il.c_uom_id IS NULL OR il.c_uom_id < 1)
;

UPDATE c_invoiceline il
SET price_uom_id = il.c_uom_id
WHERE (il.price_uom_id IS NULL OR il.price_uom_id < 1)
;
