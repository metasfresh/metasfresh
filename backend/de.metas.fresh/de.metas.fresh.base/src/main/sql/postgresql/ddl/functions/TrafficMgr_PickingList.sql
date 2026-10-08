DROP FUNCTION IF EXISTS report.TrafficMgr_PickingList(p_m_shipmentschedule_ids TEXT, p_view_id TEXT);

CREATE OR REPLACE FUNCTION report.TrafficMgr_PickingList(
    p_m_shipmentschedule_ids TEXT,
    p_view_id                TEXT
)
    RETURNS TABLE
            (
                productValue          VARCHAR,
                productname               VARCHAR,
                QtyToPick        NUMERIC,
                status           VARCHAR,
                workplace        VARCHAR,
                prep_date        TIMESTAMP WITH TIME ZONE,
                lagernr          VARCHAR,
                menge_at_locator NUMERIC,
                m_product_id     NUMERIC,
                picklist_group   VARCHAR
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- M_Picking_Job_Schedule_view has a compound key (M_ShipmentSchedule_ID, M_Picking_Job_Schedule_ID).
    -- WebUI passes selected IDs as "M_ShipmentSchedule_ID$M_Picking_Job_Schedule_ID" (e.g. "1002746$0").
    -- Strip the $xxx suffix so each element can be cast to INT.
    p_m_shipmentschedule_ids := REGEXP_REPLACE(p_m_shipmentschedule_ids, '\$[^,]*', '', 'g');

    RETURN QUERY
        SELECT p.Value::VARCHAR                                                                   AS productValue,
               p.Name::VARCHAR                                                                    AS productname,
               pjs.QtyToPick                                                                      AS QtyToPick,
               CASE
                   WHEN pjs.processed = 'Y' THEN 'Verarbeitet'
                   WHEN pjs.isassigned = 'Y' THEN 'Zugewiesen'
                   ELSE 'Offen'
               END::VARCHAR                                                                       AS status,
               w.Name::VARCHAR                                                                    AS workplace,
               pjs.DatePromised                                                                   AS prep_date,
               (wh.Name || COALESCE(' / ' || loc.Value, ''))::VARCHAR                            AS lagernr,
               pjs.qtypicked                                                                      AS menge_at_locator,
               p.M_Product_ID                                                                     AS m_product_id,
               CASE WHEN pjs.qtypicked > 0 THEN 'Bereits gepickt' ELSE 'Zu picken' END::VARCHAR  AS picklist_group
        FROM M_Picking_Job_Schedule_view pjs
                 JOIN M_Product p ON p.M_Product_ID = pjs.M_Product_ID
                 LEFT JOIN C_Workplace w ON w.C_Workplace_ID = pjs.C_Workplace_ID
                 LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID = pjs.M_Warehouse_ID
                 LEFT JOIN M_Locator loc ON loc.M_Warehouse_ID = pjs.M_Warehouse_ID AND loc.IsDefault = 'Y'
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
        ORDER BY (CASE WHEN pjs.qtypicked > 0 THEN 2 ELSE 1 END), p.Value, wh.Name;
END;
$$;
