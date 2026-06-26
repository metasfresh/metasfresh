-- me03 #29937 - Traffic Manager Picking List: create report SQL function
-- Uses M_Picking_Job_Schedule_view directly, driven by WEBUI view selection.

DROP FUNCTION IF EXISTS report.TrafficMgr_PickingList(p_m_shipmentschedule_ids TEXT, p_view_id TEXT);

CREATE OR REPLACE FUNCTION report.TrafficMgr_PickingList(
    p_m_shipmentschedule_ids TEXT,
    p_view_id                TEXT
)
    RETURNS TABLE
            (
                artikel          VARCHAR,
                kz               VARCHAR,
                zu_packen        NUMERIC,
                wo               VARCHAR,
                workplace        VARCHAR,
                prep_date        TIMESTAMP WITH TIME ZONE,
                lagernr          VARCHAR,
                menge_at_locator NUMERIC,
                m_product_id     NUMERIC
            )
    LANGUAGE plpgsql
AS
$$
-- NOTE: assumes T_WEBUI_ViewSelection.IntKey1 = M_ShipmentSchedule_ID for the Traffic Manager view.
-- If the view uses a compound key where IntKey1 = M_Picking_Job_Schedule_ID, change IntKey1 → IntKey2
-- and the filter column accordingly.
BEGIN
    RETURN QUERY
        SELECT p.Value::VARCHAR  AS artikel,
               p.Name::VARCHAR   AS kz,
               pjs.QtyToPick     AS zu_packen,
               cp.Name::VARCHAR  AS wo,
               w.Name::VARCHAR   AS workplace,
               pjs.DatePromised  AS prep_date,
               wh.Name::VARCHAR  AS lagernr,
               pjs.qtypicked     AS menge_at_locator,
               p.M_Product_ID    AS m_product_id
        FROM M_Picking_Job_Schedule_view pjs
                 JOIN M_Product p ON p.M_Product_ID = pjs.M_Product_ID
                 LEFT JOIN Carrier_Product cp ON cp.Carrier_Product_ID = pjs.Carrier_Product_ID
                 LEFT JOIN C_Workplace w ON w.C_Workplace_ID = pjs.C_Workplace_ID
                 LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID = pjs.M_Warehouse_ID
        WHERE p_m_shipmentschedule_ids IS NOT NULL
          AND (
                (p_m_shipmentschedule_ids = 'all'
                    AND pjs.M_ShipmentSchedule_ID IN (SELECT vs.IntKey1
                                                      FROM T_WEBUI_ViewSelection vs
                                                      WHERE vs.UUID = p_view_id))
                OR
                (p_m_shipmentschedule_ids <> 'all'
                    AND pjs.M_ShipmentSchedule_ID = ANY (REGEXP_SPLIT_TO_ARRAY(p_m_shipmentschedule_ids, ',')::INT[]))
            )
        ORDER BY p.Value, wh.Name;
END;
$$;
