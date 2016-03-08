drop view if exists PMM_PurchaseCandidate_Weekly;
create or replace view PMM_PurchaseCandidate_Weekly as
select
	c.C_BPartner_ID
	, c.M_Product_ID
	,  date_trunc('week', c.DatePromised) as DatePromised
	--
	, sum(QtyPromised) as QtyPromised
	, sum(QtyOrdered) as QtyOrdered
	--
	, c.AD_Client_ID as AD_Client_ID
	, min(c.AD_Org_ID) as AD_Org_ID
	, min(c.Created) as Created
	, 0 as CreatedBy
	, max(c.Updated) as Updated
	, 0 as UpdatedBy
	, 'Y'::char(1) as IsActive
from PMM_PurchaseCandidate c
group by
	c.AD_Client_ID
	, c.C_BPartner_ID
	, c.M_Product_ID
	, date_trunc('week', c.DatePromised)
;

-- select * from PMM_PurchaseCandidate_Weekly