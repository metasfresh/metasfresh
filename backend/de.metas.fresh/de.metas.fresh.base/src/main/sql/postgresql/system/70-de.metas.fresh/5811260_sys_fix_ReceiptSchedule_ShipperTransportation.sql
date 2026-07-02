-- Column: M_ReceiptSchedule.M_ShipperTransportation_ID
-- Column SQL (old): (SELECT MAX(st.m_shippertransportation_id) from m_receiptschedule rs INNER JOIN m_shippingpackage sp on sp.c_order_id = rs.c_order_id INNER JOIN m_shippertransportation st on st.m_shippertransportation_id = sp.m_shippertransportation_id where rs.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)
-- 2025-10-31T10:11:56.827Z
UPDATE AD_Column
SET ColumnSQL='(SELECT MAX(st.m_shippertransportation_id) from m_receiptschedule rs INNER JOIN m_shippingpackage sp on sp.c_order_id = rs.c_order_id AND sp.c_orderline_id = rs.c_orderline_id INNER JOIN m_shippertransportation st on st.m_shippertransportation_id = sp.m_shippertransportation_id where rs.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)',
    Updated=TO_TIMESTAMP('2025-10-31 10:11:56.827000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Column_ID = 591446
;
