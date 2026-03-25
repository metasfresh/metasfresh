-- Returns the value for a specific attribute from an AttributesKey string.
--
-- Scalar wrapper over UnnestAttributesKey. Returns NULL when:
--   * p_AttributesKey is NULL / empty / NONE sentinel (-1002)
--   * The attribute is not present in the key
--
-- Java equivalent: AttributesKeys.toImmutableAttributeSet + ImmutableAttributeSet.getValueAsString

select db_drop_functions('*.getAttributesKeyValue');

CREATE OR REPLACE FUNCTION getAttributesKeyValue(
    p_AttributesKey  text,
    p_M_Attribute_ID numeric
)
    RETURNS text
    LANGUAGE sql
    STABLE
AS
$$
SELECT value
FROM UnnestAttributesKey(p_AttributesKey)
WHERE m_attribute_id = p_M_Attribute_ID
LIMIT 1
$$;

COMMENT ON FUNCTION getAttributesKeyValue(text, numeric) IS
    'Returns the value for a given M_Attribute_ID from an AttributesKey string (§&§-separated).
    Returns NULL when the attribute is absent from the key or the key is NONE.

    Works for all attribute types:
      L (List):   returns M_AttributeValue.Value (e.g. "DE", "CH")
      S (String): returns the string value directly
      N (Number): returns the numeric value as text
      D (Date):   returns YYYY-MM-DD string

    See also: UnnestAttributesKey';
