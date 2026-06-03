-- Source DDL: backend/de.metas.material/dispo-service/src/main/sql/postgresql/ddl/de_metas_material/MD_getStockWarehouse.sql
-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_MigrationScript sequence: 5806090 (filename prefix)
--
-- Function: MD_getStockWarehouse(p_M_Warehouse_ID numeric) returns numeric
--
-- Maps a picking/packing warehouse to its stocking (source) warehouse via the
-- Distribution Network. If the input warehouse has IsAutoDistributionOrder='Y'
-- and a DD_NetworkDistribution with an active line targeting it, returns the
-- M_WarehouseSource_ID from that line. Otherwise returns the input unchanged.
--
-- LIMIT 1 simplification: if a packing WH has multiple network lines resolving
-- to different sources, only the source with the lowest ID is returned.
-- This is documented as a known simplification — surface for human confirmation
-- if multi-source packing warehouses exist in production.

CREATE OR REPLACE FUNCTION MD_getStockWarehouse(p_M_Warehouse_ID numeric)
RETURNS numeric AS $$
  -- Resolve packing WH to its stocking source; return input unchanged otherwise.
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