DROP VIEW IF EXISTS M_AttributeSetInstance_ID_AttributeInstances;

CREATE OR REPLACE VIEW M_AttributeSetInstance_ID_AttributeInstances AS
SELECT M_AttributeSetInstance_ID,
array_agg(M_Attribute_ID || '_' || value) as AttributeInstances
FROM
	(
		SELECT
			ai.M_AttributeSetInstance_ID, ai.M_Attribute_ID,
			CASE
			WHEN a.AttributeValueType = 'N'
				THEN  coalesce(ai.m_attributevalue_id :: character varying, ai.valuenumber :: character varying, '')
			WHEN a.AttributeValueType = 'D'
				THEN coalesce(ai.m_attributevalue_id :: character varying, ai.valuedate :: character varying, '')
			ELSE
				coalesce(ai.m_attributevalue_id :: character varying, ai.value, '')
			END AS Value
		FROM M_AttributeInstance ai
			JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID
	) as data
GROUP BY M_AttributeSetInstance_ID;
COMMENT ON VIEW M_AttributeSetInstance_ID_AttributeInstances
IS 'Returns M_AttributeSetInstance_IDs with arrays that represent all M_AttributeInstances of the respective ASI.
Each array element is a string containing of <M_Attribute_ID>_<Attribute-Instance-Value>';