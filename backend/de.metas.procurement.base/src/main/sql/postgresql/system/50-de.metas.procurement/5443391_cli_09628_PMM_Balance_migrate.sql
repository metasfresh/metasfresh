delete from PMM_Balance;

--
-- Weekly balances
INSERT INTO PMM_Balance
(
	c_bpartner_id
	, m_product_id
	, m_hu_pi_item_product_id
	--
	, monthdate
	, weekdate
	--
	, qtyordered
	, qtyordered_tu
	, qtypromised
	, qtypromised_tu
	--
	, pmm_balance_id
	, ad_client_id
	, ad_org_id
	, isactive
	, created
	, createdby
	, updated
	, updatedby
)
select
	c.C_BPartner_ID
	, c.M_Product_ID
	, c.M_HU_PI_Item_Product_ID
	--
	,  date_trunc('month', c.DatePromised) as MonthDate
	,  date_trunc('week', c.DatePromised) as WeekDate
	--
	, sum(QtyOrdered) as QtyOrdered
	, sum(QtyOrdered_TU) as QtyOrdered_TU
	, sum(QtyPromised) as QtyPromised
	, sum(QtyPromised_TU) as QtyPromised_TU
	--
	, nextval('pmm_balance_seq')
	, c.AD_Client_ID as AD_Client_ID
	, 0 as AD_Org_ID
	, 'Y'::char(1) as IsActive
	, min(c.Created) as Created
	, 0 as CreatedBy
	, max(c.Updated) as Updated
	, 0 as UpdatedBy
from PMM_PurchaseCandidate c
group by
	c.AD_Client_ID
	, c.C_BPartner_ID
	, c.M_Product_ID
	, c.M_HU_PI_Item_Product_ID
	, date_trunc('month', c.DatePromised)
	, date_trunc('week', c.DatePromised)
;



--
-- Monthly balances
INSERT INTO PMM_Balance
(
	c_bpartner_id
	, m_product_id
	, m_hu_pi_item_product_id
	--
	, monthdate
	, weekdate
	--
	, qtyordered
	, qtyordered_tu
	, qtypromised
	, qtypromised_tu
	--
	, pmm_balance_id
	, ad_client_id
	, ad_org_id
	, isactive
	, created
	, createdby
	, updated
	, updatedby
)
select
	c.C_BPartner_ID
	, c.M_Product_ID
	, c.M_HU_PI_Item_Product_ID
	--
	,  date_trunc('month', c.DatePromised) as MonthDate
	,  null as WeekDate
	--
	, sum(QtyOrdered) as QtyOrdered
	, sum(QtyOrdered_TU) as QtyOrdered_TU
	, sum(QtyPromised) as QtyPromised
	, sum(QtyPromised_TU) as QtyPromised_TU
	--
	, nextval('pmm_balance_seq')
	, c.AD_Client_ID as AD_Client_ID
	, 0 as AD_Org_ID
	, 'Y'::char(1) as IsActive
	, min(c.Created) as Created
	, 0 as CreatedBy
	, max(c.Updated) as Updated
	, 0 as UpdatedBy
from PMM_PurchaseCandidate c
group by
	c.AD_Client_ID
	, c.C_BPartner_ID
	, c.M_Product_ID
	, c.M_HU_PI_Item_Product_ID
	, date_trunc('month', c.DatePromised)
;

-- Check
-- select * from PMM_Balance;

