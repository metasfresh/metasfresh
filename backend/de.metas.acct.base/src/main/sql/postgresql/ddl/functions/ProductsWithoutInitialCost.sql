DROP FUNCTION IF EXISTS "de_metas_acct".report_ProductsWithoutInitialCost
(p_D_Client_ID NUMERIC,
 p_M_CostElement_ID NUMERIC);

CREATE OR REPLACE FUNCTION "de_metas_acct".report_ProductsWithoutInitialCost(
    p_AD_Client_Id NUMERIC,
    p_M_CostElement_ID NUMERIC)
    RETURNS table
            (
                M_Product_ID numeric,
                ProductValue text,
                ProductName  text
            )
AS
$$
WITH sch AS
         (
             select s.c_acctschema_id

             FROM c_acctschema s
                      JOIN ad_clientinfo ci on ci.ad_client_id = p_AD_Client_Id
                 AND s.c_acctschema_id = ci.c_acctschema1_id
         ),
     firstCostDetails AS
         (
             SELECT cd.M_Product_ID,
                    min(M_CostDetail_ID) as firstCostDetail
             from M_costDetail cd
                      JOIN sch on
                     cd.c_acctschema_id = sch.c_acctschema_id
                     AND cd.m_costelement_id = p_M_CostElement_ID

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
                OR cd.issotrx = 'Y'
         )
SELECT p.M_Product_ID,
       p.value as ProductValue,
       p.name  as ProductName
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
FROM "de_metas_acct".report_ProductsWithoutInitialCost(1000000,  1000002)
;
 */

