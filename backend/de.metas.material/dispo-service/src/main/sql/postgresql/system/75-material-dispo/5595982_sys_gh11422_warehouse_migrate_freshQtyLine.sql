SELECT wh.m_warehouse_id, l.*
FROM fresh_qtyonhand_line l
         LEFT JOIN m_warehouse wh ON wh.PP_Plant_ID = l.PP_Plant_ID
WHERE wh.m_warehouse_id IS NULL
;


--- fill in the plant's warehouse
UPDATE fresh_qtyonhand_line l
SET M_Warehouse_ID=data.M_Warehouse_ID, updated='2021-09-07 09:24', updatedby=99
FROM (
         SELECT wh.m_warehouse_id, l.fresh_qtyonhand_line_ID
         FROM fresh_qtyonhand_line l
                  JOIN m_warehouse wh ON wh.PP_Plant_ID = l.PP_Plant_ID
     ) data
WHERE data.fresh_qtyonhand_line_ID = l.fresh_qtyonhand_line_id
;

--- fall back to the org's warehouse
UPDATE fresh_qtyonhand_line l
SET M_Warehouse_ID= (SELECT wh.m_warehouse_id
                     FROM ad_orginfo o
                              JOIN m_warehouse wh ON o.m_warehouse_id = wh.m_warehouse_id
                     WHERE o.ad_org_id = l.ad_org_id),
    updated='2021-09-07 09:24',
    updatedby=99
WHERE l.m_warehouse_id IS NULL
;

--- fall back to any warehouse from the org
UPDATE fresh_qtyonhand_line l
SET M_Warehouse_ID= (SELECT wh.m_warehouse_id FROM m_warehouse wh WHERE l.ad_org_id = wh.ad_org_id AND wh.isactive = 'Y' limit 1),
    updated='2021-09-07 09:24',
    updatedby=99
WHERE l.m_warehouse_id IS NULL
;

