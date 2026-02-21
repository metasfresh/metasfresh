DROP FUNCTION IF EXISTS report.SupplyVsDemand_Product_Info(
    p_date_from TIMESTAMP,
    p_date_to   TIMESTAMP
)
;

CREATE OR REPLACE FUNCTION report.SupplyVsDemand_Product_Info(
    p_date_from TIMESTAMP,
    p_date_to   TIMESTAMP
)
    RETURNS TABLE
            (
                ProductNo                  VARCHAR,
                ProductName                VARCHAR,
                CurrentVendorName          VARCHAR,
                CurrentVendorPurchasePrice NUMERIC,
                CurrentCostPrice           NUMERIC,
                QtyPurchased               NUMERIC,
                QtyStock                   NUMERIC,
                QtySold                    NUMERIC,
                UOM                        VARCHAR
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT p.Value,
               p.Name,
               COALESCE(cv_bp.Name, '-'),
               COALESCE(cv_price.PriceStd, 0),
               pc.CurrentCostPrice,
               dsv.qtyToMove,
               dsv.qtyStock,
               COALESCE(sales.QtySold, 0) AS QtySold,
               uom.UOMSymbol

        FROM M_Product p
                 INNER JOIN QtyDemand_QtySupply_V dsv ON p.M_Product_ID = dsv.M_Product_ID
                 INNER JOIN C_UOM uom ON p.C_UOM_ID = uom.C_UOM_ID

            -- Current Vendor and Purchase Price
                 LEFT JOIN C_BPartner_Product cpp ON (
            cpp.M_Product_ID = p.M_Product_ID
                AND cpp.IsCurrentVendor = 'Y'
            )
                 LEFT JOIN C_BPartner cv_bp ON cpp.C_BPartner_ID = cv_bp.C_BPartner_ID

            -- Get purchase price from vendor's pricelist
                 LEFT JOIN LATERAL (
            SELECT pp.PriceStd
            FROM M_ProductPrice pp
                     INNER JOIN M_PriceList_Version plv ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID
                     INNER JOIN M_PriceList pl ON plv.M_PriceList_ID = pl.M_PriceList_ID
            WHERE pp.M_Product_ID = p.M_Product_ID
              AND pl.M_PriceList_ID = cv_bp.PO_PriceList_ID
              AND plv.ValidFrom <= NOW()
              AND pp.IsActive = 'Y'
            ORDER BY plv.ValidFrom DESC
            LIMIT 1
            ) cv_price ON TRUE

            -- Product Cost Price Logic
                 LEFT JOIN LATERAL (
            SELECT mc.CurrentCostPrice
            FROM M_Cost mc
                     INNER JOIN AD_ClientInfo ci ON p.AD_Client_ID = ci.AD_Client_ID
                     INNER JOIN C_AcctSchema acs ON ci.C_AcctSchema1_ID = acs.C_AcctSchema_ID AND COALESCE(acs.AD_Org_ID, 0) = COALESCE(mc.AD_Org_ID, 0)
                     INNER JOIN M_CostElement ce ON mc.M_CostElement_ID = ce.M_CostElement_ID AND COALESCE(ce.AD_Org_ID, 0) = COALESCE(ce.AD_Org_ID, 0)
                AND ce.CostingMethod = acs.CostingMethod
            WHERE mc.M_Product_ID = p.M_Product_ID
            ORDER BY ce.m_costelement_id DESC
            LIMIT 1
            ) pc ON TRUE

            -- Sum of Sold Qty for the Parameterized Period
                 LEFT JOIN LATERAL (
            SELECT SUM(ol.QtyOrdered - ol.qtydelivered) AS QtySold
            FROM C_OrderLine ol
                     INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
                     INNER JOIN C_DocType dt ON o.C_DocTypeTarget_ID = dt.C_DocType_ID
            WHERE ol.M_Product_ID = p.M_Product_ID
              AND dt.DocBaseType = 'SOO'      -- Sales Order
              AND o.DocStatus IN ('CO', 'CL') -- Completed or Closed
              AND o.IsSOTrx = 'Y'             -- Sales transaction
              AND o.DateOrdered >= p_date_from
              AND o.DateOrdered <= p_date_to
              AND ol.IsActive = 'Y'
              AND o.IsActive = 'Y'
            GROUP BY ol.M_Product_ID
            ) sales ON TRUE

        WHERE p.IsPurchased = 'Y'
          AND p.IsActive = 'Y'
        ORDER BY p.Value, p.Name;
END;
$$
    LANGUAGE plpgsql STABLE
;