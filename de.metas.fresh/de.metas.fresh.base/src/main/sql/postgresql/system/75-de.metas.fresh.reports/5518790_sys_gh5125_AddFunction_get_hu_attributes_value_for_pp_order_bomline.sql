DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline( IN numeric, IN numeric, IN numeric );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline(
  IN p_m_attribute_id numeric, IN p_pp_order_id numeric, IN p_pp_order_bomline_id numeric)
  RETURNS TABLE(
    attributes_value text
  )
AS
$$
SELECT String_agg(value, ', ') AS Attributes
FROM m_hu_attribute hua
  LEFT JOIN pp_order_qty ppqty on ppqty.m_hu_id = hua.m_hu_id
WHERE hua.m_attribute_id = p_m_attribute_id
      AND ppqty.pp_order_id = p_pp_order_id
      AND coalesce(ppqty.pp_order_bomline_id,0) = coalesce(p_pp_order_bomline_id, 0)
GROUP BY m_attribute_id;
$$
LANGUAGE sql
STABLE;	
comment on function de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline( IN numeric, IN numeric, IN numeric )
is 'This function returns a attribute value for matching PP_Order and pp_order_bomline.
pp_order_bomline parameter can be bull and then the attribute value for the product produced is retrieved
If multiple pp_order_bomline records match, is retrieved a string obatined by aggregating attribute values, separate by comma.
';
