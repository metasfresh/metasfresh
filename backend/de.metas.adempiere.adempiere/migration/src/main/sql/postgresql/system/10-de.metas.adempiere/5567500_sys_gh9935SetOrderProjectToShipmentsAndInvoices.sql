UPDATE m_inout
SET c_project_id = (
    SELECT c_project_id
    FROM c_order o
    WHERE o.c_order_id = m_inout.c_order_id
      AND o.issotrx = 'Y'
),
    updatedby=100,
    updated='2020-09-15 01:16:02.096307+03'
WHERE c_project_id IS NULL
;

UPDATE c_invoice
SET c_project_id = (
    SELECT c_project_id
    FROM c_order o
    WHERE o.c_order_id = c_invoice.c_order_id
      AND o.issotrx = 'Y'
),
    updatedby=100,
    updated='2020-09-15 01:16:45.452325+03'
WHERE c_project_id IS NULL
;
