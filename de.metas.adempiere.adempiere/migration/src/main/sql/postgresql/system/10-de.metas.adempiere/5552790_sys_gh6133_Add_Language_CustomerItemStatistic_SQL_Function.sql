DROP FUNCTION IF EXISTS customerItemStatistics(numeric, numeric, date, date, numeric, numeric);
DROP FUNCTION IF EXISTS customerItemStatistics(numeric, numeric, date, date, numeric, character varying);

CREATE OR REPLACE FUNCTION customerItemStatistics(p_AD_Client_ID numeric,
                                                  p_AD_Org_ID numeric,
                                                  p_dateFrom date,
                                                  p_dateTo date,
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


SELECT t.BPValue,
       t.productValue,
       t.Name,
       t.qtyInvoiced,
       t.UOM,
       t.Revenue                   AS Revenue,
       t.costPrice * t.qtyInvoiced AS ProductCosts,
       accounting.iso_Code,
       round((CASE
                  WHEN t.Revenue > 0 THEN (t.costPrice * t.qtyInvoiced * 100) / t.Revenue
                  ELSE 0 END), 2)  AS ProductCostsPercent

FROM (
         SELECT bp.value                                                     AS BPValue,
                p.value                                                      AS productValue,
                COALESCE(trl.name, p.name)                                   AS Name,
                sum(
                        uomconvert(p.M_Product_ID, il.c_uom_id, p.C_UOM_ID, il.qtyInvoiced)
                    )                                                        AS qtyInvoiced,
                (SELECT COALESCE(uomtrl.UOMSymbol, uom.UOMSymbol)
                 FROM C_UOM uom
                          LEFT JOIN C_UOM_TRL uomtrl ON uom.C_UOM_ID = uomtrl.C_UOM_ID AND uomtrl.AD_Language = p_AD_Language
                 WHERE uom.C_UOM_ID = p.C_UOM_ID)                            AS UOM,

                sum
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
                                                                             AS Revenue,
                -- note: We don't have to convert this to the accounting currency because these costs are already in the accounting currency
                -- see de.metas.costing.impl.CurrentCostsRepository.create(CostSegmentAndElement)
                getCostPrice(p.M_Product_ID, p_AD_Client_ID, p_AD_Client_ID) AS costPrice

         FROM M_Product p
                  LEFT JOIN M_Product_TRL trl ON p.M_Product_ID = trl.M_Product_ID AND trl.AD_language = p_AD_Language
                  JOIN C_InvoiceLine il ON p.M_Product_ID = il.M_Product_ID
                  JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
                  JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID

         WHERE i.isActive = 'Y'
           AND il.IsActive = 'Y'
           AND i.AD_Client_ID = p_AD_Client_ID
           AND i.AD_Org_ID = p_AD_Org_ID
           AND i.DocStatus IN ('CO', 'CL')
           AND (p_C_BPartner_ID IS NULL OR p_C_BPartner_ID <= 0 OR i.C_BPartner_ID = p_C_BPartner_ID)
           AND (p_M_Product_ID IS NULL OR p_M_Product_ID <= 0 OR p.M_Product_ID = p_M_Product_ID)
           AND (p_DateFrom IS NULL OR i.DateInvoiced >= p_dateFrom)
           AND (p_DateTo IS NULL OR i.DateInvoiced <= p_dateTo)

         GROUP BY bp.value, p.M_Product_ID, p.C_UOM_ID, trl.name
     ) t,
     (
         SELECT ISO_Code
         FROM C_Currency c
                  JOIN C_AcctSchema acc ON c.c_currency_id = acc.c_currency_id
                  JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID = acc.C_AcctSchema_ID
         WHERE ci.AD_Client_ID = p_AD_Client_ID
     ) accounting
ORDER BY t.BPValue, t.productValue
$$
    LANGUAGE SQL
    STABLE;

