-- drop view if exists PMM_PurchaseCandidate_Weekly;
create or replace view PMM_PurchaseCandidate_Weekly as
select
	t.*
	, (select PMM_Trend from PMM_Week w where w.C_BPartner_ID=t.C_BPartner_ID and w.M_Product_ID=t.M_Product_ID and w.WeekDate=t.DatePromised) as PMM_Trend
from (
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
) t
;

-- select * from PMM_PurchaseCandidate_Weekly
