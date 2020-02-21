DROP FUNCTION IF EXISTS getCustomerRevenue(numeric, numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getCustomerRevenue
(p_C_BPartner_ID numeric,
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

WHERE p_C_BPartner_ID = i.C_Bpartner_ID
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