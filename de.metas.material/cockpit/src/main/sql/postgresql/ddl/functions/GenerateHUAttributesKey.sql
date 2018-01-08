
CREATE OR REPLACE FUNCTION GenerateHUAttributesKey(huId numeric)
  RETURNS text AS
$BODY$
BEGIN
	RETURN (
		SELECT COALESCE(string_agg(sub.M_AttributeValue_ID::VARCHAR, 'ยง&ยง'), '-1002')
		FROM (
			SELECT
				av.M_AttributeValue_ID AS M_AttributeValue_ID
			FROM M_HU hu
				INNER JOIN M_HU_Attribute hua ON hua.M_HU_ID=hu.M_HU_ID
				INNER JOIN M_Attribute a ON a.M_Attribute_ID=hua.M_Attribute_ID
				INNER JOIN M_AttributeValue av ON av.Value=hua.Value AND av.M_Attribute_ID=a.M_Attribute_ID
			WHERE hu.M_HU_ID = huId
				AND hua.IsActive='Y'
				AND av.IsActive='Y'
				AND a.IsActive='Y'
			GROUP BY a.M_Attribute_ID, av.M_AttributeValue_ID
			ORDER BY a.M_Attribute_ID, av.M_AttributeValue_ID
		) sub
	);
END;
$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
COMMENT ON FUNCTION GenerateHUAttributesKey(numeric) IS
'This function is used to generate values for the MD_Stock.AttributesKey column when initializing or resetting the MD_Stock table. 
Please make sure it is in sync with the java implemention in AttributesKeys.createAttributesKeyFromASIAllAttributeValues()

Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';
