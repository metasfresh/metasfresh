DROP FUNCTION IF EXISTS report.goods_purchase_report(
    p_date_from         DATE,
    p_date_to           DATE,
    p_bpartner_id       NUMERIC,
    p_product_id        NUMERIC
)
;

CREATE OR REPLACE FUNCTION report.goods_purchase_report(
    p_date_from         DATE,
    p_date_to           DATE,
    p_bpartner_id       NUMERIC DEFAULT NULL,
    p_bpartner_group_id NUMERIC DEFAULT NULL,
    p_product_id        NUMERIC DEFAULT NULL
)
    RETURNS TABLE
            (
                partner_param                 TEXT,
                bp_group_param                TEXT,
                product_param                 TEXT,
                ProductNo                     VARCHAR, -- ArtNr
                ProductName                   VARCHAR, -- Bezeichnung
                PackingInstruction            VARCHAR, -- Packvorschrift
                vendor_value                  VARCHAR, -- Lieferant Value
                Vendor                        VARCHAR, -- Lieferant
                QtyTU                         NUMERIC, -- Menge
                Weight                        NUMERIC, -- Gewicht
                NetPurchaseValue              NUMERIC, -- WWert
                Discount                      NUMERIC, -- Rabatte
                PurchaseValue                 NUMERIC, -- Einkaufswert
                ProductTotal_QtyTU            NUMERIC, -- Summe Artikel – Menge
                ProductTotal_Weight           NUMERIC, -- Summe Artikel – Gewicht
                ProductTotal_NetPurchaseValue NUMERIC, -- Summe Artikel – WWert
                ProductTotal_Discount         NUMERIC, -- Summe Artikel – Rabatte
                ProductTotal_PurchaseValue    NUMERIC, -- Summe Artikel – Einkaufswert
                GrandTotal_QtyTU              NUMERIC, -- Gesamtsumme – Menge       (per currency)
                GrandTotal_Weight             NUMERIC, -- Gesamtsumme – Gewicht     (per currency)
                GrandTotal_NetPurchaseValue   NUMERIC, -- Gesamtsumme – WWert       (per currency)
                GrandTotal_Discount           NUMERIC, -- Gesamtsumme – Rabatte     (per currency)
                GrandTotal_PurchaseValue      NUMERIC, -- Gesamtsumme – Einkaufswert (per currency)
                currency                      CHAR(3)
            )
AS
$$
BEGIN
    RETURN QUERY
        WITH base_data AS (SELECT (SELECT value || ' ' || name FROM C_BPartner WHERE C_BPartner_ID = p_bpartner_id)        AS partner_param,
                                  (SELECT value || ' ' || name FROM c_bp_group WHERE c_bp_group_ID = p_bpartner_group_id)  AS bp_group_param,
                                  (SELECT value || ' ' || name FROM m_product WHERE m_product_id = p_product_id)           AS product_param,

                                  p.Value                                                                                  AS ProductNo,
                                  p.Name                                                                                   AS ProductName,
                                  (SELECT pip.Name
                                   FROM M_HU_PI_Item_Product pip
                                            JOIN M_HU_PI_Item pii ON pip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
                                   WHERE pip.M_Product_ID = p.M_Product_ID
                                     AND pip.IsActive = 'Y'
                                   ORDER BY pip.M_HU_PI_Item_Product_ID
                                   LIMIT 1)                                                                                AS PackingInstruction,
                                  bp.value                                                                                 AS vendor_value,
                                  bp.Name                                                                                  AS Vendor,
                                  SUM(il.QtyEnteredTU)                                                                     AS QtyTU,
                                  SUM(il.QtyEntered * p.Weight)                                                            AS Weight,
                                  SUM(ROUND((100 * il.linenetamt) / NULLIF(100 - COALESCE(il.discount, 0), 0), 2))         AS NetPurchaseValue,
                                  SUM(ROUND(COALESCE(il.discount, 0) * ROUND((100 * il.linenetamt) / NULLIF(100 - COALESCE(il.discount, 0), 0), 2) / 100, 2)) AS Discount,
                                  SUM(il.linenetamt)                                                                       AS PurchaseValue,
                                  cu.iso_code                                                                              AS currency
                           FROM C_InvoiceLine il
                                    JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
                                    JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
                                    JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
                                    JOIN c_currency cu ON i.C_Currency_ID = cu.C_Currency_ID
                                    LEFT JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
                           WHERE i.DateInvoiced BETWEEN p_date_from AND p_date_to
                             AND i.IsSOTrx = 'N'
                             AND i.DocStatus IN ('CO', 'CL')
                             AND il.IsDescription = 'N'
                             AND (p_bpartner_id IS NULL OR bp.C_BPartner_ID = p_bpartner_id)
                             AND (p_bpartner_group_id IS NULL OR bp.C_BP_Group_ID = p_bpartner_group_id)
                             AND (p_product_id IS NULL OR p.M_Product_ID = p_product_id)
                           GROUP BY p.Value,
                                    p.Name,
                                    p.M_Product_ID,
                                    bp.C_BPartner_ID,
                                    bp.value,
                                    bp.Name,
                                    cu.iso_code)
        SELECT base_data.partner_param,
               base_data.bp_group_param,
               base_data.product_param,
               base_data.ProductNo,
               base_data.ProductName,
               base_data.PackingInstruction,
               base_data.vendor_value,
               base_data.Vendor,
               base_data.QtyTU,
               base_data.Weight,
               base_data.NetPurchaseValue,
               base_data.Discount,
               base_data.PurchaseValue,
               -- ProductTotal: per product + currency
               SUM(base_data.QtyTU) OVER (PARTITION BY base_data.ProductNo, base_data.currency)              AS ProductTotal_QtyTU,
               SUM(base_data.Weight) OVER (PARTITION BY base_data.ProductNo, base_data.currency)             AS ProductTotal_Weight,
               SUM(base_data.NetPurchaseValue) OVER (PARTITION BY base_data.ProductNo, base_data.currency)   AS ProductTotal_NetPurchaseValue,
               SUM(base_data.Discount) OVER (PARTITION BY base_data.ProductNo, base_data.currency)           AS ProductTotal_Discount,
               SUM(base_data.PurchaseValue) OVER (PARTITION BY base_data.ProductNo, base_data.currency)      AS ProductTotal_PurchaseValue,
               -- GrandTotal: per currency only — never mix currencies!
               SUM(base_data.QtyTU) OVER (PARTITION BY base_data.currency)                                   AS GrandTotal_QtyTU,
               SUM(base_data.Weight) OVER (PARTITION BY base_data.currency)                                  AS GrandTotal_Weight,
               SUM(base_data.NetPurchaseValue) OVER (PARTITION BY base_data.currency)                        AS GrandTotal_NetPurchaseValue,
               SUM(base_data.Discount) OVER (PARTITION BY base_data.currency)                                AS GrandTotal_Discount,
               SUM(base_data.PurchaseValue) OVER (PARTITION BY base_data.currency)                           AS GrandTotal_PurchaseValue,
               base_data.currency
        FROM base_data
        ORDER BY base_data.currency,
                 base_data.ProductNo,
                 base_data.vendor_value,
                 base_data.Vendor;
END;
$$
    LANGUAGE plpgsql
;
