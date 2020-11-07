--migrate
INSERT INTO m_shipmentschedule_exportaudit (m_shipmentschedule_exportaudit_id,
                                            TransactionIdAPI,
                                            ExportStatus,
                                            AD_Client_ID,
                                            AD_Org_ID,
                                            Created,
                                            CreatedBy,
                                            Updated,
                                            UpdatedBy,
                                            IsActive)
SELECT nextval('m_shipmentschedule_exportaudit_seq') AS m_shipmentschedule_exportaudit_id,
       TransactionIdAPI,
       min(ExportStatus)                             AS ExportStatus, --all items with the same TransactionIdAPI have the same
       AD_Client_ID,
       AD_Org_ID,
       min(created)                                  AS Created,
       99                                            AS CreatedBy,
       min(updated)                                  AS Updated,
       99                                            AS UpdatedBy,
       'Y'                                           AS IsActive
FROM m_shipmentschedule_exportaudit_item eai
GROUP BY TransactionIdAPI, AD_Client_ID, AD_Org_ID
;

UPDATE m_shipmentschedule_exportaudit_item i
SET m_shipmentschedule_exportaudit_id=audit.m_shipmentschedule_exportaudit_id
FROM m_shipmentschedule_exportaudit audit
WHERE audit.TransactionIdAPI = i.TransactionIdAPI
;