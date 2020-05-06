drop view if exists de_metas_procurement.PMM_Balance_Events_v;
create or replace view de_metas_procurement.PMM_Balance_Events_v as
	--
	-- QtyPromised, QtyOrdered
	select
		c.C_BPartner_ID
		, c.M_Product_ID
		, c.M_AttributeSetInstance_ID
		, c.M_HU_PI_Item_Product_ID
		, c.C_Flatrate_DataEntry_ID
		--
		,  date_trunc('month', c.DatePromised) as MonthDate
		,  date_trunc('week', c.DatePromised) as WeekDate
		--
		, QtyOrdered as QtyOrdered
		, QtyOrdered_TU as QtyOrdered_TU
		, QtyPromised as QtyPromised
		, QtyPromised_TU as QtyPromised_TU
		, 0::numeric as QtyDelivered
		--
		-- , nextval('pmm_balance_seq')
		, c.AD_Client_ID as AD_Client_ID
		, 0 as AD_Org_ID
		, 'Y'::char(1) as IsActive
		, c.Created as Created
		, 0 as CreatedBy
		, c.Updated as Updated
		, 0 as UpdatedBy
	from PMM_PurchaseCandidate c
--
-- QtyDelivered
union all (
	select
		ol.C_BPartner_ID
		, ol.M_Product_ID
		, ol.PMM_Contract_ASI_ID as M_AttributeSetInstance_ID
		, ol.M_HU_PI_Item_Product_ID
		, ol.C_Flatrate_DataEntry_ID
		--
		,  date_trunc('month', ol.DatePromised) as MonthDate
		,  date_trunc('week', ol.DatePromised) as WeekDate
		--
		, 0 as QtyOrdered
		, 0 as QtyOrdered_TU
		, 0 as QtyPromised
		, 0 as QtyPromised_TU
		, ol.QtyDelivered as QtyDelivered
		--
		, ol.AD_Client_ID as AD_Client_ID
		, 0 as AD_Org_ID
		, 'Y'::char(1) as IsActive
		, ol.Created as Created
		, 0 as CreatedBy
		, ol.Updated as Updated
		, 0 as UpdatedBy
	from C_OrderLine ol
	inner join C_Order o on (o.C_Order_ID=ol.C_Order_ID)
	where true
	-- Consider all purchase orders, no matter if they were created from procurement or not (FRESH-191)
	-- NOTE: keep in sync with de.metas.procurement.base.order.interceptor.C_OrderLine.isEligibleForTrackingQtyDelivered(I_C_OrderLine)
	and o.IsSOTrx='N'
	-- and ol.IsMFProcurement='Y'
)
;

-- select * from de_metas_procurement.PMM_Balance_Events_v;

