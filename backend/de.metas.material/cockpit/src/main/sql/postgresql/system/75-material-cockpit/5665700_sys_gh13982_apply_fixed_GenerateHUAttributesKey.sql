

CREATE OR REPLACE FUNCTION GenerateHUAttributesKey(huId numeric)
    RETURNS text
    LANGUAGE 'sql'
    COST 100
    STABLE
AS $BODY$
    -- IMPORTANT: keep in sync with other Generate*AttributesKey functions (e.g. GenerateASIStorageAttributesKey)
SELECT COALESCE(string_agg(sub.keyPart, '§&§'), '-1002'/*NONE*/)
FROM (
         SELECT
             (case
                  when a.AttributeValueType = 'S' and coalesce(hua.Value, '') != '' then a.M_Attribute_ID || '=' || coalesce(hua.Value, '')::varchar
                  when a.AttributeValueType = 'N' and coalesce(hua.valuenumber,0) != 0 then a.M_Attribute_ID || '=' || coalesce(trim(hua.ValueNumber::varchar, '0'), '')
                  when a.AttributeValueType = 'D' and hua.valuedate is not null then a.M_Attribute_ID || '=' || coalesce(to_char(hua.ValueDate, 'YYYY-MM-DD'), '')::varchar
                  when a.AttributeValueType = 'L' and av.m_attributevalue_id is not null then av.M_AttributeValue_ID::varchar
                  else null
              end) as keyPart
         FROM M_HU hu
                  INNER JOIN M_HU_Attribute hua ON hua.M_HU_ID=hu.M_HU_ID
                  INNER JOIN M_Attribute a ON a.M_Attribute_ID=hua.M_Attribute_ID
                  LEFT OUTER JOIN M_AttributeValue av ON av.Value=hua.Value AND av.M_Attribute_ID=a.M_Attribute_ID
         WHERE hu.M_HU_ID = huId
           AND hua.IsActive='Y'
           -- AND av.IsActive='Y'
           AND a.IsActive='Y'
           AND a.IsStorageRelevant='Y'
         ORDER BY
             av.M_AttributeValue_ID NULLS LAST,
             a.M_Attribute_ID
     ) sub
WHERE sub.keyPart is not null
    ;
$BODY$;
COMMENT ON FUNCTION GenerateHUAttributesKey(numeric) IS
    'This function is used to generate values for the MD_Stock.AttributesKey column when initializing or resetting the MD_Stock table. 
    Please make sure it is in sync with the java implemention in AttributesKeys.createAttributesKeyFromASIStorageAttributes().
    Note that we sort by M_AttributeValue_ID and M_Attribute_ID because that is what AttributesKey.ofAttributeValueIds() does.
    
    Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';
