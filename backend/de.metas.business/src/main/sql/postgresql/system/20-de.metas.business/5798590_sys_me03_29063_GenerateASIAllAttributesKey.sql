-- me03#29063: Variant of GenerateASIStorageAttributesKey that includes ALL active attributes,
-- not just storage-relevant ones. Used for M_Product_ASI_Data ASI subset matching.
--
-- This migration first re-installs GenerateASIStorageAttributesKeyPart with the canonical signature
-- (some preloaded DBs carry older overloads with incompatible parameter types, which would make
-- GenerateASIAllAttributesKey fail to resolve the call below). That way GenerateASIAllAttributesKey
-- and GenerateASIStorageAttributesKey share the same KeyPart encoding — single source of truth.

-- Drop any stale overloads of GenerateASIStorageAttributesKeyPart so the CREATE below lands cleanly.
SELECT db_drop_functions('*.GenerateASIStorageAttributesKeyPart');

-- Canonical GenerateASIStorageAttributesKeyPart (copied verbatim from its DDL file).
CREATE OR REPLACE FUNCTION GenerateASIStorageAttributesKeyPart(
    IN p_M_Attribute_ID      numeric,
    IN p_AttributeValueType  text,
    IN p_Value               text,
    IN p_ValueNumber         numeric,
    IN p_ValueDate           timestamp with time zone,
    IN p_M_AttributeValue_ID numeric
)
    RETURNS text
    LANGUAGE 'sql'
    IMMUTABLE
AS
$BODY$
    -- Extracts one AttributesKeyPart from a single M_AttributeInstance
    -- Matches logic in: org.adempiere.mm.attributes.keys.AttributesKeys#createAttributesKeyPart
SELECT (CASE
    -- String Attribute (S): 'M_Attribute_ID=Value'
            WHEN p_AttributeValueType = 'S' AND COALESCE(p_Value, '') != ''
                THEN p_M_Attribute_ID || '=' || COALESCE(p_Value, '')::varchar

    -- Number Attribute (N): 'M_Attribute_ID=Value' (with stripped trailing zeros)
            WHEN p_AttributeValueType = 'N' AND COALESCE(p_ValueNumber, 0) != 0
                THEN p_M_Attribute_ID || '=' || TRIM(TRAILING '.' FROM TRIM(TRAILING '0' FROM p_ValueNumber::text))

    -- Date Attribute (D): 'M_Attribute_ID=YYYY-MM-DD'
            WHEN p_AttributeValueType = 'D' AND p_ValueDate IS NOT NULL
                THEN p_M_Attribute_ID || '=' || TO_CHAR(p_ValueDate, 'YYYY-MM-DD')

    -- List Attribute (L): 'M_AttributeValue_ID'
            WHEN p_AttributeValueType = 'L' AND p_M_AttributeValue_ID IS NOT NULL
                THEN p_M_AttributeValue_ID::varchar

                ELSE NULL
        END);
$BODY$
;

COMMENT ON FUNCTION GenerateASIStorageAttributesKeyPart(numeric, text, text, numeric, timestamp with time zone, numeric) IS
    'Encodes a single attribute instance''s value into one part of an AttributesKey string.

    Despite the "Storage" in its name, this function does NOT filter by IsStorageRelevant — it just
    encodes whichever attribute the caller passes in. The storage-relevance filter (when applicable)
    lives in the caller (e.g. GenerateASIStorageAttributesKey vs GenerateASIAllAttributesKey).

    Output format depends on the attribute''s value type:
    * String (S):      ''<M_Attribute_ID>=<Value>''
    * Number (N):      ''<M_Attribute_ID>=<Value>''     (trailing zeros stripped)
    * Date   (D):      ''<M_Attribute_ID>=YYYY-MM-DD''
    * List   (L):      ''<M_AttributeValue_ID>''
    * anything else:   NULL (caller should drop NULLs before STRING_AGG)

    Java equivalent: org.adempiere.mm.attributes.keys.AttributesKeys#createAttributesKeyPart'
;

-- GenerateASIAllAttributesKey: like GenerateASIStorageAttributesKey but WITHOUT the IsStorageRelevant filter.
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
        -- NOTE: No IsStorageRelevant filter — includes ALL active attributes
        AND (p_Only_Attribute_IDs IS NULL OR a.M_Attribute_ID = ANY (p_Only_Attribute_IDs))
      ORDER BY av.M_AttributeValue_ID NULLS LAST,
               a.M_Attribute_ID) sub
WHERE sub.keyPart IS NOT NULL
    ;
$BODY$
;

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

COMMENT ON FUNCTION GenerateASIAllAttributesKey(numeric, text) IS
    'Convenience overload of GenerateASIAllAttributesKey that passes NULL for p_Only_Attribute_IDs.
    See GenerateASIAllAttributesKey(numeric, text, numeric[]) for details.'
;

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

COMMENT ON FUNCTION GenerateASIAllAttributesKey(numeric) IS
    'Convenience overload of GenerateASIAllAttributesKey that uses ''-1002'' (NONE sentinel)
    when the ASI has no attributes. See GenerateASIAllAttributesKey(numeric, text, numeric[]) for details.'
;

COMMENT ON FUNCTION GenerateASIAllAttributesKey(numeric, text, numeric[]) IS
    'Creates a string to represent ALL active attribute values of the given M_AttributeSetInstance_ID
    (not just storage-relevant ones, unlike GenerateASIStorageAttributesKey).
    Used for ASI subset matching in M_Product_ASI_Data lookups.

    Parameters:
    * p_M_AttributeSetInstance_ID: ASI to render
    * p_NullString: string to return if no attributes found (usually ''-1002'' for NONE or '''' for empty)
    * p_Only_Attribute_IDs: optional array to filter by specific attributes

    Java equivalent: AttributesKeys.createAttributesKeyFromASIAllAttributes()'
;
