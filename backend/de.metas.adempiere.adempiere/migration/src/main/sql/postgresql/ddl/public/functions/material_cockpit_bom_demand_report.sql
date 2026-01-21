DROP FUNCTION IF EXISTS material_cockpit_bom_demand_report(integer[]);


-- Function to generate BOM component report with demand vs supply
CREATE OR REPLACE FUNCTION material_cockpit_bom_demand_report(
    p_qtydemand_qtysupply_v_ids integer[]
)
    RETURNS TABLE (
                      ParentProductValue text,
                      ParentProductName text,
                      ParentQtyReserved numeric,
                      ParentWarehouse text,
                      ParentAttributesKey text,
                      BOMLine text,
                      ComponentProductValue text,
                      ComponentProductName text,
                      BOMQtyPerUnit numeric,
                      BOMPercentage numeric,
                      TotalComponentQtyNeeded numeric,
                      ComponentOnHandQty numeric,
                      Shortage numeric,
                      UOMSymbol text
                  ) AS $$
BEGIN
    RETURN QUERY
        WITH product_demand AS (
            -- Get demand (qtyReserved) for selected QtyDemand_QtySupply_V records
            SELECT
                v.m_product_id,
                v.ProductValue::text,
                v.ProductName::text,
                v.attributesKey::text,
                v.m_warehouse_id,
                v.qtyReserved,
                v.qtyStock as onHandQty,
                v.c_uom_id,
                v.QtyDemand_QtySupply_V_ID,
                COALESCE(w.Name::text, 'All Warehouses') as warehouse_name
            FROM QtyDemand_QtySupply_V v
                     LEFT JOIN m_warehouse w ON v.m_warehouse_id = w.m_warehouse_id
          --  WHERE v.QtyDemand_QtySupply_V_ID = ANY(p_qtydemand_qtysupply_v_ids)
              AND COALESCE(v.qtyReserved, 0) > 0
        ),
             active_boms AS (
                 -- Get the active BOM for each product
                 SELECT DISTINCT ON (pb.m_product_id)
                     pb.m_product_id,
                     pb.pp_product_bom_id,
                     pb.validfrom
                 FROM pp_product_bom pb
                 WHERE pb.IsActive = 'Y'
                   AND pb.docstatus = 'CO' -- Completed BOMs only
                   AND pb.validfrom <= NOW()
                   AND (pb.validto IS NULL OR pb.validto >= NOW())
                 ORDER BY pb.m_product_id, pb.validfrom DESC
             ),
             bom_components AS (
                 -- Get BOM components for each product with demand
                 SELECT DISTINCT
                     pd.m_product_id as parent_product_id,
                     pd.ProductValue as parent_product_value,
                     pd.ProductName as parent_product_name,
                     pd.qtyReserved,
                     pd.attributesKey as parent_attributes_key,
                     pd.m_warehouse_id,
                     pd.warehouse_name,
                     bom.Line::text as bom_line,
                     bom.ProductValue::text as component_product_value,
                     bom.ProductName::text as component_product_name,
                     bom.QtyBOM,
                     bom.Percentage,
                     bom.UOMSymbol::text as component_uom_symbol,
                     p_comp.m_product_id as component_product_id
                 FROM product_demand pd
                          INNER JOIN active_boms ab ON pd.m_product_id = ab.m_product_id
                          CROSS JOIN LATERAL PP_Product_BOM_Recursive_Report(ab.pp_product_bom_id) bom
                          INNER JOIN m_product p_comp ON bom.ProductValue = p_comp.Value
                 WHERE bom.Line IS NOT NULL -- Only actual BOM lines (components)
                   AND bom.QtyBOM IS NOT NULL
                   AND bom.QtyBOM <> 0
             ),
             component_demand AS (
                 -- Calculate total component demand grouped by component and warehouse
                 SELECT
                     bc.parent_product_value,
                     bc.parent_product_name,
                     bc.qtyReserved,
                     bc.warehouse_name,
                     bc.parent_attributes_key,
                     bc.bom_line,
                     bc.component_product_value,
                     bc.component_product_name,
                     bc.QtyBOM,
                     bc.Percentage,
                     bc.component_uom_symbol,
                     bc.component_product_id,
                     bc.m_warehouse_id,
                     -- Total component qty needed = BOM qty * reserved qty of parent
                     (bc.QtyBOM * bc.qtyReserved) as total_component_qty_needed
                 FROM bom_components bc
             ),
             component_stock AS (
                 -- Get stock for components, aggregated by product and warehouse
                 SELECT
                     s.m_product_id,
                     s.m_warehouse_id,
                     SUM(s.qtyStock) as total_stock
                 FROM QtyDemand_QtySupply_V s
                 GROUP BY s.m_product_id, s.m_warehouse_id
             )
        -- Join component demand with stock to compare
        SELECT
            cd.parent_product_value,
            cd.parent_product_name,
            cd.qtyReserved,
            cd.warehouse_name,
            cd.parent_attributes_key,
            cd.bom_line,
            cd.component_product_value,
            cd.component_product_name,
            cd.QtyBOM,
            cd.Percentage,
            SUM(cd.total_component_qty_needed) as total_component_qty_needed,
            COALESCE(MAX(cs.total_stock), 0) as component_on_hand_qty,
            SUM(cd.total_component_qty_needed) - COALESCE(MAX(cs.total_stock), 0) as shortage,
            cd.component_uom_symbol
        FROM component_demand cd
                 LEFT JOIN component_stock cs
                           ON cd.component_product_id = cs.m_product_id
                               AND (cd.m_warehouse_id = cs.m_warehouse_id OR cd.m_warehouse_id IS NULL)
        GROUP BY
            cd.parent_product_value,
            cd.parent_product_name,
            cd.qtyReserved,
            cd.warehouse_name,
            cd.parent_attributes_key,
            cd.bom_line,
            cd.component_product_value,
            cd.component_product_name,
            cd.QtyBOM,
            cd.Percentage,
            cd.component_uom_symbol
        ORDER BY
            cd.parent_product_value,
            cd.bom_line;
END;
$$ LANGUAGE plpgsql;

-- Example usage with QtyDemand_QtySupply_V_IDs:
-- SELECT * FROM material_cockpit_bom_demand_report(ARRAY[1411646192, 1727350501, 1352065672]);
