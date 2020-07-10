DROP FUNCTION IF EXISTS getCustomerRevenue(numeric, numeric, numeric, date, date);

CREATE OR REPLACE FUNCTION getCustomerRevenue(p_C_BPartner_ID numeric,
                                              p_AD_Client_ID numeric,
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
         JOIN C_DocTYpe dt ON i.C_DocType_ID = dt.C_DocType_ID

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










DROP FUNCTION IF EXISTS CustomersTopRevenue(NUMERIC, NUMERIC, DATE, DATE, INTEGER);

CREATE OR REPLACE FUNCTION CustomersTopRevenue(p_AD_Client_ID NUMERIC,
                                               p_AD_Org_ID NUMERIC,
                                               p_DateFrom DATE,
                                               p_DateTo DATE,
                                               p_Limit INTEGER)

    RETURNS TABLE
            (
                Rang                INTEGER,
                BPValue             CHARACTER VARYING,
                Name                CHARACTER VARYING,
                Revenue             NUMERIC,
                OpenAmt             NUMERIC,
                ISO_Code            CHARACTER(3),
                SalesPercentOfTotal NUMERIC
            )

AS
$$
SELECT ROW_NUMBER() OVER (ORDER BY t.Revenue DESC NULLS LAST, t.BPValue) :: INTEGER AS Rang,
       t.BPValue,
       t.Name,
       t.Revenue,
       t.OpenAmt,
       t.ISO_Code,
       (CASE
            WHEN totals.totalRevenueAmt != 0 THEN (t.Revenue * 100) / totals.totalRevenueAmt
                                             ELSE 0
        END)                                                                        AS SalesPercentOfTotal


FROM (
         SELECT accounting.C_Currency_ID,
                accounting.ISO_Code,
                bp.value AS BPValue,
                bp.Name  AS NAME,
                getCustomerRevenue(bp.C_BPartner_ID, p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo)
                         AS Revenue,

                (getBPOpenAmtToDate(
                        p_AD_Client_ID := p_AD_Client_ID,
                        p_AD_Org_ID := p_AD_Org_ID,
                        p_DateTo := p_DateTo,
                        p_C_BPartner_id := bp.C_BPartner_ID,
                        p_C_Currency_ID := accounting.C_Currency_ID,
                        p_UseDateActual := 'Y',
                        p_IsSOTrx := 'Y')
                    )    AS OpenAmt
         FROM C_BPartner bp,
              (
                  SELECT C.ISO_Code, C.c_currency_id
                  FROM C_Currency C
                           JOIN C_AcctSchema acc
                                ON C.c_currency_id = acc.c_currency_id
                           JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
                  WHERE ci.AD_Client_ID = p_AD_Client_ID
              ) accounting
     ) t,
     (
         SELECT COALESCE(getTotalRevenue(p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo), 0) AS totalRevenueAmt
     ) totals


ORDER BY Rang
LIMIT (CASE
           WHEN p_Limit > 0 THEN p_Limit
                            ELSE 999999999999999
       END)
$$
    LANGUAGE SQL
    VOLATILE;