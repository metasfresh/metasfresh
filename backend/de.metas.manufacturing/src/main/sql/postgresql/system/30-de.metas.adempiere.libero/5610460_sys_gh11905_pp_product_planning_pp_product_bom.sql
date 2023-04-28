drop index ppproductbomunique;

create unique index ppproductbomunique
	on pp_product_bom (ad_client_id, value, validfrom);

insert into pp_product_bomversions(PP_Product_BOMVersions_ID,
                                   AD_Client_ID,
                                   AD_Org_ID,
                                   Created,
                                   Createdby,
                                   IsActive,
                                   Updated,
                                   UpdatedBy,
                                   M_Product_ID,
                                   Name)
select distinct nextval('pp_product_bomversions_seq'),
                max(AD_Client_ID),
                max(AD_Org_ID),
                '11-01-2021'::timestamp,
                99,
                max(IsActive),
                '11-01-2021'::timestamp,
                99,
                M_Product_ID,
                max(name)
from pp_product_bom
group by M_Product_ID
;

update pp_product_bom
set pp_product_bomversions_id = version.pp_product_bomversions_id
from pp_product_bom bom
         inner join pp_product_bomversions version on bom.m_product_id = version.m_product_id
where bom.pp_product_bom_id = pp_product_bom.pp_product_bom_id
;