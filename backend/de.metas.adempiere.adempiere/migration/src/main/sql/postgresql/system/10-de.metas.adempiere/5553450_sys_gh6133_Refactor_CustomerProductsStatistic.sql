DROP FUNCTION IF EXISTS customerItemStatistics(numeric, numeric, DATE, DATE, numeric, numeric);
DROP FUNCTION IF EXISTS customerItemStatistics(numeric, numeric, DATE, DATE, numeric, numeric, character varying);

CREATE OR REPLACE FUNCTION customerItemStatistics(p_AD_Client_ID numeric,
                                                  p_AD_Org_ID numeric,
                                                  p_dateFrom DATE,
                                                  p_dateTo DATE,
                                                  p_C_BPartner_ID numeric,
                                                  p_M_Product_ID numeric,
                                                  p_AD_Language character varying)
    RETURNS TABLE
            (

                BPValue             character varying,
                productValue        character varying,
                Name                character varying,
                qtyInvoiced         numeric,
                UOM                 character varying,
                Revenue             numeric,
                ProductCosts        numeric,
                ISO_Code            character(3),
                ProductCostsPercent numeric

            )
AS


$$

WITH t AS (
    WITH accounting AS (
        SELECT c.C_Currency_ID, c.ISO_Code
        FROM AD_ClientInfo ci
                 JOIN C_AcctSchema acc ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
                 JOIN C_Currency c ON acc.c_currency_id = c.c_currency_id
        WHERE ci.AD_Client_ID = p_AD_Client_ID
    ),
         InvoiceLines AS
             (
                 SELECT i.C_BPartner_ID,
                        i.C_ConversionType_ID,
                        il.M_Product_ID,
                        il.C_UOM_ID,
                        i.C_DocType_ID,
                        i.IsTaxIncluded,
                        il.LineNetAmt,
                        il.TaxAmtInfo,
                        i.C_Currency_ID,
                        i.DateInvoiced,

                        CASE
                            WHEN dt.DocBaseType IN ('ARC', 'APC') -- subtract credit memos
                                THEN -1
                                ELSE
                                1
                        END AS multiplier,

                        CASE
                            WHEN i.IsTaxIncluded = 'Y' THEN il.LineNetAmt - il.TaxAmtInfo
                                                       ELSE il.LineNetAmt
                        END AS amt,

                        CASE
                            WHEN il.c_uom_id <> p.C_UOM_ID -- Only convert the UOM if it's not the same one. The uomconvert function is very time consuming.
                                THEN
                                uomconvert(il.M_Product_ID, il.c_uom_id, p.C_UOM_ID, il.qtyInvoiced)
                                ELSE il.qtyInvoiced
                        END AS QtyInvoiced


                 FROM C_InvoiceLine il
                          JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
                          JOIN C_DocTYpe dt ON i.C_DocType_ID = dt.C_DocType_ID
                          JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID

                 WHERE i.isActive = 'Y'
                   AND il.IsActive = 'Y'
                   AND i.AD_Client_ID = p_AD_Client_ID
                   AND i.AD_Org_ID = p_AD_Org_ID
                   AND i.DocStatus IN ('CO', 'CL')
                   AND (p_C_BPartner_ID IS NULL OR p_C_BPartner_ID <= 0 OR i.C_BPartner_ID = p_C_BPartner_ID)
                   AND (p_M_Product_ID IS NULL OR p_M_Product_ID <= 0 OR il.M_Product_ID = p_M_Product_ID)
                   AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
                   AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo)
             )

    SELECT accounting.ISO_Code,
           bp.value                                                                AS BPValue,
           p.value                                                                 AS productValue,
           COALESCE(trl.name, p.name)                                              AS NAME,
           sum(InvoiceLines.multiplier * InvoiceLines.qtyInvoiced)                 AS qtyInvoiced,
           COALESCE(uomtrl.UOMSymbol, uom.UOMSymbol)                               AS UOM,
           sum(InvoiceLines.multiplier *
               currencyconvert(
                       p_amount := InvoiceLines.amt,
                       p_curfrom_id := InvoiceLines.C_Currency_ID,
                       p_curto_ID := accounting.c_currency_id,
                       p_convdate := InvoiceLines.DateInvoiced,
                       p_conversiontype_id := InvoiceLines.c_conversiontype_id,
                       p_client_id := p_AD_Client_ID,
                       p_org_id := p_AD_Org_ID)
               )                                                                   AS Revenue,

           getCostPrice(InvoiceLines.M_Product_ID, p_AD_Client_ID, p_AD_Client_ID) AS CostPrice


    FROM accounting,
         InvoiceLines
             JOIN M_Product p ON InvoiceLines.M_Product_ID = p.M_Product_ID
             JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID
             JOIN C_BPartner bp ON bp.C_Bpartner_ID = InvoiceLines.C_Bpartner_ID
             LEFT JOIN M_Product_TRL trl ON p.M_Product_ID = trl.M_Product_ID AND trl.AD_language = p_AD_Language
             LEFT JOIN C_UOM_TRL uomtrl ON uom.C_UOM_ID = uomtrl.C_UOM_ID AND uomtrl.AD_Language = p_AD_Language

    GROUP BY bp.value, InvoiceLines.M_Product_ID, p.value, trl.name, p.name, uomtrl.uomSymbol, uom.uomSymbol, accounting.iso_code
)

SELECT t.BPValue,
       t.productValue,
       t.Name,
       t.qtyInvoiced,
       t.UOM,
       t.Revenue                   AS Revenue,
       t.costPrice * t.qtyInvoiced AS ProductCosts,
       t.iso_Code,
       round((CASE
                  WHEN t.Revenue > 0 THEN (t.costPrice * t.qtyInvoiced * 100) / t.Revenue
                                     ELSE 0
              END), 2)             AS ProductCostsPercent


FROM t

ORDER BY t.BPValue, t.productValue
$$
    LANGUAGE SQL
    STABLE;


COMMENT ON FUNCTION customerItemStatistics(NUMERIC, NUMERIC, DATE, DATE, NUMERIC, NUMERIC, CHARACTER VARYING) IS
    '  -- TEST :
    select * from customerItemStatistics(
    1000000,
    1000000,
    NULL,
    NULL,
    NULL,
    NULL,
    ''de_DE''); ';


/* TEST:
DROP TABLE IF EXISTS TMP_customerItemStatistics;
CREATE TEMPORARY TABLE TMP_customerItemStatistics AS
SELECT *
FROM customerItemStatistics(
        1000000,
        1000000,
        NULL,
        NULL,
        NULL,
        NULL,
        'de_DE');
SELECT *
FROM TMP_customerItemStatistics;
 */
