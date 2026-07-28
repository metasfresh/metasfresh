SELECT db_drop_functions('*.GenerateASIStorageAttributesKeyPart')
;

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
