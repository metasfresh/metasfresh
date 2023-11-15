DROP INDEX IF EXISTS m_shipping_notificationline_orderLine
;

CREATE UNIQUE INDEX m_shipping_notificationline_orderLine
    ON m_shipping_notificationline (
                                    m_shipping_notification_id,
                                    c_orderline_id
        )
    WHERE c_orderline_id IS NOT NULL
;

