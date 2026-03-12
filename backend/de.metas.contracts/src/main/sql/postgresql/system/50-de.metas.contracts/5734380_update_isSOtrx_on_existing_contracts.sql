UPDATE c_flatrate_term ft SET issotrx = o.issotrx
FROM (SELECT issotrx, c_order_id FROM c_order) AS o
WHERE ft.c_order_term_id = o.c_order_id
;

