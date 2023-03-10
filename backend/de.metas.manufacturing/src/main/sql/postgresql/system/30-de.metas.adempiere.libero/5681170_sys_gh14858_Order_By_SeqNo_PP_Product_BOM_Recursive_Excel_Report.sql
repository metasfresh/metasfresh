/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

drop function if exists PP_Product_BOM_Recursive(numeric, character varying);
drop function if exists PP_Product_BOM_Recursive_Report(numeric);

CREATE OR REPLACE FUNCTION pp_product_bom_recursive(p_pp_product_bom_id numeric, p_ad_language character varying)
    RETURNS TABLE(line text, parent_line text, productvalue character varying, productname character varying, qtybom numeric, percentage numeric, uomsymbol character varying, depth integer, m_product_id numeric, isqtypercentage character, c_uom_id numeric, path integer[])
    STABLE
    LANGUAGE sql
AS
$$
--
	with recursive bomNode as (
		(
			select
				array[1::integer] as path,
				null::integer[] as parent_path,
				1 as depth,
				bomProduct.Value as ProductValue,
				coalesce(pt.Name, bomProduct.Name) as ProductName,
				bomProduct.M_Product_ID,
				bomProduct.IsBOM,
				bom.PP_Product_BOM_ID,
				'N'::char(1) as IsQtyPercentage,
				round(1::numeric, uom.StdPrecision) as QtyBOM,
				null::numeric as Percentage,
				COALESCE(uom.UOMSymbol, uomt.UOMSymbol) as UOMSymbol,
				uom.C_UOM_ID
			from PP_Product_BOM bom
			inner join M_Product bomProduct on bomProduct.M_Product_ID=bom.M_Product_ID
			LEFT OUTER JOIN M_Product_Trl pt    ON bomProduct.M_Product_ID = pt.M_Product_ID AND pt.AD_Language =p_ad_language
       AND pt.isActive = 'Y'
			left outer join C_UOM uom on uom.C_UOM_ID=coalesce(bom.C_UOM_ID, bomProduct.C_UOM_ID)
			LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.IsActive='Y' and uomt.AD_Language = p_ad_language
			where
			bom.PP_Product_BOM_ID=PP_Product_BOM_Recursive.p_PP_Product_BOM_ID
		)
		--
		union all
		--
		(
			select
				parent.path || (row_number() over (partition by bomLine.PP_Product_BOM_ID order by bomLine.line, bomLine.PP_Product_BOMLine_ID))::integer as path,
				parent.path as parent_path,
				parent.depth + 1 as depth,
				bomLineProduct.Value as ProductValue,
				coalesce(pt.Name, bomLineProduct.Name) as ProductName,
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
				COALESCE(uom.UOMSymbol, uomt.UOMSymbol) as UOMSymbol,
				uom.C_UOM_ID
			from bomNode parent
			inner join PP_Product_BOMLine bomLine on bomLine.PP_Product_BOM_ID=parent.PP_Product_BOM_ID
			inner join M_Product bomLineProduct on bomLineProduct.M_Product_ID = bomLine.M_Product_ID
			LEFT OUTER JOIN M_Product_Trl pt    ON bomLineProduct.M_Product_ID = pt.M_Product_ID AND pt.AD_Language =p_ad_language
       AND pt.isActive = 'Y'
			left outer join C_UOM uom on uom.C_UOM_ID=bomLine.C_UOM_ID
			LEFT OUTER JOIN C_UOM_Trl uomt ON bomLine.C_UOM_ID = uomt.C_UOM_ID AND uomt.IsActive='Y' and uomt.AD_Language = p_ad_language
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
$$
;

ALTER FUNCTION pp_product_bom_recursive(numeric, varchar) OWNER TO metasfresh
;

CREATE OR REPLACE FUNCTION pp_product_bom_recursive_report(p_pp_product_bom_id numeric)
    RETURNS TABLE(line text, productvalue character varying, productname character varying, qtybom numeric, percentage numeric, uomsymbol character varying)
    STABLE
    LANGUAGE sql
AS
$$
select
    t.Line,
    t.ProductValue,
    t.ProductName,
    t.QtyBOM,
    t.Percentage,
    t.UOMSymbol
from PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, null) t
order by t.path
$$
;

ALTER FUNCTION pp_product_bom_recursive_report(numeric) OWNER TO metasfresh
;