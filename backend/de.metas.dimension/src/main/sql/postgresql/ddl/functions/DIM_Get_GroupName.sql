CREATE OR REPLACE FUNCTION "de.metas.dimension".DIM_Get_GroupName(dimInternalName text, asiKey text)
  RETURNS character varying[] AS
$BODY$

SELECT array_agg(distinct dim.GroupName) 
FROM "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues dim
WHERE dim.InternalName=$1
	AND (
		$2 ILIKE '%'||dim.ValueName||'%'
		OR (dim.ValueName='DIM_EMPTY' AND NOT EXISTS (
			select 1 from "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues dim2 where dim2.InternalName=dim.InternalName and $2 ILIKE '%'||dim2.ValueName||'%')
		)
	)
$BODY$
  LANGUAGE sql STABLE
  COST 100;
COMMENT ON  FUNCTION  "de.metas.dimension".DIM_Get_GroupName(text, text) IS 'Returns the attribute-dimension-spec-lines'' GroupNames for the given ASI-key as array.
Its purpose is to join order lines and other "ASI-Aware" records to the "measures" (but note, this is not really olap!) of a given dimension. 
So if you e.g. have a bunch of order lines and a dimension, 
you can aggregate those lines and provide sums based on the attributes that are part of the dimension

To that end, it can be used in joins like this:
 dim.GroupName = ANY("de.metas.dimension".DIM_Get_GroupName(''MRP_Product_Info_ASI_Values'', v.ASIKey))

That way, you can join the given ASI-Key to its respective GroupName(s) as returned by the DIM_Dimension_Spec_Attribute_AllValues.

That groupname is some Dim_Dimension_Spec_Attribute.ValueAggregateName (i.e. a common name given to multiple attribute values) or M_AttributeValue.Name.
Check out the view DIM_Dimension_Spec_Attribute_AllValues for more infos.

Note: for better performance, consider not using the function in a join..rather write it''s results into a physical column.';
