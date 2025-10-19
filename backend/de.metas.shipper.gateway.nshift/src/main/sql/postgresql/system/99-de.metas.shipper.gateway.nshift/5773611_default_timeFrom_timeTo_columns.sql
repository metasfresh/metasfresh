SELECT backup_table('carrier_shipmentorder')
;

WITH st AS (SELECT * FROM m_shippertransportation)
UPDATE carrier_shipmentorder
SET pickuptimeto   = st.pickuptimeto,
    pickuptimefrom = st.pickuptimefrom,
    updatedby=100,
    updated=TO_TIMESTAMP('2025-10-19 13:00:00', 'YYYY-MM-DD HH24:MI:SS')
WHERE st.m_shippertransportation_id = carrier_shipmentorder.m_shippertransportation_id
;
