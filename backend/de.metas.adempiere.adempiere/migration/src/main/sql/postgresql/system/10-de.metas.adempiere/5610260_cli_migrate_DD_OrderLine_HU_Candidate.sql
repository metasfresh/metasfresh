UPDATE dd_orderline_hu_candidate c
SET dd_order_id=l.dd_order_id
FROM dd_orderline l
WHERE l.dd_orderline_id = c.dd_orderline_id
  AND c.dd_order_id IS NULL
;


UPDATE dd_orderline_hu_candidate
SET pickfrom_hu_id=m_hu_id, ispickwholehu='Y'
WHERE pickfrom_hu_id IS NULL
;

WITH t AS (
    SELECT c.dd_orderline_hu_candidate_id, hus.qty, hus.c_uom_id
    FROM dd_orderline_hu_candidate c
             INNER JOIN dd_orderline l ON l.dd_orderline_id = c.dd_orderline_id
             INNER JOIN m_hu_storage hus ON hus.m_hu_id = c.m_hu_id AND hus.m_product_id = l.m_product_id
    WHERE c.c_uom_id IS NULL
)
UPDATE dd_orderline_hu_candidate c
SET qtytopick=t.qty, c_uom_id=t.c_uom_id
FROM t
WHERE t.dd_orderline_hu_candidate_id = c.dd_orderline_hu_candidate_id
;
