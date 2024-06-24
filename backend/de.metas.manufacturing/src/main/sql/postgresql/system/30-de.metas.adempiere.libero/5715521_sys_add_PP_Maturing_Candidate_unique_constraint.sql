CREATE UNIQUE INDEX pp_product_planning_maturing_uc
    ON pp_product_planning (ad_org_id, m_warehouse_id, m_maturing_configuration_line_id, m_product_id)
    WHERE (isactive = 'Y'::bpchar AND ismatured = 'Y')
;
