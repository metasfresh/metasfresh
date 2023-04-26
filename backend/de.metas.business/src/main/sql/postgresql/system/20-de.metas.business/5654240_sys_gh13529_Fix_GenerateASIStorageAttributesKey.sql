DROP FUNCTION IF EXISTS public.GenerateASIStorageAttributesKey(numeric, text);

CREATE OR REPLACE FUNCTION public.GenerateASIStorageAttributesKey(
    IN p_M_AttributeSetInstance_ID numeric,
    IN p_NullString text)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS $BODY$
    -- IMPORTANT: keep in sync with other Generate*AttributesKey functions (e.g. GenerateHUAttributesKey)
SELECT COALESCE(string_agg(sub.keyPart, '§&§'), p_NullString)
FROM (
         SELECT
             (case
                  when a.AttributeValueType = 'S' and coalesce(ai.Value, '') != '' then a.M_Attribute_ID || '=' || coalesce(ai.Value, '')::varchar
                  when a.AttributeValueType = 'N' and coalesce(ai.valuenumber,0) != 0 then a.M_Attribute_ID || '=' || coalesce(trim(ai.ValueNumber::varchar, '0'), '')
                  when a.AttributeValueType = 'D' and ai.valuedate is not null then a.M_Attribute_ID || '=' || coalesce(to_char(ai.ValueDate, 'YYYY-MM-DD'), '')::varchar
                  when a.AttributeValueType = 'L' and av.m_attributevalue_id is not null then av.M_AttributeValue_ID::varchar
                  else null
              end) as keyPart
         FROM M_AttributeInstance ai
                  INNER JOIN M_Attribute a ON a.M_Attribute_ID=ai.M_Attribute_ID
                  LEFT OUTER JOIN M_AttributeValue av ON av.M_AttributeValue_ID=ai.M_AttributeValue_ID
         WHERE ai.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
           AND a.IsActive='Y'
           AND a.IsStorageRelevant='Y' -- Match significant attributes only
           -- AND av.IsActive='Y'
         ORDER BY
             av.M_AttributeValue_ID NULLS LAST,
             a.M_Attribute_ID
     ) sub
WHERE sub.keyPart is not null
    ;
$BODY$;
COMMENT ON FUNCTION public.generateASIStorageAttributesKey(numeric, text) IS
    'Creates a string to represent the given M_AttributeSetInstance_ID''s storage relevant attribute values.
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
