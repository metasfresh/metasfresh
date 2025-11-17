-- Run mode: SWING_CLIENT

-- Column: M_ReceiptSchedule.ContainerNo
-- Column SQL (old): (SELECT max(st.ContainerNo) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)
-- 2025-11-12T08:38:55.365Z
UPDATE AD_Column SET ColumnSQL='(SELECT STRING_AGG(DISTINCT st.ContainerNo, ''; '') from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID and st.ContainerNo is not NULL)',Updated=TO_TIMESTAMP('2025-11-12 08:38:55.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591263
;

-- Column: M_ReceiptSchedule.VesselName
-- Column SQL (old): (SELECT max(st.VesselName)  from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)
-- 2025-11-12T08:43:51.163Z
UPDATE AD_Column SET ColumnSQL='(SELECT STRING_AGG(DISTINCT st.VesselName, ''; '') from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID AND st.VesselName IS NOT NULL)',Updated=TO_TIMESTAMP('2025-11-12 08:43:51.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591273
;

-- Column: M_ReceiptSchedule.TrackingID
-- Column SQL (old): (SELECT max(st.TrackingID)  from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)
-- 2025-11-12T08:52:43.657Z
UPDATE AD_Column SET ColumnSQL='(SELECT STRING_AGG(DISTINCT st.TrackingID, ''; '')  from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID AND st.TrackingID IS NOT NULL)',Updated=TO_TIMESTAMP('2025-11-12 08:52:43.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591272
;

-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- Column SQL (old): (SELECT MaterialReceiptInfo from C_BPartner where C_BPartner_ID=M_ReceiptSchedule.C_BPartner_ID)
-- 2025-11-12T09:02:54.461Z
UPDATE AD_Column SET ColumnSQL='(SELECT STRING_AGG(DISTINCT MaterialReceiptInfo, ''; '') from C_BPartner where C_BPartner_ID=M_ReceiptSchedule.C_BPartner_ID AND MaterialReceiptInfo IS NOT NULL)',Updated=TO_TIMESTAMP('2025-11-12 09:02:54.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589629
;

