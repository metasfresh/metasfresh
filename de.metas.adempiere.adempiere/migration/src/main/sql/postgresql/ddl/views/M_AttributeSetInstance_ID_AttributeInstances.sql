DROP VIEW IF EXISTS M_AttributeSetInstance_ID_AttributeInstances;

CREATE OR REPLACE VIEW M_AttributeSetInstance_ID_AttributeInstances AS
SELECT
    ai.M_AttributeSetInstance_ID,
	CASE
      WHEN a.AttributeValueType = 'N' THEN array_agg(ai.M_Attribute_ID || '_'
		||  coalesce(ai.m_attributevalue_id::character varying, ai.valuenumber::character varying, ''))
      WHEN a.AttributeValueType = 'D' THEN array_agg(ai.M_Attribute_ID || '_'
        ||  coalesce(ai.m_attributevalue_id::character varying, ai.valuedate::character varying, ''))
	  ELSE
    array_agg(
        ai.M_Attribute_ID || '_' ||
        coalesce(ai.m_attributevalue_id::character varying, ai.value, ''))
    END AS AttributeInstances
FROM M_AttributeInstance ai
JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
GROUP BY ai.M_AttributeSetInstance_ID, a.AttributeValueType;
COMMENT ON VIEW M_AttributeSetInstance_ID_AttributeInstances
IS 'Returns M_AttributeSetInstance_IDs with arrays that represent all M_AttributeInstances of the respective ASI.
Each array element is a string containing of <M_Attribute_ID>_<Attribute-Instance-Value>';
