DROP VIEW IF EXISTS PP_Maturing_Candidates_v
;

CREATE OR REPLACE VIEW PP_Maturing_Candidates_v AS
SELECT hu.m_hu_id                                                 AS PP_Maturing_Candidates_v_ID,
       hu.m_hu_id,
       hus.qty,
       hu.hustatus,
       pp.m_product_id,
       hus.m_product_id as issue_m_product_id,
       hus.c_uom_id,
       w.m_warehouse_id,
       mc.m_maturing_configuration_id,
       mcl.m_maturing_configuration_line_id,
       pp.pp_product_planning_id,
       pp.pp_product_bomversions_id,
       pp.m_attributesetinstance_id,
       hua.valuedate + MAKE_INTERVAL(0, mcl.maturityage::integer) AS DateStartSchedule,
       ppoc.pp_order_candidate_id,
       hu.ad_org_id,
       hu.ad_client_id
FROM m_hu hu
         INNER JOIN m_hu_storage hus ON hu.m_hu_id = hus.m_hu_id
         INNER JOIN m_hu_attribute hua ON hu.m_hu_id = hua.m_hu_id AND m_attribute_id = (SELECT m_attribute_id FROM m_attribute WHERE VALUE = 'ProductionDate')
         INNER JOIN m_locator l ON hu.m_locator_id = l.m_locator_id
         INNER JOIN m_warehouse w ON l.m_warehouse_id = w.m_warehouse_id
         INNER JOIN m_maturing_configuration_line mcl ON hus.m_product_id = mcl.from_product_id AND mcl.isactive = 'Y'
         INNER JOIN m_maturing_configuration mc ON mcl.m_maturing_configuration_id = mc.m_maturing_configuration_id AND mc.isactive = 'Y'
         INNER JOIN pp_product_planning pp ON w.m_warehouse_id = pp.m_warehouse_id AND mcl.m_maturing_configuration_line_id = pp.m_maturing_configuration_line_id AND pp.m_product_id = mcl.matured_product_id AND pp.isactive = 'Y' AND pp.ismatured = 'Y'
         LEFT JOIN pp_order_candidate ppoc ON hu.m_hu_id = ppoc.issue_hu_id
         LEFT JOIN m_hu_item hui ON hu.m_hu_id = hui.m_hu_id
         LEFT JOIN m_hu childHu ON childHu.m_hu_item_parent_id = hui.m_hu_item_id
WHERE hu.isactive = 'Y'
  AND hu.hustatus IN ('A', 'I')
  AND hu.isreserved != 'Y'
  AND hu.locked != 'Y'
  AND hus.qty > 0
  AND (ppoc.pp_order_candidate_id IS NULL
    OR (ppoc.isclosed != 'Y' AND ppoc.processed != 'Y'))
  AND hua.valuedate IS NOT NULL
  AND childHu.m_hu_id IS NULL
;
