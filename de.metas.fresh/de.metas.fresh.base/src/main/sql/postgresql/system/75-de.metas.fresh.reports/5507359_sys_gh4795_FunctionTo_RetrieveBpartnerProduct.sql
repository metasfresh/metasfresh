
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, numeric);
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

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(
	IN p_M_Product_id numeric, 
	IN p_C_BPartner_ID numeric, 
	IN p_M_AttributeSetInstance_ID numeric)
RETURNS TABLE(
	ProductNo character varying, 
	ProductName character varying, 
	C_BPartner_Product_ID numeric
) AS
$BODY$

SELECT 
	DISTINCT ON (bpp.M_AttributeSetInstance_ID, ProductNo, ProductName)
	ProductNo, ProductName, C_BPartner_Product_ID
FROM C_BPartner_Product bpp
	LEFT JOIN M_AttributeSetInstance_ID_AttributeInstances asi_bpp ON asi_bpp.M_AttributeSetInstance_ID = bpp.M_AttributeSetInstance_ID
WHERE true
	AND bpp.M_Product_id = p_M_Product_id
	AND bpp.C_BPartner_ID = p_C_BPartner_ID
	
	/*asi_bpp is null, or its AttributeInstances array is contained in the given p_M_AttributeSetInstance_ID's AttributeInstances array */
	AND (
	(select asi.AttributeInstances from M_AttributeSetInstance_ID_AttributeInstances asi where asi.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID)
		@> COALESCE(asi_bpp.AttributeInstances, ARRAY[]::character varying[]) 
		OR bpp.M_AttributeSetInstance_ID = 0
		)
ORDER BY bpp.M_AttributeSetInstance_ID desc, ProductNo, ProductName, SeqNo
LIMIT 1
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, numeric) 
IS 'This function returns a C_BPartner_Product record with a matching matching given product-Id, bpartner-Id and p_M_AttributeSetInstance_ID.
For the attribute-set-instance-id to match, its array as returned by the view M_AttributeSetInstance_ID_AttributeInstances 
needs to be included in the given p_M_AttributeSetInstance_ID''s M_AttributeSetInstance_ID_AttributeInstances-array.
C_BPartner_Product records that have no M_AttributeSetInstance_ID match every p_M_AttributeSetInstance_ID.
If multiple C_BPartner_Product records match, the one with the lowest SeqNo is returned.

Also see 
view M_AttributeSetInstance_ID_AttributeInstances and
issue https://github.com/metasfresh/metasfresh/issues/4795'
;
