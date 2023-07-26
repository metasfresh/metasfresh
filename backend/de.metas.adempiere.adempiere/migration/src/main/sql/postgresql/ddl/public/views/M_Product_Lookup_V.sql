drop view if exists M_Product_Lookup_V;
create or replace view M_Product_Lookup_V as
	select
		p.M_Product_ID,
		p.Value,
		p.Name,
		p.UPC,
		p.IsSummary,
		p.IsActive,
        p.Discontinued,
		p.IsBOM,
		null::numeric as C_BPartner_ID,
		null::varchar as BPartnerProductNo,
		null::varchar as BPartnerProductName,
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
        p.Discontinued,
		p.IsBOM,
		bpp.C_BPartner_ID,
		bpp.ProductNo as BPartnerProductNo,
		bpp.ProductName as BPartnerProductName,
		p.AD_Client_ID, p.AD_Org_ID, p.Created, p.CreatedBy, p.Updated, p.UpdatedBy
	from C_BPartner_Product bpp
	inner join M_Product p on (p.M_Product_ID=bpp.M_Product_ID)
	where bpp.IsActive='Y'
;
