-- View: de_metas_purchasecandidate.C_PurchaseCandidate_Enqueued

-- DROP VIEW de_metas_purchasecandidate.C_PurchaseCandidate_Enqueued;


CREATE OR REPLACE VIEW "de_metas_purchasecandidate".C_PurchaseCandidate_Enqueued AS
SELECT wp.c_queue_workpackage_id,
       wp.processed AS wp_processed,
       wp.iserror   AS wp_error,
       pc.processed,
       pc.c_orderso_id,
       pc.c_orderlineso_id,
       pc.QtyToPurchase,
       pca.c_orderlinepo_id,
       pca.c_orderpo_id,
       pca.ad_issue_id,
       pca.ad_table_id,
       pca.record_id,
       pca.RemotePurchaseOrderId
FROM c_queue_workpackage wp
         JOIN c_queue_element qe ON qe.c_queue_workpackage_id = wp.c_queue_workpackage_id
         LEFT JOIN c_purchasecandidate pc ON pc.c_purchasecandidate_id = qe.record_id
         LEFT JOIN c_purchasecandidate_alloc pca ON pca.c_purchasecandidate_id = pc.c_purchasecandidate_id
WHERE wp.c_queue_packageprocessor_id = 540053::numeric
ORDER BY wp.c_queue_workpackage_id
;



