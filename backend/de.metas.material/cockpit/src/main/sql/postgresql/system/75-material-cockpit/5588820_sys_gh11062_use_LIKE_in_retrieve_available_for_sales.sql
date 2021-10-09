
DROP FUNCTION IF EXISTS de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer);
CREATE FUNCTION de_metas_material.retrieve_available_for_sales(
    IN p_QueryNo integer,
    IN p_M_Product_ID numeric,
    IN p_StorageAttributesKey character varying,
    IN p_PreparationDate timestamptz,
    IN p_shipmentDateLookAheadHours integer,
    IN p_salesOrderLookBehindHours integer)
    RETURNS TABLE
            ( QueryNo integer,
              M_Product_ID numeric,
              StorageAttributesKey character varying,
              QtyToBeShipped numeric,
              QtyOnHandStock numeric,
              C_UOM_ID numeric
            )
AS
$BODY$
SELECT p_QueryNo,
       p_M_Product_ID,
       COALESCE(sales.AttributesKey, stock.AttributesKey),
       QtyToBeShipped,
       QtyOnHandStock,
       stock.C_UOM_ID
FROM (
         SELECT SUM(QtyToBeShipped) AS QtyToBeShipped,
                sq.AttributesKey    AS AttributesKey,
                C_UOM_ID
         FROM de_metas_material.MD_ShipmentQty_V sq
         WHERE sq.M_Product_ID = p_M_Product_ID
           -- if not ALL, then we want an exact match. i.e. when looking for goods without any attribute, we do not want to find e.g. goods with an "expired" attribute
           AND ('-1000' LIKE p_StorageAttributesKey /*ALL*/ OR sq.AttributesKey LIKE p_StorageAttributesKey)
           AND ( /*max. future PreparationDates we still care about; avoiding coalesce in an attempt to make it easier for the planner */
                 sq.s_PreparationDate_Override <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
                 OR sq.s_PreparationDate_Override IS NULL AND sq.s_PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
                 OR sq.s_PreparationDate_Override IS NULL AND sq.s_PreparationDate IS NULL AND sq.o_PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
             )
           AND (sq.SalesOrderLastUpdated IS NULL /*could have used COALESCE(sq.SalesOrderLastUpdated, now()), but i hope this makes it easier for the planner*/
             OR sq.SalesOrderLastUpdated >= (NOW() - (p_salesOrderLookBehindHours || ' hours')::INTERVAL) /*min updated value that is not yet too old for us to care */
             )
         GROUP BY C_UOM_ID, sq.AttributesKey
     ) sales
         FULL OUTER JOIN (
    SELECT SUM(QtyOnHand)  AS QtyOnHandStock,
           s.AttributesKey AS AttributesKey,
           p.C_UOM_ID
    FROM MD_Stock s
             JOIN M_Product p ON p.M_Product_ID = s.M_Product_ID
    WHERE s.M_Product_ID = p_M_Product_ID
      AND s.IsActive = 'Y'
      -- if not ALL, then we want an exact match. i.e. when looking for goods without any attribute, we do not want to find e.g. goods with an "expired" attribute
      AND ('-1000' LIKE p_StorageAttributesKey /*ALL*/ OR s.AttributesKey LIKE p_StorageAttributesKey)
    GROUP BY s.AttributesKey,
             C_UOM_ID
) stock ON TRUE
$BODY$

LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer) IS
    'Returns the current stock and the shipments to be expected in the nearest future.
    Parameters:
    * p_QueryNo: the given value is returend in the QueryNo result column; used by the function invoker in the context of select .. union.
    * p_M_Product_ID
    * p_StorageAttributesKey: if not -1000 ("ALL"), then the function will return rows whose attributesKey is an exact match of this parameter''s value. -1002 means "no storage attributes".
    * p_PreparationDate: the date in question, where the respective quantity would have to be available. Returned rows have a ShipmentPreparationDate and SalesOrderLastUpdated within a certain range around this parameter''s value.
    * p_shipmentDateLookAheadHours: returned rows have a ShipmentPreparationDate not after p_PreparationDate plus the given number of hours.
    * p_salesOrderLookBehindHours: Used to ignore old/stale sales order lines. Returned rows have a SalesOrderLastUpdated date not before now() minus the given number of hours.

    Also see https://github.com/metasfresh/metasfresh/issues/5108
    Also see https://github.com/metasfresh/me03/issues/7048'
;
