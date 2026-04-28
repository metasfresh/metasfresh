
-- 2017-05-12T22:50:43.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DIM_Dimension_Spec_Attribute (
	AD_Client_ID,
	AD_Org_ID,
	AttributeValueType,
	Created,
	CreatedBy,
	DIM_Dimension_Spec_Attribute_ID,
	DIM_Dimension_Spec_ID,
	IsActive,IsIncludeAllAttributeValues,IsValueAggregate,
	M_Attribute_ID,
	Updated,UpdatedBy
)
SELECT 
	1000000,
	1000000,
	'S',
	TO_TIMESTAMP('2017-05-12 22:50:43','YYYY-MM-DD HH24:MI:SS'),
	100,
	540015,
	540007, /* DIM_Dimension_Spec_ID */
	'Y','Y','N',
	540011, /* M_Attribute_ID */
	TO_TIMESTAMP('2017-05-12 22:50:43','YYYY-MM-DD HH24:MI:SS'),100
WHERE 
	NOT EXISTS (select 1 from DIM_Dimension_Spec_Attribute WHERE DIM_Dimension_Spec_ID=540007 AND M_Attribute_ID=540011)
;
