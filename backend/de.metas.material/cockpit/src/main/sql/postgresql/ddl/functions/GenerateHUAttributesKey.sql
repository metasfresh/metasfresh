-- SELECT db_drop_functions('GenerateHUAttributesKey');
DROP FUNCTION IF EXISTS GenerateHUAttributesKey(
    p_M_HU_ID            numeric,
    p_Only_Attribute_IDs numeric[] /*DEFAULT NULL */
);

CREATE OR REPLACE FUNCTION GenerateHUAttributesKey(
    p_M_HU_ID            numeric,
    p_Only_Attribute_IDs numeric[] /*DEFAULT NULL */
)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS
$BODY$
    -- IMPORTANT: keep in sync with other Generate*AttributesKey functions (e.g. GenerateASIStorageAttributesKey)
SELECT COALESCE(STRING_AGG(sub.keyPart, '§&§'), '-1002'/*NONE*/)
FROM (SELECT GenerateASIStorageAttributesKeyPart(
                     p_M_Attribute_ID => a.M_Attribute_ID,
                     p_AttributeValueType => a.AttributeValueType,
                     p_Value => hua.Value,
                     p_ValueNumber => hua.valuenumber,
                     p_ValueDate => hua.valuedate,
                     p_M_AttributeValue_ID => av.m_attributevalue_id
             ) AS keyPart
      FROM M_HU hu
               INNER JOIN M_HU_Attribute hua ON hua.M_HU_ID = hu.M_HU_ID
               INNER JOIN M_Attribute a ON a.M_Attribute_ID = hua.M_Attribute_ID
               LEFT OUTER JOIN M_AttributeValue av ON av.Value = hua.Value AND av.M_Attribute_ID = a.M_Attribute_ID
      WHERE hu.M_HU_ID = p_M_HU_ID
        AND hua.IsActive = 'Y'
        -- AND av.IsActive='Y'
        AND a.IsActive = 'Y'
        AND a.IsStorageRelevant = 'Y'
        AND (p_Only_Attribute_IDs IS NULL OR a.M_Attribute_ID = ANY (p_Only_Attribute_IDs))
      ORDER BY av.M_AttributeValue_ID NULLS LAST,
               a.M_Attribute_ID) sub
WHERE sub.keyPart IS NOT NULL
    ;
$BODY$
;

COMMENT ON FUNCTION GenerateHUAttributesKey(numeric, numeric[]) IS
    'This function is used to generate values for the MD_Stock.AttributesKey column when initializing or resetting the MD_Stock table. 
    Please make sure it is in sync with the java implemention in AttributesKeys.createAttributesKeyFromASIStorageAttributes().
    Note that we sort by M_AttributeValue_ID and M_Attribute_ID because that is what AttributesKey.ofAttributeValueIds() does.
    
    Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762'
;



--
--
--
--
--
--
--
--
--
--

CREATE OR REPLACE FUNCTION GenerateHUAttributesKey(
    huId numeric
)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS
$BODY$
SELECT GenerateHUAttributesKey(huId, NULL::numeric[]);
$BODY$
;

COMMENT ON FUNCTION GenerateHUAttributesKey(numeric) IS
    'This function is used to generate values for the MD_Stock.AttributesKey column when initializing or resetting the MD_Stock table. 
    Please make sure it is in sync with the java implemention in AttributesKeys.createAttributesKeyFromASIStorageAttributes().
    Note that we sort by M_AttributeValue_ID and M_Attribute_ID because that is what AttributesKey.ofAttributeValueIds() does.
    
    Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762'
;
