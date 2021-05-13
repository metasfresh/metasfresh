DROP FUNCTION IF EXISTS ProductsWithoutInitialCost
(p_D_Client_ID NUMERIC,
 p_ad_org_id NUMERIC);

CREATE OR REPLACE FUNCTION ProductsWithoutInitialCost(
    p_AD_Client_Id NUMERIC,
    p_AD_Org_Id NUMERIC)
    RETURNS table
            (
                AD_Org_ID    numeric,
                OrgValue     text,
                OrgName      text,
                M_Product_ID numeric,
                ProductValue text,
                ProductName  text
            )
AS
$$
WITH element AS
         (
             SELECT el.m_costelement_id

             FROM c_acctschema s
                      JOIN ad_clientinfo ci on ci.ad_client_id = p_AD_Client_Id
                 AND s.c_acctschema_id = ci.c_acctschema1_id
                      JOIN m_costelement el on s.costingmethod = el.costingmethod
         ),
     firstCostDetails AS
         (
             SELECT cd.M_Product_ID,
                    cd.ad_org_id,
                    min(M_CostDetail_ID) as firstCostDetail

             from M_costDetail cd
                      JOIN element on cd.m_costelement_id = element.m_costelement_id

             where (case when p_AD_Org_Id < 0 or p_ad_org_ID IS NULL then 1 = 1 else cd.ad_org_id = p_AD_Org_Id end)
             group by cd.M_Product_ID, cd.AD_Org_ID
         )
        ,
     firstPrices as
         (
             SELECT p.m_product_id,
                    coalesce(fcd.ad_org_id, p.ad_org_id) as ad_org_ID,
                    cd.isSOTrx,
                    (CASE
                         WHEN (cd.Qty = 0 or cd.QTY IS NULL) THEN COALESCE(cd.Amt, 0)
                         ELSE cd.Amt / cd.Qty END)       as price
             FROM M_Product p
                      LEFT JOIN firstCostDetails fcd on p.m_product_id = fcd.m_product_id
                      LEFT JOIN m_costdetail cd on fcd.firstCostDetail = cd.m_costdetail_id
             WHERE cd.m_costdetail_id IS NULL
                OR cd.issotrx = 'N'
         )
SELECT fp.AD_Org_ID,
       o.value as OrgValue,
       o.name  as OrgName,
       p.M_Product_ID,
       p.value,
       p.name
from m_product p
         JOIN firstPrices fp on p.m_product_id = fp.m_product_id
         JOIN AD_Org o on fp.ad_org_ID = o.ad_org_ID
where fp.price = 0
  AND ((p_AD_Org_Id < 0 or p_ad_org_ID IS NULL) OR o.ad_org_ID = p_AD_Org_ID)
order by o.value, p.value
    ;


$$
    LANGUAGE sql
    STABLE;

/*
How to run:

SELECT *
FROM ProductsWithoutInitialCost(1000000, 1000000)
;
 */
