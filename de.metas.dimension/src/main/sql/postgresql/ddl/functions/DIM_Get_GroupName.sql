CREATE OR REPLACE FUNCTION "de.metas.dimension".DIM_Get_GroupName(dimInternalName text, asiKey text)
  RETURNS character varying[] AS
$BODY$

SELECT array_agg(distinct dim.GroupName) 
FROM "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues dim
WHERE dim.InternalName=$1
	AND (
		$2 ILIKE '%'||dim.ValueName||'%'
		OR (dim.ValueName='DIM_EMPTY' AND NOT EXISTS (select 1 from "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues dim2 where dim2.InternalName=dim.InternalName and $2 ILIKE '%'||dim2.ValueName||'%'))
	)
$BODY$
  LANGUAGE sql STABLE
  COST 100;
COMMENT ON  FUNCTION  "de.metas.dimension".DIM_Get_GroupName(text, text) IS 'returns the attribute-dimentsion-spec-lines'' GroupNames for the given ASI-key. can be used in joins like this:
 dim.GroupName = ANY("de.metas.dimension".DIM_Get_GroupName(''MRP_Product_Info_ASI_Values'', v.ASIKey))
...but for better performance, consider not using the function in a join..rather write it''s results into a physical column
';
