SELECT backup_table('carrier_shipmentorder')
;

WITH st AS (SELECT * FROM m_shippertransportation)
UPDATE carrier_shipmentorder
SET pickuptimefrom = COALESCE(st.pickuptimefrom, TO_TIMESTAMP('2025-01-01 08:00:00', 'YYYY-MM-DD HH24:MI:SS')),
    pickuptimeto   = COALESCE(st.pickuptimeto, TO_TIMESTAMP('2025-01-01 17:00:00', 'YYYY-MM-DD HH24:MI:SS')),
    updatedby=100,
    updated=TO_TIMESTAMP('2025-10-19 13:00:00', 'YYYY-MM-DD HH24:MI:SS')
FROM st
WHERE st.m_shippertransportation_id = carrier_shipmentorder.m_shippertransportation_id
;
