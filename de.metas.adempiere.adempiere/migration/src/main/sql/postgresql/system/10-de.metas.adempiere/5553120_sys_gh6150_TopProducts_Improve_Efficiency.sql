DROP FUNCTION IF EXISTS getTotalRevenue(numeric, numeric, numeric, date, date);


DROP FUNCTION IF EXISTS getTotalRevenue(numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getTotalRevenue (p_AD_Client_ID numeric,
                                            p_AD_Org_ID numeric,
                                            p_DateFrom date,
                                            p_DateTo date)
    RETURNS numeric
AS
$$


WITH t AS (
    SELECT il.M_Product_ID,
           i.DateInvoiced,
           i.C_Currency_ID,
           CASE
               WHEN dt.DocBaseType IN ('ARC', 'APC') -- subtract credit memos
                   THEN -1
                   ELSE
                   1
           END
               AS multiplier,

           CASE
               WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo
                                          ELSE il.LineNetAmt
           END AS amt


    FROM C_InvoiceLine il
             JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
             JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID

    WHERE i.isSOTrx = 'Y'
      AND il.IsActive = 'Y'
      AND i.IsActive = 'Y'
      AND i.DocStatus IN ('CO', 'CL')
      AND i.AD_Client_ID = p_AD_Client_ID
      AND i.AD_Org_ID = p_AD_Org_ID
      AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
      AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo))
     ,
      accounting as (
                  SELECT C.c_currency_id
                  FROM C_Currency C
                           JOIN C_AcctSchema acc
                                ON C.c_currency_id = acc.c_currency_id
                           JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
                  WHERE ci.AD_Client_ID = p_AD_Client_ID
              )


SELECT sum(
                   CASE
                       WHEN (t.C_Currency_ID <> accounting.C_Currency_ID)
                           THEN currencyBase
                           (
                               t.amt,
                               t.C_Currency_ID, -- currencyFrom
                               t.DateInvoiced, -- date
                               p_AD_Client_ID,
                               p_AD_Org_ID
                           )
                           ELSE t.amt
                   END
                   * t.multiplier)

FROM t, accounting;


$$
    LANGUAGE SQL STABLE;













DROP FUNCTION IF EXISTS getProductRevenue(numeric, numeric, numeric, date, date);
DROP FUNCTION IF EXISTS getProductRevenue(numeric, numeric, numeric, numeric, date, date);




CREATE OR REPLACE FUNCTION getProductRevenue (p_M_Product_ID numeric,
                                              p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_DateFrom date,
                                              p_DateTo date)
    RETURNS numeric
AS
$$



WITH t as (

SELECT il.M_Product_ID,
       i.DateInvoiced,
       i.C_Currency_ID,
       CASE
           WHEN dt.DocBaseType IN ('ARC', 'APC') -- subtract credit memos
               THEN -1
               ELSE
               1
       END
           AS multiplier,

       CASE
           WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo
                                      ELSE il.LineNetAmt
       END AS amt


FROM C_InvoiceLine il
         JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
         JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID

WHERE i.isSOTrx = 'Y'
  AND il.IsActive = 'Y'
  AND i.IsActive = 'Y'
  AND i.DocStatus IN ('CO', 'CL')
   AND p_M_Product_ID = il.M_Product_ID
  AND i.AD_Client_ID = p_AD_Client_ID
  AND i.AD_Org_ID = p_AD_Org_ID
  AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
  AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo) ),

      accounting as (
                  SELECT C.c_currency_id
                  FROM C_Currency C
                           JOIN C_AcctSchema acc
                                ON C.c_currency_id = acc.c_currency_id
                           JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
                  WHERE ci.AD_Client_ID = p_AD_Client_ID
              )



SELECT sum(
                   CASE
                       WHEN (t.C_Currency_ID <> accounting.C_Currency_ID)
                           THEN currencyBase
                           (
                               t.amt,
                               t.C_Currency_ID, -- currencyFrom
                               t.DateInvoiced, -- date
                               p_AD_Client_ID,
                               p_AD_Org_ID
                           )
                           ELSE t.amt
                   END
                   * t.multiplier)

    from t, accounting;




$$
    LANGUAGE SQL STABLE;















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
       t.Revenue,
       accounting.Iso_Code,
       t.QtyOnHandStock,
       round((CASE
                  WHEN totals.totalRevenueAmt != 0 THEN (t.Revenue * 100) / totals.totalRevenueAmt
                                                   ELSE 0
              END), 2)                                                                   AS SalesPercentOfTotal

from t, accounting, totals

ORDER BY Rang
LIMIT (CASE
           WHEN p_Limit > 0 THEN p_Limit
                            ELSE 999999999999999
       END)
$$
    LANGUAGE SQL STABLE;













