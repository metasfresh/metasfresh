drop view if exists MD_Stock_WarehouseAndProduct_v;
create or replace view MD_Stock_WarehouseAndProduct_v as
select
	pc.M_Product_Category_ID
	, p.M_Product_ID
	, p.Value as ProductValue
	, s.M_Warehouse_ID
	, COALESCE(SUM(s.QtyOnHand), 0) as QtyOnHand
	--
	, s.AD_Client_ID
	, s.AD_Org_ID
from M_Product_Category pc
inner join M_Product p on (p.M_Product_Category_ID=pc.M_Product_Category_ID)
left outer join MD_Stock s on (s.M_Product_ID=p.M_Product_ID)
where pc.IsActive='Y' and p.IsActive='Y' and s.IsActive='Y'
group by
	pc.M_Product_Category_ID
	, p.M_Product_ID
	, p.Value
	, s.M_Warehouse_ID
	, s.AD_Client_ID
	, s.AD_Org_ID
;

