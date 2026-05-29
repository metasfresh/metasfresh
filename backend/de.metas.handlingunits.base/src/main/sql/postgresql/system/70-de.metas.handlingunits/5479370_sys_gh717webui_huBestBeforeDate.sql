CREATE OR REPLACE FUNCTION "de.metas.handlingunits".huBestBeforeDate(
    p_m_hu_id numeric
)
RETURNS timestamp without time zone AS
$BODY$
	select hua.ValueDate
	from M_HU hu
	inner join M_HU_Attribute hua on (
		hua.M_HU_ID=hu.M_HU_ID
		and hua.M_Attribute_ID=(select a.M_Attribute_ID from M_Attribute a where a.Value='HU_BestBeforeDate')
	)
	where hu.M_HU_ID=$1
$BODY$
LANGUAGE sql STABLE;

