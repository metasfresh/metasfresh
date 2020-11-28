--DROP VIEW rv_async_batch_statistics;

CREATE OR REPLACE VIEW rv_async_batch_statistics AS 
SELECT 
	AD_Client_ID,
	CreatedBy,
	CreatedBy AS UpdatedBy,
	now() AS Created,
	now() AS Updated,
	C_Async_Batch_ID, 
	C_Queue_PackageProcessor_ID, 
	AD_Table_ID, 
	SUM(enqueued) AS CountEnqueued, 
	SUM(proc) AS CountProcessed
FROM
	(
		SELECT 
			qw.AD_Client_ID,
			qw.CreatedBy,
			qw.C_Async_Batch_ID, 
			qb.C_Queue_PackageProcessor_ID, 
			qe.AD_Table_ID, 
			COUNT(qe.AD_Table_ID) AS enqueued, 
			qw.Processed,
			(CASE 
				WHEN qw.Processed='Y' 
					THEN COUNT(qw.Processed)
				ELSE 0
			END) AS proc
		FROM C_Queue_WorkPackage qw 
		INNER JOIN C_Queue_Element qe ON (qe.C_Queue_WorkPackage_ID = qw.C_Queue_WorkPackage_ID)
		INNER JOIN C_Queue_Block qb ON (qb.C_Queue_Block_ID = qw.C_Queue_Block_ID)
		GROUP BY  
			qb.C_Queue_PackageProcessor_ID, 
			qe.AD_Table_ID, 
			qw.C_Async_Batch_ID, 
			qw.Processed,
			qw.AD_Client_ID,
			qw.CreatedBy
	) AS info
GROUP BY 
	C_Queue_PackageProcessor_ID, 
	AD_Table_ID, 
	C_Async_Batch_ID,
	AD_Client_ID,
	CreatedBy
;


