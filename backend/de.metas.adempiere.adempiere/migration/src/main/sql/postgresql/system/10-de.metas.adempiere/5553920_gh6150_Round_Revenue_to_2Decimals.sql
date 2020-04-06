DROP FUNCTION IF EXISTS ProductsTopRevenue(numeric, numeric, date, date, numeric);

DROP FUNCTION IF EXISTS ProductsTopRevenue(numeric, numeric, date, date, numeric, character varying);

CREATE OR REPLACE FUNCTION ProductsTopRevenue
(p_AD_Client_ID numeric,
 p_AD_Org_ID numeric,
 p_DateFrom date,
 p_DateTo date,
 p_Limit numeric,
 p_AD_Language character varying)

    RETURNS TABLE
            (
                Rang                integer,
                ProductValue        character varying,
                Name                character varying,
                Revenue             numeric,
                ISO_Code            character(3),
                QtyOnHandStock      numeric,
                SalesPercentOfTotal numeric
            )
AS
$$


WITH totals AS (
    SELECT COALESCE(getTotalRevenue(p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo), 0) AS totalRevenueAmt
),
     t AS (
         SELECT p.value                                                                              AS ProductValue,
                COALESCE(trl.Name, p.Name)                                                           AS NAME,
                getProductRevenue(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo) AS Revenue,
                getProductCurrentStock(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID)                  AS QtyOnHandStock
         FROM M_Product p
                  LEFT JOIN M_Product_TRL trl ON p.M_Product_ID = trl.M_Product_ID AND trl.ad_language = p_AD_Language
     ),
     accounting AS (
         SELECT C.ISO_Code
         FROM C_Currency C
                  JOIN C_AcctSchema acc
                       ON C.c_currency_id = acc.c_currency_id
                  JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
         WHERE ci.AD_Client_ID = p_AD_Client_ID
     )


SELECT ROW_NUMBER() OVER (ORDER BY t.Revenue DESC NULLS LAST, t.ProductValue) :: INTEGER AS Rang,
       t.ProductValue,
       t.Name,
       round(t.Revenue, 2) as Revenue,
       accounting.Iso_Code,
       t.QtyOnHandStock,
       round((CASE
                  WHEN totals.totalRevenueAmt != 0 THEN (t.Revenue * 100) / totals.totalRevenueAmt
                                                   ELSE 0
              END), 2)                                                                   AS SalesPercentOfTotal

FROM t,
     accounting,
     totals

ORDER BY Rang
LIMIT (CASE
           WHEN p_Limit > 0 THEN p_Limit
                            ELSE 999999999999999
       END)
$$
    LANGUAGE SQL STABLE;



COMMENT ON FUNCTION ProductsTopRevenue(numeric, numeric, date, date, numeric, character varying) IS
    '  TEST
    SELECT *
    FROM ProductsTopRevenue
        (p_AD_Client_ID := 1000000,
         p_AD_Org_ID := 1000000,
         p_DateFrom := NULL,
         p_DateTo := NULL,
         p_Limit := -1,
         p_AD_Language := ''de_DE''); ';
