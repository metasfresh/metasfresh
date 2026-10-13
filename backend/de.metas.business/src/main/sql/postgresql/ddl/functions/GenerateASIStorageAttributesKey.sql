-- DROP FUNCTION IF EXISTS GenerateASIStorageAttributesKey(
--     p_M_AttributeSetInstance_ID numeric,
--     p_NullString                text,
--     p_Only_Attribute_IDs        numeric[] /*DEFAULT NULL*/
-- )
-- ;


CREATE OR REPLACE FUNCTION GenerateASIStorageAttributesKey(
    IN p_M_AttributeSetInstance_ID numeric,
    IN p_NullString                text,
    IN p_Only_Attribute_IDs        numeric[] /*DEFAULT NULL*/
)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS
$BODY$
    -- IMPORTANT: keep in sync with other Generate*AttributesKey functions (e.g. GenerateHUAttributesKey)
SELECT COALESCE(STRING_AGG(sub.keyPart, '§&§'), p_NullString)
FROM (SELECT GenerateASIStorageAttributesKeyPart(
                     p_M_Attribute_ID => ai.M_Attribute_ID,
                     p_AttributeValueType => a.AttributeValueType,
                     p_Value => ai.Value,
                     p_ValueNumber => ai.ValueNumber,
                     p_ValueDate => ai.ValueDate,
                     p_M_AttributeValue_ID => ai.M_AttributeValue_ID
             ) AS keyPart
      FROM M_AttributeInstance ai
               INNER JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
               LEFT OUTER JOIN M_AttributeValue av ON av.M_AttributeValue_ID = ai.M_AttributeValue_ID
      WHERE ai.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
        AND a.IsActive = 'Y'
        AND a.IsStorageRelevant = 'Y' -- Match significant attributes only
        -- AND av.IsActive='Y'
        AND (p_Only_Attribute_IDs IS NULL OR a.M_Attribute_ID = ANY (p_Only_Attribute_IDs))
      ORDER BY av.M_AttributeValue_ID NULLS LAST,
               a.M_Attribute_ID) sub
WHERE sub.keyPart IS NOT NULL
    ;
$BODY$
;

COMMENT ON FUNCTION generateASIStorageAttributesKey(numeric, text, numeric[]) IS
    'Creates a string to represent the given M_AttributeSetInstance_ID''s storage relevant attribute values.
    Parmeters:
    * p_M_AttributeSetInstance_ID: M_AttributeSetInstance_ID of the ASI to render as StorageAttributesKey. InActive and not-StorageRelevant records are ignored
    * p_NullString: string to return if there aren'' any matching attribute values. Usual parameter values are
      * ''-1002'' which means "none"
      * '''' (empty string) which can be useful when doing substring matching
    * p_Only_Attribute_IDs: optional array of M_Attribute_ID to filter by.
    
    Needs to be kept in sync with the java method org.adempiere.mm.attributes.api.AttributesKeys.createAttributesKeyFromASIStorageAttributes(int)'
;

CREATE OR REPLACE FUNCTION GenerateASIStorageAttributesKey(
    p_M_AttributeSetInstance_ID numeric,
    p_NullString                text
)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS
$BODY$
SELECT GenerateASIStorageAttributesKey(p_M_AttributeSetInstance_ID, p_NullString, NULL::numeric[]);
$BODY$
;

COMMENT ON FUNCTION generateASIStorageAttributesKey(numeric, text) IS
    'Creates a string to represent the given M_AttributeSetInstance_ID''s storage relevant attribute values.
    Parmeters:
    * p_M_AttributeSetInstance_ID: M_AttributeSetInstance_ID of the ASI to render as StorageAttributesKey. InActive and not-StorageRelevant records are ignored
    * p_NullString: string to return if there aren'' any matching attribute values. Usual parameter values are
      * ''-1002'' which means "none"
      * '''' (empty string) which can be useful when doing substring matching
    
    Needs to be kept in sync with the java method org.adempiere.mm.attributes.api.AttributesKeys.createAttributesKeyFromASIStorageAttributes(int)'
;

--
--------------------------------------------------------------------------------------
--

CREATE OR REPLACE FUNCTION GenerateASIStorageAttributesKey(IN p_M_AttributeSetInstance_ID numeric)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS
$BODY$
SELECT *
FROM GenerateASIStorageAttributesKey(p_M_AttributeSetInstance_ID, '-1002'/*NONE*/);
$BODY$
;

COMMENT ON FUNCTION generateASIStorageAttributesKey(numeric) IS
    'Creates a string to represent the given M_AttributeSetInstance_ID''s storage relevant attributes.
    
    Needs to be kept in sync with the java method org.adempiere.mm.attributes.api.AttributesKeys.createAttributesKeyFromASIStorageAttributes(int)'
;
