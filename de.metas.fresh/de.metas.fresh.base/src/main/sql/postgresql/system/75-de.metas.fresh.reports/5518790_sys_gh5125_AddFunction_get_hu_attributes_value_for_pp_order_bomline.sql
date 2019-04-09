DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_hu_attributes_value_for_pp_order_bomline( IN numeric, IN numeric );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_hu_attributes_value_for_pp_order_bomline(
  IN p_attribute_id numeric, IN p_order_bomline_id numeric)
  RETURNS TABLE(
    attributes_value text
  )
AS
$$
SELECT String_agg(value, ', ') AS Attributes
FROM m_hu_attribute hua
  LEFT JOIN pp_order_qty ppqty on ppqty.m_hu_id = hua.m_hu_id
WHERE hua.m_attribute_id = p_attribute_id
      AND ppqty.pp_order_bomline_id = p_order_bomline_id
GROUP BY m_attribute_id;
$$
LANGUAGE sql
STABLE;	