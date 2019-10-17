DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description( IN numeric, IN numeric, IN Character Varying(6) );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description
(IN record_id   numeric,
 IN p_m_attribute_id   numeric,
 IN p_ad_language character Varying(6))

  RETURNS TABLE
  (
    documentno  character varying(30),
    dateordered timestamp with time zone,
    value       character varying(60),
    name        character varying(255),
    PrintName   character varying(60),
	attributes     character varying
  )
AS
$$
SELECT
  pp.DocumentNo,
  pp.DateOrdered,
  pbom.value,
  coalesce(pt.name, pbom.name) as name,
  COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
   Attributes.attributes_value
FROM PP_Order pp
  JOIN PP_Product_BOM bom on pp.PP_Product_BOM_ID = bom.PP_Product_BOM_ID

  LEFT JOIN de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline(p_m_attribute_id, pp.pp_order_id, null)  as Attributes on 1=1

   -- Product and its translation
  JOIN M_product pbom on bom.m_product_id = pbom.m_product_id
  LEFT JOIN M_Product_Trl pt ON bom.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language AND pt.isActive ='Y'

  LEFT JOIN C_DocType dt ON pp.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
  LEFT JOIN C_DocType_Trl dtt
    ON pp.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_ad_language AND dtt.isActive = 'Y'
WHERE pp.PP_Order_ID = record_id AND pp.isActive = 'Y'
$$
LANGUAGE sql
STABLE;