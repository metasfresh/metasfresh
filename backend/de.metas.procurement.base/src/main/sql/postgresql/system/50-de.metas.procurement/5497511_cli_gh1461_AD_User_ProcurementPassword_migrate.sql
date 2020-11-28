update AD_User set ProcurementPassword=Password
where 
	IsMFProcurementUser='Y' 
	and ProcurementPassword is null;

