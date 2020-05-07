DROP TABLE IF EXISTS tmp_m_cost_to_delete;
CREATE TEMPORARY TABLE tmp_m_cost_to_delete AS
SELECT *
FROM (
         SELECT p.m_product_id,
                p.Value                                                                       AS productValue,
                p.Name                                                                        AS ProductName,
                p.c_uom_id                                                                    AS product_uom_id,
                c.c_uom_id,
                (SELECT count(1) FROM m_costdetail cd WHERE cd.m_product_id = c.m_product_id) AS count_costdetail,
                (SELECT count(1)
                 FROM m_costdetail cd
                 WHERE cd.m_product_id = c.m_product_id
                   AND cd.ischangingcosts = 'Y'
                   AND cd.amt <> 0)                                                           AS count_costdetail_withChangingCosts,
                c.currentqty,
                c.cumulatedqty,
                c.currentcostprice,
                c.currentcostpricell,
                c.cumulatedamt,
                c.created,
                c.updated,
                c.m_cost_id
         FROM m_cost c
                  INNER JOIN m_product p ON c.m_product_id = p.m_product_id
         WHERE TRUE
           AND p.c_uom_id <> c.c_uom_id
           AND currentcostprice = 0
           AND currentcostpricell = 0
           AND currentqty = 0
           AND cumulatedamt = 0
           AND cumulatedqty = 0
           AND c.created = c.updated -- just created, never updated
     ) t
WHERE TRUE
  AND t.count_costdetail_withChangingCosts = 0
-- ORDER BY t.count_costdetail_withChangingCosts DESC
;

-- SELECT * FROM tmp_m_cost_to_delete;

-- drop table backup.m_cost_bkp20200421_2;
create table backup.m_cost_bkp20200421_2 as select * from m_cost where m_cost_id in (select m_cost_id from tmp_m_cost_to_delete);
-- select * from backup.m_cost_bkp20200421_2;

delete from m_cost where m_cost_id in (select m_cost_id from tmp_m_cost_to_delete);

