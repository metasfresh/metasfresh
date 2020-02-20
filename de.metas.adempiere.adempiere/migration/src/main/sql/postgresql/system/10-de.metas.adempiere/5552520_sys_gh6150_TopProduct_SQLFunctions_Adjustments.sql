DROP FUNCTION IF EXISTS getProductRevenue(numeric, numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getProductRevenue (p_M_Product_ID numeric,
                                              p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_DateFrom date,
                                              p_DateTo date)
    RETURNS numeric
AS
$$

SELECT sum
           (
               currencyBase
                   (
                       (
                           SELECT CASE
                                      WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo
                                      ELSE il.LineNetAmt
                                      END
                       ), -- amt
                       i.C_Currency_ID, -- currencyFrom
                       i.DateInvoiced, -- date
                       p_AD_Client_ID,
                       p_AD_Org_ID
                   )
           )

FROM C_InvoiceLine il
         JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID

WHERE p_M_Product_ID = il.M_Product_ID
  AND il.IsActive = 'Y'
  AND i.IsActive = 'Y'
  AND i.isSOTrx = 'Y'
  AND i.DocStatus IN ('CO', 'CL')
  AND i.AD_Client_ID = p_AD_Client_ID
  AND i.AD_Org_ID = p_AD_Org_ID
  AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
  AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo)

$$
    LANGUAGE SQL STABLE;
	
	
	
	
	
	
	
	
	
	
	
	
	
	DROP FUNCTION IF EXISTS getTotalRevenue(numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getTotalRevenue(p_AD_Client_ID numeric,
                                           p_AD_Org_ID numeric,
                                           p_DateFrom date,
                                           p_DateTo date)
    RETURNS numeric
AS

$$

SELECT sum
           (
               currencyBase
                   (
                       (
                           SELECT CASE
                                      WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo
                                      ELSE il.LineNetAmt
                                      END
                       ), -- amt
                       i.C_Currency_ID, -- currencyFrom
                       i.DateInvoiced, -- date
                       p_AD_Client_ID,
                       p_AD_Org_ID
                   )
           )

FROM C_InvoiceLine il
         JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID

WHERE i.isSOTrx = 'Y'
  AND il.IsActive = 'Y'
  AND i.IsActive = 'Y'
  AND i.DocStatus IN ('CO', 'CL')
  AND i.AD_Client_ID = p_AD_Client_ID
  AND i.AD_Org_ID = p_AD_Org_ID
  AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
  AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo)

$$
    LANGUAGE SQL STABLE;
	
	
	
	
	
	
	
	
	
	
	DROP FUNCTION IF EXISTS ProductsTopRevenue(numeric, numeric, date, date, numeric);

CREATE OR REPLACE FUNCTION ProductsTopRevenue
(p_AD_Client_ID numeric,
 p_AD_Org_ID numeric,
 p_DateFrom date,
 p_DateTo date,
 p_Limit numeric)

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

SELECT ROW_NUMBER() OVER (ORDER BY t.Revenue DESC NULLS LAST, t.ProductValue) :: integer AS Rang,
       t.ProductValue,
       t.Name,
       t.Revenue,
       accounting.Iso_Code,
       t.QtyOnHandStock,
       round((CASE
                  WHEN totals.totalRevenueAmt != 0 THEN (t.Revenue * 100) / totals.totalRevenueAmt
                  ELSE 0 END), 2)                                                        AS SalesPercentOfTotal


FROM (
         SELECT p.value                                                                              AS ProductValue,
                p.Name                                                                               AS Name,
                getProductRevenue(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo) AS Revenue,
                getProductCurrentStock(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID)                  AS QtyOnHandStock
         FROM M_Product p
     ) t,
     (
         SELECT COALESCE(getTotalRevenue(p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo), 0) AS totalRevenueAmt
     ) totals,
     (
         SELECT ISO_Code
         FROM C_Currency c
                  JOIN C_AcctSchema acc ON c.c_currency_id = acc.c_currency_id
                  JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
         WHERE ci.AD_Client_ID = p_AD_Client_ID
     ) accounting
ORDER BY Rang
LIMIT (CASE
           WHEN p_Limit > 0 THEN p_Limit
           ELSE 999999999999999 END)

$$
    LANGUAGE SQL STABLE;
	
	
	
	
	