DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description( IN record_id numeric, IN ad_language Character Varying(6) );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description
(IN record_id   numeric,                                              
 IN ad_language character Varying(6))

  RETURNS TABLE
  (
    documentno  character varying(30),
    dateordered timestamp with time zone,
    value       character varying(60),
    name        character varying(255),
    orderno     character varying(30),
    PrintName   character varying(60)
  )
AS
$$
SELECT
  pp.DocumentNo,
  pp.DateOrdered,
  pbom.value,
  coalesce(pt.name, pbom.name) as name,
  o.DocumentNo                          as orderno,
  COALESCE(dtt.PrintName, dt.PrintName) AS PrintName
FROM PP_Order pp
  JOIN PP_Product_BOM bom on pp.PP_Product_BOM_ID = bom.PP_Product_BOM_ID
  
   -- Product and its translation
  JOIN M_product pbom on bom.m_product_id = pbom.m_product_id
  LEFT JOIN M_Product_Trl pt ON bom.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive ='Y'
  
  LEFT JOIN C_OrderLine ol on pp.c_orderline_id = ol.c_orderline_id
  LEFT JOIN c_order o on ol.C_Order_ID = o.c_order_id
  
  LEFT JOIN C_DocType dt ON pp.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
  LEFT JOIN C_DocType_Trl dtt
    ON pp.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE pp.PP_Order_ID = $1 AND pp.isActive = 'Y'
$$
LANGUAGE sql
STABLE;