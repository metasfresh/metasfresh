-- Function: "de.metas.handlingunits".huattributeinfo(numeric, varchar)

-- DROP FUNCTION IF EXISTS "de.metas.handlingunits".huattributeinfo(numeric, varchar);

CREATE OR REPLACE FUNCTION "de.metas.handlingunits".huattributeinfo(p_m_hu_id numeric, m_attribute_value varchar)
  RETURNS character varying AS
$$
	SELECT
	(
		a.Value
		|| '(' || a.Name || ')'
		|| ', Value: ' || COALESCE(hua.Value::VARCHAR, '')
		|| ', ValueNumber: ' || COALESCE(hua.ValueNumber::VARCHAR, '')
		|| ', ValueInitial: ' || COALESCE(hua.ValueInitial::VARCHAR, '')
		|| ', ValueNumberInitial: ' || COALESCE(hua.ValueNumberInitial::VARCHAR, '')
	) AS huAttributeInfo
	FROM M_HU hu
	INNER JOIN M_HU_Attribute hua ON hua.M_HU_ID=hu.M_HU_ID
	INNER JOIN M_Attribute a ON a.M_Attribute_ID=hua.M_Attribute_ID
	WHERE hu.M_HU_ID=$1 AND a.Value=$2;
$$
  LANGUAGE SQL STABLE
  COST 100;


COMMENT ON FUNCTION  "de.metas.handlingunits".huattributeinfo(numeric, varchar) IS 'fresh 06936: Track attribute values of given HU ID and M_Attribute.Value - i.e HU ID "1000000" and Attribute value "WeightNet"'
