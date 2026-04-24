DROP FUNCTION IF EXISTS report.goods_sales_report(
    p_date_from         DATE,
    p_date_to           DATE,
    p_bpartner_id       NUMERIC,
    p_bpartner_group_id NUMERIC,
    p_product_id        NUMERIC
)
;



CREATE OR REPLACE FUNCTION report.goods_sales_report(
    p_date_from         DATE,
    p_date_to           DATE,
    p_bpartner_id       NUMERIC DEFAULT NULL,
    p_bpartner_group_id NUMERIC DEFAULT NULL,
    p_product_id        NUMERIC DEFAULT NULL
)
    RETURNS TABLE
            (
                ProductNo               VARCHAR, -- ArtNr
                ProductName             VARCHAR, -- Bezeichnung
                PackingInstruction      VARCHAR, -- Packvorschrift
                Customer                VARCHAR, -- Kunde
                QtyTU                   NUMERIC, -- Menge
                Weight                  NUMERIC, -- Gewicht
                NetRevenue              NUMERIC, -- WWert
                Discount                NUMERIC, -- Rabatte
                Revenue                 NUMERIC, -- Erlös
                ProductTotal_QtyTU      NUMERIC, -- Summe Artikel – Menge
                ProductTotal_Weight     NUMERIC, -- Summe Artikel – Gewicht
                ProductTotal_NetRevenue NUMERIC, -- Summe Artikel – WWert
                ProductTotal_Discount   NUMERIC, -- Summe Artikel – Rabatte
                ProductTotal_Revenue    NUMERIC, -- Summe Artikel – Erlös
                GrandTotal_QtyTU        NUMERIC, -- Gesamtsumme – Menge
                GrandTotal_Weight       NUMERIC, -- Gesamtsumme – Gewicht
                GrandTotal_NetRevenue   NUMERIC, -- Gesamtsumme – WWert
                GrandTotal_Discount     NUMERIC, -- Gesamtsumme – Rabatte
                GrandTotal_Revenue      NUMERIC  -- Gesamtsumme – Erlös
            )
AS
$$
BEGIN
    RETURN QUERY
        WITH base_data AS (SELECT p.Value                               AS ProductNo,
                                  p.Name                                AS ProductName,
                                  COALESCE(
                                          (SELECT pip.Name
                                           FROM M_HU_PI_Item_Product pip
                                                    JOIN M_HU_PI_Item pii ON pip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID
                                           WHERE pip.M_Product_ID = p.M_Product_ID
                                             AND pip.IsActive = 'Y'
                                           LIMIT 1),
                                          ''
                                  )                                     AS PackingInstruction,
                                  bp.Name                               AS Customer,
                                  SUM(il.QtyEnteredTU)                  AS QtyTU,
                                  SUM(il.QtyEntered * p.Weight)         AS Weight,
                                  SUM(il.linenetamt)                    AS NetRevenue,
                                  SUM(il.discount)                      AS Discount,
                                  SUM(il.linenetamt) - SUM(il.discount) AS Revenue
                           FROM C_InvoiceLine il
                                    JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
                                    JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
                                    JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
                                    LEFT JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
                           WHERE i.DateInvoiced BETWEEN p_date_from AND p_date_to
                             AND i.IsSOTrx = 'Y'
                             AND i.DocStatus IN ('CO', 'CL')
                             AND il.IsDescription = 'N'
                             AND (p_bpartner_id IS NULL OR bp.C_BPartner_ID = p_bpartner_id)
                             AND (p_bpartner_group_id IS NULL OR bp.C_BP_Group_ID = p_bpartner_group_id)
                             AND (p_product_id IS NULL OR p.M_Product_ID = p_product_id)
                           GROUP BY p.Value,
                                    p.Name,
                                    p.M_Product_ID,
                                    bp.C_BPartner_ID,
                                    bp.Name)
        SELECT base_data.ProductNo,
               base_data.ProductName,
               base_data.PackingInstruction,
               base_data.Customer,
               base_data.QtyTU,
               base_data.Weight,
               base_data.NetRevenue,
               base_data.Discount,
               base_data.Revenue,
               SUM(base_data.QtyTU) OVER (PARTITION BY base_data.ProductNo)      AS ProductTotal_QtyTU,
               SUM(base_data.Weight) OVER (PARTITION BY base_data.ProductNo)     AS ProductTotal_Weight,
               SUM(base_data.NetRevenue) OVER (PARTITION BY base_data.ProductNo) AS ProductTotal_NetRevenue,
               SUM(base_data.Discount) OVER (PARTITION BY base_data.ProductNo)   AS ProductTotal_Discount,
               SUM(base_data.Revenue) OVER (PARTITION BY base_data.ProductNo)    AS ProductTotal_Revenue,
               SUM(base_data.QtyTU) OVER ()                                      AS GrandTotal_QtyTU,
               SUM(base_data.Weight) OVER ()                                     AS GrandTotal_Weight,
               SUM(base_data.NetRevenue) OVER ()                                 AS GrandTotal_NetRevenue,
               SUM(base_data.Discount) OVER ()                                   AS GrandTotal_Discount,
               SUM(base_data.Revenue) OVER ()                                    AS GrandTotal_Revenue
        FROM base_data
        ORDER BY base_data.ProductNo,
                 base_data.Customer;
END;
$$
    LANGUAGE plpgsql
;