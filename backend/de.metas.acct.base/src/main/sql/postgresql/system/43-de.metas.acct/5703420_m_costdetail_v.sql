DROP VIEW IF EXISTS m_costdetail_v
;

CREATE OR REPLACE VIEW m_costdetail_v AS
SELECT cd.*,
       (CASE
            WHEN cd.m_matchinv_id IS NOT NULL                  THEN 'CO'
            WHEN cd.m_matchpo_id IS NOT NULL                   THEN 'CO'
            WHEN cd.pp_cost_collector_id IS NOT NULL           THEN pp.docstatus
            WHEN cd.m_inventoryline_id IS NOT NULL             THEN inv.docstatus
            WHEN cd.m_movementline_id IS NOT NULL              THEN m.docstatus
            WHEN cd.m_inoutline_id IS NOT NULL                 THEN io.docstatus
            WHEN cd.m_costrevaluation_id IS NOT NULL           THEN cr.docstatus
            WHEN cd.m_shipping_notificationline_id IS NOT NULL THEN sn.docstatus
        END)                                       AS docstatus,
       (CASE
            WHEN cd.m_matchinv_id IS NOT NULL                  THEN 'M_MatchInv'
            WHEN cd.m_matchpo_id IS NOT NULL                   THEN 'M_MatchPO'
            WHEN cd.pp_cost_collector_id IS NOT NULL           THEN 'PP_Cost_Collector'
            WHEN cd.m_inventoryline_id IS NOT NULL             THEN 'M_InventoryLine'
            WHEN cd.m_movementline_id IS NOT NULL              THEN 'M_MovementLine'
            WHEN cd.m_inoutline_id IS NOT NULL                 THEN 'M_InOutLine'
            WHEN cd.m_costrevaluation_id IS NOT NULL           THEN 'M_CostRevaluation'
            WHEN cd.m_shipping_notificationline_id IS NOT NULL THEN 'M_Shipping_Notification'
        END)                                       AS tablename,
       COALESCE(cd.m_matchinv_id,
                cd.m_matchpo_id,
                cd.pp_cost_collector_id,
                cd.m_inventoryline_id,
                cd.m_movementline_id,
                cd.m_inoutline_id,
                cd.m_costrevaluationline_id,
                cd.m_shipping_notificationline_id) AS record_id
FROM m_costdetail cd
         LEFT JOIN M_MatchInv mi ON mi.m_matchinv_id = cd.m_matchinv_id
         LEFT JOIN M_MatchPO mpo ON mpo.M_MatchPO_ID = cd.m_MatchPO_ID
         LEFT JOIN PP_Cost_Collector pp ON pp.PP_Cost_Collector_ID = cd.pp_cost_collector_id
         LEFT JOIN m_inventoryline il ON il.m_inventoryline_id = cd.m_inventoryline_id
         LEFT JOIN M_Inventory inv ON inv.m_inventory_id = il.m_inventory_id
         LEFT JOIN M_MovementLine ml ON ml.m_movementline_id = cd.m_movementline_id
         LEFT JOIN M_Movement m ON m.m_movement_id = ml.m_movement_ID
         LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = cd.m_inoutline_id
         LEFT JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
         LEFT OUTER JOIN M_CostRevaluation cr ON cr.M_CostRevaluation_ID = cd.M_CostRevaluation_ID
         LEFT JOIN M_Shipping_NotificationLine snl ON snl.M_Shipping_NotificationLine_ID = cd.M_Shipping_NotificationLine_ID
         LEFT JOIN M_Shipping_Notification sn ON sn.M_Shipping_Notification_ID = snl.M_Shipping_Notification_ID
;

COMMENT ON VIEW m_costdetail_v IS 'M_CostDetail table but with some missing columns like DocStatus'
;
