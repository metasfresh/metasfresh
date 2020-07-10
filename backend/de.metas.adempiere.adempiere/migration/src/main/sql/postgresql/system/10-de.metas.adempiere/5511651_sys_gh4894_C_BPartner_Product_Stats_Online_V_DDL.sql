drop view if exists C_BPartner_Product_Stats_Online_v;

create or replace view C_BPartner_Product_Stats_Online_v as
select 
	io.C_BPartner_ID,
	iol.M_Product_ID,
	max(case when IsSOTrx='Y' then MovementDate else null end) as LastShipDate,
	max(case when IsSOTrx='N' then MovementDate else null end) as LastReceiptDate,
	--
	io.AD_Client_ID,
	0 as AD_Org_ID,
	min(io.Created) as Created,
	0 as CreatedBy,
	max(io.Updated) as Updated,
	0 as UpdatedBy,
	'Y' as IsActive
from M_InOut io
inner join M_InOutLine iol on iol.M_InOut_ID=io.M_InOut_ID
where io.DocStatus in ('CO', 'CL')
group by 
	io.C_BPartner_ID,
	iol.M_Product_ID,
	--
	io.AD_Client_ID
;

/*
select * from C_BPartner_Product_Stats_Online_v;
*/

