DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Details( IN record_id numeric, IN ad_language Character Varying );

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Details
  (IN record_id   numeric,
   IN ad_language character Varying)

  RETURNS TABLE
  (
    line            numeric,
    qtyrequiered    numeric,
    uomsymbol       character varying,
    value           character varying,
    vendorProductNo character varying,
    description     character varying
  )
AS
$$

SELECT
  line,
  qtyrequiered,
  COALESCE(uom.UOMSymbol, uomt.UOMSymbol) AS UOMSymbol,
  p.value,
  coalesce(bpp.productno, p.value)        as vendorProductNo,
  coalesce(pt.description, p.description) as description
FROM PP_Order_BOMLine bomLine

  -- Product and its translation
  JOIN M_Product p ON bomLine.m_product_id = p.m_product_id AND p.isActive = 'Y'
  LEFT OUTER JOIN M_Product_Trl pt ON bomLine.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
  -- Unit of measurement and its translation
  JOIN c_uom uom on bomLine.c_uom_id = uom.c_uom_id AND uom.isActive = 'Y'
  LEFT OUTER JOIN C_UOM_Trl uomt ON bomLine.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2
  LEFT JOIN getc_bpartner_product_vendor(p.m_product_id) bpp on 1 = 1
WHERE
  bomLine.PP_Order_ID = $1 AND bomLine.isActive = 'Y'
ORDER BY
  line
$$
LANGUAGE sql
STABLE;