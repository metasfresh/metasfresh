-- Variant of GenerateASIStorageAttributesKey that includes ALL active attributes,
-- not just storage-relevant ones. Used for ASI subset matching in M_Product_ASI_Data lookups
-- where GTIN-relevant attributes (e.g., country of origin) may not be storage-relevant.
--
-- Note: The KeyPart encoding logic is inlined (instead of calling GenerateASIStorageAttributesKeyPart)
-- to avoid parameter-type mismatches with older overloads that may be installed in preloaded DBs.
-- The encoding MUST stay in sync with GenerateASIStorageAttributesKeyPart.

CREATE OR REPLACE FUNCTION GenerateASIAllAttributesKey(
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
SELECT COALESCE(STRING_AGG(sub.keyPart, '§&§'), p_NullString)
FROM (SELECT CASE
                 -- String Attribute (S): 'M_Attribute_ID=Value'
                 WHEN a.AttributeValueType = 'S' AND COALESCE(ai.Value, '') != ''
                     THEN ai.M_Attribute_ID || '=' || COALESCE(ai.Value, '')::varchar
                 -- Number Attribute (N): 'M_Attribute_ID=Value' (with stripped trailing zeros)
                 WHEN a.AttributeValueType = 'N' AND COALESCE(ai.ValueNumber, 0) != 0
                     THEN ai.M_Attribute_ID || '=' || TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM ai.ValueNumber::text))
                 -- Date Attribute (D): 'M_Attribute_ID=YYYY-MM-DD'
                 WHEN a.AttributeValueType = 'D' AND ai.ValueDate IS NOT NULL
                     THEN ai.M_Attribute_ID || '=' || TO_CHAR(ai.ValueDate, 'YYYY-MM-DD')
                 -- List Attribute (L): 'M_AttributeValue_ID'
                 WHEN a.AttributeValueType = 'L' AND ai.M_AttributeValue_ID IS NOT NULL
                     THEN ai.M_AttributeValue_ID::varchar
                 ELSE NULL
                 END AS keyPart
      FROM M_AttributeInstance ai
               INNER JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
               LEFT OUTER JOIN M_AttributeValue av ON av.M_AttributeValue_ID = ai.M_AttributeValue_ID
      WHERE ai.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
        AND a.IsActive = 'Y'
        -- NOTE: No IsStorageRelevant filter — includes ALL active attributes
        AND (p_Only_Attribute_IDs IS NULL OR a.M_Attribute_ID = ANY (p_Only_Attribute_IDs))
      ORDER BY av.M_AttributeValue_ID NULLS LAST,
               a.M_Attribute_ID) sub
WHERE sub.keyPart IS NOT NULL
    ;
$BODY$
;

COMMENT ON FUNCTION GenerateASIAllAttributesKey(numeric, text, numeric[]) IS
    'Creates a string to represent ALL active attribute values of the given M_AttributeSetInstance_ID
    (not just storage-relevant ones, unlike GenerateASIStorageAttributesKey).
    Used for ASI subset matching in M_Product_ASI_Data lookups.

    Parameters:
    * p_M_AttributeSetInstance_ID: ASI to render
    * p_NullString: string to return if no attributes found (usually ''-1002'' for NONE or '''' for empty)
    * p_Only_Attribute_IDs: optional array to filter by specific attributes

    Encoding logic is inlined (must stay in sync with GenerateASIStorageAttributesKeyPart).
    Java equivalent: AttributesKeys.createAttributesKeyFromASIAllAttributes()'
;

--
--------------------------------------------------------------------------------------
--

CREATE OR REPLACE FUNCTION GenerateASIAllAttributesKey(
    p_M_AttributeSetInstance_ID numeric,
    p_NullString                text
)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS
$BODY$
SELECT GenerateASIAllAttributesKey(p_M_AttributeSetInstance_ID, p_NullString, NULL::numeric[]);
$BODY$
;

--
--------------------------------------------------------------------------------------
--

CREATE OR REPLACE FUNCTION GenerateASIAllAttributesKey(IN p_M_AttributeSetInstance_ID numeric)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS
$BODY$
SELECT GenerateASIAllAttributesKey(p_M_AttributeSetInstance_ID, '-1002'/*NONE*/);
$BODY$
;
