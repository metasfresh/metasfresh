-- Function: GenerateHUStorageHUKey(numeric)

-- DROP FUNCTION IF EXISTS GenerateHUStorageHUKey(numeric);

CREATE OR REPLACE FUNCTION GenerateHUStorageHUKey(huId numeric)
  RETURNS text AS
$BODY$
BEGIN
	RETURN (
		SELECT string_agg(sub.AttributeValue::VARCHAR, '_')
		FROM (
			SELECT
				av.Name AS AttributeValue
			FROM M_HU hu
			INNER JOIN M_HU_Attribute hua ON hua.M_HU_ID=hu.M_HU_ID
			INNER JOIN M_Attribute a ON a.M_Attribute_ID=hua.M_Attribute_ID
			INNER JOIN M_AttributeValue av ON av.Value=hua.Value AND av.M_Attribute_ID=a.M_Attribute_ID
			WHERE hu.M_HU_ID = huId
			AND hua.IsActive='Y'
			AND av.IsActive='Y'
			AND a.IsMatchHUStorage='Y' -- Match significant attributes for HUStorage
			AND a.IsActive='Y'
			GROUP BY a.M_Attribute_ID, av.Name
			ORDER BY a.M_Attribute_ID, av.Name
		) sub
	);
END;
$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;


