UPDATE AD_USER
SET IsDefaultContact='N', 
	IsBillToContact_Default='N', 
	IsSalesContact_Default='N', 
	IsPurchaseContact_Default='N', 
	IsShipToContact_Default='N'
WHERE IsActive='N';
