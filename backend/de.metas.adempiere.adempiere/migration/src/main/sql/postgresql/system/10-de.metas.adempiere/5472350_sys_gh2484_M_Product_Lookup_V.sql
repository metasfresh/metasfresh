drop view if exists M_Product_Lookup_V;
create or replace view M_Product_Lookup_V as
	select
		p.M_Product_ID,
		p.Value,
		p.Name,
		p.UPC,
		p.IsSummary,
		p.IsActive,
		null::numeric as C_BPartner_ID,
		null::varchar as VendorProductNo,
		p.AD_Client_ID, p.AD_Org_ID, p.Created, p.CreatedBy, p.Updated, p.UpdatedBy
	from M_Product p

	union all

	select
		bpp.M_Product_ID,
		p.Value,
		p.Name,
		p.UPC,
		p.IsSummary,
		p.IsActive,
		bpp.C_BPartner_ID,
		bpp.VendorProductNo,
		p.AD_Client_ID, p.AD_Org_ID, p.Created, p.CreatedBy, p.Updated, p.UpdatedBy
	from C_BPartner_Product bpp
	inner join M_Product p on (p.M_Product_ID=bpp.M_Product_ID)
	where bpp.IsActive='Y'
;
