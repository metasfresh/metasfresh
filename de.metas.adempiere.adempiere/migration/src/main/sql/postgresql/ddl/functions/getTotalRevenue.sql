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
           i.C_ConversionType_ID,
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
     accounting AS (
         SELECT C.c_currency_id
         FROM C_Currency C
                  JOIN C_AcctSchema acc
                       ON C.c_currency_id = acc.c_currency_id
                  JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
         WHERE ci.AD_Client_ID = p_AD_Client_ID
     )


SELECT sum(
                   t.multiplier *
                   currencyconvert(
                           p_amount := t.amt,
                           p_curfrom_id := t.C_Currency_ID,
                           p_curto_ID := accounting.c_currency_id,
                           p_convdate := t.DateInvoiced,
                           p_conversiontype_id := t.c_conversiontype_id,
                           p_client_id := p_AD_Client_ID,
                           p_org_id := p_AD_Org_ID))

FROM t,
     accounting;


$$
    LANGUAGE SQL STABLE;

COMMENT ON FUNCTION
    getTotalRevenue
    (NUMERIC, NUMERIC, date, date)
    IS
        ' TEST :
        SELECT *
        FROM getTotalRevenue(1000000 :: numeric,
                             1000000 ::numeric,
                              NULL :: date,
                              NULL :: date) ; ';

/* TEST:

 SELECT *
        FROM getTotalRevenue(1000000 :: numeric,
                             1000000 ::numeric,
                              NULL :: date,
                              NULL :: date) ;

*/