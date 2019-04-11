drop function if exists PP_Product_BOM_Recursive(numeric);
create or replace function PP_Product_BOM_Recursive(p_PP_Product_BOM_ID numeric)
returns table
(
	Line text,
	Parent_Line text,
	ProductValue varchar,
	ProductName varchar,
	QtyBOM numeric,
	Percentage numeric,
	UOMSymbol varchar,
	--
	depth integer,
	M_Product_ID numeric,
	IsQtyPercentage char(1),
	C_UOM_ID numeric,
    path integer[]
)
as
$BODY$
--
	with recursive bomNode as (
		(
			select
				array[1::integer] as path,
				null::integer[] as parent_path,
				1 as depth,
				bomProduct.Value as ProductValue,
				bomProduct.Name as ProductName,
				bomProduct.M_Product_ID,
				bomProduct.IsBOM,
				bom.PP_Product_BOM_ID,
				'N'::char(1) as IsQtyPercentage,
				round(1::numeric, uom.StdPrecision) as QtyBOM,
				null::numeric as Percentage,
				uom.UOMSymbol,
				uom.C_UOM_ID
			from PP_Product_BOM bom
			inner join M_Product bomProduct on bomProduct.M_Product_ID=bom.M_Product_ID
			left outer join C_UOM uom on uom.C_UOM_ID=coalesce(bom.C_UOM_ID, bomProduct.C_UOM_ID)
			where
			bom.PP_Product_BOM_ID=PP_Product_BOM_Recursive.p_PP_Product_BOM_ID
		)
		--
		union all
		--
		(
			select
				parent.path || (row_number() over (partition by bomLine.PP_Product_BOM_ID order by bomLine.PP_Product_BOMLine_ID))::integer as path,
				parent.path as parent_path,
				parent.depth + 1 as depth,
				bomLineProduct.Value as ProductValue,
				bomLineProduct.Name as ProductName,
				bomLineProduct.M_Product_ID,
				bomLineProduct.IsBOM,
				(case when bomLineProduct.IsBOM='Y'
					then (select bom.PP_Product_BOM_ID from PP_Product_BOM bom
						where bom.M_Product_ID=bomLineProduct.M_Product_ID
						and bom.IsActive='Y'
						and bom.Value=bomLineProduct.Value
						order by bom.PP_Product_BOM_ID
						limit 1)
					else null
					end)::numeric(10,0) as PP_Product_BOM_ID,
				bomLine.IsQtyPercentage,
				(case when bomLine.IsQtyPercentage='N' then round(bomLine.QtyBOM, uom.StdPrecision) else null end) as QtyBOM,
				(case when bomLine.IsQtyPercentage='Y' then round(bomLine.QtyBatch, 2) else null end) as Percentage,
				uom.UOMSymbol,
				uom.C_UOM_ID
			from bomNode parent
			inner join PP_Product_BOMLine bomLine on bomLine.PP_Product_BOM_ID=parent.PP_Product_BOM_ID
			inner join M_Product bomLineProduct on bomLineProduct.M_Product_ID = bomLine.M_Product_ID
			left outer join C_UOM uom on uom.C_UOM_ID=bomLine.C_UOM_ID
			where bomLine.IsActive='Y'
			order by bomLine.PP_Product_BOMLine_ID
		)
	)
	--
	select
		array_to_string(n.path, '.')||'.' as Line,
		array_to_string(n.parent_path, '.')||'.' as Parent_Line,
		n.ProductValue,
		n.ProductName,
		n.QtyBOM,
		n.Percentage,
		n.UOMSymbol,
		--
		n.depth,
		n.M_Product_ID,
		n.IsQtyPercentage,
		n.C_UOM_ID,
        n.path
	from bomNode n
	order by path
	;
$BODY$
LANGUAGE sql STABLE
COST 100;

