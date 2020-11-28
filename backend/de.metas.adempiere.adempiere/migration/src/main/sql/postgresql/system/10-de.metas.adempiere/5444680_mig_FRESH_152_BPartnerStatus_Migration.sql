insert into C_BPartner_Stats 
(
	C_BPartner_Stats_ID,
	
	AD_Client_ID,
	AD_Org_ID,
	C_BPartner_ID,
	Created,
	CreatedBy,
	IsActive,
	Updated,
	UpdatedBy,

	ActualLifeTimeValue,
	SOCreditStatus,
	SO_CreditUsed,
	TotalOpenBalance
	
	
)
select
	nextval('c_bpartner_stats_seq') as C_BPartner_Stats_ID,
	bp.AD_Client_ID as AD_Client_ID,
	bp.AD_Org_ID as AD_Org_ID,
	bp.C_Bpartner_ID as C_Bpartner_ID,
	now() as Created,
	99 as CreatedBy,
	bp.isActive as IsActive,
	now() as Updated,
	99 as UpdatedBy,

	bp.ActualLifeTimeValue as ActualLifeTimeValue,
	bp.SOCreditStatus as SOCreditStatus,
	bp.SO_CreditUsed as SO_CreditUsed,
	bp.TotalOpenBalance as TotalOpenBalance
	
	
from C_BPartner bp;
