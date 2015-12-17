update AD_Scheduler set IsActive='N'
where AD_Scheduler_ID in (
	select s.AD_Scheduler_ID
	from AD_Process p
	inner join AD_Scheduler s on (s.AD_Process_ID=p.AD_Process_ID)
	where p.ClassName='de.metas.invoicecandidate.process.C_Invoice_Candidate_Update'
	and s.IsActive='Y'
);
