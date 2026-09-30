-- Source DDL: backend/de.metas.material/cockpit/src/main/sql/postgresql/ddl/functions/de_metas_material/Retrieve_available_for_Sales.sql
--
-- Performance rewrite of de_metas_material.retrieve_available_for_sales (Available-for-Sales,
-- gh#5108). The previous body read from de_metas_material.MD_ShipmentQty_V and applied the
-- look-behind / look-ahead date filters on top of the view. Because the view FULL-OUTER-JOINs
-- the order-line side and the shipment-schedule side, and the filter contained
-- "... OR SalesOrderLastUpdated IS NULL", Postgres could not push the date filters down: for a
-- high-runner product it materialised the product's ENTIRE C_OrderLine history (150k+ rows) and
-- only then discarded almost all of it -> several seconds per order-line entry.
--
-- The view's two demand sides are disjoint (the order-line side excludes lines that already have
-- a shipment schedule; the schedule side is the schedules), so the join is effectively a
-- concatenation. We inline both sides as separate branches and push the date filters INTO each
-- source, so the look-behind prunes order lines (via the existing C_OrderLine(Updated, M_Product_ID)
-- index) before any join. Output semantics are unchanged.

SELECT db_drop_functions('*.retrieve_available_for_sales')
;

CREATE FUNCTION de_metas_material.retrieve_available_for_sales(
    IN p_QueryNo                    integer,
    IN p_M_Product_ID               numeric,
    IN p_StorageAttributesKey       character varying,
    IN p_PreparationDate            timestamptz,
    IN p_shipmentDateLookAheadHours integer,
    IN p_salesOrderLookBehindHours  integer,
    IN p_AD_ORG_ID                  numeric,
    IN p_M_Warehouse_ID             numeric DEFAULT NULL)
    RETURNS TABLE
            (
                QueryNo              integer,
                M_Product_ID         numeric,
                StorageAttributesKey character varying,
                QtyToBeShipped       numeric,
                QtyOnHandStock       numeric,
                C_UOM_ID             numeric,
                AD_ORG_ID            numeric,
                M_Warehouse_ID       NUMERIC
            )
AS
$BODY$
SELECT p_QueryNo,
       p_M_Product_ID,
       final.AttributesKey,
       SUM(final.QtyToBeShipped) AS QtyToBeShipped,
       SUM(final.QtyOnHandStock) AS QtyOnHandStock,
       final.C_UOM_ID,
       p_AD_ORG_ID               AS AD_ORG_ID,
       final.M_Warehouse_ID
