DROP FUNCTION IF EXISTS getCustomerRevenue(numeric, numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getCustomerRevenue(p_C_BPartner_ID numeric,
                                              p_AD_Client_ID numeric,
                                              p_AD_Org_ID numeric,
                                              p_DateFrom date,
                                              p_DateTo date)
    RETURNS numeric
AS
$$

WITH t AS (
    SELECT i.DateInvoiced,
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
             JOIN C_DocTYpe dt ON i.C_DocType_ID = dt.C_DocType_ID

    WHERE p_C_BPartner_ID = i.C_Bpartner_ID
      AND il.IsActive = 'Y'
      AND i.IsActive = 'Y'
      AND i.isSOTrx = 'Y'
      AND i.DocStatus IN ('CO', 'CL')
      AND i.AD_Client_ID = p_AD_Client_ID
      AND i.AD_Org_ID = p_AD_Org_ID
      AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
      AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo)),

     accounting AS (
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

FROM t,
     accounting;
$$
    LANGUAGE SQL STABLE;



COMMENT ON FUNCTION getCustomerRevenue(numeric, numeric, numeric, date, date) IS
    '   TEST :
     SELECT C_BPartner_ID,
            getCustomerRevenue(C_BPartner_ID,
                               1000000,
                               1000000,
                               NULL,
                               NULL)

     FROM C_BPartner; ';
