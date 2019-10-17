

CREATE OR REPLACE FUNCTION public.GenerateASIStorageAttributesKey(IN p_M_AttributeSetInstance_ID numeric)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE 
AS $BODY$
		SELECT COALESCE(string_agg(sub.M_AttributeValue_ID::VARCHAR, '§&§'), '-1002'/*NONE*/)
		FROM (
			SELECT
				av.M_AttributeValue_ID
			FROM M_AttributeInstance ai
				INNER JOIN M_AttributeValue av ON av.M_AttributeValue_ID=ai.M_AttributeValue_ID
				INNER JOIN M_Attribute a ON a.M_Attribute_ID=ai.M_Attribute_ID
			WHERE ai.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
				AND av.IsActive='Y'
				AND a.IsStorageRelevant='Y' -- Match significant attributes only
				AND a.IsActive='Y'
			ORDER BY a.M_Attribute_ID
		) sub;
$BODY$;
COMMENT ON FUNCTION public.generateASIStorageAttributesKey(numeric) IS 
'Creates a string to represent the given M_AttributeSetInstance_ID''s storage relevant attributes.

Needs to be keps in sync with the java method org.adempiere.mm.attributes.api.AttributesKeys.createAttributesKeyFromASIStorageAttributes(int)';
