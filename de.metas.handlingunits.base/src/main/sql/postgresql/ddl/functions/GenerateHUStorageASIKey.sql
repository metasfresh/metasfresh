CREATE OR REPLACE FUNCTION GenerateHUStorageASIKey(asiId numeric, nullString text)
  RETURNS text AS
$BODY$
		SELECT COALESCE(string_agg(sub.AttributeValue::VARCHAR, '_'), $2)
		FROM (
			SELECT
				av.Name AS AttributeValue
			FROM M_AttributeSetInstance asi
				INNER JOIN M_AttributeInstance ai ON ai.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID
				INNER JOIN M_AttributeValue av ON av.M_AttributeValue_ID=ai.M_AttributeValue_ID
				INNER JOIN M_Attribute a ON a.M_Attribute_ID=ai.M_Attribute_ID
			WHERE asi.M_AttributeSetInstance_ID = $1
				AND av.IsActive='Y'
				AND a.IsMatchHUStorage='Y' -- Match significant attributes for HUStorage
				AND a.IsActive='Y'
			GROUP BY a.M_Attribute_ID, av.Name
			ORDER BY a.M_Attribute_ID, av.Name
		) sub;
$BODY$
  LANGUAGE sql STABLE;

COMMENT ON FUNCTION GenerateHUStorageASIKey(numeric, text) IS 'Creates a string that contains the M_AttributeValue.Names of those M_Attributes that have IsMatchHUStorage=Y.
Ff there are none, the fuction returns the given nullString.';

CREATE OR REPLACE FUNCTION GenerateHUStorageASIKey(asiId numeric)
  RETURNS text AS
$BODY$
	SELECT GenerateHUStorageASIKey(
		$1,
		null::text -- return null as null
	);
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION GenerateHUStorageASIKey(numeric, text) IS 'Similar to GenerateHUStorageASIKey(asiId numeric), but returns the given string if there is no attribuate-value-string to return';

  