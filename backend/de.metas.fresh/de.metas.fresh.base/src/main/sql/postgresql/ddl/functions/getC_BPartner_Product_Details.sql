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
	LEFT JOIN M_AttributeSetInstance_ID_AttributeInstances asi ON asi.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
WHERE true
	AND bpp.M_Product_id = p_M_Product_id
	AND bpp.C_BPartner_ID = p_C_BPartner_ID
	
	/*asi_bpp is null, or its AttributeInstances array is contained in the given p_M_AttributeSetInstance_ID's AttributeInstances array */
	AND (bpp.M_AttributeSetInstance_ID = 0 or asi.AttributeInstances @> COALESCE(asi_bpp.AttributeInstances, ARRAY[]::character varying[]))
	
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
