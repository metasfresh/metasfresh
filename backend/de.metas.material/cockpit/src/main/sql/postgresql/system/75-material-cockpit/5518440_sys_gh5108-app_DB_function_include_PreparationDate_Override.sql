
CREATE INDEX m_shipmentSchedule_PreparationDate_Override_Product 
ON public.m_shipmentSchedule (PreparationDate_Override DESC, M_Product_ID)
WHERE PreparationDate_Override IS NOT NULL;

DROP VIEW IF EXISTS de_metas_material.MD_ShipmentQty_V;
CREATE VIEW de_metas_material.MD_ShipmentQty_V AS
SELECT 
  sq.QtyToBeShipped, 
  p.C_UOM_ID,
  p.M_Product_ID,
  sq.AttributesKey,
  sq.s_PreparationDate_Override,
  sq.s_PreparationDate, 
  sq.o_PreparationDate,
  sq.SalesOrderLastUpdated
FROM
  M_Product p
  JOIN LATERAL (
    select 
        COALESCE(s.QtyToDeliver, ol.QtyOrdered) AS QtyToBeShipped,

        /* we expect relatively few rows to create the AttributesKey for, 
           because this view is invoked only for a certain SalesOrderLastUpdated /PreparationDate range */
        GenerateHUStorageASIKey( COALESCE(s.M_AttributeSetInstance_ID, ol.M_AttributeSetInstance_ID), '-1002'/*NONE*/) AS AttributesKey,

        /* avoid COALESCE because we can't index on COALESCE expressions. so we want to have these columns in our whereclause */
        s_PreparationDate_Override,
        s_PreparationDate, 
        o_PreparationDate,
        ol.Updated AS SalesOrderLastUpdated
    from 
    ( 
        select ol.C_OrderLine_ID, 
            ol.M_AttributeSetInstance_ID, 
            ol.QtyOrdered, 
            o.PreparationDate AS o_PreparationDate, 
            ol.Updated
        from C_OrderLine ol
        join C_Order o ON o.C_Order_ID=ol.C_Order_ID
        where o.IsSOTrx='Y' AND ol.Processed='N'
            AND ol.M_Product_ID = p.M_Product_ID
    ) ol
    FULL OUTER JOIN (
        select s.C_OrderLine_ID, 
            s.M_AttributeSetInstance_ID, 
            s.PreparationDate_Override AS s_PreparationDate_Override, 
            s.PreparationDate AS s_PreparationDate, 
            s.QtyToDeliver
        from M_ShipmentSchedule s
        where s.Processed='N'
            AND s.M_Product_ID = p.M_Product_ID
    ) s ON s.C_OrderLine_ID=ol.C_OrderLine_ID
  ) sq ON true
WHERE true;



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
  C_UOM_ID numeric,
  SalesOrderLastUpdated timestamptz, 
  ShipmentPreparationDate timestamptz
)
AS
$BODY$
  SELECT p_QueryNo, p_M_Product_ID, p_StorageAttributesKey, QtyToBeShipped, QtyOnHandStock, stock.C_UOM_ID, SalesOrderLastUpdated, ShipmentPreparationDate
  FROM
  (
    SELECT 
      SUM(QtyToBeShipped) AS QtyToBeShipped, 
      C_UOM_ID, 
      SalesOrderLastUpdated, 
      COALESCE(s_PreparationDate_Override, s_PreparationDate, o_PreparationDate) AS ShipmentPreparationDate
    FROM de_metas_material.MD_ShipmentQty_V sq
    WHERE sq.M_Product_ID = p_M_Product_ID
      AND sq.AttributesKey ILIKE '%' || p_StorageAttributesKey || '%'
      AND ( /*max. future PreparationDates we still care about */
        sq.s_PreparationDate_Override <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
        OR sq.s_PreparationDate_Override IS NULL AND sq.s_PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
        OR sq.s_PreparationDate_Override IS NULL AND sq.s_PreparationDate IS NULL AND sq.o_PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
      )
      AND (sq.SalesOrderLastUpdated IS NULL /*could have used COALESCE(sq.SalesOrderLastUpdated, now()), but i hope this makes it easier for the planner*/
        OR sq.SalesOrderLastUpdated >= (now() - (p_salesOrderLookBehindHours || ' hours')::INTERVAL) /*min updated value that is not yet too old for us to care */
      )
    GROUP BY C_UOM_ID, SalesOrderLastUpdated, COALESCE(s_PreparationDate_Override, s_PreparationDate, o_PreparationDate)
  ) sales
  FULL OUTER JOIN (
    SELECT SUM(QtyOnHand) AS QtyOnHandStock, p.C_UOM_ID
    FROM MD_Stock s
      JOIN M_Product p ON p.M_Product_ID=s.M_Product_ID
    WHERE s.M_Product_ID = p_M_Product_ID
      AND s.AttributesKey ILIKE '%' || p_StorageAttributesKey || '%'
      AND s.IsActive='Y'
    GROUP BY C_UOM_ID
  ) stock ON TRUE
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer) IS
'Returns the current stock and the shipments to be expected in the nearest future.
Parameters:
* p_QueryNo: the given value is returend in the QueryNo result column; used by the function invoker in the context of select .. union.
* p_M_Product_ID
* p_StorageAttributesKey: the function will return rows whose attributesKey is a substring of this parameter. -1002 means "no storage attributes".
* p_PreparationDate: the date in question, where the respective quantity would have to be available. Returned rows have a ShipmentPreparationDate and SalesOrderLastUpdated within a certain range around this parameter''s value.
* p_shipmentDateLookAheadHours: returned rows have a ShipmentPreparationDate not after p_PreparationDate plus the given number of hours.
* p_salesOrderLookBehindHours: Used to ignore old/stale sales order lines. Returned rows have a SalesOrderLastUpdated date not before now() minus the given number of hours.

Also see https://github.com/metasfresh/metasfresh/issues/5108'
;
