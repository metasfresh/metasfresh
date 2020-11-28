drop table if exists TMP_C_Flatrate_Term_ToUpdate;
create temporary table TMP_C_Flatrate_Term_ToUpdate as
select t.* 
	, pmmp.ProductName as PMM_Product_Name_New
from (
	select 
		ft.C_Flatrate_Term_ID
		--
		, ft.DropShip_BPartner_ID
		, (select bp.Value||'_'||bp.Name from C_BPartner bp where bp.C_BPartner_ID=ft.DropShip_BPartner_ID) as DropShip_BPartner_Name
		--
		, ft.M_Product_ID
		, (select p.Value||'_'||p.Name from M_Product p where p.M_Product_ID=ft.M_Product_ID) as ProductName
		, ft.PMM_Product_ID as PMM_Product_ID_Old
		, (
			select
				pmmp.PMM_Product_ID
			from PMM_Product pmmp
			where true
				and pmmp.M_Product_ID=ft.M_Product_ID
				and (pmmp.C_BPartner_ID is null or pmmp.C_BPartner_ID=ft.DropShip_BPartner_ID)
			order by pmmp.C_BPartner_ID nulls last
			limit 1
		) as PMM_Product_ID_New
		--
		, (
			select string_agg(', ', pmmp.ProductName)
			from PMM_Product pmmp
			where true
				and pmmp.M_Product_ID=ft.M_Product_ID
				and (pmmp.C_BPartner_ID is null or pmmp.C_BPartner_ID=ft.DropShip_BPartner_ID)
		) as PMM_Product_ID_New_AllVariants
	from C_Flatrate_Term ft
	where ft.type_conditions='Procuremnt'
	and ft.PMM_Product_ID is null
) t
left outer join PMM_Product pmmp on (pmmp.PMM_Product_ID=t.PMM_Product_ID_New)
;
--
create index on TMP_C_Flatrate_Term_ToUpdate(C_Flatrate_Term_ID);

--
-- Show results
/*
select * from TMP_C_Flatrate_Term_ToUpdate;
*/

--
-- Migrate C_Flatrate_Term.PMM_Product_ID
update C_Flatrate_Term ft set PMM_Product_ID=t.PMM_Product_ID_New
from TMP_C_Flatrate_Term_ToUpdate t
where t.C_Flatrate_Term_ID=ft.C_Flatrate_Term_ID
and PMM_Product_ID is null
and PMM_Product_ID_New is not null
;
