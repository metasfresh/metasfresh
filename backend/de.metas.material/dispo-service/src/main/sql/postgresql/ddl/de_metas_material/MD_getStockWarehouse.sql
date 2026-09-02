CREATE OR REPLACE FUNCTION MD_getStockWarehouse(p_M_Warehouse_ID numeric)
RETURNS numeric AS $$
  -- If the input warehouse is a picking/packing warehouse with a distribution network,
  -- return the network's SOURCE (stocking) warehouse for the line targeting this WH.
  -- Otherwise return the input warehouse unchanged.
  --
  -- LIMIT 1 simplification: if a packing WH has multiple network lines resolving to
  -- different sources, only the source with the lowest M_WarehouseSource_ID is returned.
  -- This is a documented simplification — flag for human confirmation if multi-source
  -- packing warehouses exist in production.
  SELECT COALESCE(
    ( SELECT ndl.M_WarehouseSource_ID
        FROM M_Warehouse w
        JOIN DD_NetworkDistribution nd
          ON nd.DD_NetworkDistribution_ID = w.DD_NetworkDistribution_ID
         AND nd.IsActive = 'Y'
        JOIN DD_NetworkDistributionLine ndl
          ON ndl.DD_NetworkDistribution_ID = w.DD_NetworkDistribution_ID
         AND ndl.M_Warehouse_ID = w.M_Warehouse_ID          -- line target = this WH
         AND ndl.IsActive = 'Y'
       WHERE w.M_Warehouse_ID = p_M_Warehouse_ID
         AND w.IsActive = 'Y'
         AND w.IsAutoDistributionOrder = 'Y'
         AND w.DD_NetworkDistribution_ID IS NOT NULL
       ORDER BY ndl.M_WarehouseSource_ID
       LIMIT 1 ),
    p_M_Warehouse_ID )
$$ LANGUAGE sql STABLE;
