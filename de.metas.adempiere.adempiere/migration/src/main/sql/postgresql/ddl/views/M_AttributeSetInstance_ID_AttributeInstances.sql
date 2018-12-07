
DROP VIEW IF EXISTS M_AttributeSetInstance_ID_AttributeInstances;

CREATE OR REPLACE VIEW M_AttributeSetInstance_ID_AttributeInstances AS
SELECT
    ai.M_AttributeSetInstance_ID,
    array_agg(
        ai.M_Attribute_ID || '_' || 
        coalesce(
            ai.m_attributevalue_id::character varying, ai.value, ai.valuenumber::character varying, ai.valuedate::character varying, '')
    ) as AttributeInstances
FROM M_AttributeInstance ai
GROUP BY ai.M_AttributeSetInstance_ID;
COMMENT ON VIEW M_AttributeSetInstance_ID_AttributeInstances 
IS 'Returns M_AttributeSetInstance_IDs with arrays that represent all M_AttributeInstances of the respective ASI.
Each array element is a string containing of <M_Attribute_ID>_<Attribute-Instance-Value>';
