-- me03#29063: Variant of GenerateASIStorageAttributesKey that includes ALL active attributes,
-- not just storage-relevant ones. Used for M_Product_ASI_Data ASI subset matching.

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
                     p_AttributeValueType => a.AttributeValueType::text,
                     p_Value => ai.Value::text,
                     p_ValueNumber => ai.ValueNumber,
                     p_ValueDate => ai.ValueDate::timestamp with time zone,
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
