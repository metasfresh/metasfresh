

CREATE OR REPLACE FUNCTION public.GenerateASIStorageAttributesKey(
		IN p_M_AttributeSetInstance_ID numeric,
		IN p_NullString text)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE 
AS $BODY$
		SELECT COALESCE(string_agg(sub.M_AttributeValue_ID::VARCHAR, '§&§'), p_NullString)
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
COMMENT ON FUNCTION public.generateASIStorageAttributesKey(numeric, text) IS 
'Creates a string to represent the given M_AttributeSetInstance_ID''s storage relevant attribute values via their M_AttributeValue_IDs.
Parmeters:
* p_M_AttributeSetInstance_ID: M_AttributeSetInstance_ID of the ASI to render as StorageAttributesKey. InActive and not-StorageRelevant records are ignored
* p_NullString: string to return if there aren'' any matching attribute values. Usual parameter values are
  * ''-1002'' which means "none"
  * '''' (empty string) which can be useful when doing substring matching

Needs to be kept in sync with the java method org.adempiere.mm.attributes.api.AttributesKeys.createAttributesKeyFromASIStorageAttributes(int)';

--
--------------------------------------------------------------------------------------
--

CREATE OR REPLACE FUNCTION public.GenerateASIStorageAttributesKey(IN p_M_AttributeSetInstance_ID numeric)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE 
AS $BODY$
		SELECT * FROM GenerateASIStorageAttributesKey(p_M_AttributeSetInstance_ID, '-1002'/*NONE*/);
$BODY$;
COMMENT ON FUNCTION public.generateASIStorageAttributesKey(numeric) IS 
'Creates a string to represent the given M_AttributeSetInstance_ID''s storage relevant attributes.

Needs to be kept in sync with the java method org.adempiere.mm.attributes.api.AttributesKeys.createAttributesKeyFromASIStorageAttributes(int)';
