
CREATE OR REPLACE VIEW "de.metas.monitoring".Alerts_V AS
	SELECT 
		'Scheduler: execution overdue: '|| s.Name AS Summary, 
		'Overdue since '||DateNextRun AS Detail
	FROM AD_Scheduler s
	WHERE s.IsActive='Y'
		AND DateNextRun < now() - interval '10 min'
UNION 
	SELECT DISTINCT 'Async: error in package of '||p.ClassName, 'Latest failure: '||max(wp.Updated)
	FROM C_Queue_Workpackage wp
		JOIN C_Queue_PackageProcessor p ON p.C_Queue_PackageProcessor_ID=wp.C_Queue_PackageProcessor_ID
	WHERE 
		wp.IsActive='Y' AND wp.IsError='Y' and wp.Processed='N'
	GROUP BY p.ClassName
UNION
	-- "remove" from this view by using UPDATE AD_Issue SET Processed='Y' WHERE Processed='N' AND LoggerName='de.metas.invoicecandidate.process.C_Invoice_Candidate_Update' 
	SELECT 'InvoiceCandiate: Update-Process failed', 'Latest failure: '||max(i.Created)
	FROM AD_Issue i
	WHERE i.LoggerName='de.metas.invoicecandidate.process.C_Invoice_Candidate_Update' AND Processed='N'
	GROUP BY i.LoggerName
	HAVING i.LoggerName IS NOT NULL
UNION
	SELECT Distinct 'OLCand: Candidates with Errors',''
	FROM C_OLCand 
	WHERE true AND IsActive='Y' AND Processed='N' AND IsError='Y'
UNION
	SELECT DISTINCT 'Replication: Error in processor "'||p.Name||'"',''
	FROM IMP_ProcessorLog pl
		JOIN IMP_Processor p ON p.IMP_Processor_ID=pl.IMP_Processor_ID
	WHERE
		pl.IsError='Y' AND pl.IsActive='Y' AND p.IsActive='Y'
;
