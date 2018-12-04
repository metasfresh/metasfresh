-- Function: de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, numeric)

-- DROP FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(IN p_M_Product_id numeric, IN p_C_BPartner_ID numeric, IN p_M_AttributeSetInstance_ID numeric)
  RETURNS TABLE(M_Product_ID numeric, ProductNo character varying, ProductName character varying, attributes text[], C_BPartner_ID numeric,C_BPartner_Product_ID numeric) AS
$BODY$
	SELECT M_Product_ID, ProductNo, ProductName, Attributes, C_BPartner_ID, C_BPartner_Product_ID
FROM
  (
    SELECT
      array_agg(bpp.AttValue) as paramAttributes,
      array_agg(bpp.Value)    as Attributes,
      ProductNo,
      ProductName,
      M_Product_ID,
      C_BPartner_ID,
	  C_BPartner_Product_ID
    FROM
      (
        SELECT
          ai1.M_attribute_ID,
          bpp.ProductNo,
          bpp.ProductName,
          bpp.M_Product_ID,
          bpp.C_BPartner_ID,
		  bpp.C_BPartner_Product_ID,
          bpp.M_AttributeSetInstance_ID,
          seqno,
          ai1.M_attribute_ID || '_' ||
          coalesce(ai1.m_attributevalue_id :: VARCHAR, ai1.value, ai1.valuenumber :: VARCHAR, ai1.valuedate :: VARCHAR,
                   '') as value,
          att.M_attribute_ID || '_' ||
          coalesce(att.m_attributevalue_id :: VARCHAR, att.value, att.valuenumber :: VARCHAR, att.valuedate :: VARCHAR,
                   '') as attValue
        FROM C_BPartner_Product bpp
          LEFT JOIN M_AttributeSetInstance asi1 on asi1.M_AttributeSetInstance_ID = bpp.M_AttributeSetInstance_ID
          LEFT JOIN M_AttributeInstance ai1 ON ai1.M_AttributeSetInstance_ID = asi1.M_AttributeSetInstance_ID
          LEFT JOIN
          (
            SELECT
              ai2.M_attribute_ID,
              ai2.value,
              ai2.m_attributevalue_id,
              ai2.valuenumber,
              ai2.valuedate
            FROM M_AttributeSetInstance asi2
              INNER JOIN M_AttributeInstance ai2 ON ai2.M_AttributeSetInstance_ID = asi2.M_AttributeSetInstance_ID
            WHERE asi2.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
          ) as att on att.m_attribute_id = ai1.m_attribute_id OR ai1.m_attribute_id is null
        WHERE bpp.isActive = 'Y'
        ORDER by bpp.M_AttributeSetInstance_ID desc, seqno
      ) bpp
    WHERE bpp.M_Product_ID = p_M_Product_id AND bpp.C_BPartner_ID = p_C_BPartner_ID
    GROUP by ProductNo, ProductName, M_Product_ID, bpp.C_BPartner_ID, C_BPartner_Product_ID
  ) sub
where sub.paramAttributes @> sub.Attributes or sub.Attributes = ARRAY[NULL]
order by Attributes
limit 1
$BODY$
LANGUAGE sql STABLE;