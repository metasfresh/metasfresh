DROP VIEW IF EXISTS M_Forecast_ProductQty_V
;

-- One row per (forecast document x product x warehouse x organisation x attributes-key), carrying that
-- combination's forecast quantity in the product's stock UOM.
--
-- Backs the 'Sprung zu Prognose' overlay of Material Cockpit v2: a forecast document may hold lines for
-- several products, so the quantity the planner sees must be scoped to the product they jumped from,
-- never the document's multi-product total.
--
-- Notes on the column choices:
--  * AttributesKey is computed exactly as the storage attributes key the material-cockpit row carries, so
--    the overlay's where-clause can compare the two directly.
--  * DatePromised and DocStatus are taken from the HEADER: the overlay lists document-level values, and a
--    header-level column is functionally dependent on M_Forecast_ID, so it cannot split a row. (The line's
--    own DatePromised is copied from the header when the line is assigned to a forecast, but it is not
--    re-copied when the header's date is edited afterwards, so the two can drift.)
--  * The line quantity is converted into the product's stock UOM (M_Product.C_UOM_ID) before summing, so
--    lines stored in different UOMs still add up to a single, meaningful figure. A line whose C_UOM_ID is
--    empty is read as already being in the stock UOM -- without that fallback uomconvert() returns NULL and
--    the line would silently vanish from the sum.
--  * Zero-quantity lines are excluded, matching the forecast query the jump has always used.
CREATE OR REPLACE VIEW M_Forecast_ProductQty_V AS
SELECT ABS((('x' || SUBSTR(MD5(CONCAT_WS('#',
                                         fl.AD_Client_ID::text,
                                         fl.AD_Org_ID::text,
                                         fl.M_Forecast_ID::text,
                                         fl.M_Product_ID::text,
                                         fl.M_Warehouse_ID::text,
                                         generateASIStorageAttributesKey(fl.M_AttributeSetInstance_ID))), 1, 10))::bit(32)::int))
           AS M_Forecast_ProductQty_V_ID,
       fl.AD_Client_ID,
       fl.AD_Org_ID,
       'Y'::CHAR(1)                                                        AS IsActive,
       MIN(fl.Created)                                                     AS Created,
       MIN(fl.CreatedBy)                                                   AS CreatedBy,
       MAX(fl.Updated)                                                     AS Updated,
       MAX(fl.UpdatedBy)                                                   AS UpdatedBy,
       fl.M_Forecast_ID,
       fl.M_Product_ID,
       fl.M_Warehouse_ID,
       generateASIStorageAttributesKey(fl.M_AttributeSetInstance_ID)       AS AttributesKey,
       f.DatePromised,
       f.DocStatus,
       SUM(uomconvert(fl.M_Product_ID, COALESCE(fl.C_UOM_ID, p.C_UOM_ID), p.C_UOM_ID, fl.Qty))
           AS Qty,
       p.C_UOM_ID
FROM M_ForecastLine fl
         JOIN M_Forecast f ON f.M_Forecast_ID = fl.M_Forecast_ID
         JOIN M_Product p ON p.M_Product_ID = fl.M_Product_ID
WHERE fl.IsActive = 'Y'
  AND fl.Qty <> 0
GROUP BY fl.AD_Client_ID,
         fl.AD_Org_ID,
         fl.M_Forecast_ID,
         fl.M_Product_ID,
         fl.M_Warehouse_ID,
         generateASIStorageAttributesKey(fl.M_AttributeSetInstance_ID),
         f.DatePromised,
         f.DocStatus,
         p.C_UOM_ID
;
