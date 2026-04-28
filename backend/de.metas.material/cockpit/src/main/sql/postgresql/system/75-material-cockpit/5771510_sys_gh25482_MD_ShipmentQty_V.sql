DROP VIEW IF EXISTS de_metas_material.MD_ShipmentQty_V
;

CREATE VIEW de_metas_material.MD_ShipmentQty_V AS
SELECT sq.QtyToBeShipped,
       p.C_UOM_ID,
       p.M_Product_ID,
       sq.AttributesKey,
       sq.s_PreparationDate_Override,
       sq.s_PreparationDate,
       sq.o_PreparationDate,
       sq.SalesOrderLastUpdated,
       sq.AD_Org_ID,
       sq.M_Warehouse_ID
FROM M_Product p
         JOIN LATERAL (
    SELECT COALESCE(s.s_QtyReserved, ol.QtyOrdered)                                     AS QtyToBeShipped,
           CASE
               WHEN s.s_QtyReserved IS NOT NULL THEN s.ad_org_id
                                                ELSE ol.ad_org_id
           END                                                                          AS AD_Org_ID,

        /* we expect relatively few rows to create the AttributesKey for,
           because this view is invoked only for a certain SalesOrderLastUpdated /PreparationDate range */
           GenerateASIStorageAttributesKey(
                   COALESCE(s.M_AttributeSetInstance_ID, ol.M_AttributeSetInstance_ID)) AS AttributesKey,

        /* avoid COALESCE because we can't index on COALESCE expressions. so we want to have these columns in our whereclause */
           s_PreparationDate_Override,
           s_PreparationDate,
           o_PreparationDate,
           ol.Updated                                                                   AS SalesOrderLastUpdated,
           COALESCE(s_M_Warehouse_ID, o_M_Warehouse_ID)                                 AS M_Warehouse_ID
    FROM (SELECT ol.C_OrderLine_ID,
                 ol.M_AttributeSetInstance_ID,
                 ol.QtyOrdered,
                 o.PreparationDate AS o_PreparationDate,
                 ol.Updated,
                 ol.ad_org_id,
                 o.m_warehouse_id  AS o_M_Warehouse_ID
          FROM C_OrderLine ol
                   JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
                   LEFT JOIN M_ShipmentSchedule sched ON sched.c_orderline_id = ol.c_orderline_id
          WHERE o.IsSOTrx = 'Y' /* don't exclude processed records; the shipment schedule might no be there just yet */
            AND ol.M_Product_ID = p.M_Product_ID
            AND sched.m_shipmentschedule_id IS NULL) ol
             FULL OUTER JOIN (SELECT s.C_OrderLine_ID,
                                     s.M_AttributeSetInstance_ID,
                                     s.PreparationDate_Override                            AS s_PreparationDate_Override,
                                     s.PreparationDate                                     AS s_PreparationDate,
                                     GREATEST(COALESCE(s.QtyReserved, 0), 0)               AS s_QtyReserved,
                                     s.ad_org_id,
                                     COALESCE(s.m_warehouse_override_id, s.m_warehouse_id) AS s_M_Warehouse_ID
                              FROM M_ShipmentSchedule s
                              WHERE s.Processed = 'N'
                                AND s.M_Product_ID = p.M_Product_ID) s ON s.C_OrderLine_ID = ol.C_OrderLine_ID
    ) sq ON TRUE
WHERE TRUE
;
