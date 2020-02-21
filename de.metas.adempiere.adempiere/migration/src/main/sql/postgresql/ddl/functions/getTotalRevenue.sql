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
                   CASE
                       WHEN dt.DocBaseType IN ('ARC', 'APC') -- subtract credit memos
                           THEN -1
                       ELSE
                           1
                       END
                   *
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
         JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID

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