drop function if exists de_metas_procurement.PMM_Balance_Rebuild();
create or replace function de_metas_procurement.PMM_Balance_Rebuild()
returns void
AS
$BODY$
begin
	--
	--
	raise notice 'Snapshot current events';
	drop table if exists TMP_PMM_Balance_Events;
	create temporary table TMP_PMM_Balance_Events as 
	select * from de_metas_procurement.PMM_Balance_Events_v 
	where monthdate>='2016-04-01'; -- no point polluting the events table with older records
	
	create index on TMP_PMM_Balance_Events(ad_client_id, c_bpartner_id, m_product_id, M_AttributeSetInstance_ID, m_hu_pi_item_product_id, C_Flatrate_DataEntry_ID, MonthDate);

	--
	--
	raise notice 'Calculating weekly balances';
	drop table if exists TMP_PMM_Balance_Rebuild;
	create temporary table TMP_PMM_Balance_Rebuild as
	select
		e.C_BPartner_ID
		, e.M_Product_ID
		, e.M_AttributeSetInstance_ID
		-- , e.M_HU_PI_Item_Product_ID
		, e.C_Flatrate_DataEntry_ID
		--
		,  e.MonthDate
		,  e.WeekDate
		--
		, sum(e.QtyOrdered) as QtyOrdered
		, sum(e.QtyOrdered_TU) as QtyOrdered_TU
		, sum(e.QtyPromised) as QtyPromised
		, sum(e.QtyPromised_TU) as QtyPromised_TU
		, sum(e.QtyDelivered) as QtyDelivered
		--
		, nextval('pmm_balance_seq') as PMM_Balance_ID
		, e.AD_Client_ID as AD_Client_ID
		, 0 as AD_Org_ID
		, 'Y'::char(1) as IsActive
		, min(e.Created) as Created
		, 0 as CreatedBy
		, max(e.Updated) as Updated
		, 0 as UpdatedBy
	from TMP_PMM_Balance_Events e
	group by
		e.AD_Client_ID
		, e.C_BPartner_ID
		, e.M_Product_ID
		, e.M_AttributeSetInstance_ID
		-- , e.M_HU_PI_Item_Product_ID
		, e.C_Flatrate_DataEntry_ID
		, e.MonthDate
		, e.WeekDate
	;
	--
	--
	raise notice 'Calculating monthly balances';
	INSERT INTO TMP_PMM_Balance_Rebuild
	(
		c_bpartner_id
		, m_product_id
		, M_AttributeSetInstance_ID
		-- , m_hu_pi_item_product_id
		, C_Flatrate_DataEntry_ID
		--
		, MonthDate
		, WeekDate
		--
		, QtyOrdered
		, QtyOrdered_TU
		, QtyPromised
		, QtyPromised_TU
		, QtyDelivered
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
		e.C_BPartner_ID
		, e.M_Product_ID
		, e.M_AttributeSetInstance_ID
		-- , e.M_HU_PI_Item_Product_ID
		, e.C_Flatrate_DataEntry_ID
		--
		,  e.MonthDate
		,  null as WeekDate
		--
		, sum(QtyOrdered) as QtyOrdered
		, sum(QtyOrdered_TU) as QtyOrdered_TU
		, sum(QtyPromised) as QtyPromised
		, sum(QtyPromised_TU) as QtyPromised_TU
		, sum(e.QtyDelivered) as QtyDelivered
		--
		, nextval('pmm_balance_seq') as PMM_Balance_ID
		, e.AD_Client_ID as AD_Client_ID
		, 0 as AD_Org_ID
		, 'Y'::char(1) as IsActive
		, min(e.Created) as Created
		, 0 as CreatedBy
		, max(e.Updated) as Updated
		, 0 as UpdatedBy
	from TMP_PMM_Balance_Events e
	group by
		e.AD_Client_ID
		, e.C_BPartner_ID
		, e.M_Product_ID
		, e.M_AttributeSetInstance_ID
		-- , e.M_HU_PI_Item_Product_ID
		, e.C_Flatrate_DataEntry_ID
		, e.MonthDate
	;

	--
	--
	--
	raise notice 'Replacing PMM_Balance content with what we calculated';
	delete from PMM_Balance;
	insert into PMM_Balance
	(
		c_bpartner_id
		, m_product_id
		, M_AttributeSetInstance_ID
		-- , m_hu_pi_item_product_id
		, C_Flatrate_DataEntry_ID
		--
		, MonthDate
		, WeekDate
		--
		, QtyOrdered
		, QtyOrdered_TU
		, QtyPromised
		, QtyPromised_TU
		, QtyDelivered
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
		c_bpartner_id
		, m_product_id
		, M_AttributeSetInstance_ID
		-- , m_hu_pi_item_product_id
		, C_Flatrate_DataEntry_ID
		--
		, monthdate
		, weekdate
		--
		, qtyordered
		, qtyordered_tu
		, qtypromised
		, qtypromised_tu
		, QtyDelivered
		--
		, pmm_balance_id
		, ad_client_id
		, ad_org_id
		, isactive
		, created
		, createdby
		, updated
		, updatedby
	from TMP_PMM_Balance_Rebuild;
end;
$BODY$
LANGUAGE plpgsql;



-- select de_metas_procurement.PMM_Balance_Rebuild();

