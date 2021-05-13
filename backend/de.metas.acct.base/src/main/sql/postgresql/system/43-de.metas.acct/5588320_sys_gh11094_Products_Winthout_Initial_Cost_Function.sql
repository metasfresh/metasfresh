DROP FUNCTION IF EXISTS ProductsWithoutInitialCost
    (p_ad_org_id NUMERIC);

CREATE OR REPLACE FUNCTION ProductsWithoutInitialCost(p_ad_org_id NUMERIC)
    RETURNS table
            (
                M_Product_ID numeric,
                ProductValue text,
                ProductName text
            )
AS
$$
WITH element AS
         (
             SELECT el.m_costelement_id

             FROM c_acctschema s
                      JOIN m_costelement el on s.costingmethod = el.costingmethod
             WHERE s.ad_org_id = 1000000
         ),
     firstCostDetails AS
         (
             SELECT cd.M_Product_ID,
                    min(M_CostDetail_ID) as firstCostDetail

             from M_costDetail cd
                      JOIN element on cd.m_costelement_id = element.m_costelement_id

             group by cd.M_Product_ID
         )
        ,
     firstPrices as
         (
             SELECT p.m_product_id,
                    cd.isSOTrx,
                    (CASE
                         WHEN (cd.Qty = 0 or cd.QTY IS NULL) THEN COALESCE(cd.Amt, 0)
                         ELSE cd.Amt / cd.Qty END) as price
             FROM M_Product p
                      LEFT JOIN firstCostDetails fcd on p.m_product_id = fcd.m_product_id
                      LEFT JOIN m_costdetail cd on fcd.firstCostDetail = cd.m_costdetail_id
             WHERE cd.m_costdetail_id IS NULL
                OR cd.issotrx = 'N'
         )
SELECT p.M_Product_ID,
       p.value,
       p.name
from m_product p
         JOIN firstPrices fp on p.m_product_id = fp.m_product_id
where fp.price = 0
order by p.value
    ;


$$
    LANGUAGE sql
    STABLE;

/*
How to run:

SELECT *
FROM ProductsWithoutInitialCost(1000000)
;
 */