FROM (
         -- (1) demand from open sales-order lines that do NOT yet have a shipment schedule.
         --     Look-behind is applied here on ol.Updated (the former SalesOrderLastUpdated) so
         --     the product's order-line history is pruned before the anti-join. Look-ahead
         --     applies to the order's PreparationDate (these lines have no schedule yet).
         SELECT GenerateASIStorageAttributesKey(ol.M_AttributeSetInstance_ID) AS AttributesKey,
                SUM(ol.QtyOrdered)                                            AS QtyToBeShipped,
                0                                                             AS QtyOnHandStock,
                p.C_UOM_ID                                                    AS C_UOM_ID,
                o.M_Warehouse_ID                                              AS M_Warehouse_ID
         FROM M_Product p
                  JOIN C_OrderLine ol ON ol.M_Product_ID = p.M_Product_ID
                  JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
         WHERE p.M_Product_ID = p_M_Product_ID
           AND o.IsSOTrx = 'Y'
           AND ol.AD_Org_ID = p_AD_ORG_ID
           AND ol.Updated >= (NOW() - (p_salesOrderLookBehindHours || ' hours')::INTERVAL)
           AND o.PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
           AND NOT EXISTS (SELECT 1 FROM M_ShipmentSchedule sched WHERE sched.C_OrderLine_ID = ol.C_OrderLine_ID)
           AND (COALESCE(p_M_Warehouse_ID, o.M_Warehouse_ID) = o.M_Warehouse_ID)
         GROUP BY p.C_UOM_ID, GenerateASIStorageAttributesKey(ol.M_AttributeSetInstance_ID), o.M_Warehouse_ID

         UNION ALL

         -- (2) demand from open (Processed='N') shipment schedules. Look-ahead applies to the
         --     schedule's effective preparation date. No look-behind here: these rows previously
         --     had a NULL SalesOrderLastUpdated and were therefore always kept.
         SELECT GenerateASIStorageAttributesKey(s.M_AttributeSetInstance_ID)  AS AttributesKey,
                SUM(GREATEST(COALESCE(s.QtyReserved, 0), 0))                  AS QtyToBeShipped,
                0                                                             AS QtyOnHandStock,
                p.C_UOM_ID                                                    AS C_UOM_ID,
                COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID)         AS M_Warehouse_ID
         FROM M_Product p
                  JOIN M_ShipmentSchedule s ON s.M_Product_ID = p.M_Product_ID
         WHERE p.M_Product_ID = p_M_Product_ID
           AND s.Processed = 'N'
           AND s.AD_Org_ID = p_AD_ORG_ID
           AND (s.PreparationDate_Override <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)
               OR (s.PreparationDate_Override IS NULL
                   AND s.PreparationDate <= (p_PreparationDate + (p_shipmentDateLookAheadHours || ' hours')::INTERVAL)))
           AND (COALESCE(p_M_Warehouse_ID, COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID))
               = COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID))
         GROUP BY p.C_UOM_ID, GenerateASIStorageAttributesKey(s.M_AttributeSetInstance_ID), COALESCE(s.M_Warehouse_Override_ID, s.M_Warehouse_ID)

         UNION ALL

         -- (3) on-hand stock (unchanged)
         SELECT s.AttributesKey  AS AttributesKey,
                0                AS QtyToBeShipped,
                SUM(s.QtyOnHand) AS QtyOnHandStock,
                p.C_UOM_ID       AS C_UOM_ID,
                s.M_Warehouse_ID AS M_Warehouse_ID
         FROM MD_Stock s
                  JOIN M_Product p ON p.M_Product_ID = s.M_Product_ID
         WHERE s.M_Product_ID = p_M_Product_ID
           AND s.IsActive = 'Y'
           AND s.AD_ORG_ID = p_AD_ORG_ID
           AND (COALESCE(p_M_Warehouse_ID, s.M_Warehouse_ID) = s.M_Warehouse_ID)
         GROUP BY s.AttributesKey, p.C_UOM_ID, s.M_Warehouse_ID
     ) AS final
-- attribute-key filter (kept here so it applies uniformly to all three branches)
WHERE ('-1000' LIKE p_StorageAttributesKey /*ALL*/ OR final.AttributesKey LIKE p_StorageAttributesKey)
GROUP BY final.AttributesKey, final.C_UOM_ID, final.M_Warehouse_ID

$BODY$
    LANGUAGE sql STABLE
;

COMMENT ON FUNCTION de_metas_material.retrieve_available_for_sales(integer, numeric, character varying, timestamptz, integer, integer, numeric, numeric) IS
    'Returns the current stock and the shipments to be expected in the nearest future.
    Parameters:
    * p_QueryNo: the given value is returend in the QueryNo result column; used by the function invoker in the context of select .. union.
    * p_M_Product_ID
    * p_StorageAttributesKey: if not -1000 ("ALL"), then the function will return rows whose attributesKey is an exact match of this parameter''s value. -1002 means "no storage attributes".
    * p_PreparationDate: the date in question, where the respective quantity would have to be available. Returned rows have a ShipmentPreparationDate and SalesOrderLastUpdated within a certain range around this parameter''s value.
    * p_shipmentDateLookAheadHours: returned rows have a ShipmentPreparationDate not after p_PreparationDate plus the given number of hours.
    * p_salesOrderLookBehindHours: Used to ignore old/stale sales order lines. Returned rows have a SalesOrderLastUpdated date not before now() minus the given number of hours.
    * p_M_Warehouse_ID: optional filter by p_M_Warehouse_ID if the caller passes one

    Also see https://github.com/metasfresh/metasfresh/issues/5108'
;
