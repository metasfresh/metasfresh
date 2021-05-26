DROP FUNCTION IF EXISTS "de_metas_acct".report_M_Product_QtyBooked_For_Date
(p_M_Product_ID NUMERIC,
 p_m_warehouse_ID NUMERIC,
 p_date DATE);

CREATE OR REPLACE FUNCTION "de_metas_acct".report_M_Product_QtyBooked_For_Date(
    p_M_CostElement_ID NUMERIC,
    p_M_Product_ID NUMERIC,
    p_m_warehouse_ID NUMERIC,
    p_date DATE)
    RETURNS table
            (
                WarehouseName Text,
                ProductValue  text,
                ProductName   text,
                QtyBook       NUMERIC
            )
AS
$$


WITH tmp_costdetails AS
         (
             SELECT cd.m_product_id,
                    cd.amt,
                    cd.qty,
                    cd.issotrx,
                    COALESCE(inv.movementdate, io.MovementDate, m.movementdate) AS movementDate,
                    COALESCE(
                            inv.m_warehouse_id,
                            io.m_warehouse_id,
                            (CASE WHEN cd.issotrx = 'Y' THEN ml_locFrom.m_warehouse_id ELSE ml_locTo.m_warehouse_id END)
                        )                                                       AS m_warehouse_id,
                    --
                    iol.m_inout_id,
                    cd.m_inoutline_id,
                    cd.m_movementline_id,
                    inv.m_inventory_id,
                    cd.m_inventoryline_id,
                    cd.m_costdetail_id
             FROM m_costdetail cd
                      LEFT OUTER JOIN m_inventoryline il ON il.m_inventoryline_id = cd.m_inventoryline_id
                      LEFT OUTER JOIN M_Inventory inv ON inv.m_inventory_id = il.m_inventory_id
                      LEFT OUTER JOIN M_MovementLine ml ON ml.m_movementline_id = cd.m_movementline_id
                      LEFT OUTER JOIN M_Locator ml_locFrom ON ml.m_locator_id = ml_locFrom.m_locator_id
                      LEFT OUTER JOIN M_Locator ml_locTo ON ml.m_locatorto_id = ml_locTo.m_locator_id
                      LEFT OUTER JOIN M_Movement m ON m.m_movement_id = ml.m_movement_ID
                      LEFT OUTER JOIN m_inoutline iol ON iol.m_inoutline_id = cd.m_inoutline_id
                      LEFT OUTER JOIN m_inout io ON iol.m_inout_id = io.m_inout_id
             WHERE TRUE
               AND cd.m_costelement_id = p_M_CostElement_ID
               AND COALESCE(cd.m_inoutline_id, cd.m_movementline_id, cd.m_inventoryline_id) IS NOT NULL
         )


SELECT wh.name  as WarehouseName,
       p.value  as ProductValue,
       p.name   as ProductName,

       sum(qty) as qtyBook
from m_product p
         LEFT JOIN tmp_costdetails cd
                   on p.m_product_id = cd.m_product_id
                       AND (p_m_warehouse_ID <= 0 OR cd.m_warehouse_id = p_m_warehouse_ID)
                       AND ((p_date IS NULL AND cd.movementDate <= now()) OR cd.movementDate <= p_date)
--
         LEFT JOIN M_Warehouse wh on cd.M_Warehouse_ID = wh.M_Warehouse_ID
WHERE (p_M_Product_ID <= 0 OR p.m_product_id = p_M_Product_ID)
group by wh.name, p.value, p.name
order by p.value
    ;


$$
    LANGUAGE sql
    STABLE;

/*
How to run:

SELECT *
FROM "de_metas_acct".report_M_Product_QtyBooked_For_Date(  1000002,
    -1 ,
    -1 ,
    now()::date)
;
 */

